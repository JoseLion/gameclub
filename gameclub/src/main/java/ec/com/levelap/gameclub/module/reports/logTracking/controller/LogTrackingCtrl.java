package ec.com.levelap.gameclub.module.reports.logTracking.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.jasper.JasperService;
import ec.com.levelap.gameclub.module.reports.logTracking.entity.LogTracking;
import ec.com.levelap.gameclub.module.reports.logTracking.repository.LogTrackingRepo;
import ec.com.levelap.gameclub.utils.Const;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping(value="api/report/logTracking", produces=MediaType.APPLICATION_JSON_VALUE)
public class LogTrackingCtrl {

	@Autowired
	private LogTrackingRepo logTrackingRepo;
	
	@Autowired
	private JasperService jasperService;
	
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
	
	@RequestMapping(value="getExcelReport", method=RequestMethod.GET)
	public void getExcelReport(@RequestParam(required=false) Map<String, Object> params, HttpServletResponse response) throws ServletException, JRException, SQLException, IOException {
		File report = jasperService.createExcelReport("/jasper/logTracking.jrxml", params);
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"log-tracking.xlsx\""));
		response.setContentLengthLong(report.length());
		
		InputStream inputStream = new BufferedInputStream(new FileInputStream(report));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
	
	@RequestMapping(value="getPdfReport", method=RequestMethod.GET)
	public void getPdfReport(@RequestParam(required=false) Map<String, Object> params, HttpServletResponse response) throws ServletException, JRException, SQLException, IOException {
		File report = jasperService.createPdfReport("/jasper/logTracking.jrxml", params);
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"log-tracking.pdf\""));
		response.setContentLengthLong(report.length());
		
		InputStream inputStream = new BufferedInputStream(new FileInputStream(report));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
	
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
