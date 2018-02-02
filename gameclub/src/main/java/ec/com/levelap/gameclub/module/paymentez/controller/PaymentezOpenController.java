package ec.com.levelap.gameclub.module.paymentez.controller;

import javax.servlet.ServletException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONTokener;
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
	public ResponseEntity<?> callback(@RequestBody Object response) throws ServletException, JSONException {
		
		JSONObject json = new JSONObject((String) response);
		
		System.out.println("Transaction_Id: " + json.getString("transaction_id"));
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
