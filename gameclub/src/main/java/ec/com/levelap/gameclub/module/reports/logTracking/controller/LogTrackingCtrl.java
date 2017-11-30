package ec.com.levelap.gameclub.module.reports.logTracking.controller;

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
	
	@RequestMapping(value="findLogTracking", method=RequestMethod.POST)
	public ResponseEntity<List<LogTracking>> findLogTracking(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		List<LogTracking> logTracking = logTrackingRepo.findLogTracking(search.name, search.document, search.game, search.startDate, search.endDate);
		return new ResponseEntity<List<LogTracking>>(logTracking, HttpStatus.OK);
	}
	
	private static class Search {
		
		public String name = "";
		
		public String document = "";
		
		public String game = "";
		
		public Date startDate = new Date(0);
		
		public Date endDate = new Date();
	}
}
