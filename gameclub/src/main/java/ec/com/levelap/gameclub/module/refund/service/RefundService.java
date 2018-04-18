package ec.com.levelap.gameclub.module.refund.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

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

import ec.com.levelap.base.entity.ErrorControl;
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
	
	public ResponseEntity<?> save(Transaction transaction, HttpSession session, HttpServletRequest request) throws ServletException, IOException, GeneralSecurityException, RestClientException, URISyntaxException, JSONException, MessagingException{
		transaction = transactionRepo.findOne(transaction.getId());
		String response = paymentezService.refund(session, transaction.getCcTransaction());
		JSONObject json = new JSONObject(response);
		
		if (json.getString("status").equals("success")) {
			transaction.setStatusRefund("ACREDITADO");
		} else {
			Map<String, String> responseError = new HashMap<>();
			System.out.println("Error refund" + response);
//			paymentezService.sendMailError(responseError, "", "Invalid Status");
			return new ResponseEntity<ErrorControl>(new ErrorControl("La transacción de Paymentez no se procesó correctamente. Statusw: " + json.getString("status"), true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		transaction = transactionRepo.save(transaction);
		return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
	}

	public TransactionRepo getTransactionRepo() {
		return transactionRepo;
	}
	
	private String descriptionError(String response) {
		Map<String, String> error = new HashMap<>();
		error.put("0", "Pago en espera.");
		error.put("1", "Se requiere verificación, ver la sección Verificación.");
		error.put("4", "Fraude.");
		error.put("8", "Contracargo.");
		error.put("9", "Rechazado por el transportista.");
		error.put("10", "Error de Sistema.");
		error.put("11", "Fraude de Paymentez.");
		error.put("12", "Lista negra Pamentez.");
		error.put("13", "Tolerancia de tiempo.");
		error.put("19", "Código de Autrización Inválido.");
		error.put("20", "Código de autorización expirado.");
		error.put("21", "Fraude de Paymentez - Reembolso Pendiente.");
		error.put("22", "Código Autorización Inválido - Reembolso Pendiente.");
		error.put("23", "Código Autorización Expirado - Reembolso Pendiente.");
		error.put("24", "Fraude de Paymentez - Reembolso Pendiente.");
		error.put("25", "Código Autorización Inválido - Reembolso Pendiente.");
		error.put("26", "Código Autorización Expirado - Reembolso Pendiente.");
		error.put("27", "Comerciante - Reembolso Pendiente.");
		error.put("28", "Comerciante - Reembolso Pendiente.");
		error.put("35", "Transacción sentada (Datafast).");
		
//		String key = response.get("status_detail").toString();
		String description = "";//error.get(key);
//		System.out.println(description);
		
		return description;
	}
}
