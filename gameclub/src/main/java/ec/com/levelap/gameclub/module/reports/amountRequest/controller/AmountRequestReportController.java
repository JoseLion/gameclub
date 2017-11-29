package ec.com.levelap.gameclub.module.reports.amountRequest.controller;

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

import ec.com.levelap.gameclub.module.reports.amountRequest.entity.AmountRequest;
import ec.com.levelap.gameclub.module.reports.amountRequest.repository.AmountRequestReportRepo;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/amountRequestReport", produces=MediaType.APPLICATION_JSON_VALUE)
public class AmountRequestReportController {

	@Autowired
	private AmountRequestReportRepo amtRqRpRepo;
	
	@RequestMapping(value="amountRequestAll", method=RequestMethod.GET)
	public ResponseEntity<?> amountRequestAll() throws ServletException, IOException, GeneralSecurityException {
		Page<AmountRequest> AmountsRequestsReport = amtRqRpRepo.amountRequestPage(new PageRequest(0, Const.TABLE_SIZE));
		return new ResponseEntity<>(AmountsRequestsReport, HttpStatus.OK);
	}
	
}
