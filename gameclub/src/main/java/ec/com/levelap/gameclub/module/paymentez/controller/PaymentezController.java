package ec.com.levelap.gameclub.module.paymentez.controller;

import javax.servlet.ServletException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.paymentez.entity.PaymentezResponse;

@RestController
@RequestMapping(value="open/paymentez", produces=MediaType.APPLICATION_JSON_VALUE)
public class PaymentezController {
	
	@RequestMapping(value="callback", method=RequestMethod.POST)
	public void callback(@RequestBody PaymentezResponse response) throws ServletException {
		System.out.println("RESPONSE: " + response.toString());
	}
}
