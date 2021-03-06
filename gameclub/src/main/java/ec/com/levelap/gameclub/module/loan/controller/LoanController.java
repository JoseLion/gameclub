package ec.com.levelap.gameclub.module.loan.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.entity.LoanLite;
import ec.com.levelap.gameclub.module.loan.service.LoanService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/loan", produces=MediaType.APPLICATION_JSON_VALUE)
public class LoanController {
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@RequestMapping(value="findLoans", method=RequestMethod.POST)
	public ResponseEntity<Page<LoanLite>> findLoans(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		Page<LoanLite> loans = loanService.getLoanRepo().findLoans(search.lender, search.gamer, search.shippingStatus, search.tracking, search.startDate, search.endDate, new PageRequest(search.page, Const.TABLE_SIZE));
		return new ResponseEntity<Page<LoanLite>>(loans, HttpStatus.OK);
	}
	
	@RequestMapping(value="findOne/{id}", method=RequestMethod.GET)
	public ResponseEntity<Loan> findOne(@PathVariable Long id) throws ServletException {
		Loan loan = loanService.getLoanRepo().findOne(id);
		return new ResponseEntity<Loan>(loan, HttpStatus.OK);
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public ResponseEntity<LoanLite> save(@RequestBody Loan loan, HttpSession session, HttpServletRequest request) throws ServletException, RestClientException, GeneralSecurityException, IOException, URISyntaxException, JSONException {
		LoanLite loanLite = loanService.save(loan, session, request);
		return new ResponseEntity<LoanLite>(loanLite, HttpStatus.OK);
	}
	
	@RequestMapping(value="requestGame", method=RequestMethod.POST)
	public ResponseEntity<PublicUser> requestGame(@RequestBody Loan loan) throws ServletException, MessagingException, IOException, GeneralSecurityException {
		loanService.requestGame(loan, loan.getCost(), loan.getBalancePart(), loan.getCardPart(), loan.getShippningCost(), loan.getFeeGameClub(), loan.getTaxes());
		PublicUser currentUser = publicUserService.getCurrentUser();
		return new ResponseEntity<PublicUser>(currentUser, HttpStatus.OK);
	}
	
	@RequestMapping(value="acceptLoan/{id}", method=RequestMethod.GET)
	public ResponseEntity<Loan> acceptLoan(@PathVariable Long id) throws ServletException {
		Loan loan = loanService.acceptOrRejectLoan(id, true);
		return new ResponseEntity<Loan>(loan, HttpStatus.OK);
	}
	
	@RequestMapping(value="rejectLoan/{id}", method=RequestMethod.GET)
	public ResponseEntity<Loan> rejectLoan(@PathVariable Long id) throws ServletException {
		Loan loan = loanService.acceptOrRejectLoan(id, false);
		return new ResponseEntity<Loan>(loan, HttpStatus.OK);
	}
	
	@RequestMapping(value="confirmLender", method=RequestMethod.POST)
	public ResponseEntity<Loan> confirmLender(@RequestBody Loan loan, HttpSession session, HttpServletRequest request) throws ServletException, RestClientException, IOException, GeneralSecurityException, URISyntaxException, JSONException, MessagingException {
		loan = loanService.confirmLoan(loan, false, session, request);
		return new ResponseEntity<Loan>(loan, HttpStatus.OK);
	}
	
	@RequestMapping(value="confirmGamer", method=RequestMethod.POST)
	public ResponseEntity<Loan> confirmGamer(@RequestBody Loan loan, HttpSession session, HttpServletRequest request) throws ServletException, RestClientException, IOException, GeneralSecurityException, URISyntaxException, JSONException, MessagingException {
		loan = loanService.confirmLoan(loan, true, session, request);
		return new ResponseEntity<Loan>(loan, HttpStatus.OK);
	}
	
	@RequestMapping(value="cancelLoan/{id}", method=RequestMethod.GET)
	public ResponseEntity<Loan> cancelLoan(@PathVariable Long id) throws ServletException {
		Loan loan = loanService.cancelLoan(id);
		return new ResponseEntity<>(loan, HttpStatus.OK);
	}
	
	@RequestMapping(value="payAllWithCreditCard/{id}", method=RequestMethod.GET)
	public ResponseEntity<Loan> payAllWithCreditCard(@PathVariable Long id) throws ServletException, IOException, GeneralSecurityException {
		Loan loan = loanService.payAllWithCreditCard(id);
		return new ResponseEntity<Loan>(loan, HttpStatus.OK);
	}
	
	private static class Search {
		public String lender = "";
		
		public String gamer = "";
		
		public Catalog shippingStatus;
		
		public String tracking = "";
		
		public Date startDate = new Date(0);
		
		public Date endDate = new Date();
		
		public Integer page = 0;
	}
}