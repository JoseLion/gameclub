package ec.com.levelap.gameclub.module.refund.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import ec.com.levelap.gameclub.module.refund.service.RefundService;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value = "api/refund", produces = MediaType.APPLICATION_JSON_VALUE)
public class RefundController {

	@Autowired
	private RefundService refundService;
	
	@RequestMapping(value = "findRefunds", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Transaction>> findFinesFilter(@RequestBody(required = false) Search search) throws ServletException, IOException, GeneralSecurityException {
		if (search == null) {
			search = new Search();
		}
		
		Page<Transaction> transactions = refundService.getRefundRepo().findRefounds(new PageRequest(search.page, Const.TABLE_SIZE));
		return new ResponseEntity<Page<Transaction>>(transactions, HttpStatus.OK);
	}
	
	@RequestMapping(value = "applyRefund", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody Transaction transactionObj, HttpSession session, HttpServletRequest request) throws ServletException, RestClientException, IOException, GeneralSecurityException, URISyntaxException, JSONException, MessagingException {
		return refundService.save(transactionObj, Boolean.FALSE, session, request);
	}
	
	@RequestMapping(value="findRefound", method=RequestMethod.POST)
	public ResponseEntity<Page<Transaction>> findAmountRequest(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		Page<Transaction> transactions = refundService.getRefundRepo().findRefundRequest(search.name, search.startDate, search.endDate, search.transaction, search.statusRefund, new PageRequest(search.page, Const.TABLE_SIZE));
		return new ResponseEntity<Page<Transaction>>(transactions, HttpStatus.OK);
	}
	
	private static class Search {
		public String name = "";

		public Date startDate = new Date(0);

		public Date endDate = new Date();
		
		public String transaction = "";
		
		public String statusRefund = "";
		
		public Integer page = 0;
	}
}
