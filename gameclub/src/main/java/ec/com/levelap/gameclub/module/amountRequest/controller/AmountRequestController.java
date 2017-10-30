package ec.com.levelap.gameclub.module.amountRequest.controller;


import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.amountRequest.entity.AmountRequest;
import ec.com.levelap.gameclub.module.amountRequest.service.AmountRequestService;
@RestController
@RequestMapping(value="api/amountRequest", produces=MediaType.APPLICATION_JSON_VALUE)
public class AmountRequestController {
	
	@Autowired
	private AmountRequestService amountRequestService;
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public ResponseEntity<AmountRequest> save(@RequestBody AmountRequest amtRqObj) throws ServletException, IOException{
//		return amountRequestService.save(amtRqObj);
		AmountRequest amountRequest = amountRequestService.save(amtRqObj);
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
