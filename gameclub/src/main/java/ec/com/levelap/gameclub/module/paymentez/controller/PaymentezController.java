package ec.com.levelap.gameclub.module.paymentez.controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import ec.com.levelap.gameclub.module.paymentez.service.PaymentezService;

@RestController
@RequestMapping(value="api/paymentez", produces=MediaType.APPLICATION_JSON_VALUE)
public class PaymentezController {
	
	@Autowired
	private PaymentezService paymentezService;
	
	@RequestMapping(value="listCards", method=RequestMethod.GET)
	public ResponseEntity<String> listCards(HttpSession session) throws ServletException, RestClientException, UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException {
		String cards = paymentezService.listCards(session);
		return new ResponseEntity<String>(cards, HttpStatus.OK);
	}
	
	@RequestMapping(value="deleteCard/{cardReference}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteCard(@PathVariable String cardReference, HttpSession session) throws ServletException, RestClientException, NoSuchAlgorithmException, UnsupportedEncodingException, URISyntaxException {
		HttpStatus status = paymentezService.deleteCard(session, cardReference);
		return new ResponseEntity<>(status);
	}
}
