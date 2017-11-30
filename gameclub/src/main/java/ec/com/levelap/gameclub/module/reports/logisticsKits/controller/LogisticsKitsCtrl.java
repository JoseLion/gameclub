package ec.com.levelap.gameclub.module.reports.logisticsKits.controller;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.reports.logisticsKits.entity.LogisticsKits;
import ec.com.levelap.gameclub.module.reports.logisticsKits.repository.LogisticsKitsRepo;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/report", produces=MediaType.APPLICATION_JSON_VALUE)
public class LogisticsKitsCtrl {

	@Autowired
	private LogisticsKitsRepo logisticsKitsRepo;
	
	@RequestMapping(value="logisticsKits", method=RequestMethod.GET)
	public ResponseEntity<?> logisticsKits() throws ServletException, IOException, GeneralSecurityException {
		Page<LogisticsKits> logTracking = logisticsKitsRepo.logisticsKits(new PageRequest(0, Const.TABLE_SIZE));
		return new ResponseEntity<>(logTracking, HttpStatus.OK);
	}
	
	@RequestMapping(value="totalShippingKidsSold", method=RequestMethod.GET)
	public ResponseEntity<?> totalShippingKidsSold() throws ServletException, IOException, GeneralSecurityException {
		Long totalShippingKidsSold = logisticsKitsRepo.totalShippingKitsSold();
		return new ResponseEntity<>(totalShippingKidsSold, HttpStatus.OK);
	}
	
	@RequestMapping(value="shippingKidsDelivered", method=RequestMethod.GET)
	public ResponseEntity<?> shippingKidsDelivered() throws ServletException, IOException, GeneralSecurityException {
		Long shippingKidsDelivered = logisticsKitsRepo.totalShippingKitsSold();
		return new ResponseEntity<>(shippingKidsDelivered, HttpStatus.OK);
	}
	
	@RequestMapping(value="welcomeKitsDelivered", method=RequestMethod.GET)
	public ResponseEntity<?> welcomeKitsDelivered() throws ServletException, IOException, GeneralSecurityException {
		Long welcomeKitsDelivered = logisticsKitsRepo.totalShippingKitsSold();
		return new ResponseEntity<>(welcomeKitsDelivered, HttpStatus.OK);
	}
	
	@RequestMapping(value="findLogisticsKits", method=RequestMethod.POST)
	public ResponseEntity<List<LogisticsKits>> findLogisticsKits(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		List<LogisticsKits> logisticsKits = logisticsKitsRepo.findLogisticsKit(search.name, search.document, search.transaction, search.startDate, search.endDate);
		return new ResponseEntity<List<LogisticsKits>>(logisticsKits, HttpStatus.OK);
	}
	
	private static class Search {
		
		public String name = "";
		
		public String document = "";
		
		public String transaction = "";
		
		public Date startDate = new Date(0);
		
		public Date endDate = new Date();
	}
}
