package ec.com.levelap.gameclub.module.reports.amountRequestReport.controller;

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

import ec.com.levelap.gameclub.module.reports.amountRequestReport.entity.AmountRequestReport;
import ec.com.levelap.gameclub.module.reports.amountRequestReport.repository.AmountRequestReportRepo;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/amountRequestReport", produces=MediaType.APPLICATION_JSON_VALUE)
public class AmountRequestReportCtrl {

	@Autowired
	private AmountRequestReportRepo amtRqRpRepo;
	
	@RequestMapping(value="amountRequestAll", method=RequestMethod.GET)
	public ResponseEntity<?> amountRequestAll() throws ServletException, IOException, GeneralSecurityException {
		Page<AmountRequestReport> AmountsRequestsReport = amtRqRpRepo.amountRequestPage(new PageRequest(0, Const.TABLE_SIZE));
		return new ResponseEntity<>(AmountsRequestsReport, HttpStatus.OK);
	}
	
}
