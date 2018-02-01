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
import org.codehaus.jettison.json.JSONArray;
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
import ec.com.levelap.gameclub.utils.GameClubMailService;
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
	public void requestGame(Loan loan, Double cost, Double balancePart, Double cardPart, Double shippingCost,
			Double feeGameClub, Double taxes)
			throws ServletException, MessagingException, IOException, GeneralSecurityException {

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
		loan = loanRepo.save(loan);

		LevelapMail levelapMail = new LevelapMail();
		levelapMail.setFrom(Const.EMAIL_NOTIFICATIONS);
		levelapMail.setRecipentTO(Arrays.asList(loan.getPublicUserGame().getPublicUser().getUsername()));

		Double subtotal = loan.getPublicUserGame().getCost() * loan.getWeeks();
		Double fee = Double.parseDouble(feeLender.getValue()) / 100.0;
		Map<String, String> params = new HashMap<>();
		params.put("name", loan.getPublicUserGame().getPublicUser().getName());
		params.put("user", loan.getGamer().getName() + " " + loan.getGamer().getLastName().substring(0, 1) + ".");
		params.put("game", loan.getPublicUserGame().getGame().getName());
		params.put("console", loan.getPublicUserGame().getConsole().getName());
		params.put("weeks", "" + loan.getWeeks());
		params.put("cost", "$" + String.format("%.2f", (subtotal * (1.0 - fee))));
		mailService.sendMailWihTemplate(levelapMail, "MSPYCF", params);
	}

	@Transactional
	public Loan acceptOrRejectLoan(Long id, boolean wasAccepted) {

		Loan loan = loanRepo.findOne(id);
		loan.setWasAccepted(wasAccepted);

		if (wasAccepted) {
			loan.setAcceptedDate(new Date());

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());

			if (realTimes) {
				calendar.add(Calendar.HOUR, 2);
			} else {
				calendar.add(Calendar.MINUTE, 5);
			}

			final Long taskLoanId = loan.getId();
			levelapTaskScheduler.scheduleTaskAtDate(calendar.getTime(),
					Loan.class.getSimpleName() + "-W1-" + loan.getId(), new Runnable() {
						@Override
						public void run() {
							cancelLoanByTimeout(taskLoanId);
						}
					});
		}

		loan = loanRepo.save(loan);
		return loan;
	}

	@Transactional
	public Loan confirmLoan(Loan loan, boolean isGamer, HttpSession session, HttpServletRequest request)
			throws ServletException, IOException, GeneralSecurityException, RestClientException, URISyntaxException,
			JSONException, MessagingException {

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
			levelapTaskScheduler.removeAndCancelFutureTask(Loan.class.getSimpleName() + "-W2-" + loan.getId());
			loan.setGamerConfirmed(Boolean.TRUE);
			loan.setGamerStatusDate(new Date());

			Double promoBalance = gamer.getPromoBalance() != null
					? Double.parseDouble(cryptoService.decrypt(gamer.getPromoBalance(), keyGamer))
					: 0.0;

			if (loan.getBalancePart() > 0.0 && loan.getCardPart() == 0.0) {
				Double remaining = promoBalance - loan.getBalancePart();

				if (remaining < 0.0) {
					gamer = publicUserService.setUserPromoBalance(gamer.getId(), 0.0);
					gamer = publicUserService.substractFromUserBalance(gamer.getId(), Math.abs(remaining));
				} else {
					gamer = publicUserService.setUserPromoBalance(gamer.getId(), remaining);
				}
			} else if(loan.getCardPart() > 0.0){
				Double remaining = promoBalance - loan.getBalancePart();

				if (remaining < 0.0) {
					gamer = publicUserService.setUserPromoBalance(gamer.getId(), 0.0);
					gamer = publicUserService.substractFromUserBalance(gamer.getId(), (-1)*(remaining));
				} else {
					gamer = publicUserService.setUserPromoBalance(gamer.getId(), remaining);
				}
			}

			if (loan.getCardPart() > 0.0) {
				String description = "Préstamo del juego " +
										loan.getPublicUserGame().getGame().getName() + " durante " + loan.getWeeks()
										+ " semana(s)";
				String response = paymentezService.debitFromCard(session,
				request.getRemoteAddr(), loan.getCardReference(), loan.getCardPart(),
				loan.getTaxes(), description, gamer);
				JSONObject json = new JSONObject(response);
				loan.setTransactionId(json.getString("transaction_id"));
				LevelapMail levelapMail = new LevelapMail();
				levelapMail.setFrom(Const.EMAIL_NOTIFICATIONS);
				levelapMail.setRecipentTO(Arrays.asList(loan.getGamer().getUsername()));

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Map<String, String> params = new HashMap<>();
				params.put("name", loan.getGamer().getName());
				params.put("game", loan.getPublicUserGame().getGame().getName());
				params.put("console", loan.getPublicUserGame().getConsole().getName());
				params.put("user",
						loan.getGamer().getName() + " " + loan.getGamer().getLastName().substring(0, 1) + ".");
				params.put("weeks", "" + loan.getWeeks());
				if (loan.getWasAccepted()) {
					params.put("status", "aceptado");
				} else {
					params.put("status", "rechazado");
				}
				params.put("date", sdf.format(loan.getGamerStatusDate()));
				params.put("authorizationNumber", loan.getTransactionId());
				params.put("subtotal", "$" + String.format("%.2f", (loan.getCost() - loan.getTaxes())));
				params.put("iva", "$" + String.format("%.2f", loan.getTaxes()));
				params.put("total", "$" + String.format("%.2f", loan.getCost()));
				params.put("cardPart", "$" + String.format("%.2f", loan.getCardPart()));
				params.put("balancePart", "$" + String.format("%.2f", (loan.getCost() - loan.getCardPart())));

				mailService.sendMailWihTemplate(levelapMail, "MSGPYC", params);
			}

			loan.setAcceptedDate(new Date());
			PublicUserGame publicUserGame = loan.getPublicUserGame();
			publicUserGame.setIsBorrowed(true);
			publicUserGame = publicUserService.getPublicUserGameRepo().save(publicUserGame);
			loan.setPublicUserGame(publicUserGame);

			Transaction transaction = new Transaction(currentUser, "JUGASTE",
					loan.getPublicUserGame().getGame().getName(), loan.getPublicUserGame().getConsole().getName(),
					loan.getWeeks(), null, loan.getBalancePartEnc(), loan.getCardPartEnc());
			if(loan.getTransactionId() != null) {
				transaction.setCcTransaction(loan.getTransactionId());
				transaction.setStatusRefund("DEBITADO");
			}
			transactionService.getTransactionRepo().save(transaction);

			if (loan.getGamer().getReferrer() != null && !loan.getGamer().getReferrer().isEmpty()) {
				PublicUser refferer = publicUserService.getPublicUserRepo()
						.findByUrlToken(loan.getGamer().getReferrer());

				if (refferer != null) {
					Setting setting = settingService.getSettingsRepo().findByCode(Code.SETTING_REFFERED_REWARD);
					publicUserService.addToUserPromoBalance(refferer.getId(), Double.parseDouble(setting.getValue()));
					loan.setGamer(publicUserService.addToUserPromoBalance(loan.getGamer().getId(),
							Double.parseDouble(setting.getValue())));

					File userKey = File.createTempFile("userKey", "tmp");
					FileUtils.writeByteArrayToFile(userKey, loan.getGamer().getPrivateKey());
					File referrerKey = File.createTempFile("referrerKey", ".tmp");
					FileUtils.writeByteArrayToFile(referrerKey, refferer.getPrivateKey());
					Transaction reffererTransaction = new Transaction(refferer, Const.TRS_REFFERED_BONUS, null, null,
							null, cryptoService.encrypt(setting.getValue(), referrerKey), null, null);
					Transaction userTransaction = new Transaction(loan.getGamer(), Const.TRS_REFFERED_BONUS, null, null,
							null, cryptoService.encrypt(setting.getValue(), userKey), null, null);

					transactionService.getTransactionRepo().save(reffererTransaction);
					transactionService.getTransactionRepo().save(userTransaction);

					loan.getGamer().setReferrer(null);
					loan.setGamer(publicUserService.getPublicUserRepo().save(loan.getGamer()));
				}
			}
		} else {
			levelapTaskScheduler.removeAndCancelFutureTask(Loan.class.getSimpleName() + "-W1-" + loan.getId());

			loan.setLenderConfirmed(Boolean.TRUE);
			loan.setLenderStatusDate(new Date());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());

			if (realTimes) {
				calendar.add(Calendar.HOUR, 24);
			} else {
				calendar.add(Calendar.MINUTE, 5);
			}

			final Long taskLoanId = loan.getId();
			levelapTaskScheduler.scheduleTaskAtDate(calendar.getTime(),
					Loan.class.getSimpleName() + "-W2-" + loan.getId(), new Runnable() {
						@Override
						public void run() {
							cancelLoanByTimeout(taskLoanId);
						}
					});

			loan.getGamerMessage().setRead(false);
			messageService.getMessageRepo().save(loan.getGamerMessage());

			if (loan.getLenderConfirmed()) {
				try {
					sendRequestMails(loan);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		if (loan.getGamerConfirmed() && loan.getLenderConfirmed()) {
			PublicUser lender = publicUserService.getPublicUserRepo()
					.findOne(loan.getPublicUserGame().getPublicUser().getId());
			Double subtotal = loan.getPublicUserGame().getCost() * loan.getWeeks();
			Double fee = Double.parseDouble(settingService.getSettingValue(Code.SETTING_FEE_LENDER)) / 100.0;
			lender = publicUserService.addToUserBalance(lender.getId(), subtotal * (1.0 - fee));

			File keyLender = File.createTempFile("keyLender", ".tmp");
			FileUtils.writeByteArrayToFile(keyLender, lender.getPrivateKey());
			Transaction transaction = new Transaction(lender, "ALQUILASTE",
					loan.getPublicUserGame().getGame().getName(), loan.getPublicUserGame().getConsole().getName(),
					loan.getWeeks(), cryptoService.encrypt(Double.toString(subtotal * (1.0 - fee)), keyLender), null,
					null);
			transactionService.getTransactionRepo().save(transaction);
		}

		loan = loanRepo.save(loan);
		return loan;
	}

	@Transactional
	public LoanLite save(Loan loan, HttpSession session, HttpServletRequest request) throws ServletException,
			GeneralSecurityException, IOException, RestClientException, URISyntaxException, JSONException {
		Loan previous = loanRepo.findOne(loan.getId());
		byte[] keyEncript = previous.getGamer().getPrivateKey();
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

		levelapTaskScheduler.scheduleTaskAtDate(calendar.getTime(), Loan.class.getSimpleName() + "-R1-" + loan.getId(),
				new Runnable() {
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

		levelapTaskScheduler.scheduleTaskAtDate(calendar.getTime(), Loan.class.getSimpleName() + "-R2-" + loan.getId(),
				new Runnable() {
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
		levelapTaskScheduler.scheduleTaskAtDate(loan.getReturnDate(),
				Loan.class.getSimpleName() + "-R3-" + loan.getId(), new Runnable() {
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

		List<Loan> loans = loanRepo.findAll();
		Date today = new Date();
		Calendar threeDays = Calendar.getInstance();
		Calendar oneDay = Calendar.getInstance();

		for (Loan loan : loans) {
			if (loan.getShippingStatus() != null
					&& loan.getShippingStatus().getCode().equals(Code.SHIPPING_DELIVERED)) {
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

		levelapMail.setFrom(Const.EMAIL_REMINDER);
		mailService.sendMailWihTemplate(levelapMail, "MSGARM", params);

		levelapMail.setFrom(Const.EMAIL_NOTIFICATIONS);
		levelapMail.setRecipentTO(Arrays.asList(loan.getPublicUserGame().getPublicUser().getUsername()));
		mailService.sendMailWihTemplate(levelapMail, "MSGLRM", params);

		levelapMail.setFrom(Const.EMAIL_NOTIFICATIONS);
		levelapMail.setRecipentTO(Arrays.asList(loan.getGamer().getUsername()));
		mailService.sendMailWihTemplate(levelapMail, "MSGGRM", params);
	}

	private void sendRequestMails(Loan loan) throws Exception {
		LevelapMail levelapMail = new LevelapMail();
		Map<String, String> params = new HashMap<>();

		levelapMail.setFrom(Const.EMAIL_NOTIFICATIONS);
		levelapMail.setRecipentTO(Arrays.asList(loan.getGamer().getUsername()));

		params.put("lender", loan.getPublicUserGame().getPublicUser().getName() + " "
				+ loan.getPublicUserGame().getPublicUser().getLastName().toString().substring(0, 1).toUpperCase());
		params.put("name", loan.getGamer().getName());
		params.put("game", loan.getPublicUserGame().getGame().getName());
		params.put("console", loan.getPublicUserGame().getConsole().getName());
		params.put("weeks", "" + loan.getWeeks());

		mailService.sendMailWihTemplate(levelapMail, "REQACP", params);
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
		params.put("lenderConfirmationDate",
				restore.getLenderStatusDate() != null ? df.format(restore.getLenderStatusDate()) : "SIN CONFIRMAR");
		params.put("gamerConfirmationDate",
				restore.getGamerStatusDate() != null ? df.format(restore.getGamerStatusDate()) : "SIN CONFIRMAR");

		if (restore.getLenderReceiver() == null || restore.getGamerReceiver() == null) {
			levelapMail.setFrom(Const.EMAIL_REMINDER);
			levelapMail.setRecipentTO(Arrays.asList(Const.EMAIL_LOGISTICS));
			mailService.sendMailWihTemplate(levelapMail, "MSGAUR", params);
		}

		if (restore.getLenderReceiver() == null) {
			levelapMail.setFrom(Const.EMAIL_NOTIFICATIONS);
			levelapMail.setRecipentTO(Arrays.asList(restore.getPublicUserGame().getPublicUser().getUsername()));
			mailService.sendMailWihTemplate(levelapMail, "MSGLUR", params);
		}

		if (restore.getGamerReceiver() == null) {
			levelapMail.setFrom(Const.EMAIL_NOTIFICATIONS);
			levelapMail.setRecipentTO(Arrays.asList(restore.getGamer().getUsername()));
			mailService.sendMailWihTemplate(levelapMail, "MSGGUR", params);
		}
	}

	@Transactional
	private void createFines(Loan loan, HttpSession session, HttpServletRequest request) throws ServletException, IOException, GeneralSecurityException, RestClientException, URISyntaxException, JSONException {

		PublicUser gamer = publicUserService.getPublicUserRepo().findOne(loan.getGamer().getId());
		// byte[] keyEncriptGamer = gamer.getPrivateKey();
		File gamerKey = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(gamerKey, gamer.getPrivateKey());

		PublicUser lender = publicUserService.getPublicUserRepo()
				.findOne(loan.getPublicUserGame().getPublicUser().getId());
		// byte[] keyEncriptLender = lender.getPrivateKey();
		File lenderKey = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(lenderKey, lender.getPrivateKey());

		Double subtotal = (loan.getPublicUserGame().getCost() * loan.getWeeks()) + loan.getShippningCost()
				+ loan.getFeeGameClub();

		if (loan.getShippingStatus().getCode().equals(Code.SHIPPING_LENDER_DIDNT_DELIVER)) {
			Setting fineSetting = settingService.getSettingsRepo().findByCode(Code.SETTING_LENDER_DIDNT_DELIVER);
			Double fineAmount;

			if (fineSetting.getType().equals(Const.SETTINGS_PERCENTAGE)) {
				fineAmount = subtotal * (Double.parseDouble(fineSetting.getValue()) / 100.0);
			} else {
				fineAmount = loan.getCost() + Double.parseDouble(fineSetting.getValue());
			}

			Fine fine = new Fine();
			fine.setLoan(loan);
			fine.setOwner(loan.getPublicUserGame().getPublicUser());
			fine.setAmountEnc(cryptoService.encrypt(Double.toString(fineAmount), lenderKey));
			fine.setDescription(loan.getShippingStatus().getName());
			fineService.getFineRepo().save(fine);
			
			gamer = publicUserService.addToUserBalance(gamer.getId(), (loan.getCost()));
			

			PublicUserGame publicUserGame = loan.getPublicUserGame();
			publicUserGame.setIsBorrowed(false);

			Double fineCost = (loan.getPublicUserGame().getCost() * loan.getWeeks()) - loan.getFeeGameClub();
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, lender.getPrivateKey());

			byte[] encrypted = cryptoService.encrypt(Double.toString(lender.getShownBalance() - fineCost), key);
			lender.setBalance(encrypted);
			lender = publicUserService.getPublicUserRepo().save(lender);

			publicUserGame.setPublicUser(lender);
			publicUserGame = publicUserService.getPublicUserGameRepo().save(publicUserGame);

			Double devolucionGamer = loan.getCost();
			Transaction transactionGamer = new Transaction(gamer, "DEVOLUCIÓN",
					loan.getPublicUserGame().getGame().getName(), loan.getPublicUserGame().getConsole().getName(),
					loan.getWeeks(), (cryptoService.encrypt(Double.toString(devolucionGamer), gamerKey)), null, null);
			transactionService.getTransactionRepo().save(transactionGamer);

			Double devoluvionLender = ((loan.getPublicUserGame().getCost() * loan.getWeeks()) - loan.getFeeGameClub());
			Transaction transactionLender = new Transaction(lender, "DEVOLUCIÓN",
					loan.getPublicUserGame().getGame().getName(), loan.getPublicUserGame().getConsole().getName(),
					loan.getWeeks(), null, (cryptoService.encrypt(Double.toString(devoluvionLender), lenderKey)), null);
			transactionService.getTransactionRepo().save(transactionLender);

			loan.setPublicUserGame(publicUserGame);
			loan = loanRepo.save(loan);

		} else if (loan.getShippingStatus().getCode().equals(Code.SHIPPING_GAMER_DIDNT_RECEIVE)) {
			Setting fineSetting = settingService.getSettingsRepo().findByCode(Code.SETTING_GAMER_DIDNT_RECEIVE);
			Double fineAmount;
			String idTransaction = "";

			if (fineSetting.getType().equals(Const.SETTINGS_PERCENTAGE)) {
				fineAmount = subtotal * (Double.parseDouble(fineSetting.getValue()) / 100.0);
			} else {
				fineAmount = Double.parseDouble(fineSetting.getValue());
			}

			Fine fine = new Fine();
			fine.setLoan(loan);
			fine.setOwner(gamer);
			fine.setAmountEnc(cryptoService.encrypt(Double.toString(fineAmount), gamerKey));
			fine.setDescription(loan.getShippingStatus().getName());
			fineService.getFineRepo().save(fine);

			publicUserService.addToUserBalance(gamer.getId(), loan.getCost());
			PublicUserGame publicUserGame = loan.getPublicUserGame();
			publicUserGame.setIsBorrowed(false);
			publicUserGame = publicUserService.getPublicUserGameRepo().save(publicUserGame);
			loan.setPublicUserGame(publicUserGame);

			loan = loanRepo.save(loan);
			Double lenderFee = Double.parseDouble(settingService.getSettingValue(Code.SETTING_FEE_LENDER)) / 100.0;
			Double lenderReturn = (loan.getPublicUserGame().getCost() * loan.getWeeks()) * (1.0 - lenderFee);
			Double lenderDiff = Double.parseDouble(cryptoService.decrypt(lender.getBalance(), lenderKey))
					- lenderReturn;
			if (lenderDiff < 0.0) {
				publicUserService.setUserBalance(lender.getId(), 0.0);
				String description = "Retorno por préstamo cancelado";
				JSONArray cardList = new JSONArray(paymentezService.listCardsOfUser(lender, session));
				JSONObject card = cardList.getJSONObject(0);
				String response = paymentezService.debitFromCard(session, request.getRemoteAddr(), card.getString("card_reference"),
						Math.abs(lenderDiff), 0.0, description,lender);
				JSONObject json = new JSONObject(response);
				idTransaction = json.getString("transaction_id");
				
			} else {
				publicUserService.substractFromUserBalance(lender.getId(), lenderReturn);
			}

			Setting rewardSetting = settingService.getSettingsRepo().findByCode(Code.SETTING_LENDER_REWARD_ON_CANCEL);
			Double rewardAmount;

			if (rewardSetting.getType().equals(Const.SETTINGS_PERCENTAGE)) {
				rewardAmount = subtotal * (Double.parseDouble(rewardSetting.getValue()) / 100.0);
			} else {
				rewardAmount = Double.parseDouble(rewardSetting.getValue());
			}

			publicUserService.addToUserBalance(lender.getId(), rewardAmount);

			Transaction gamerTransaction = new Transaction();
			gamerTransaction.setOwner(gamer);
			gamerTransaction.setTransaction("DEVOLUCIÓN");
			gamerTransaction.setGame(loan.getPublicUserGame().getGame().getName());
			gamerTransaction.setConsole(loan.getPublicUserGame().getConsole().getName());
			gamerTransaction.setWeeks(loan.getWeeks());
			gamerTransaction.setBalancePartEnc(cryptoService.encrypt(Double.toString(loan.getCost()), gamerKey));
			transactionService.getTransactionRepo().save(gamerTransaction);

			Transaction lenderTransaction = new Transaction();
			lenderTransaction.setOwner(lender);
			lenderTransaction.setTransaction("ALQUILER CANCELADO");
			lenderTransaction.setGame(loan.getPublicUserGame().getGame().getName());
			lenderTransaction.setConsole(loan.getPublicUserGame().getConsole().getName());
			lenderTransaction.setWeeks(loan.getWeeks());

			if (lenderDiff < 0.0) {
				lenderTransaction.setDebitBalanceEnc(lender.getBalance());
				lenderTransaction.setDebitCardEnc(cryptoService.encrypt(Double.toString(Math.abs(lenderDiff)), lenderKey));
				lenderTransaction.setCcTransaction(idTransaction);
				lenderTransaction.setStatusRefund("DEBITADO");
			} else {
				lenderTransaction.setDebitBalanceEnc(cryptoService.encrypt(Double.toString(lenderReturn), lenderKey));
			}

			transactionService.getTransactionRepo().save(lenderTransaction);

			Transaction rewardTransaction = new Transaction();
			rewardTransaction.setOwner(lender);
			rewardTransaction.setTransaction("RECOMPENSA POR ALQUILER CANCELADO");
			rewardTransaction.setGame(loan.getPublicUserGame().getGame().getName());
			rewardTransaction.setConsole(loan.getPublicUserGame().getConsole().getName());
			rewardTransaction.setWeeks(loan.getWeeks());
			rewardTransaction.setBalancePartEnc(cryptoService.encrypt(Double.toString(rewardAmount), lenderKey));
			transactionService.getTransactionRepo().save(rewardTransaction);
		}
	}

	private void cancelLoanByTimeout(Long loanId) {
		if (loanRepo == null) {
			loanRepo = (LoanRepo) ApplicationContextHolder.getContext().getBean(LoanRepo.class);
		}

		Loan loan = loanRepo.findOne(loanId);

		loan.setUpdateDate(new Date());
		loan.setStatus(false);
		loan.setWasTimedOut(true);

		if (publicUserService == null) {
			publicUserService = (PublicUserService) ApplicationContextHolder.getContext()
					.getBean(PublicUserService.class);
		}

		PublicUserGame publicUserGame = loan.getPublicUserGame();
		publicUserGame.setIsBorrowed(false);
		publicUserGame = publicUserService.getPublicUserGameRepo().save(publicUserGame);
		loan.setPublicUserGame(publicUserGame);

		loanRepo.save(loan);

		if (messageService == null) {
			messageService = (MessageService) ApplicationContextHolder.getContext().getBean(MessageService.class);
		}

		loan.getGamerMessage().setRead(false);
		loan.getLenderMessage().setRead(false);
		messageService.getMessageRepo().save(Arrays.asList(loan.getGamerMessage(), loan.getLenderMessage()));
	}

	@Transactional
	public Loan cancelLoan(Long id) throws ServletException {
		PublicUser currentUser = publicUserService.getCurrentUser();
		Loan loan = loanRepo.findOne(id);
		loan.setStatus(false);
		loan = loanRepo.save(loan);

		if (currentUser.getId().longValue() == loan.getGamer().getId().longValue()) {
			loan.getLenderMessage().setRead(false);
			messageService.getMessageRepo().save(loan.getLenderMessage());
		}

		if (currentUser.getId().longValue() == loan.getPublicUserGame().getPublicUser().getId().longValue()) {
			loan.getGamerMessage().setRead(false);
			messageService.getMessageRepo().save(loan.getGamerMessage());
		}

		return loan;
	}

	public LoanRepo getLoanRepo() {
		return loanRepo;
	}
}
