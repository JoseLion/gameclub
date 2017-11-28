package ec.com.levelap.gameclub.module.transaction.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.service.TransactionService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping(value = "api/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@Autowired
	private JasperService jasperService;

	@RequestMapping(value = "lastFiveTransactions", method = RequestMethod.GET)
	public ResponseEntity<?> lastFiveTransactions()
			throws ServletException, IOException, NumberFormatException, GeneralSecurityException {
		PublicUser publicUser = publicUserService.getCurrentUser();
		Page<Transaction> transactions = transactionService.getTransactionRepo().findFiveTransactions(publicUser.getId(), new PageRequest(0, 5));
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	@RequestMapping(value = "allTransactions", method = RequestMethod.GET)
	public ResponseEntity<List<Transaction>> allTransactions(@RequestBody PublicUser publicUser)
			throws ServletException {
		if (publicUser == null) {
			publicUser = new PublicUser();
		}

		List<Transaction> transactions = new ArrayList<>();
		transactions.clear();
		transactions = transactionService.getTransactionRepo().findAll(publicUser);
		return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
	}
	
	@RequestMapping(value="getTransactionsReport", method=RequestMethod.GET)
	public void getTransactionsReport(@RequestParam(required=false) Map<String, Object> params, HttpServletResponse response) throws ServletException, JRException, SQLException, IOException {
		File file = jasperService.createExcelReport("/jasper/transaction.jrxml", params);
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"transacciones.xlsx\""));
		response.setContentLengthLong(file.length());
		
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
}