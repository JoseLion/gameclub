package ec.com.levelap.gameclub.module.paymentez.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
	
	@RequestMapping(value="callback", method=RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> callback(HttpServletRequest servletRequest, Map<String, String> body) throws ServletException, JSONException, IOException, GeneralSecurityException, MessagingException {
		Set<String> keys = body.keySet();
		
		System.out.println("RESPONSE STREAM: " + IOUtils.toString(servletRequest.getInputStream(), StandardCharsets.UTF_8));
		System.out.println("RESPONSE OBJECT: " + body);
		for (String key : keys) {
			System.out.println(key + ": " + body.get(key));
		}
		
		
		/*JSONObject json = new JSONObject((String) response);
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
		}*/
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private static class Callback {
		public String response;
	}
}
