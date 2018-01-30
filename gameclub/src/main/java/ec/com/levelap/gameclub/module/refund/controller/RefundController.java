package ec.com.levelap.gameclub.module.refund.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.refund.service.RefundService;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;

@RestController
@RequestMapping(value = "api/refund", produces = MediaType.APPLICATION_JSON_VALUE)
public class RefundController {

	@Autowired
	private RefundService refundService;
	
	@RequestMapping(value = "findRefunds", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Transaction>> findFinesFilter(@RequestBody(required = false) Search search) throws ServletException, IOException, GeneralSecurityException {
		if (search == null) {
			search = new Search();
		}

//		List<Transaction> transactions = refundService.getRefundRepo().findFines(search.name, search.apply, search.wasPayed, search.startDate, search.endDate);
		List<Transaction> transactions = refundService.getRefundRepo().findRefounds();
		return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
	}
	
	private static class Search {
		public String name = "";

		public Boolean apply;

		public Boolean wasPayed;

		public Date startDate = new Date(0);

		public Date endDate = new Date();
	}
}
