package ec.com.levelap.gameclub.module.amountRequest.controller;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import ec.com.levelap.gameclub.module.amountRequest.entity.AmountRequest;
import ec.com.levelap.gameclub.module.amountRequest.service.AmountRequestService;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.repository.MessageRepo;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.service.TransactionService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.kushki.KushkiException;


@RestController
@RequestMapping(value="api/amountRequest", produces=MediaType.APPLICATION_JSON_VALUE)
public class AmountRequestController {
	
	@Autowired
	private AmountRequestService amountRequestService;
	
	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@Autowired
	private TransactionService transactionService;
	
//	@SuppressWarnings("finally")
	@RequestMapping(value="save", method=RequestMethod.POST)
	public ResponseEntity<AmountRequest> save(@RequestBody AmountRequest amtRqObj) {
		Message message = new Message();
		PublicUser usr = new PublicUser();
		Transaction transaction = new Transaction();
		AmountRequest amountRequest = new AmountRequest();
		
		try {			
			if(amtRqObj.getRequestStatus().getCode().equals("PGSPGD")) {
				transaction.setCreditPart(amountRequest.getPublicUser().getShownBalance());
				transaction.setOwner(amountRequest.getPublicUser());
				transaction.setTransaction("Retir Balance");
				transaction = transactionService.getTransactionRepo().save(transaction);
				
				usr = publicUserService.substractFromUserBalance(amountRequest.getPublicUser().getId(), amountRequest.getPublicUser().getShownBalance());
				
				message.setIsLoan(false);
				message.setOwner(amountRequest.getPublicUser());
				message.setDate(new Date());
				message.setSubject(Const.SBJ_AMOUNT_REQUEST);
				message = messageRepo.save(message);
				amountRequest.setMessage(message);
				amountRequest = amountRequestService.save(amtRqObj);
			} else {
				amountRequest = amountRequestService.save(amtRqObj);
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} //finally {
//			return new ResponseEntity<AmountRequest>(amountRequest, HttpStatus.OK);
		//}
		
		return new ResponseEntity<AmountRequest>(amountRequest, HttpStatus.OK);
	}
	
	@RequestMapping(value="findAll", method=RequestMethod.POST)
	public ResponseEntity<List<AmountRequest>> findAll() throws ServletException {
		List<AmountRequest> amountRequests = amountRequestService.getAmountRequesteRepo().findAll();
		return new ResponseEntity<List<AmountRequest>>(amountRequests, HttpStatus.OK);
	}
	
	@RequestMapping(value="findAmountRequest", method=RequestMethod.POST)
	public ResponseEntity<List<AmountRequest>> findAmountRequest(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}

		System.out.println(search.name);
		System.out.println(search.lastName);
		System.out.println(search.catalogId);
		List<AmountRequest> amountRequests = amountRequestService.getAmountRequesteRepo().findAmountRequest(search.catalogId, search.name, search.lastName, search.startDate, search.endDate);
//		List<AmountRequest> amountRequests = amountRequestService.getAmountRequesteRepo().findAmountRequest(/*search.name, search.lastName,*/ search.catalogId/*, search.startDate, search.endDate*/);List<AmountRequest> amountRequests = amountRequestService.getAmountRequesteRepo().findAmountRequest(/*search.name, search.lastName,*/ search.catalogId/*, search.startDate, search.endDate*/);
		System.out.println(search.catalogId);
		return new ResponseEntity<List<AmountRequest>>(amountRequests, HttpStatus.OK);
	}
	
	@RequestMapping(value="findOne/{id}", method=RequestMethod.GET)
	public ResponseEntity<AmountRequest> findOne(@PathVariable Long id) throws ServletException {
		AmountRequest amountRequest = amountRequestService.getAmountRequesteRepo().findOne(id);
		return new ResponseEntity<AmountRequest>(amountRequest, HttpStatus.OK);
	}
	
	private static class Search {
		public String name = "";
		
		public String lastName = "";
		
		public Long catalogId;
		
		public Date startDate = new Date(0);
		
		public Date endDate = new Date();
	}
}
