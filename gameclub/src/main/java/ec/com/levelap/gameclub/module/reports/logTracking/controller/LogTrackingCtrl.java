package ec.com.levelap.gameclub.module.reports.logTracking.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

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

import ec.com.levelap.gameclub.module.reports.logTracking.entity.LogTracking;
import ec.com.levelap.gameclub.module.reports.logTracking.repository.LogTrackingRepo;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/report", produces=MediaType.APPLICATION_JSON_VALUE)
public class LogTrackingCtrl {

	@Autowired
	private LogTrackingRepo logTrackingRepo;
	
	@RequestMapping(value="logTracking", method=RequestMethod.GET)
	public ResponseEntity<?> logTracking() throws ServletException, IOException, GeneralSecurityException {
		Page<LogTracking> logTracking = logTrackingRepo.logTrackingPage(new PageRequest(0, Const.TABLE_SIZE));
		return new ResponseEntity<>(logTracking, HttpStatus.OK);
	}
	
//	@RequestMapping(value="totalBilling", method=RequestMethod.GET)
//	public ResponseEntity<?> totalBilling() throws ServletException, IOException, GeneralSecurityException {
//		Double totalBilling = billingRepo.total();
//		return new ResponseEntity<>(totalBilling, HttpStatus.OK);
//	}
}
