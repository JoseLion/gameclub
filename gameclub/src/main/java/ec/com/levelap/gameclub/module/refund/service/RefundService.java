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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.paymentez.service.PaymentezService;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.repository.TransactionRepo;

@Service
public class RefundService extends BaseService<Transaction>{

	public RefundService() {
		super(Transaction.class);
	}
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	@Autowired
	private PaymentezService paymentezService;
	
	public Transaction save(Transaction transaction, HttpSession session, HttpServletRequest request) throws ServletException, IOException, GeneralSecurityException, RestClientException, URISyntaxException, JSONException, MessagingException{
		transaction = transactionRepo.findOne(transaction.getId());
		String response = paymentezService.refund(session, transaction.getCcTransaction());
		JSONObject json = new JSONObject(response);
		
		if (json.getString("status").equals("success")) {
			transaction.setStatusRefund("ACREDITADO");
		}
		
		transaction = transactionRepo.save(transaction);
		return transaction;
	}

	public TransactionRepo getTransactionRepo() {
		return transactionRepo;
	}
}
