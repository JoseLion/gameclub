package ec.com.levelap.gameclub.module.loan.controller;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.service.LoanService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;

@RestController
@RequestMapping(value="api/loan", produces=MediaType.APPLICATION_JSON_VALUE)
public class LoanController {
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@RequestMapping(value="requestGame", method=RequestMethod.POST)
	public ResponseEntity<PublicUser> requestGame(@RequestBody Loan loan) throws ServletException {
		loanService.requestGame(loan);
		PublicUser currentUser = publicUserService.getCurrentUser();
		return new ResponseEntity<PublicUser>(currentUser, HttpStatus.OK);
	}
	
	@RequestMapping(value="cancelLoan/{id}", method=RequestMethod.GET)
	public ResponseEntity<Loan> cancelLoan(@PathVariable Long id) throws ServletException {
		Loan loan = loanService.getLoanRepo().findOne(id);
		loan.setStatus(false);
		loan = loanService.getLoanRepo().save(loan);
		
		return new ResponseEntity<Loan>(loan, HttpStatus.OK);
	}
	
	@RequestMapping(value="acceptLoan/{id}", method=RequestMethod.GET)
	public ResponseEntity<Loan> acceptLoan(@PathVariable Long id) throws ServletException {
		Loan loan = loanService.getLoanRepo().findOne(id);
		loan.setWasAccepted(true);
		loan.setAcceptedDate(new Date());
		loan = loanService.getLoanRepo().save(loan);
		
		return new ResponseEntity<Loan>(loan, HttpStatus.OK);
	}
	
	@RequestMapping(value="rejectLoan/{id}", method=RequestMethod.GET)
	public ResponseEntity<Loan> rejectLoan(@PathVariable Long id) throws ServletException {
		Loan loan = loanService.getLoanRepo().findOne(id);
		loan.setWasAccepted(false);
		loan = loanService.getLoanRepo().save(loan);
		
		return new ResponseEntity<Loan>(loan, HttpStatus.OK);
	}
}