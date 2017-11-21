package ec.com.levelap.gameclub.module.paymentez.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

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

import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;

@Service
public class PaymentezService {
	@Autowired
	private PublicUserService publicUserService;
	
	@Value("${levelap.paymentez.base-url}")
	private String BASE_URL;
	
	@Value("${levelap.paymentez.app-code}")
	private String APP_CODE;
	
	@Value("${levelap.paymentez.app-key}")
	private String APP_KEY;
	
	public String listCards(HttpSession session) throws ServletException, UnsupportedEncodingException, NoSuchAlgorithmException, RestClientException, URISyntaxException {
		MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
		PublicUser currentUser = publicUserService.getCurrentUser();
		Date today = new Date();
		String plainText = "application_code=" + APP_CODE +
				"&email=" + URLEncoder.encode(currentUser.getUsername(), StandardCharsets.UTF_8.name()) +
				"&session_id=" + session.getId() +
				"&uid=" + currentUser.getId() +
				"&" + today.getTime() +
				"&" + APP_KEY;
		messageDigest.update(plainText.getBytes(StandardCharsets.UTF_8));
		byte[] digestToken = messageDigest.digest();
		String token = String.format("%064x", new BigInteger(1, digestToken));
		
		String url = BASE_URL + "/api/cc/list" +
				"?application_code=" + APP_CODE +
				"&uid=" + currentUser.getId() +
				"&email=" + URLEncoder.encode(currentUser.getUsername(), StandardCharsets.UTF_8.name()) + 
				"&session_id=" + session.getId() +
				"&auth_timestamp=" + today.getTime() +
				"&auth_token=" + token;
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(new URI(url), String.class);
		return response.getBody();
	}
	
	public HttpStatus deleteCard(HttpSession session, String cardReference) throws ServletException, NoSuchAlgorithmException, RestClientException, URISyntaxException {
		MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
		PublicUser currentUser = publicUserService.getCurrentUser();
		Date today = new Date();
		String plainText = "application_code=" + APP_CODE +
				"&card_reference=" + cardReference +
				"&session_id=" + session.getId() +
				"&uid=" + currentUser.getId() +
				"&" + today.getTime() +
				"&" + APP_KEY;
		messageDigest.update(plainText.getBytes(StandardCharsets.UTF_8));
		byte[] digestToken = messageDigest.digest();
		String token = String.format("%064x", new BigInteger(1, digestToken));
		
		String url = BASE_URL + "/api/cc/delete" +
				"?application_code=" + APP_CODE +
				"&card_reference=" + cardReference +
				"&session_id=" + session.getId() +
				"&uid=" + currentUser.getId() +
				"&auth_timestamp=" + today.getTime() +
				"&auth_token=" + token;
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(new URI(url), null, String.class);
		return response.getStatusCode();
	}
	
	public String debitFromCard(HttpSession session, String ipAddress, String cardReference, Double amount, Double taxes, String description) throws ServletException, NoSuchAlgorithmException, UnsupportedEncodingException, RestClientException, URISyntaxException {
		MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
		PublicUser currentUser = publicUserService.getCurrentUser();
		Date today = new Date();
		String plainText = "application_code=" + APP_CODE +
				"&card_reference=" + cardReference +
				"&dev_reference=" + currentUser.getId() + "-" + cardReference + "-" + today.getTime() +
				"&email=" + URLEncoder.encode(currentUser.getUsername(), StandardCharsets.UTF_8.name()) +
				"&ip_address=" + ipAddress +
				"&product_amount=" + String.format("%1.2f", amount.doubleValue()) +
				"&product_description=" + URLEncoder.encode(description, StandardCharsets.UTF_8.name()) +
				"&session_id=" + session.getId() +
				"&uid=" + currentUser.getId() +
				"&vat=" + String.format("%1.2f", taxes.doubleValue()) +
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
				"&product_amount=" + String.format("%1.2f", amount.doubleValue()) +
				"&product_description=" + URLEncoder.encode(description, StandardCharsets.UTF_8.name()) +
				"&session_id=" + session.getId() +
				"&uid=" + currentUser.getId() +
				"&vat=" + String.format("%1.2f", taxes.doubleValue()) +
				"&buyer_fiscal_number=" + currentUser.getDocument() +
				"&buyer_phone=" + currentUser.getContactPhone() +
				"&auth_timestamp=" + today.getTime() +
				"&auth_token=" + token;
		
		System.out.println("PLAIN TEXT: " + plainText);
		System.out.println("URL: " + url);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(new URI(url), null, String.class);
		return response.getBody();
	}
}
