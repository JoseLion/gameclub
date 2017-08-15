package ec.com.levelap.gameclub.module.loan.controller;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.service.LoanService;

@RestController
@RequestMapping(value="api/loan", produces=MediaType.APPLICATION_JSON_VALUE)
public class LoanController {
	@Autowired
	private LoanService loanService;
	
	@RequestMapping(value="requestGame", method=RequestMethod.POST)
	public ResponseEntity<?> requestGame(@RequestBody Loan loan) throws ServletException {
		loanService.requestGame(loan);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}