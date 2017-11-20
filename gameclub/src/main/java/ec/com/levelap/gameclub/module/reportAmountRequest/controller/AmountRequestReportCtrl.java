package ec.com.levelap.gameclub.module.reportAmountRequest.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.reportAmountRequest.entity.AmountRequestReport;
import ec.com.levelap.gameclub.module.reportAmountRequest.repository.AmountRequestReportRepo;

@RestController
@RequestMapping(value="api/amountRequestReport", produces=MediaType.APPLICATION_JSON_VALUE)
public class AmountRequestReportCtrl {

	@Autowired
	private AmountRequestReportRepo amtRqRpRepo;
	
	@RequestMapping(value="amountRequestAll", method=RequestMethod.POST)
	public ResponseEntity<List<AmountRequestReport>> amountRequestAll() throws ServletException, IOException, GeneralSecurityException {
		List<AmountRequestReport> AmountsRequestsReport = amtRqRpRepo.findAll();
//		List<AmountRequestReport> AmountsRequestsReport1 = amtRqRpRepo.findAllAMR();
		return new ResponseEntity<List<AmountRequestReport>>(AmountsRequestsReport, HttpStatus.OK);
	}
	
}
