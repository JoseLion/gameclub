package ec.com.levelap.gameclub.module.loan.service;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.catalog.CatalogRepo;
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
import ec.com.levelap.gameclub.module.shippingPrice.service.ShippingPriceService;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.service.TransactionService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.module.user.repository.PublicUserGameRepo;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Code;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.kushki.KushkiException;
import ec.com.levelap.kushki.object.KushkiAmount;
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
	private KushkiService kushkiService;
	
	@Autowired
	private FineRepo fineRepo;
	
	@Autowired
	private SettingService settingsService;
	
	@Autowired
	private ShippingPriceService shippingPriceService;
	
	@Autowired
	private LevelapTaskScheduler levelapTaskScheduler;
	
	@Autowired
	private MailService mailService;
	
	@Value("${game-club.real-times}")
	private boolean realTimes;
	
	@Autowired
	private TransactionService transactionService;
	
	@Transactional
	public void requestGame(Loan loan) throws ServletException, MessagingException {
		Map<String, Message> messages = messageService.createLoanMessages(loan.getPublicUserGame().getPublicUser());
		PublicUser gamer = publicUserService.getCurrentUser();
		loan.setGamer(gamer);
		loan.setGamerMessage(messages.get(Const.GAMER));
		loan.setLenderMessage(messages.get(Const.LENDER));
		loan = loanRepo.save(loan);
		
		PublicUserGame cross = publicUserGameRepo.findOne(loan.getPublicUserGame().getId());
		loan.setPublicUserGame(cross);
		MailParameters mailParameters = new MailParameters();
		mailParameters.setRecipentTO(Arrays.asList(loan.getPublicUserGame().getPublicUser().getUsername()));
		Map<String, String> params = new HashMap<>();
		params.put("user", loan.getGamer().getName() + " " + loan.getGamer().getLastName().substring(0, 1) + ".");
		params.put("game", loan.getPublicUserGame().getGame().getName());
		params.put("console", loan.getPublicUserGame().getConsole().getName());
		params.put("cost", "" + loan.getPublicUserGame().getCost().intValue());

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
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Loan confirmLoan(Loan loan, boolean isGamer) throws ServletException, KushkiException, GeneralSecurityException, IOException {
		Catalog noTracking = catalogRepo.findByCode(Code.SHIPPING_NO_TRACKING);
		loan.setShippingStatus(noTracking);
		
		if (!isGamer) {
			Transaction transaction = new Transaction();
			loan.setLenderConfirmed(true);
			loan.setLenderStatusDate(new Date());
			
			if (loan.getBalancePart() > 0.0) {
				publicUserService.substractFromUserBalance(loan.getGamer().getId(), loan.getBalancePart());
				transaction.setDebitBalance(loan.getBalancePart());
			}
			
			if (loan.getCardPart() > 0.0) {
				Map<String, Object> optionals = new HashMap<>();
				optionals.put("amount", new KushkiAmount(loan.getCardPart()));
				String ticket = kushkiService.subscriptionCharge(loan.getPayment().getSubscriptionId(), optionals);
				if(ticket != "" && ticket != null) {
					transaction.setDebitCard(loan.getCardPart());
				}
				loan.setTransactionTicket(ticket);
			}
			transaction.setCreditPart(loan.getCost());
			transaction.setWeeks(loan.getWeeks());
			transaction.setOwner(loan.getGamer());
			transaction.setGame(loan.getPublicUserGame().getGame().getName());
			transaction.setTransaction("JUGASTE");
			transaction = transactionService.getTransactionRepo().save(transaction);
		} else {
			loan.setGamerConfirmed(true);
			loan.setGamerStatusDate(new Date());
		}
		
		loan = loanRepo.save(loan);
		return loan;
	}
	
	@Transactional
	public LoanLite save(Loan loan) throws ServletException, GeneralSecurityException, IOException {
		Loan previous = loanRepo.findOne(loan.getId());
		
		if (!loan.getShippingStatus().equals(previous.getShippingStatus()) || (loan.getShippingNote() != null && !loan.getShippingNote().equalsIgnoreCase(previous.getShippingNote()))) {
			loan.setLenderStatusDate(new Date());
			loan.setGamerStatusDate(new Date());
			
			loan.getLenderMessage().setRead(false);
			loan.getGamerMessage().setRead(false);
			messageService.getMessageRepo().save(loan.getLenderMessage());
			messageService.getMessageRepo().save(loan.getGamerMessage());
		}
		
		if (loan.getShippingStatus().getCode().equals(Code.SHIPPING_DELIVERED)) {
			Transaction transaction = new Transaction();
			final Loan taskLoan = loan;
			Calendar calendar = Calendar.getInstance();
			loan.setDeliveryDate(new Date());
			publicUserService.addToUserBalance(loan.getPublicUserGame().getPublicUser().getId(), loan.getPublicUserGame().getCost() * loan.getWeeks().doubleValue());
			
			transaction.setCreditPart(loan.getWeeks()*(loan.getPublicUserGame().getCost()));
			transaction.setWeeks(loan.getWeeks());
			transaction.setOwner(loan.getPublicUserGame().getPublicUser());
			transaction.setGame(loan.getPublicUserGame().getGame().getName());
			transaction.setTransaction("ALQUILASTE");
			transaction = transactionService.getTransactionRepo().save(transaction);
			
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
			
			levelapTaskScheduler.scheduleTaskAtDate(calendar.getTime(), Loan.class.getSimpleName() + "-R2-" + loan.getId(), new Runnable() {
				@Override
				public void run() {
					try {
						sendRemindingMails(taskLoan, true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			levelapTaskScheduler.scheduleTaskAtDate(loan.getReturnDate(), Loan.class.getSimpleName() + "-R3-" + loan.getId(), new Runnable() {
				@Override
				public void run() {
					try {
						Restore restore = restoreRepo.findByLoan(taskLoan);
						restore.getLoan().getGamerMessage().setRead(false);
						restore.getLoan().getLenderMessage().setRead(false);
						
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
				levelapTaskScheduler.scheduleTaskAtDate(threeDays.getTime(), Loan.class.getSimpleName() + "-R1-" + loan.getId(), new Runnable() {
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
						
						try {
							sendRemindingMails(loan, false);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
				levelapTaskScheduler.scheduleTaskAtDate(oneDay.getTime(), Loan.class.getSimpleName() + "-R2-" + loan.getId(), new Runnable() {
					@Override
					public void run() {
						try {
							sendRemindingMails(loan, true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
				levelapTaskScheduler.scheduleTaskAtDate(loan.getReturnDate(), Loan.class.getSimpleName() + "-R3-" + loan.getId(), new Runnable() {
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
			
			if (today.before(oneDay.getTime()) && today.after(threeDays.getTime())) {
				levelapTaskScheduler.scheduleTaskAtDate(oneDay.getTime(), Loan.class.getSimpleName() + "-R2-" + loan.getId(), new Runnable() {
					@Override
					public void run() {
						try {
							sendRemindingMails(loan, true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
				levelapTaskScheduler.scheduleTaskAtDate(loan.getReturnDate(), Loan.class.getSimpleName() + "-R3-" + loan.getId(), new Runnable() {
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
			
			if (today.before(loan.getReturnDate()) && today.after(oneDay.getTime())) {
				levelapTaskScheduler.scheduleTaskAtDate(loan.getReturnDate(), Loan.class.getSimpleName() + "-R3-" + loan.getId(), new Runnable() {
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
		params.put("lender", loan.getPublicUserGame().getPublicUser().getName()  + " " + loan.getPublicUserGame().getPublicUser().getLastName());
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
		params.put("lender", restore.getPublicUserGame().getPublicUser().getName()  + " " + restore.getPublicUserGame().getPublicUser().getLastName());
		params.put("gamer", restore.getGamer().getName() + " " + restore.getGamer().getLastName());
		params.put("game", restore.getPublicUserGame().getGame().getName());
		params.put("console", restore.getPublicUserGame().getConsole().getName());
		params.put("returnDate", df.format(new Date()));
		params.put("lenderConfirmationDate", restore.getLenderStatusDate() != null ? df.format(restore.getLenderStatusDate()) : "SIN CONFIRMAR");
		params.put("gamerConfirmationDate", restore.getGamerStatusDate() != null ? df.format(restore.getGamerStatusDate()) : "SIN CONFIRMAR");
		
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
	
	@Transactional
	private void createFines(Loan loan) throws ServletException, GeneralSecurityException, IOException {
		Double shippingCost = shippingPriceService.getPrice(loan.getPublicUserGame().getPublicUser().getLocation(), loan.getGamer().getLocation());
		
		if (loan.getShippingStatus().getCode().equals(Code.SHIPPING_LENDER_DIDNT_DELIVER)) {
			loan.setGamer(publicUserService.addToUserBalance(loan.getGamer().getId(), loan.getCost()));
			
			Fine fine = new Fine();
			fine.setOwner(loan.getPublicUserGame().getPublicUser());
			fine.setAmount(shippingCost * 2.0);
			fine.setDescription(loan.getShippingStatus().getName());
			
			fineRepo.save(fine);
			loan.getPublicUserGame().setIsBorrowed(false);
			loan.setPublicUserGame(publicUserGameRepo.save(loan.getPublicUserGame()));
		}
		
		if (loan.getShippingStatus().getCode().equals(Code.SHIPPING_GAMER_DIDNT_RECEIVE)) {
			Double penalty = Double.parseDouble(settingsService.getSettingValue(Code.SETTING_GAMER_DIDNT_RECEIVE));
			Double reward = Double.parseDouble(settingsService.getSettingValue(Code.SETTING_NO_LOAN_REWARD));
			loan.setGamer(publicUserService.addToUserBalance(loan.getGamer().getId(), loan.getCost() - penalty));
			loan.getPublicUserGame().setPublicUser(publicUserService.addToUserBalance(loan.getPublicUserGame().getPublicUser().getId(), reward));
			
			loan.getPublicUserGame().setIsBorrowed(false);
			loan.setPublicUserGame(publicUserGameRepo.save(loan.getPublicUserGame()));
		}
	}
	
	public LoanRepo getLoanRepo() {
		return loanRepo;
	}
}
