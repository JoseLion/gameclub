package ec.com.levelap.gameclub.module.reports.leaseCosts.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import ec.com.levelap.gameclub.module.reports.leaseCosts.entity.LeaseCost;
import ec.com.levelap.gameclub.module.reports.leaseCosts.repository.LeaseCostRepo;
import ec.com.levelap.gameclub.utils.Const;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping(value="api/report/leaseCost", produces=MediaType.APPLICATION_JSON_VALUE)
public class LeaseCostsController {
	
	@Autowired
	private LeaseCostRepo leaseCostRepo;
	
	@Autowired
	private JasperService jasperService;
	
	@RequestMapping(value="find", method=RequestMethod.POST)
	public ResponseEntity<Page<LeaseCost>> find(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		Page<LeaseCost> leaseCosts = leaseCostRepo.findLeaseCosts(search.name, search.document, search.totalStart, search.totalEnd, search.dateStart, search.dateEnd, new PageRequest(search.page, Const.TABLE_SIZE));
		return new ResponseEntity<Page<LeaseCost>>(leaseCosts, HttpStatus.OK);
	}
	
	@RequestMapping(value="getExcelReport", method=RequestMethod.GET)
	public void getExcelReport(@RequestParam(required=false) Map<String, Object> params, HttpServletResponse response) throws ServletException, JRException, SQLException, IOException {
		File report = jasperService.createExcelReport("/jasper/leaseCost.jrxml", params);
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"costos-arriendo.xlsx\""));
		response.setContentLengthLong(report.length());
		
		InputStream inputStream = new BufferedInputStream(new FileInputStream(report));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
	
	@RequestMapping(value="getPdfReport", method=RequestMethod.GET)
	public void getPdfReport(@RequestParam(required=false) Map<String, Object> params, HttpServletResponse response) throws ServletException, JRException, SQLException, IOException {
		File report = jasperService.createPdfReport("/jasper/leaseCost.jrxml", params);
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"costos-arriendo.pdf\""));
		response.setContentLengthLong(report.length());
		
		InputStream inputStream = new BufferedInputStream(new FileInputStream(report));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
	
	private static class Search {
		public String name = "";
		
		public String document = "";
		
		public Date dateStart = new Date(0);
		
		public Date dateEnd = new Date();
		
		public Double totalStart = 0.0;
		
		public Double totalEnd = Double.MAX_VALUE;
		
		public Integer page = 0;
	}
}
