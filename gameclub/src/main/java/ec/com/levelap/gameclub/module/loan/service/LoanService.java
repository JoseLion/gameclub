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
import ec.com.levelap.gameclub.module.mail.service.MailService;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.service.MessageService;
import ec.com.levelap.gameclub.module.paymentez.service.PaymentezService;
import ec.com.levelap.gameclub.module.restore.entity.Restore;
import ec.com.levelap.gameclub.module.restore.service.RestoreService;
import ec.com.levelap.gameclub.module.settings.service.SettingService;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.service.TransactionService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Code;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.kushki.KushkiException;
import ec.com.levelap.kushki.service.KushkiService;
import ec.com.levelap.mail.MailParameters;
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
	private MailService mailService;

	@Autowired
	private LevelapCryptography cryptoService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private KushkiService kushkiService;
	
	@Autowired
	private PaymentezService paymentezService;
	
	@Value("${game-club.real-times}")
	private boolean realTimes;

	@Transactional
	public void requestGame(Loan loan, Double cost, Double balancePart, Double cardPart, Double shippingCost, Double feeGameClub, Double taxes)
			throws ServletException, MessagingException, IOException, GeneralSecurityException {
		Map<String, Message> messages = messageService.createLoanMessages(loan.getPublicUserGame().getPublicUser());
		PublicUser gamer = publicUserService.getCurrentUser();
		byte[] encrypted = null;
		byte[] keyEncript = gamer.getPrivateKey();
		loan.setPrivateKeyGamer(keyEncript);
		File key = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(key, loan.getPrivateKeyGamer());

		loan.setGamer(gamer);
		loan.setGamerMessage(messages.get(Const.GAMER));
		loan.setLenderMessage(messages.get(Const.LENDER));
		encrypted = cryptoService.encrypt(Double.toString(cost), key); 
		loan.setCostEnc(encrypted);
		encrypted = null;
		encrypted = cryptoService.encrypt(Double.toString(balancePart), key);
		loan.setBalancePartEnc(encrypted);
		encrypted = null;
		encrypted = cryptoService.encrypt(Double.toString(cardPart), key);
		loan.setCardPartEnc(encrypted);
		encrypted = null;
		encrypted = cryptoService.encrypt(Double.toString(shippingCost), key);
		loan.setShippningCostEnc(encrypted);
		encrypted = null;
		encrypted = cryptoService.encrypt(Double.toString(feeGameClub), key);
		loan.setFeeGameClubEnc(encrypted);
		encrypted = null;
		encrypted = cryptoService.encrypt(Double.toString(taxes), key);
		loan.setTaxesEnc(encrypted);
		encrypted = null;
		loan = loanRepo.save(loan);
		
		System.out.println(loan.getCost() + " " + loan.getShippningCost() + " " + loan.getFeeGameClub() + " " + loan.getTaxes());

		PublicUserGame cross = publicUserService.getPublicUserGameRepo().findOne(loan.getPublicUserGame().getId());
		loan.setPublicUserGame(cross);
		MailParameters mailParameters = new MailParameters();
		mailParameters.setRecipentTO(Arrays.asList(loan.getPublicUserGame().getPublicUser().getUsername()));
		Map<String, String> params = new HashMap<>();
		params.put("user", loan.getGamer().getName() + " " + loan.getGamer().getLastName().substring(0, 1) + ".");
		params.put("game", loan.getPublicUserGame().getGame().getName());
		params.put("console", loan.getPublicUserGame().getConsole().getName());
		params.put("cost", "" + loan.getPublicUserGame().getCost());

		mailService.sendMailWihTemplate(mailParameters, "MSGREQ", params);
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
	public Loan confirmLoan(Loan loan, boolean isGamer, HttpSession session, HttpServletRequest request) throws ServletException, KushkiException, GeneralSecurityException, IOException, RestClientException, URISyntaxException {
		Catalog noTracking = catalogService.getCatalogRepo().findByCode(Code.SHIPPING_NO_TRACKING);
		loan.setShippingStatus(noTracking);

		PublicUser gamer = publicUserService.getPublicUserRepo().findOne(loan.getGamer().getId());
		PublicUser connected = publicUserService.getCurrentUser();
		File keyGamer = File.createTempFile("keyGamer", ".tmp");
		FileUtils.writeByteArrayToFile(keyGamer, gamer.getPrivateKey());

		loan.setCostEnc(cryptoService.encrypt(Double.toString(loan.getCost()), keyGamer));
		loan.setBalancePartEnc(cryptoService.encrypt(Double.toString(loan.getBalancePart()), keyGamer));
		loan.setCardPartEnc(cryptoService.encrypt(Double.toString(loan.getCardPart()), keyGamer));

		if (isGamer) {
			loan.setGamerConfirmed(Boolean.TRUE);
			loan.setGamerStatusDate(new Date());

			Double totalToSubstract = gamer.getShownBalance() - loan.getBalancePart();
			Double totalToCard = loan.getCardPart();
			if (totalToSubstract < 0) {
				totalToCard += Math.abs(totalToSubstract);
				connected = publicUserService.setUserBalance(connected.getId(), 0D);
				loan.setBalancePartEnc(cryptoService.encrypt(Double.toString(gamer.getShownBalance()), keyGamer));
				loan.setCardPartEnc(cryptoService.encrypt(Double.toString(totalToCard), keyGamer));
			} else {
				connected = publicUserService.substractFromUserBalance(connected.getId(), loan.getBalancePart());
			}
			if (totalToCard > 0) {
				String description = "Préstamo del juego " + loan.getPublicUserGame().getGame().getName() + " durante " + loan.getWeeks() + " semana(s)";
				Map<String, String> response = paymentezService.debitFromCard(session, request.getRemoteAddr(), loan.getCardReference(), totalToCard, 0.0, description);
				System.out.println("DEBIT RESPONSE: " + response);
				
				/*Map<String, Object> kushkiSubscription = new HashMap<>();
				kushkiSubscription.put("amount", totalToCard);
				try {
					kushkiService.subscriptionCharge(loan.getPayment().getSubscriptionId(), kushkiSubscription);
				} catch (KushkiException ex) {
					throw new KushkiException(ex);
				}*/
			}

			Transaction transaction = new Transaction(connected, "JUGASTE",
					loan.getPublicUserGame().getGame().getName(), loan.getWeeks(), null, loan.getBalancePartEnc(),
					loan.getCardPartEnc());
			transactionService.getTransactionRepo().save(transaction);

		} else {
			loan.setLenderConfirmed(Boolean.TRUE);
			loan.setLenderStatusDate(new Date());
			connected = publicUserService.addToUserBalance(connected.getId(), loan.getPublicUserGame().getCost() * loan.getWeeks());
			File keyLender = File.createTempFile("keyGamer", ".tmp");
			FileUtils.writeByteArrayToFile(keyLender, connected.getPrivateKey());

			Transaction transaction = new Transaction(connected, "ALQUILASTE",
					loan.getPublicUserGame().getGame().getName(),
					loan.getWeeks(), cryptoService
							.encrypt(Double.toString(loan.getPublicUserGame().getCost() * loan.getWeeks()), keyLender),
					null, null);
			transactionService.getTransactionRepo().save(transaction);

		}
		loan = loanRepo.save(loan);
		
		return loan;
	}
	
	@Transactional
	public LoanLite save(Loan loan) throws ServletException, GeneralSecurityException, IOException, KushkiException {
		Loan previous = loanRepo.findOne(loan.getId());

		if (!loan.getShippingStatus().equals(previous.getShippingStatus()) || (loan.getShippingNote() != null
				&& !loan.getShippingNote().equalsIgnoreCase(previous.getShippingNote()))) {
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
				publicUserService.addToUserBalance(loan.getPublicUserGame().getPublicUser().getId(),
						loan.getPublicUserGame().getCost());
			}
			
			loan.setDeliveryDate(new Date());

			scheduleThreeDaysBefore(loan);
			scheduleOneDayBefore(loan);
			scheduleOnFinishDay(loan);
		} else {
			createFines(loan);
		}

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
				
				try {
					sendFinishedMails(restore);
				} catch (Exception e) {
					e.printStackTrace();
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
		MailParameters mailParameters = new MailParameters();
		Map<String, String> params = new HashMap<>();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		mailParameters.setRecipentTO(Arrays.asList(Const.SYSTEM_ADMIN_EMAIL));
		params.put("lender", loan.getPublicUserGame().getPublicUser().getName() + " "
				+ loan.getPublicUserGame().getPublicUser().getLastName());
		params.put("gamer", loan.getGamer().getName() + " " + loan.getGamer().getLastName());
		params.put("game", loan.getPublicUserGame().getGame().getName());
		params.put("console", loan.getPublicUserGame().getConsole().getName());
		params.put("returnDate", df.format(loan.getReturnDate()));
		params.put("days", isLastReminder ? "1" : "3");

		mailService.sendMailWihTemplate(mailParameters, "MSGARM", params);

		mailParameters.setRecipentTO(Arrays.asList(loan.getPublicUserGame().getPublicUser().getUsername()));
		mailService.sendMailWihTemplate(mailParameters, "MSGLRM", params);

		mailParameters.setRecipentTO(Arrays.asList(loan.getGamer().getUsername()));
		mailService.sendMailWihTemplate(mailParameters, "MSGGRM", params);
	}

	private void sendFinishedMails(Restore restore) throws Exception {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		MailParameters mailParameters = new MailParameters();
		Map<String, String> params = new HashMap<>();
		params.put("lender", restore.getPublicUserGame().getPublicUser().getName() + " "
				+ restore.getPublicUserGame().getPublicUser().getLastName());
		params.put("gamer", restore.getGamer().getName() + " " + restore.getGamer().getLastName());
		params.put("game", restore.getPublicUserGame().getGame().getName());
		params.put("console", restore.getPublicUserGame().getConsole().getName());
		params.put("returnDate", df.format(new Date()));
		params.put("lenderConfirmationDate",
				restore.getLenderStatusDate() != null ? df.format(restore.getLenderStatusDate()) : "SIN CONFIRMAR");
		params.put("gamerConfirmationDate",
				restore.getGamerStatusDate() != null ? df.format(restore.getGamerStatusDate()) : "SIN CONFIRMAR");

		if (restore.getLenderStatusDate() == null || restore.getGamerStatusDate() == null) {
			mailParameters.setRecipentTO(Arrays.asList(Const.SYSTEM_ADMIN_EMAIL));
			mailService.sendMailWihTemplate(mailParameters, "MSGAUR", params);
		}

		if (restore.getLenderStatusDate() == null) {
			mailParameters.setRecipentTO(Arrays.asList(Const.SYSTEM_ADMIN_EMAIL));
			mailService.sendMailWihTemplate(mailParameters, "MSGLUR", params);
		}

		if (restore.getGamerStatusDate() == null) {
			mailParameters.setRecipentTO(Arrays.asList(Const.SYSTEM_ADMIN_EMAIL));
			mailService.sendMailWihTemplate(mailParameters, "MSGGUR", params);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	private void createFines(Loan loan) throws ServletException, GeneralSecurityException, IOException, KushkiException {
		PublicUser gamer = publicUserService.getPublicUserRepo().findOne(loan.getGamer().getId());
		File keyGamer = File.createTempFile("keyGamer", ".tmp");
		FileUtils.writeByteArrayToFile(keyGamer, gamer.getPrivateKey());

		PublicUser lender = publicUserService.getPublicUserRepo().findOne(loan.getPublicUserGame().getPublicUser().getId());
		File keyLender = File.createTempFile("keyLender", ".tmp");
		FileUtils.writeByteArrayToFile(keyLender, lender.getPrivateKey());

		Transaction transaction;

		Catalog shippingStatus = catalogService.getCatalogRepo().findByCode(loan.getShippingStatus().getCode());

		Double totalToDebit = loan.getPublicUserGame().getCost() * loan.getWeeks();
		if (shippingStatus.getCode().equals(Code.SHIPPING_LENDER_DIDNT_DELIVER)) {
			gamer = publicUserService.addToUserBalance(gamer.getId(), (loan.getCost()));

			Double totalBalanceLender = lender.getShownBalance() - totalToDebit;
			byte[] toBalance = null;
			byte[] toCard = null;
			if (totalBalanceLender < 0) {
				lender = publicUserService.setUserBalance(lender.getId(), 0D);
				Map<String, Object> kushkiSubscription = new HashMap<>();
				kushkiSubscription.put("amount", (Double) Math.abs(totalBalanceLender));
				try {
					kushkiService.subscriptionCharge(loan.getPayment().getSubscriptionId(), kushkiSubscription);
				} catch (KushkiException ex) {
					throw new KushkiException(ex);
				}
				toBalance = cryptoService.encrypt(Double.toString(lender.getShownBalance()), keyLender);
				toCard = cryptoService.encrypt(Double.toString(Math.abs(totalBalanceLender)), keyLender);
			} else {
				lender = publicUserService.substractFromUserBalance(lender.getId(), totalToDebit);
				toBalance = cryptoService.encrypt(Double.toString(totalToDebit), keyLender);
			}

			loan.getPublicUserGame().setIsBorrowed(Boolean.FALSE);
			loan.setPublicUserGame(publicUserService.getPublicUserGameRepo().save(loan.getPublicUserGame()));

			Double shippingCost = loan.getCost() - (loan.getPublicUserGame().getCost() * loan.getWeeks());

			Fine fine = new Fine();
			fine.setOwner(loan.getPublicUserGame().getPublicUser());
			fine.setAmountEnc(cryptoService.encrypt(Double.toString(shippingCost), keyLender));
			fine.setDescription(shippingStatus.getName());
			fineService.getFineRepo().save(fine);

			transaction = new Transaction(gamer, "DEVOLUCION", loan.getPublicUserGame().getGame().getName(),
					loan.getWeeks(), cryptoService.encrypt(Double.toString((loan.getCost())), keyGamer), null, null);
			transactionService.getTransactionRepo().save(transaction);

			transaction = new Transaction(lender, "DEVOLUCION", loan.getPublicUserGame().getGame().getName(),
					loan.getWeeks(), null, toBalance, toCard);
			transactionService.getTransactionRepo().save(transaction);

		} else if (shippingStatus.getCode().equals(Code.SHIPPING_GAMER_DIDNT_RECEIVE)) {
			Double value = Double.valueOf(settingService.getSettingValue(Code.SETTING_GAMER_DIDNT_RECEIVE));
			gamer = publicUserService.addToUserBalance(loan.getGamer().getId(), loan.getCost() - value);
			lender = publicUserService.addToUserBalance(loan.getPublicUserGame().getPublicUser().getId(), value);

			transaction = new Transaction(gamer, "DEVOLUCION", loan.getPublicUserGame().getGame().getName(),
					loan.getWeeks(), cryptoService.encrypt(Double.toString(loan.getCost()), keyGamer), null, null);
			transactionService.getTransactionRepo().save(transaction);

			transaction = new Transaction(gamer, "MULTA - " + shippingStatus.getName(),
					loan.getPublicUserGame().getGame().getName(), loan.getWeeks(), null,
					cryptoService.encrypt(Double.toString(value), keyGamer), null);
			transactionService.getTransactionRepo().save(transaction);

			Double totalBalanceLender = lender.getShownBalance() - totalToDebit;
			byte[] toBalance = null;
			byte[] toCard = null;
			if (totalBalanceLender < 0) {
				lender = publicUserService.setUserBalance(lender.getId(), 0D);
				Map<String, Object> kushkiSubscription = new HashMap<>();
				kushkiSubscription.put("amount", (Double) Math.abs(totalBalanceLender));
				try {
					kushkiService.subscriptionCharge(loan.getPayment().getSubscriptionId(), kushkiSubscription);
				} catch (KushkiException ex) {
					throw new KushkiException(ex);
				}
				toBalance = cryptoService.encrypt(Double.toString(lender.getShownBalance()), keyLender);
				toCard = cryptoService.encrypt(Double.toString(Math.abs(totalBalanceLender)), keyLender);
			} else {
				lender = publicUserService.substractFromUserBalance(lender.getId(), totalToDebit);
				toBalance = cryptoService.encrypt(Double.toString(Math.abs(totalToDebit)), keyLender);
			}
			transaction = new Transaction(lender, "DEVOLUCION", loan.getPublicUserGame().getGame().getName(),
					loan.getWeeks(), null, toBalance, toCard);
			transactionService.getTransactionRepo().save(transaction);

			transaction = new Transaction(lender, "DEVOLUCION", loan.getPublicUserGame().getGame().getName(),
					loan.getWeeks(), cryptoService.encrypt(Double.toString(value), keyLender), null, null);
			transactionService.getTransactionRepo().save(transaction);

		}
	}

	public LoanRepo getLoanRepo() {
		return loanRepo;
	}
}
