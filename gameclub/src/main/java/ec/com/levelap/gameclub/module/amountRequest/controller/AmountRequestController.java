package ec.com.levelap.gameclub.module.amountRequest.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ec.com.levelap.archive.ArchiveService;
import ec.com.levelap.gameclub.module.amountRequest.entity.AmountRequest;
import ec.com.levelap.gameclub.module.amountRequest.service.AmountRequestService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;

@RestController
@RequestMapping(value="api/amountRequest", produces=MediaType.APPLICATION_JSON_VALUE)
public class AmountRequestController {
	
	@Autowired
	private AmountRequestService amountRequestService;
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@RequestMapping(value="requestBalance", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PublicUser> requestBalance(@RequestPart AmountRequest request, @RequestPart MultipartFile identityPhoto, @RequestPart(required=false) MultipartFile billPhoto) throws ServletException, IOException, GeneralSecurityException {
		return new ResponseEntity<PublicUser>(amountRequestService.requestBalance(request, identityPhoto, billPhoto), HttpStatus.OK);
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public ResponseEntity<AmountRequest> save(@RequestBody AmountRequest amtRqObj) throws ServletException, IOException, GeneralSecurityException {
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
		
		List<AmountRequest> amountRequests = amountRequestService.getAmountRequesteRepo().findAmountRequest(search.catalogId, search.name, search.lastName, search.startDate, search.endDate);
		return new ResponseEntity<List<AmountRequest>>(amountRequests, HttpStatus.OK);
	}
	
	@RequestMapping(value="findOne/{id}", method=RequestMethod.GET)
	public ResponseEntity<AmountRequest> findOne(@PathVariable Long id) throws ServletException {
		AmountRequest amountRequest = amountRequestService.getAmountRequesteRepo().findOne(id);
		return new ResponseEntity<AmountRequest>(amountRequest, HttpStatus.OK);
	}
	
	@RequestMapping(value="downloadIdentityPhoto/{id}", method=RequestMethod.GET)
	public void downloadIdentityPhoto(@PathVariable Long id, HttpServletResponse response) throws ServletException, IOException {
		AmountRequest amountRequest = amountRequestService.getAmountRequesteRepo().findOne(id);
		response.setContentType(amountRequest.getIdentityPhoto().getType());
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + amountRequest.getIdentityPhoto().getName() +"\""));
		
		InputStream inputStream = new FileInputStream(archiveService.getFile(amountRequest.getIdentityPhoto().getName(), amountRequest.getIdentityPhoto().getModule()));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
	
	@RequestMapping(value="downloadBillPhoto/{id}", method=RequestMethod.GET)
	public void downloadBillPhoto(@PathVariable Long id, HttpServletResponse response) throws ServletException, IOException {
		AmountRequest amountRequest = amountRequestService.getAmountRequesteRepo().findOne(id);
		
		if(amountRequest.getBillPhoto() != null) {
			response.setContentType(amountRequest.getBillPhoto().getType());
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + amountRequest.getBillPhoto().getName() +"\""));
				
			InputStream inputStream = new FileInputStream(archiveService.getFile(amountRequest.getBillPhoto().getName(), amountRequest.getBillPhoto().getModule()));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} 
		
	}
	
	@RequestMapping(value="getCurrentRequestAmount", method=RequestMethod.GET)
	public ResponseEntity<Double> getCurrentRequestAmount() throws ServletException, IOException, GeneralSecurityException {
		PublicUser currentUser = publicUserService.getCurrentUser();
		AmountRequest request = amountRequestService.getAmountRequesteRepo().findCurrentRequest(currentUser.getId());
		
		if (request != null) {
			return new ResponseEntity<>(request.getShowAmount(), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	private static class Search {
		
		public String name = "";
		
		public String lastName = "";
		
		public Long catalogId;
		
		public Date startDate = new Date(0);
		
		public Date endDate = new Date();
	}
}
