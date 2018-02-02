package ec.com.levelap.gameclub.module.refund.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.paymentez.service.PaymentezService;
import ec.com.levelap.gameclub.module.refund.repository.RefundRepo;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;

@Service
public class RefundService extends BaseService<Transaction>{

	public RefundService() {
		super(Transaction.class);
	}
	
	@Autowired
	private RefundRepo refundRepo;
	
	@Autowired
	private PaymentezService paymentezService;
	
	public ResponseEntity<?> save(Transaction transaction, Boolean isApply, HttpSession session, HttpServletRequest request) throws ServletException, IOException, GeneralSecurityException, RestClientException, URISyntaxException, JSONException, MessagingException{
		
		Transaction transactionTmp = refundRepo.findOne(transaction.getId());
		
		String response = paymentezService.refund(session, transactionTmp.getCcTransaction());
		
		JSONObject json = new JSONObject(response);
		if(json.getString("status").equals("success")) {
			transactionTmp.setStatusRefund("ACREDITADO");
		}
		
		transactionTmp = refundRepo.save(transactionTmp);
		
		return new ResponseEntity<>(transactionTmp, HttpStatus.OK);
	}

	public RefundRepo getRefundRepo() {
		return refundRepo;
	}
	
}
