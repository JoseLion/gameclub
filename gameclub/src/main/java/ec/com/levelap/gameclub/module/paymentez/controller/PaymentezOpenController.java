package ec.com.levelap.gameclub.module.paymentez.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.fine.repository.FineRepo;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.repository.LoanRepo;
import ec.com.levelap.gameclub.module.paymentez.service.PaymentezService;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKit;
import ec.com.levelap.gameclub.module.welcomeKit.repository.WelcomeKitRepo;

@RestController
@RequestMapping(value="open/paymentez", produces=MediaType.APPLICATION_JSON_VALUE)
public class PaymentezOpenController {
	
	@Autowired
	private LoanRepo loanRepo;
	
	@Autowired
	private WelcomeKitRepo welcomeKitRepo;
	
	@Autowired
	private FineRepo fineRepo;
	
	@Autowired
	private PaymentezService paymentezService;
	
	@RequestMapping(value="callback", method=RequestMethod.POST)
	public ResponseEntity<?> callback(@RequestBody Object response) throws ServletException, JSONException, IOException, GeneralSecurityException, MessagingException {
		
		System.out.println("Ingresa al call back: ");
		JSONObject json = new JSONObject((String) response);
		String transactioId = json.getString("transaction_id");
		
		Loan loan = loanRepo.findByTransactionId(transactioId);
		WelcomeKit welcomeKit = welcomeKitRepo.findByTransactionId(transactioId);
		Fine fine = fineRepo.findByTransactionId(transactioId);
		
		if(loan != null) {
			loan.setAuthCode(json.getString("authorization_code"));
			loan = loanRepo.save(loan);
			paymentezService.sendMailLoan(loan);
		} else if(welcomeKit != null) {
			welcomeKit.setAuthCode(json.getString("authorization_code"));
			welcomeKit = welcomeKitRepo.save(welcomeKit);
			paymentezService.sendMailShippinKit(welcomeKit);
		} else if(fine != null) {
			fine.setAuthCode(json.getString("authorization_code"));
			fine = fineRepo.save(fine);
			paymentezService.sendMailFine(fine);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
