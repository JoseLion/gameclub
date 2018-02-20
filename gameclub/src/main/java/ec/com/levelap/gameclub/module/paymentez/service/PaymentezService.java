package ec.com.levelap.gameclub.module.paymentez.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.fine.service.FineService;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.service.LoanService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKit;
import ec.com.levelap.gameclub.module.welcomeKit.service.WelcomeKitService;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.gameclub.utils.GameClubMailService;
import ec.com.levelap.mail.entity.LevelapMail;

@Service
public class PaymentezService {
	@Autowired
	private PublicUserService publicUserService;
	
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private WelcomeKitService welcomeKitService;
	
	@Autowired
	private FineService fineService;
	
	@Value("${levelap.paymentez.base-url}")
	private String BASE_URL;
	
	@Value("${levelap.paymentez.app-code}")
	private String APP_CODE;
	
	@Value("${levelap.paymentez.app-key}")
	private String APP_KEY;
	
	@Autowired
	private GameClubMailService mailService;
	
	public String listCurrentUserCards(HttpSession session) throws ServletException, UnsupportedEncodingException, NoSuchAlgorithmException, RestClientException, URISyntaxException {
		PublicUser currentUser = publicUserService.getCurrentUser();
		return this.getCardsOfUser(currentUser, session);
	}
	
	public String listCardsOfUser(PublicUser user, HttpSession session) throws ServletException, RestClientException, NoSuchAlgorithmException, UnsupportedEncodingException, URISyntaxException {
		return this.getCardsOfUser(user, session);
	}
	
	public HttpStatus deleteCard(HttpSession session, String cardReference) throws ServletException, NoSuchAlgorithmException, RestClientException, URISyntaxException {
		MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
		PublicUser currentUser = publicUserService.getCurrentUser();
		Date today = new Date();
		String plainText = "application_code=" + APP_CODE +
				"&card_reference=" + cardReference +
				"&session_id=" + session.getId().substring(0, Const.PAYMENTEZ_SESSION_ID_LENGTH) +
				"&uid=" + currentUser.getId() +
				"&" + today.getTime() +
				"&" + APP_KEY;
		messageDigest.update(plainText.getBytes(StandardCharsets.UTF_8));
		byte[] digestToken = messageDigest.digest();
		String token = String.format("%064x", new BigInteger(1, digestToken));
		
		String url = BASE_URL + "/api/cc/delete" +
				"?application_code=" + APP_CODE +
				"&card_reference=" + cardReference +
				"&session_id=" + session.getId().substring(0, Const.PAYMENTEZ_SESSION_ID_LENGTH) +
				"&uid=" + currentUser.getId() +
				"&auth_timestamp=" + today.getTime() +
				"&auth_token=" + token;
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(new URI(url), null, String.class);
		return response.getStatusCode();
	}
	
	public String debitFromCard(HttpSession session, String ipAddress, String cardReference, Double amount, Double taxes, String description, PublicUser publicUser) throws ServletException, NoSuchAlgorithmException, UnsupportedEncodingException, RestClientException, URISyntaxException {
		MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
		PublicUser currentUser = publicUser;
		Date today = new Date();
		String plainText = "application_code=" + APP_CODE +
				"&card_reference=" + cardReference +
				"&dev_reference=" + currentUser.getId() + "-" + cardReference + "-" + today.getTime() +
				"&email=" + URLEncoder.encode(currentUser.getUsername(), StandardCharsets.UTF_8.name()) +
				"&ip_address=" + ipAddress +
				"&product_amount=" + String.format("%1.2f", amount.doubleValue()).replaceAll(",", ".") +
				"&product_description=" + URLEncoder.encode(description, StandardCharsets.UTF_8.name()) +
				"&session_id=" + session.getId().substring(0, Const.PAYMENTEZ_SESSION_ID_LENGTH) +
				"&uid=" + currentUser.getId() +
				"&vat=" + String.format("%1.2f", taxes.doubleValue()).replaceAll(",", ".") +
				"&" + today.getTime() +
				"&" + APP_KEY;
		messageDigest.update(plainText.getBytes(StandardCharsets.UTF_8));
		byte[] digestToken = messageDigest.digest();
		String token = String.format("%064x", new BigInteger(1, digestToken));
		
		String url = BASE_URL + "/api/cc/debit" +
				"?application_code=" + APP_CODE +
				"&card_reference=" + cardReference +
				"&dev_reference=" + currentUser.getId() + "-" + cardReference + "-" + today.getTime() +
				"&email=" + URLEncoder.encode(currentUser.getUsername(), StandardCharsets.UTF_8.name()) +
				"&ip_address=" + ipAddress +
				"&product_amount=" + String.format("%1.2f", amount.doubleValue()).replaceAll(",", ".") +
				"&product_description=" + URLEncoder.encode(description, StandardCharsets.UTF_8.name()) +
				"&session_id=" + session.getId().substring(0, Const.PAYMENTEZ_SESSION_ID_LENGTH) +
				"&uid=" + currentUser.getId() +
				"&vat=" + String.format("%1.2f", taxes.doubleValue()).replaceAll(",", ".") +
				"&buyer_fiscal_number=" + currentUser.getDocument() +
				"&buyer_phone=" + currentUser.getContactPhone() +
				"&auth_timestamp=" + today.getTime() +
				"&auth_token=" + token;
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(new URI(url), null, String.class);
		return response.getBody();
	}
	
	public String refund(HttpSession session, String transactionId) throws ServletException, NoSuchAlgorithmException, UnsupportedEncodingException, RestClientException, URISyntaxException{
		MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
		Date today = new Date();
		
		String plainText = "application_code=" + APP_CODE +
				"&transaction_id=" + transactionId +
				"&" + today.getTime() +
				"&" + APP_KEY;
		
		messageDigest.update(plainText.getBytes(StandardCharsets.UTF_8));
		byte[] digestToken = messageDigest.digest();
		String token = String.format("%064x", new BigInteger(1, digestToken));
		
		String url = BASE_URL + "/api/cc/refund" +
				"?application_code=" + APP_CODE +
				"&transaction_id=" + transactionId +
				"&auth_timestamp=" + today.getTime() +
				"&auth_token=" + token;
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(new URI(url), null, String.class);
		return response.getBody();
	}
	
	private String getCardsOfUser(PublicUser user, HttpSession session) throws ServletException, NoSuchAlgorithmException, UnsupportedEncodingException, RestClientException, URISyntaxException {
		MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
		Date today = new Date();
		String plainText = "application_code=" + APP_CODE +
				"&email=" + URLEncoder.encode(user.getUsername(), StandardCharsets.UTF_8.name()) +
				"&session_id=" + session.getId().substring(0, Const.PAYMENTEZ_SESSION_ID_LENGTH) +
				"&uid=" + user.getId() +
				"&" + today.getTime() +
				"&" + APP_KEY;
		messageDigest.update(plainText.getBytes(StandardCharsets.UTF_8));
		byte[] digestToken = messageDigest.digest();
		String token = String.format("%064x", new BigInteger(1, digestToken));
		
		String url = BASE_URL + "/api/cc/list" +
				"?application_code=" + APP_CODE +
				"&uid=" + user.getId() +
				"&email=" + URLEncoder.encode(user.getUsername(), StandardCharsets.UTF_8.name()) + 
				"&session_id=" + session.getId().substring(0, Const.PAYMENTEZ_SESSION_ID_LENGTH) +
				"&auth_timestamp=" + today.getTime() +
				"&auth_token=" + token;
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(new URI(url), String.class);
		return response.getBody();
	}
	
	public void sendMailLoan(Loan loan) throws IOException, GeneralSecurityException, MessagingException {
		LevelapMail levelapMail = new LevelapMail();
		levelapMail.setFrom(Const.EMAIL_NOTIFICATIONS);
		levelapMail.setRecipentTO(Arrays.asList(loan.getGamer().getUsername()));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Map<String, String> params = new HashMap<>();
		params.put("name", loan.getGamer().getName());
		params.put("game", loan.getPublicUserGame().getGame().getName());
		params.put("console", loan.getPublicUserGame().getConsole().getName());
		params.put("user",
				loan.getGamer().getName() + " " + loan.getGamer().getLastName().substring(0, 1) + ".");
		params.put("weeks", "" + loan.getWeeks());
		if (loan.getWasAccepted()) {
			params.put("status", "aceptado");
		} else {
			params.put("status", "rechazado");
		}
		params.put("date", sdf.format(loan.getGamerStatusDate()));
		params.put("authorizationNumber", loan.getTransactionId());
		params.put("authCode", loan.getAuthCode());
		params.put("subtotal", "$" + String.format("%.2f", (loan.getCost() - loan.getTaxes())));
		params.put("iva", "$" + String.format("%.2f", loan.getTaxes()));
		params.put("total", "$" + String.format("%.2f", loan.getCost()));
		params.put("cardPart", "$" + String.format("%.2f", loan.getCardPart()));
		params.put("balancePart", "$" + String.format("%.2f", (loan.getCost() - loan.getCardPart())));

		mailService.sendMailWihTemplate(levelapMail, "MSGPYC", params);
	}
	
	public void sendMailShippinKit(WelcomeKit welcomeKit) throws IOException, GeneralSecurityException, MessagingException {
		LevelapMail levelapMail = new LevelapMail();
		levelapMail.setFrom(Const.EMAIL_NOTIFICATIONS);
		levelapMail.setRecipentTO(Arrays.asList(welcomeKit.getPublicUser().getUsername()));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Map<String, String> params = new HashMap<>();
		params.put("name", welcomeKit.getPublicUser().getName());
		if (welcomeKit.getWasConfirmed()) {
			params.put("status", "aceptado");
		} else {
			params.put("status", "rechazado");
		}
		params.put("date", sdf.format(welcomeKit.getConfirmationDate()));
		params.put("authorizationNumber", welcomeKit.getTransactionId());
		params.put("authCode", welcomeKit.getAuthCode());
		params.put("total", "$" + String.format("%.2f", welcomeKit.getAmountBalanceValue() + welcomeKit.getAmountCardValue()));
		params.put("cardPart", "$" + String.format("%.2f", welcomeKit.getAmountCardValue()));

		mailService.sendMailWihTemplate(levelapMail, "MSSHKC", params);
	}
	
	public void sendMailFine(Fine fine) throws IOException, GeneralSecurityException, MessagingException {
		LevelapMail levelapMail = new LevelapMail();
		levelapMail.setFrom(Const.EMAIL_NOTIFICATIONS);
		levelapMail.setRecipentTO(Arrays.asList(fine.getOwner().getUsername()));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Map<String, String> params = new HashMap<>();
		params.put("name", fine.getOwner().getName());
		params.put("game", fine.getLoan().getPublicUserGame().getGame().getName());
		params.put("console", fine.getLoan().getPublicUserGame().getConsole().getName());
		params.put("user", fine.getLoan().getPublicUserGame().getPublicUser().getName() + " " + fine.getLoan().getPublicUserGame().getPublicUser().getLastName().substring(0, 1) + ".");
		params.put("status", "rechazado");
		params.put("date", sdf.format(fine.getCreationDate()));
		params.put("authorizationNumber", fine.getTransactionId());
		params.put("authCode", fine.getAuthCode());
		params.put("balancePart", "$" + String.format("%.2f", fine.getCardPart()));
		
		mailService.sendMailWihTemplate(levelapMail, "MSPYCF", params);
	}
	
	public void sendConfirmationMails(Map<String, String> response, int retries) throws IOException, GeneralSecurityException, MessagingException, InterruptedException {
		Loan loan = loanService.getLoanRepo().findByTransactionId(response.get("transaction_id"));
		WelcomeKit welcomeKit = welcomeKitService.getWelcomeKitRepo().findByTransactionId(response.get("transaction_id"));
		Fine fine = fineService.getFineRepo().findByTransactionId(response.get("transaction_id"));
		
		if (loan != null) {
			loan.setAuthCode(response.get("authorization_code"));
			loan = loanService.getLoanRepo().save(loan);
			this.sendMailLoan(loan);
		} else if (welcomeKit != null) {
			welcomeKit.setAuthCode(response.get("authorization_code"));
			welcomeKit = welcomeKitService.getWelcomeKitRepo().save(welcomeKit);
			this.sendMailShippinKit(welcomeKit);
		} else if (fine != null) {
			fine.setAuthCode(response.get("authorization_code"));
			fine = fineService.getFineRepo().save(fine);
			this.sendMailFine(fine);
			System.out.println("CONFIRMATION MAIL SENT!");
		} else {
			System.err.println("UNABLE TO FIND LOAN, WELCOME KIT OR FINE WITH TRANSACTION_ID " + response.get("transaction_id"));
			
			if (retries < 10) {
				Thread.sleep(3000);
				retries++;
				System.err.println("RETRYING(" + retries + ")" + "AFTER 3 SECONDS...");
				sendConfirmationMails(response, retries);
			} else {
				System.err.println("RETRY LIMIT REACHED!");
			}
		}
	}
}
