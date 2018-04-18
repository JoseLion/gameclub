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

import ec.com.levelap.gameclub.module.paymentez.service.PaymentezService;

@RestController
@RequestMapping(value="open/paymentez", produces=MediaType.APPLICATION_JSON_VALUE)
public class PaymentezOpenController {
	
	@Autowired
	private PaymentezService paymentezService;
	
	@RequestMapping(value="callback", method=RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> callback(HttpServletRequest servletRequest) throws ServletException, JSONException, IOException, GeneralSecurityException, MessagingException, URISyntaxException, InterruptedException {
		System.out.println("PROCESSING PAYMENTEZ CALLBACK");
		String url = "https://gamclub.com.ec?" + IOUtils.toString(servletRequest.getInputStream(), StandardCharsets.UTF_8);
		List<NameValuePair> params = URLEncodedUtils.parse(new URI(url), StandardCharsets.UTF_8);
		Map<String, String> response = new HashMap<>();
		
		for (NameValuePair param : params) {
			response.put(param.getName(), param.getValue());
		}
		System.out.println("Entra metodo de CALLBACK");
		System.out.println("Paymentez callback processed with status_detail: " + response.get("status_detail"));
		
		if (!response.get("status_detail").equals("7")) {
			paymentezService.sendConfirmationMails(response, 0);
		}
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
