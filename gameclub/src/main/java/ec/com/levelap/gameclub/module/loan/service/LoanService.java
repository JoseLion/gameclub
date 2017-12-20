package ec.com.levelap.gameclub.module.loan.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.catalog.CatalogService;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.application.ApplicationContextHolder;
import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.fine.service.FineService;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.entity.LoanLite;
import ec.com.levelap.gameclub.module.loan.repository.LoanRepo;
import ec.com.levelap.gameclub.module.mail.service.GameClubMailService;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.service.MessageService;
import ec.com.levelap.gameclub.module.paymentez.service.PaymentezService;
import ec.com.levelap.gameclub.module.restore.entity.Restore;
import ec.com.levelap.gameclub.module.restore.service.RestoreService;
import ec.com.levelap.gameclub.module.settings.entity.Setting;
import ec.com.levelap.gameclub.module.settings.service.SettingService;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.service.TransactionService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Code;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.mail.entity.LevelapMail;
import ec.com.levelap.taskScheduler.LevelapTaskScheduler;

@Service
public class LoanService {

	@Autowired
	private LoanRepo loanRepo;

	@Autowired
	private MessageService messageService;

	@Autowired
	private PublicUserService publicUserService;

	@Autowired
	private CatalogService catalogService;

	@Autowired
	private RestoreService restoreService;

	@Autowired
	private FineService fineService;

	@Autowired
	private SettingService settingService;

	@Autowired
	private LevelapTaskScheduler levelapTaskScheduler;

	@Autowired
	private GameClubMailService mailService;

	@Autowired
	private LevelapCryptography cryptoService;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private PaymentezService paymentezService;
	
	@Value("${game-club.real-times}")
	private boolean realTimes;

	@Transactional
	public void requestGame(Loan loan, Double cost, Double balancePart, Double cardPart, Double shippingCost, Double feeGameClub, Double taxes) throws ServletException, MessagingException, IOException, GeneralSecurityException {
		Map<String, Message> messages = messageService.createLoanMessages(loan.getPublicUserGame().getPublicUser());
		PublicUser gamer = publicUserService.getCurrentUser();
		byte[] keyEncript = gamer.getPrivateKey();
		loan.setPrivateKeyGamer(keyEncript);
		File key = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(key, loan.getPrivateKeyGamer());

		loan.setGamer(gamer);
		loan.setGamerMessage(messages.get(Const.GAMER));
		loan.setLenderMessage(messages.get(Const.LENDER));
		loan.setCostEnc(cryptoService.encrypt(Double.toString(cost), key));
		loan.setBalancePartEnc(cryptoService.encrypt(Double.toString(balancePart), key));
		loan.setCardPartEnc(cryptoService.encrypt(Double.toString(cardPart), key));
		loan.setShippningCostEnc(cryptoService.encrypt(Double.toString(shippingCost), key));
		loan.setFeeGameClubEnc(cryptoService.encrypt(Double.toString(feeGameClub), key));
		loan.setTaxesEnc(cryptoService.encrypt(Double.toString(taxes), key));
		
		loan = loanRepo.save(loan);

		PublicUserGame cross = publicUserService.getPublicUserGameRepo().findOne(loan.getPublicUserGame().getId());
		Setting feeLender = settingService.getSettingsRepo().findByCode(Code.SETTING_FEE_LENDER);
		loan.setPublicUserGame(cross);
		LevelapMail levelapMail = new LevelapMail();
		levelapMail.setRecipentTO(Arrays.asList(loan.getPublicUserGame().getPublicUser().getUsername()));
		
		Double subtotal = loan.getPublicUserGame().getCost() * loan.getWeeks();
		Double fee = Double.parseDouble(feeLender.getValue()) / 100.0;
		Map<String, String> params = new HashMap<>();
		params.put("name", loan.getPublicUserGame().getPublicUser().getName());
		params.put("user", loan.getGamer().getName() + " " + loan.getGamer().getLastName().substring(0, 1) + ".");
		params.put("game", loan.getPublicUserGame().getGame().getName());
		params.put("console", loan.getPublicUserGame().getConsole().getName());
		params.put("weeks", "" + loan.getWeeks());
		params.put("cost", "$" + String.format("" + (subtotal * (1.0 - fee)), "%.2f"));

		mailService.sendMailWihTemplate(levelapMail, "MSGREQ", params);
	}

	@Transactional
	public Loan acceptOrRejectLoan(Long id, boolean wasAccepted) {
		Loan loan = loanRepo.findOne(id);
		loan.setWasAccepted(wasAccepted);

		if (wasAccepted) {
			loan.setAcceptedDate(new Date());
			PublicUserGame publicUserGame = loan.getPublicUserGame();
			publicUserGame.setIsBorrowed(Boolean.TRUE);
			publicUserGame = publicUserService.getPublicUserGameRepo().save(publicUserGame);
			loan.setPublicUserGame(publicUserGame);
		}

		loan = loanRepo.save(loan);

		loan.getGamerMessage().setRead(false);
		messageService.getMessageRepo().save(loan.getGamerMessage());

		return loan;
	}
	
	@Transactional
	public Loan confirmLoan(Loan loan, boolean isGamer, HttpSession session, HttpServletRequest request) throws ServletException, IOException, GeneralSecurityException, RestClientException, URISyntaxException, JSONException {
		Catalog noTracking = catalogService.getCatalogRepo().findByCode(Code.SHIPPING_NO_TRACKING);
		loan.setShippingStatus(noTracking);

		PublicUser gamer = publicUserService.getPublicUserRepo().findOne(loan.getGamer().getId());
		PublicUser currentUser = publicUserService.getCurrentUser();
		File keyGamer = File.createTempFile("keyGamer", ".tmp");
		FileUtils.writeByteArrayToFile(keyGamer, gamer.getPrivateKey());

		loan.setCostEnc(cryptoService.encrypt(Double.toString(loan.getCost()), keyGamer));
		loan.setBalancePartEnc(cryptoService.encrypt(Double.toString(loan.getBalancePart()), keyGamer));
		loan.setCardPartEnc(cryptoService.encrypt(Double.toString(loan.getCardPart()), keyGamer));
		
		loan.setShippningCostEnc(cryptoService.encrypt(Double.toString(loan.getShippningCost()), keyGamer));
		loan.setFeeGameClubEnc(cryptoService.encrypt(Double.toString(loan.getFeeGameClub()), keyGamer));
		loan.setTaxesEnc(cryptoService.encrypt(Double.toString(loan.getTaxes()), keyGamer));
		

		if (isGamer) {
			loan.setGamerConfirmed(Boolean.TRUE);
			loan.setGamerStatusDate(new Date());

			Double totalToSubstract = gamer.getShownBalance() - loan.getBalancePart();
			Double totalToCard = loan.getCardPart();
			if (totalToSubstract < 0) {
				totalToCard += Math.abs(totalToSubstract);
				currentUser = publicUserService.setUserBalance(currentUser.getId(), 0D);
				loan.setBalancePartEnc(cryptoService.encrypt(Double.toString(gamer.getShownBalance()), keyGamer));
				loan.setCardPartEnc(cryptoService.encrypt(Double.toString(totalToCard), keyGamer));
			} else {
				currentUser = publicUserService.substractFromUserBalance(currentUser.getId(), loan.getBalancePart());
			}
			
			if (totalToCard > 0) {
				String description = "Préstamo del juego " + loan.getPublicUserGame().getGame().getName() + " durante " + loan.getWeeks() + " semana(s)";
				String response = paymentezService.debitFromCard(session, request.getRemoteAddr(), loan.getCardReference(), totalToCard, loan.getTaxes(), description);
				JSONObject json = new JSONObject(response);
				loan.setTransactionId(json.getString("transaction_id"));
			}

			Transaction transaction = new Transaction(currentUser, "JUGASTE", loan.getPublicUserGame().getGame().getName(), loan.getPublicUserGame().getConsole().getName(), loan.getWeeks(), null, loan.getBalancePartEnc(), loan.getCardPartEnc());
			transactionService.getTransactionRepo().save(transaction);
		} else {
			loan.setLenderConfirmed(Boolean.TRUE);
			loan.setLenderStatusDate(new Date());
		}
		
		if (loan.getGamerConfirmed() && loan.getLenderConfirmed()) {
			PublicUser lender = publicUserService.getPublicUserRepo().findOne(loan.getPublicUserGame().getPublicUser().getId());
			Double subtotal = loan.getPublicUserGame().getCost() * loan.getWeeks();
			Double fee = Double.parseDouble(settingService.getSettingValue(Code.SETTING_FEE_LENDER)) / 100.0;
			lender = publicUserService.addToUserBalance(lender.getId(), subtotal * (1.0 - fee));
			
			File keyLender = File.createTempFile("keyGamer", ".tmp");
			FileUtils.writeByteArrayToFile(keyLender, lender.getPrivateKey());
			Transaction transaction = new Transaction(lender, "ALQUILASTE", loan.getPublicUserGame().getGame().getName(), loan.getPublicUserGame().getConsole().getName(), loan.getWeeks(), cryptoService.encrypt(Double.toString(subtotal * (1.0 - fee)), keyLender), null, null);
			transactionService.getTransactionRepo().save(transaction);
		}
		
		loan = loanRepo.save(loan);
		return loan;
	}
	
	@Transactional
	public LoanLite save(Loan loan, HttpSession session, HttpServletRequest request) throws ServletException, GeneralSecurityException, IOException, RestClientException, URISyntaxException {
		Loan previous = loanRepo.findOne(loan.getId());
		byte[] keyEncript = previous.getGamer().getPrivateKey();

		if (!loan.getShippingStatus().equals(previous.getShippingStatus()) || (loan.getShippingNote() != null && !loan.getShippingNote().equalsIgnoreCase(previous.getShippingNote()))) {
			loan.setLenderStatusDate(new Date());
			loan.setGamerStatusDate(new Date());

			loan.getLenderMessage().setRead(false);
			loan.getGamerMessage().setRead(false);
			messageService.getMessageRepo().save(loan.getLenderMessage());
			messageService.getMessageRepo().save(loan.getGamerMessage());
		}

		if (loan.getShippingStatus().getCode().equals(Code.SHIPPING_DELIVERED)) {		
			if (previous.getShippingStatus().getCode().equals(Code.SHIPPING_LENDER_DIDNT_DELIVER)
					|| previous.getShippingStatus().getCode().equals(Code.SHIPPING_GAMER_DIDNT_RECEIVE)
					|| previous.getShippingStatus().getCode().equals(Code.SHIPPING_GAMER_DIDNT_DELIVER)
					|| previous.getShippingStatus().getCode().equals(Code.SHIPPING_GAMER_DIDNT_DELIVER_2ND)) {
				publicUserService.substractFromUserBalance(loan.getGamer().getId(), loan.getBalancePart());
			}
			
			loan.setDeliveryDate(new Date());
			scheduleThreeDaysBefore(loan);
			scheduleOneDayBefore(loan);
			scheduleOnFinishDay(loan);
		} else {
			createFines(loan, session, request);
		}
		
		File keyGamer = File.createTempFile("keyGamer", ".tmp");
		FileUtils.writeByteArrayToFile(keyGamer, keyEncript);

		loan.setCostEnc(cryptoService.encrypt(Double.toString(loan.getCost()), keyGamer));
		loan.setBalancePartEnc(cryptoService.encrypt(Double.toString(loan.getBalancePart()), keyGamer));
		loan.setCardPartEnc(cryptoService.encrypt(Double.toString(loan.getCardPart()), keyGamer));
		
		loan.setShippningCostEnc(cryptoService.encrypt(Double.toString(loan.getShippningCost()), keyGamer));
		loan.setFeeGameClubEnc(cryptoService.encrypt(Double.toString(loan.getFeeGameClub()), keyGamer));
		loan.setTaxesEnc(cryptoService.encrypt(Double.toString(loan.getTaxes()), keyGamer));

		loan = loanRepo.save(loan);
		LoanLite loanLite = loanRepo.findById(loan.getId());

		return loanLite;
	}
	
	@Transactional
	private void scheduleThreeDaysBefore(final Loan loan) {
		Calendar calendar = Calendar.getInstance();
		
		if (realTimes) {
			calendar.setTime(loan.getReturnDate());
			calendar.add(Calendar.DATE, -3);
		} else {
			calendar.setTime(loan.getDeliveryDate());
			calendar.add(Calendar.MINUTE, 1);
		}

		levelapTaskScheduler.scheduleTaskAtDate(calendar.getTime(), Loan.class.getSimpleName() + "-R1-" + loan.getId(), new Runnable() {
			@Override
			public void run() {
				Catalog noTracking = catalogService.getCatalogRepo().findByCode(Code.SHIPPING_NO_TRACKING);
				Restore restore = new Restore(loan);
				loan.getLenderMessage().setRead(Boolean.FALSE);
				restore.setLenderMessage(loan.getLenderMessage());
				loan.getGamerMessage().setRead(Boolean.FALSE);
				restore.setGamerMessage(loan.getGamerMessage());
				restore.setPublicUserGame(loan.getPublicUserGame());
				restore.setGamer(loan.getGamer());
				restore.setShippingStatus(noTracking);

				restoreService.getRestoreRepo().save(restore);
				messageService.getMessageRepo().save(restore.getLoan().getGamerMessage());
				messageService.getMessageRepo().save(restore.getLoan().getLenderMessage());

				try {
					sendRemindingMails(loan, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Transactional
	private void scheduleOneDayBefore(final Loan loan) {
		Calendar calendar = Calendar.getInstance();
		
		if (realTimes) {
			calendar.setTime(loan.getReturnDate());
			calendar.add(Calendar.DATE, -1);
		} else {
			calendar.setTime(loan.getDeliveryDate());
			calendar.add(Calendar.MINUTE, 2);
		}

		levelapTaskScheduler.scheduleTaskAtDate(calendar.getTime(), Loan.class.getSimpleName() + "-R2-" + loan.getId(), new Runnable() {
			@Override
			public void run() {
				try {
					sendRemindingMails(loan, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Transactional
	private void scheduleOnFinishDay(final Loan loan) {
		levelapTaskScheduler.scheduleTaskAtDate(loan.getReturnDate(), Loan.class.getSimpleName() + "-R3-" + loan.getId(), new Runnable() {
			@Override
			public void run() {
				Restore restore = restoreService.getRestoreRepo().findByLoan(loan);
				
				restore.getLoan().getGamerMessage().setRead(false);
				restore.getLoan().getLenderMessage().setRead(false);

				messageService.getMessageRepo().save(restore.getLoan().getGamerMessage());
				messageService.getMessageRepo().save(restore.getLoan().getLenderMessage());
				
				try {
					sendFinishedMails(restore);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (!restore.getGamerConfirmed().booleanValue()) {
					restore.setGamerAddress(loan.getGamerAddress());
					restore.setGamerGeolocation(loan.getGamerGeolocation());
					restore.setGamerReceiver(loan.getGamerReceiver());
					restore.setGamerConfirmDate(new Date());
					restore = restoreService.getRestoreRepo().save(restore);
				}
				
				if (!restore.getLenderConfirmed().booleanValue()) {
					restore.setLenderAddress(loan.getLenderAddress());
					restore.setLenderGeolocation(loan.getLenderGeolocation());
					restore.setLenderReceiver(loan.getLenderReceiver());
					restore.setLenderConfirmDate(new Date());
					restore = restoreService.getRestoreRepo().save(restore);
				}
			}
		});
	}

	@Transactional
	public void rescheduleTasks() {
		if (loanRepo == null) {
			loanRepo = ApplicationContextHolder.getContext().getBean(LoanRepo.class);
		}
		
		if (restoreService == null) {
			restoreService = ApplicationContextHolder.getContext().getBean(RestoreService.class);
		}
		
		if (catalogService == null) {
			catalogService = ApplicationContextHolder.getContext().getBean(CatalogService.class);
		}
		
		if (messageService == null) {
			messageService = ApplicationContextHolder.getContext().getBean(MessageService.class);
		}
		
		
		List<Loan> loans = loanRepo.findByShippingStatusCode(Code.SHIPPING_DELIVERED);
		Date today = new Date();
		Calendar threeDays = Calendar.getInstance();
		Calendar oneDay = Calendar.getInstance();

		for (Loan loan : loans) {
			if (realTimes) {
				threeDays.setTime(loan.getReturnDate());
				threeDays.add(Calendar.DATE, -3);
				oneDay.setTime(loan.getReturnDate());
				oneDay.add(Calendar.DATE, -1);
			} else {
				threeDays.setTime(loan.getDeliveryDate());
				threeDays.add(Calendar.MINUTE, 1);
				oneDay.setTime(loan.getDeliveryDate());
				oneDay.add(Calendar.MINUTE, 2);
			}

			if (today.before(threeDays.getTime())) {
				scheduleThreeDaysBefore(loan);
				scheduleOneDayBefore(loan);
				scheduleOnFinishDay(loan);
				return;
			}

			if (today.before(oneDay.getTime()) && today.after(threeDays.getTime())) {
				scheduleOneDayBefore(loan);
				scheduleOnFinishDay(loan);
				return;
			}

			if (today.before(loan.getReturnDate()) && today.after(oneDay.getTime())) {
				scheduleOnFinishDay(loan);
				return;
			}
		}
	}

	private void sendRemindingMails(Loan loan, boolean isLastReminder) throws Exception {
		LevelapMail levelapMail = new LevelapMail();
		Map<String, String> params = new HashMap<>();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		levelapMail.setRecipentTO(Arrays.asList(Const.EMAIL_LOGISTICS));
		params.put("lender", loan.getPublicUserGame().getPublicUser().getName() + " "
				+ loan.getPublicUserGame().getPublicUser().getLastName());
		params.put("gamer", loan.getGamer().getName() + " " + loan.getGamer().getLastName());
		params.put("game", loan.getPublicUserGame().getGame().getName());
		params.put("console", loan.getPublicUserGame().getConsole().getName());
		params.put("returnDate", df.format(loan.getReturnDate()));
		params.put("days", isLastReminder ? "1" : "3");

		mailService.sendMailWihTemplate(levelapMail, "MSGARM", params);

		levelapMail.setRecipentTO(Arrays.asList(loan.getPublicUserGame().getPublicUser().getUsername()));
		mailService.sendMailWihTemplate(levelapMail, "MSGLRM", params);

		levelapMail.setRecipentTO(Arrays.asList(loan.getGamer().getUsername()));
		mailService.sendMailWihTemplate(levelapMail, "MSGGRM", params);
	}

	private void sendFinishedMails(Restore restore) throws Exception {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		LevelapMail levelapMail = new LevelapMail();
		Map<String, String> params = new HashMap<>();
		params.put("lender", restore.getPublicUserGame().getPublicUser().getName() + " "
				+ restore.getPublicUserGame().getPublicUser().getLastName());
		params.put("gamer", restore.getGamer().getName() + " " + restore.getGamer().getLastName());
		params.put("game", restore.getPublicUserGame().getGame().getName());
		params.put("console", restore.getPublicUserGame().getConsole().getName());
		params.put("returnDate", df.format(new Date()));
		params.put("lenderConfirmationDate", restore.getLenderStatusDate() != null ? df.format(restore.getLenderStatusDate()) : "SIN CONFIRMAR");
		params.put("gamerConfirmationDate", restore.getGamerStatusDate() != null ? df.format(restore.getGamerStatusDate()) : "SIN CONFIRMAR");

		if (restore.getLenderStatusDate() == null || restore.getGamerStatusDate() == null) {
			levelapMail.setRecipentTO(Arrays.asList(Const.EMAIL_LOGISTICS));
			mailService.sendMailWihTemplate(levelapMail, "MSGAUR", params);
		}

		if (restore.getLenderStatusDate() == null) {
			levelapMail.setRecipentTO(Arrays.asList(restore.getPublicUserGame().getPublicUser().getUsername()));
			mailService.sendMailWihTemplate(levelapMail, "MSGLUR", params);
		}

		if (restore.getGamerStatusDate() == null) {
			levelapMail.setRecipentTO(Arrays.asList(restore.getGamer().getUsername()));
			mailService.sendMailWihTemplate(levelapMail, "MSGGUR", params);
		}
	}
	
	@Transactional
	private void createFines(Loan loan, HttpSession session, HttpServletRequest request) throws ServletException, IOException, GeneralSecurityException, RestClientException, URISyntaxException {
		PublicUser gamer = publicUserService.getPublicUserRepo().findOne(loan.getGamer().getId());
		File keyGamer = File.createTempFile("keyGamer", ".tmp");
		FileUtils.writeByteArrayToFile(keyGamer, gamer.getPrivateKey());

		PublicUser lender = publicUserService.getPublicUserRepo().findOne(loan.getPublicUserGame().getPublicUser().getId());
		File keyLender = File.createTempFile("keyLender", ".tmp");
		FileUtils.writeByteArrayToFile(keyLender, lender.getPrivateKey());

		Catalog shippingStatus = catalogService.getCatalogRepo().findByCode(loan.getShippingStatus().getCode());
		//Catalog shippingStatus = loan.getShippingStatus();
		Double totalToDebit = loan.getPublicUserGame().getCost() * loan.getWeeks();
		Double subtotal = (loan.getPublicUserGame().getCost() * loan.getWeeks()) + loan.getShippningCost() + loan.getFeeGameClub();
		System.out.println("SUBTOTAL: " + subtotal);
		
		if (shippingStatus.getCode().equals(Code.SHIPPING_LENDER_DIDNT_DELIVER)) {
			Setting fineSetting = settingService.getSettingsRepo().findByCode(Code.SETTING_LENDER_DIDNT_DELIVER);
			Double fineAmount;
			
			if (fineSetting.getType().equals(Const.SETTINGS_PERCENTAGE)) {
				fineAmount = subtotal * (Double.parseDouble(fineSetting.getValue()) / 100.0);
			} else {
				fineAmount = Double.parseDouble(fineSetting.getValue());
			}
			
			Fine fine = new Fine();
			fine.setOwner(loan.getPublicUserGame().getPublicUser());
			fine.setAmountEnc(cryptoService.encrypt(Double.toString(fineAmount), keyLender));
			fine.setDescription(loan.getShippingStatus().getName());
			fineService.getFineRepo().save(fine);
			
			publicUserService.addToUserBalance(gamer.getId(), (loan.getCost()));
			loan.getPublicUserGame().setIsBorrowed(Boolean.FALSE);
			loan.setPublicUserGame(publicUserService.getPublicUserGameRepo().save(loan.getPublicUserGame()));

			Transaction transaction = new Transaction();
			transaction.setOwner(gamer);
			transaction.setTransaction("DEVOLUCIÓN");
			transaction.setGame(loan.getPublicUserGame().getGame().getName());
			transaction.setConsole(loan.getPublicUserGame().getConsole().getName());
			transaction.setWeeks(loan.getWeeks());
			transaction.setBalancePartEnc(cryptoService.encrypt(Double.toString(loan.getCost()), keyGamer));
			transactionService.getTransactionRepo().save(transaction);
		} else if (shippingStatus.getCode().equals(Code.SHIPPING_GAMER_DIDNT_RECEIVE)) {
			Double value = Double.valueOf(settingService.getSettingValue(Code.SETTING_GAMER_DIDNT_RECEIVE));
			gamer = publicUserService.addToUserBalance(loan.getGamer().getId(), loan.getCost() - value);
			lender = publicUserService.addToUserBalance(loan.getPublicUserGame().getPublicUser().getId(), value);

			Transaction transaction = new Transaction(
					gamer,
					"DEVOLUCIÓN",
					loan.getPublicUserGame().getGame().getName(),
					loan.getPublicUserGame().getConsole().getName(),
					loan.getWeeks(),
					cryptoService.encrypt(Double.toString(loan.getCost()), keyGamer),
					null,
					null);
			transactionService.getTransactionRepo().save(transaction);

			transaction = new Transaction(
					gamer,
					"MULTA - " + shippingStatus.getName(),
					loan.getPublicUserGame().getConsole().getName(),
					loan.getPublicUserGame().getGame().getName(), loan.getWeeks(),
					null,
					cryptoService.encrypt(Double.toString(value), keyGamer),
					null);
			transactionService.getTransactionRepo().save(transaction);

			Double totalBalanceLender = lender.getShownBalance() - totalToDebit;
			byte[] toBalance = null;
			byte[] toCard = null;
			if (totalBalanceLender < 0) {
				lender = publicUserService.setUserBalance(lender.getId(), 0D);
				String description = "Multa GameClub - " + shippingStatus.getName();
				paymentezService.debitFromCard(session, request.getRemoteAddr(), loan.getCardReference(), Math.abs(totalBalanceLender), 0.0, description);
				toBalance = cryptoService.encrypt(Double.toString(lender.getShownBalance()), keyLender);
				toCard = cryptoService.encrypt(Double.toString(Math.abs(totalBalanceLender)), keyLender);
			} else {
				lender = publicUserService.substractFromUserBalance(lender.getId(), totalToDebit);
				toBalance = cryptoService.encrypt(Double.toString(Math.abs(totalToDebit)), keyLender);
			}
			
			transaction = new Transaction(lender, "DEVOLUCION", loan.getPublicUserGame().getGame().getName(),
					loan.getPublicUserGame().getConsole().getName(),
					loan.getWeeks(), null, toBalance, toCard);
			transactionService.getTransactionRepo().save(transaction);

			transaction = new Transaction(lender, "DEVOLUCION", loan.getPublicUserGame().getGame().getName(),
					loan.getPublicUserGame().getConsole().getName(),
					loan.getWeeks(), cryptoService.encrypt(Double.toString(value), keyLender), null, null);
			transactionService.getTransactionRepo().save(transaction);

		}
	}

	public LoanRepo getLoanRepo() {
		return loanRepo;
	}
}
