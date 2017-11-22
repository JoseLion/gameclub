package ec.com.levelap.gameclub.module.reports.billing.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.reports.billing.entity.Billing;
import ec.com.levelap.gameclub.module.reports.billing.repository.BillingRepo;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/report", produces=MediaType.APPLICATION_JSON_VALUE)
public class BillingCtrl {
	
	@Autowired
	private BillingRepo billingRepo;
	
	@RequestMapping(value="billing", method=RequestMethod.GET)
	public ResponseEntity<?> billing() throws ServletException, IOException, GeneralSecurityException {
		Page<Billing> billings = billingRepo.billingPage(new PageRequest(0, Const.TABLE_SIZE));
		return new ResponseEntity<>(billings, HttpStatus.OK);
	}
	
	@RequestMapping(value="totalBilling", method=RequestMethod.GET)
	public ResponseEntity<?> totalBilling() throws ServletException, IOException, GeneralSecurityException {
		Double totalBilling = billingRepo.total();
		return new ResponseEntity<>(totalBilling, HttpStatus.OK);
	}

}
