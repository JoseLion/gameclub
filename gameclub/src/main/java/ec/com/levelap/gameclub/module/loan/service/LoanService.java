package ec.com.levelap.gameclub.module.loan.service;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.repository.LoanRepo;
import ec.com.levelap.gameclub.module.message.service.MessageService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;

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
	
	@Transactional
	public void requestGame(Loan loan) throws ServletException {
		messageService.createLoanMessages(loan.getPublicUserGame().getPublicUser());
		PublicUser gamer = publicUserService.getCurrentUser();
		loan.setGamer(gamer);
		loanRepo.save(loan);
	}
	
	public LoanRepo getLoanRepo() {
		return loanRepo;
	}
}
