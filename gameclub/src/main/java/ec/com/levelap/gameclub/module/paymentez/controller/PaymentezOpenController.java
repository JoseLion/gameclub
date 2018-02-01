package ec.com.levelap.gameclub.module.paymentez.controller;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="open/paymentez", produces=MediaType.APPLICATION_JSON_VALUE)
public class PaymentezOpenController {
	
	@RequestMapping(value="callback", method=RequestMethod.POST)
	public ResponseEntity<?> callback(@RequestBody Object response) throws ServletException {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
