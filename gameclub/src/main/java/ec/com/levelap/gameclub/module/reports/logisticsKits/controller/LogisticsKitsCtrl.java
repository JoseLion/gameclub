package ec.com.levelap.gameclub.module.reports.logisticsKits.controller;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Date;
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
import ec.com.levelap.gameclub.module.reports.logisticsKits.entity.LogisticsKits;
import ec.com.levelap.gameclub.module.reports.logisticsKits.repository.LogisticsKitsRepo;
import ec.com.levelap.gameclub.utils.Const;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping(value="api/report/logisticsKits", produces=MediaType.APPLICATION_JSON_VALUE)
public class LogisticsKitsCtrl {

	@Autowired
	private LogisticsKitsRepo logisticsKitsRepo;
	
	@Autowired
	private JasperService jasperService;
	
	@RequestMapping(value="find", method=RequestMethod.POST)
	public ResponseEntity<Page<LogisticsKits>> find(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		Page<LogisticsKits> logisticsKits = logisticsKitsRepo.find(search.name, search.document, search.transaction, search.startDate, search.endDate, new PageRequest(search.page, Const.TABLE_SIZE));
		return new ResponseEntity<Page<LogisticsKits>>(logisticsKits, HttpStatus.OK);
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
	
	@RequestMapping(value="getExcelReport", method=RequestMethod.GET)
	public void getExcelReport(@RequestParam(required=false) Map<String, Object> params, HttpServletResponse response) throws ServletException, JRException, SQLException, IOException {
		File report = jasperService.createExcelReport("/jasper/logisticsKits.jrxml", params);
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"logistics-kits.xlsx\""));
		response.setContentLengthLong(report.length());
		
		InputStream inputStream = new BufferedInputStream(new FileInputStream(report));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
	
	@RequestMapping(value="getPdfReport", method=RequestMethod.GET)
	public void getPdfReport(@RequestParam(required=false) Map<String, Object> params, HttpServletResponse response) throws ServletException, JRException, SQLException, IOException {
		File report = jasperService.createPdfReport("/jasper/logisticsKits.jrxml", params);
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"logistics-kits.pdf\""));
		response.setContentLengthLong(report.length());
		
		InputStream inputStream = new BufferedInputStream(new FileInputStream(report));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
	
	private static class Search {
		
		public String name = "";
		
		public String document = "";
		
		public String transaction = "";
		
		public Date startDate = new Date(0);
		
		public Date endDate = new Date();
		
		public Integer page = 0;
	}
}
