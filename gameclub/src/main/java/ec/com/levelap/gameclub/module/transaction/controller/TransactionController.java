package ec.com.levelap.gameclub.module.transaction.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.service.TransactionService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;

@RestController
@RequestMapping(value="api/transaction", produces=MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	

	@RequestMapping(value="lastFiveTransactions/{publicUserId}", method=RequestMethod.GET)
	public ResponseEntity<?> lastFiveTransactions(@PathVariable Long publicUserId) throws ServletException{
		List<Transaction> transactions = transactionService.getTransactionRepo().findFiveTransactions(publicUserId, new PageRequest(0, 5)).getContent();
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}
	
	@RequestMapping(value="allTransactions", method=RequestMethod.GET)
	public ResponseEntity<List<Transaction>> allTransactions(@RequestBody PublicUser publicUser) throws ServletException{
		if (publicUser == null) {
			publicUser = new PublicUser();
		}
		
		List<Transaction> transactions = new ArrayList<>();
		transactions.clear();
		transactions = transactionService.getTransactionRepo().findAll(publicUser);
		return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
	}
	
}