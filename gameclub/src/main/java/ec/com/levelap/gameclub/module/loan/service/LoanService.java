package ec.com.levelap.gameclub.module.loan.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.catalog.CatalogRepo;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.repository.LoanRepo;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.service.MessageService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Code;
import ec.com.levelap.gameclub.utils.Const;

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
	
	@Transactional
	public void requestGame(Loan loan) throws ServletException {
		Map<String, Message> messages = messageService.createLoanMessages(loan.getPublicUserGame().getPublicUser());
		PublicUser gamer = publicUserService.getCurrentUser();
		loan.setGamer(gamer);
		loan.setGamerMessage(messages.get(Const.GAMER));
		loan.setLenderMessage(messages.get(Const.LENDER));
		loanRepo.save(loan);
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
	
	@Transactional
	public Loan confirmLoan(Loan loan, boolean isGamer) throws ServletException {
		Catalog noTracking = catalogRepo.findByCode(Code.SHIPPING_NO_TRACKING);
		loan.setShippingStatus(noTracking);
		
		if (!isGamer) {
			loan.setLenderConfirmed(true);
			loan.setLenderStatusDate(new Date());
		} else {
			loan.setGamerConfirmed(true);
			loan.setGamerStatusDate(new Date());
		}
		
		loan = loanRepo.save(loan);
		return loan;
	}
	
	public LoanRepo getLoanRepo() {
		return loanRepo;
	}
}
