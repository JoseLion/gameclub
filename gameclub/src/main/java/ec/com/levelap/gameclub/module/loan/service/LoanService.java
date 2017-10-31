package ec.com.levelap.gameclub.module.loan.service;

import java.io.File;
import java.io.IOException;
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
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.catalog.CatalogRepo;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.application.ApplicationContextHolder;
import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.fine.repository.FineRepo;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.entity.LoanLite;
import ec.com.levelap.gameclub.module.loan.repository.LoanRepo;
import ec.com.levelap.gameclub.module.mail.service.MailService;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.repository.MessageRepo;
import ec.com.levelap.gameclub.module.message.service.MessageService;
import ec.com.levelap.gameclub.module.restore.entity.Restore;
import ec.com.levelap.gameclub.module.restore.repository.RestoreRepo;
import ec.com.levelap.gameclub.module.settings.service.SettingService;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.repository.TransactionRepo;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.module.user.repository.PublicUserGameRepo;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Code;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.kushki.KushkiException;
import ec.com.levelap.kushki.service.KushkiService;
import ec.com.levelap.mail.MailParameters;
import ec.com.levelap.taskScheduler.LevelapTaskScheduler;

@Service
public class LoanService extends BaseService<Loan> {
	public LoanService() {
		super(Loan.class);
	}

	@Autowired
	private LoanRepo loanRepo;

	@Autowired
	private MessageService messageService;

	@Autowired
	private PublicUserService publicUserService;

	@Autowired
	private PublicUserGameRepo publicUserGameRepo;

	@Autowired
	private CatalogRepo catalogRepo;

	@Autowired
	private RestoreRepo restoreRepo;

	@Autowired
	private FineRepo fineRepo;

	@Autowired
	private SettingService settingService;

	@Autowired
	private LevelapTaskScheduler levelapTaskScheduler;

	@Autowired
	private MailService mailService;

	@Value("${game-club.real-times}")
	private boolean realTimes;

	@Autowired
	private LevelapCryptography cryptoService;

	@Autowired
	private TransactionRepo transactionRepo;

	@Autowired
	private KushkiService kushkiService;

	@Transactional
	public void requestGame(Loan loan, Double cost, Double balancePart, Double cardPart)
			throws ServletException, MessagingException, IOException, GeneralSecurityException {
		Map<String, Message> messages = messageService.createLoanMessages(loan.getPublicUserGame().getPublicUser());
		PublicUser gamer = publicUserService.getCurrentUser();
		File key = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(key, gamer.getPrivateKey());

		loan.setGamer(gamer);
		loan.setGamerMessage(messages.get(Const.GAMER));
		loan.setLenderMessage(messages.get(Const.LENDER));
		loan.setCostEnc(cryptoService.encrypt(Double.toString(cost), key));
		loan.setBalancePartEnc(cryptoService.encrypt(Double.toString(balancePart), key));
		loan.setCardPartEnc(cryptoService.encrypt(Double.toString(cardPart), key));
		loan = loanRepo.save(loan);

		PublicUserGame cross = publicUserGameRepo.findOne(loan.getPublicUserGame().getId());
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

			loan.getPublicUserGame().setIsBorrowed(true);
			loan.setPublicUserGame(publicUserGameRepo.save(loan.getPublicUserGame()));
		}

		loan = loanRepo.save(loan);

		loan.getGamerMessage().setRead(false);
		messageService.getMessageRepo().save(loan.getGamerMessage());

		return loan;
	}

	// TODO
	@SuppressWarnings("unchecked")
	@Transactional
	public Loan confirmLoan(Loan loan, boolean isGamer)
			throws ServletException, KushkiException, GeneralSecurityException, IOException {
		Catalog noTracking = catalogRepo.findByCode(Code.SHIPPING_NO_TRACKING);
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
				Map<String, Object> kushkiSubscription = new HashMap<>();
				kushkiSubscription.put("amount", totalToCard);
				try {
					kushkiService.subscriptionCharge(loan.getPayment().getSubscriptionId(), kushkiSubscription);
				} catch (KushkiException ex) {
					throw new KushkiException(ex);
				}
			}

			Transaction transaction = new Transaction(connected, "JUGASTE",
					loan.getPublicUserGame().getGame().getName(), loan.getWeeks(), null, loan.getBalancePartEnc(),
					loan.getCardPartEnc());
			transactionRepo.save(transaction);

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
			transactionRepo.save(transaction);

		}
		loan = loanRepo.save(loan);
		
		return loan;
	}

	// TODO
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

			final Loan taskLoan = loan;
			Calendar calendar = Calendar.getInstance();
			loan.setDeliveryDate(new Date());

			if (realTimes) {
				calendar.setTime(loan.getReturnDate());
				calendar.add(Calendar.DATE, -3);
			} else {
				calendar.setTime(loan.getDeliveryDate());
				calendar.add(Calendar.MINUTE, 1);
			}

			levelapTaskScheduler.scheduleTaskAtDate(calendar.getTime(),
					Loan.class.getSimpleName() + "-R1-" + loan.getId(), new Runnable() {
						@Override
						public void run() {
							Catalog noTracking = catalogRepo.findByCode(Code.SHIPPING_NO_TRACKING);
							Restore restore = new Restore(taskLoan);
							restore.setLenderMessage(taskLoan.getLenderMessage());
							restore.setGamerMessage(taskLoan.getGamerMessage());
							restore.setPublicUserGame(taskLoan.getPublicUserGame());
							restore.setGamer(taskLoan.getGamer());
							restore.setShippingStatus(noTracking);

							restoreRepo.save(restore);

							try {
								sendRemindingMails(taskLoan, false);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});

			if (realTimes) {
				calendar.setTime(loan.getReturnDate());
				calendar.add(Calendar.DATE, -1);
			} else {
				calendar.setTime(loan.getDeliveryDate());
				calendar.add(Calendar.MINUTE, 2);
			}

			levelapTaskScheduler.scheduleTaskAtDate(calendar.getTime(),
					Loan.class.getSimpleName() + "-R2-" + loan.getId(), new Runnable() {
						@Override
						public void run() {
							try {
								sendRemindingMails(taskLoan, true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});

			levelapTaskScheduler.scheduleTaskAtDate(loan.getReturnDate(),
					Loan.class.getSimpleName() + "-R3-" + loan.getId(), new Runnable() {
						@Override
						public void run() {
							try {
								Restore restore = restoreRepo.findByLoan(taskLoan);

								if (!restore.getGamerConfirmed()) {
									restore.setGamerAddress(taskLoan.getGamerAddress());
									restore.setGamerGeolocation(taskLoan.getGamerGeolocation());
									restore.setGamerReceiver(taskLoan.getGamerReceiver());
									restore.setGamerConfirmDate(new Date());
								}

								if (!restore.getLenderConfirmed()) {
									restore.setLenderAddress(taskLoan.getLenderAddress());
									restore.setLenderGeolocation(taskLoan.getLenderGeolocation());
									restore.setLenderReceiver(taskLoan.getLenderReceiver());
									restore.setLenderConfirmDate(new Date());
								}

								if (!restore.getGamerConfirmed() || !restore.getLenderConfirmed()) {
									restore = restoreRepo.save(restore);
								}

								restore.getLoan().getGamerMessage().setRead(false);
								restore.getLoan().getLenderMessage().setRead(false);

								messageService.getMessageRepo().save(restore.getLoan().getGamerMessage());
								messageService.getMessageRepo().save(restore.getLoan().getLenderMessage());

								sendFinishedMails(restore);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
		} else {
			createFines(loan);
		}

		loan = loanRepo.save(loan);
		LoanLite loanLite = loanRepo.findById(loan.getId());

		return loanLite;
	}

	@Transactional
	public void rescheduleTasks() {
		LoanRepo repoLoan = ApplicationContextHolder.getContext().getBean(LoanRepo.class);
		RestoreRepo repoRestore = ApplicationContextHolder.getContext().getBean(RestoreRepo.class);
		CatalogRepo repoCatalog = ApplicationContextHolder.getContext().getBean(CatalogRepo.class);
		MessageRepo repoMessage = ApplicationContextHolder.getContext().getBean(MessageRepo.class);
		List<Loan> loans = repoLoan.findByShippingStatusCode(Code.SHIPPING_DELIVERED);
		Date today = new Date();
		Calendar threeDays = Calendar.getInstance();
		Calendar oneDay = Calendar.getInstance();

		for (Loan loan : loans) {
			threeDays.setTime(loan.getReturnDate());
			threeDays.add(Calendar.DATE, -3);
			oneDay.setTime(loan.getReturnDate());
			oneDay.add(Calendar.DATE, -1);

			if (today.before(threeDays.getTime())) {
				levelapTaskScheduler.scheduleTaskAtDate(threeDays.getTime(),
						Loan.class.getSimpleName() + "-R1-" + loan.getId(), new Runnable() {
							@Override
							public void run() {
								Catalog noTracking = repoCatalog.findByCode(Code.SHIPPING_NO_TRACKING);
								Restore restore = new Restore(loan);
								restore.setLenderMessage(loan.getLenderMessage());
								restore.setGamerMessage(loan.getGamerMessage());
								restore.setPublicUserGame(loan.getPublicUserGame());
								restore.setGamer(loan.getGamer());
								restore.setShippingStatus(noTracking);

								repoRestore.save(restore);
								restore.getGamerMessage().setRead(Boolean.FALSE);
								repoMessage.save(restore.getLoan().getGamerMessage());
								restore.getLenderMessage().setRead(Boolean.FALSE);
								repoMessage.save(restore.getLoan().getLenderMessage());

								try {
									sendRemindingMails(loan, false);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

				levelapTaskScheduler.scheduleTaskAtDate(oneDay.getTime(),
						Loan.class.getSimpleName() + "-R2-" + loan.getId(), new Runnable() {
							@Override
							public void run() {
								try {
									sendRemindingMails(loan, true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

				levelapTaskScheduler.scheduleTaskAtDate(loan.getReturnDate(),
						Loan.class.getSimpleName() + "-R3-" + loan.getId(), new Runnable() {
							@Override
							public void run() {
								try {
									Restore restore = repoRestore.findByLoan(loan);

									if (!restore.getGamerConfirmed()) {
										restore.setGamerAddress(loan.getGamerAddress());
										restore.setGamerGeolocation(loan.getGamerGeolocation());
										restore.setGamerReceiver(loan.getGamerReceiver());
										restore.setGamerConfirmDate(new Date());
									}

									if (!restore.getLenderConfirmed()) {
										restore.setLenderAddress(loan.getLenderAddress());
										restore.setLenderGeolocation(loan.getLenderGeolocation());
										restore.setLenderReceiver(loan.getLenderReceiver());
										restore.setLenderConfirmDate(new Date());
									}

									if (!restore.getGamerConfirmed() || !restore.getLenderConfirmed()) {
										restore = repoRestore.save(restore);
									}

									restore.getLoan().getGamerMessage().setRead(false);
									restore.getLoan().getLenderMessage().setRead(false);

									repoMessage.save(restore.getLoan().getGamerMessage());
									repoMessage.save(restore.getLoan().getLenderMessage());

									sendFinishedMails(restore);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
			}

			if (today.before(oneDay.getTime()) && today.after(threeDays.getTime())) {
				levelapTaskScheduler.scheduleTaskAtDate(oneDay.getTime(),
						Loan.class.getSimpleName() + "-R2-" + loan.getId(), new Runnable() {
							@Override
							public void run() {
								try {
									sendRemindingMails(loan, true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

				levelapTaskScheduler.scheduleTaskAtDate(loan.getReturnDate(),
						Loan.class.getSimpleName() + "-R3-" + loan.getId(), new Runnable() {
							@Override
							public void run() {
								try {
									Restore restore = repoRestore.findByLoan(loan);

									if (!restore.getGamerConfirmed()) {
										restore.setGamerAddress(loan.getGamerAddress());
										restore.setGamerGeolocation(loan.getGamerGeolocation());
										restore.setGamerReceiver(loan.getGamerReceiver());
										restore.setGamerConfirmDate(new Date());
									}

									if (!restore.getLenderConfirmed()) {
										restore.setLenderAddress(loan.getLenderAddress());
										restore.setLenderGeolocation(loan.getLenderGeolocation());
										restore.setLenderReceiver(loan.getLenderReceiver());
										restore.setLenderConfirmDate(new Date());
									}

									if (!restore.getGamerConfirmed() || !restore.getLenderConfirmed()) {
										restore = repoRestore.save(restore);
									}

									restore.getLoan().getGamerMessage().setRead(false);
									restore.getLoan().getLenderMessage().setRead(false);

									repoMessage.save(restore.getLoan().getGamerMessage());
									repoMessage.save(restore.getLoan().getLenderMessage());

									sendFinishedMails(restore);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
			}

			if (today.before(loan.getReturnDate()) && today.after(oneDay.getTime())) {
				levelapTaskScheduler.scheduleTaskAtDate(loan.getReturnDate(),
						Loan.class.getSimpleName() + "-R3-" + loan.getId(), new Runnable() {
							@Override
							public void run() {
								try {
									Restore restore = repoRestore.findByLoan(loan);
									restore.getLoan().getGamerMessage().setRead(false);
									restore.getLoan().getLenderMessage().setRead(false);

									if (!restore.getGamerConfirmed()) {
										restore.setGamerAddress(loan.getGamerAddress());
										restore.setGamerGeolocation(loan.getGamerGeolocation());
										restore.setGamerReceiver(loan.getGamerReceiver());
										restore.setGamerConfirmDate(new Date());
									}

									if (!restore.getLenderConfirmed()) {
										restore.setLenderAddress(loan.getLenderAddress());
										restore.setLenderGeolocation(loan.getLenderGeolocation());
										restore.setLenderReceiver(loan.getLenderReceiver());
										restore.setLenderConfirmDate(new Date());
									}

									if (!restore.getGamerConfirmed() || !restore.getLenderConfirmed()) {
										restore = repoRestore.save(restore);
									}

									repoMessage.save(restore.getLoan().getGamerMessage());
									repoMessage.save(restore.getLoan().getLenderMessage());

									sendFinishedMails(restore);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
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

	// TODO
	@SuppressWarnings("unchecked")
	@Transactional
	private void createFines(Loan loan)
			throws ServletException, GeneralSecurityException, IOException, KushkiException {

		PublicUser gamer = publicUserService.getPublicUserRepo().findOne(loan.getGamer().getId());
		File keyGamer = File.createTempFile("keyGamer", ".tmp");
		FileUtils.writeByteArrayToFile(keyGamer, gamer.getPrivateKey());

		PublicUser lender = publicUserService.getPublicUserRepo()
				.findOne(loan.getPublicUserGame().getPublicUser().getId());
		File keyLender = File.createTempFile("keyLender", ".tmp");
		FileUtils.writeByteArrayToFile(keyLender, lender.getPrivateKey());

		Transaction transaction;

		Catalog shippingStatus = catalogRepo.findByCode(loan.getShippingStatus().getCode());

		Double totalToDebit = loan.getPublicUserGame().getCost() * loan.getWeeks();
		if (shippingStatus.getCode().equals(Code.SHIPPING_LENDER_DIDNT_DELIVER)) {
			gamer = publicUserService.addToUserBalance(gamer.getId(), (loan.getCost() * loan.getWeeks()));

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
			loan.setPublicUserGame(publicUserGameRepo.save(loan.getPublicUserGame()));

			Double shippingCost = loan.getCost() - (loan.getPublicUserGame().getCost() * loan.getWeeks());

			Fine fine = new Fine();
			fine.setOwner(loan.getPublicUserGame().getPublicUser());
			fine.setAmountEnc(cryptoService.encrypt(Double.toString(shippingCost), keyLender));
			fine.setDescription(shippingStatus.getName());
			fineRepo.save(fine);

			transaction = new Transaction(gamer, "DEVOLUCION", loan.getPublicUserGame().getGame().getName(),
					loan.getWeeks(), cryptoService.encrypt(Double.toString((loan.getCost())), keyGamer), null, null);
			transactionRepo.save(transaction);

			transaction = new Transaction(lender, "DEVOLUCION", loan.getPublicUserGame().getGame().getName(),
					loan.getWeeks(), null, toBalance, toCard);
			transactionRepo.save(transaction);

		} else if (shippingStatus.getCode().equals(Code.SHIPPING_GAMER_DIDNT_RECEIVE)) {
			Double value = Double.valueOf(settingService.getSettingValue(Code.SETTING_GAMER_DIDNT_RECEIVE));
			gamer = publicUserService.addToUserBalance(loan.getGamer().getId(), loan.getCost() - value);
			lender = publicUserService.addToUserBalance(loan.getPublicUserGame().getPublicUser().getId(), value);

			transaction = new Transaction(gamer, "DEVOLUCION", loan.getPublicUserGame().getGame().getName(),
					loan.getWeeks(), cryptoService.encrypt(Double.toString(loan.getCost()), keyGamer), null, null);
			transactionRepo.save(transaction);

			transaction = new Transaction(gamer, "MULTA - " + shippingStatus.getName(),
					loan.getPublicUserGame().getGame().getName(), loan.getWeeks(), null,
					cryptoService.encrypt(Double.toString(value), keyGamer), null);
			transactionRepo.save(transaction);

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
			transactionRepo.save(transaction);

			transaction = new Transaction(lender, "DEVOLUCION", loan.getPublicUserGame().getGame().getName(),
					loan.getWeeks(), cryptoService.encrypt(Double.toString(value), keyLender), null, null);
			transactionRepo.save(transaction);

		}
	}

	public LoanRepo getLoanRepo() {
		return loanRepo;
	}
}
