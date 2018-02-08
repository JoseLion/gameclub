package ec.com.levelap.gameclub.module.paymentez.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	
	@RequestMapping(value="callback", method=RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> callback(HttpServletRequest servletRequest) throws ServletException, JSONException, IOException, GeneralSecurityException, MessagingException, URISyntaxException {
		System.out.println("PROCESSING PAYMENTEZ CALLBACK");
		String url = "https://gamclub.com.ec?" + IOUtils.toString(servletRequest.getInputStream(), StandardCharsets.UTF_8);
		List<NameValuePair> params = URLEncodedUtils.parse(new URI(url), StandardCharsets.UTF_8);
		Map<String, String> response = new HashMap<>();
		
		for (NameValuePair param : params) {
			response.put(param.getName(), param.getValue());
		}
		
		String transactioId = response.get("transaction_id");
		Loan loan = loanRepo.findByTransactionId(transactioId);
		WelcomeKit welcomeKit = welcomeKitRepo.findByTransactionId(transactioId);
		Fine fine = fineRepo.findByTransactionId(transactioId);
		
		if (loan != null) {
			loan.setAuthCode(response.get("authorization_code"));
			loan = loanRepo.save(loan);
			paymentezService.sendMailLoan(loan);
		} else if (welcomeKit != null) {
			welcomeKit.setAuthCode(response.get("authorization_code"));
			welcomeKit = welcomeKitRepo.save(welcomeKit);
			paymentezService.sendMailShippinKit(welcomeKit);
		} else if (fine != null) {
			fine.setAuthCode(response.get("authorization_code"));
			fine = fineRepo.save(fine);
			paymentezService.sendMailFine(fine);
		} else {
			System.err.println("UNABLE TO FIND LOAN, WELCOME KIT OR FINE WITH TRANSACTION_ID " + response.get("transaction_id"));
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
