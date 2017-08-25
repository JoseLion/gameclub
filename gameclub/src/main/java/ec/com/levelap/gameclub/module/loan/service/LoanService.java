package ec.com.levelap.gameclub.module.loan.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.catalog.CatalogRepo;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.repository.LoanRepo;
import ec.com.levelap.gameclub.module.mail.service.MailService;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.service.MessageService;
import ec.com.levelap.gameclub.module.restore.entity.Restore;
import ec.com.levelap.gameclub.module.restore.repository.RestoreRepo;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
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
	private CatalogRepo catalogRepo;
	
	@Autowired
	private RestoreRepo restoreRepo;
	
	@Autowired
	private KushkiService kushkiService;
	
	@Autowired
	private LevelapTaskScheduler levelapTaskScheduler;
	
	@Autowired
	private MailService mailService;
	
	@Transactional
	public void requestGame(Loan loan) throws ServletException, MessagingException {
		Map<String, Message> messages = messageService.createLoanMessages(loan.getPublicUserGame().getPublicUser());
		PublicUser gamer = publicUserService.getCurrentUser();
		loan.setGamer(gamer);
		loan.setGamerMessage(messages.get(Const.GAMER));
		loan.setLenderMessage(messages.get(Const.LENDER));
		loanRepo.save(loan);
		
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
		}
		
		loan = loanRepo.save(loan);
		
		loan.getGamerMessage().setRead(false);
		messageService.getMessageRepo().save(loan.getGamerMessage());
		
		return loan;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Loan confirmLoan(Loan loan, boolean isGamer) throws ServletException, KushkiException {
		Catalog noTracking = catalogRepo.findByCode(Code.SHIPPING_NO_TRACKING);
		loan.setShippingStatus(noTracking);
		
		if (!isGamer) {
			loan.setLenderConfirmed(true);
			loan.setLenderStatusDate(new Date());
			
			Map<String, Object> optionals = new HashMap<>();
			optionals.put("amount", new KushkiAmount(loan.getCost()));
			String ticket = kushkiService.subscriptionCharge(loan.getPayment().getSubscriptionId(), optionals);
			
			loan.setTransactionTicket(ticket);
		} else {
			loan.setGamerConfirmed(true);
			loan.setGamerStatusDate(new Date());
		}
		
		loan = loanRepo.save(loan);
		return loan;
	}
	
	@Transactional
	public Loan save(Loan loan) throws ServletException {
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
			loan.setDeliveryDate(new Date());
			
			final Loan taskLoan = loan;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(loan.getReturnDate());
			calendar.add(Calendar.DATE, -3);
			levelapTaskScheduler.scheduleTaskAtDate(calendar.getTime(), Loan.class.getSimpleName() + "-R1-" + loan.getId(), new Runnable() {
				@Override
				public void run() {
					Restore restore = new Restore();
					restore.setLenderMessage(taskLoan.getLenderMessage());
					restore.setGamerMessage(taskLoan.getGamerMessage());
					restore.setPublicUserGame(taskLoan.getPublicUserGame());
					restore.setGamer(taskLoan.getGamer());
					
					restoreRepo.save(restore);
					
					try {
						sendRemindingMails(taskLoan);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		loan = loanRepo.save(loan);
		return loan;
	}
	
	private void sendRemindingMails(Loan loan) throws MessagingException {
		MailParameters mailParameters = new MailParameters();
		mailParameters.setRecipentTO(Arrays.asList(Const.SYSTEM_ADMIN_EMAIL));
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Map<String, String> params = new HashMap<>();
		params.put("lender", loan.getPublicUserGame().getPublicUser().getName()  + " " + loan.getPublicUserGame().getPublicUser().getLastName());
		params.put("gamer", loan.getGamer().getName() + " " + loan.getGamer().getLastName());
		params.put("game", loan.getPublicUserGame().getGame().getName());
		params.put("console", loan.getPublicUserGame().getConsole().getName());
		params.put("returnDate", df.format(loan.getReturnDate()));
		mailService.sendMailWihTemplate(mailParameters, "MSGARM", params);
	}
	
	public LoanRepo getLoanRepo() {
		return loanRepo;
	}
}
