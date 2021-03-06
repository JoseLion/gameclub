package ec.com.levelap.gameclub.module.restore.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.restore.entity.Restore;
import ec.com.levelap.gameclub.module.restore.entity.RestoreLite;
import ec.com.levelap.gameclub.module.restore.service.RestoreService;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/restore", produces=MediaType.APPLICATION_JSON_VALUE)
public class RestoreController {
	@Autowired
	private RestoreService restoreService;
	
	@RequestMapping(value="findRestores", method=RequestMethod.POST)
	public ResponseEntity<Page<RestoreLite>> findRestores(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		Page<RestoreLite> restores = restoreService.getRestoreRepo().findRestores(search.lender, search.gamer, search.shippingStatus, search.tracking, search.startDate, search.endDate, new PageRequest(search.page, Const.TABLE_SIZE));
		return new ResponseEntity<Page<RestoreLite>>(restores, HttpStatus.OK);
	}
	
	@RequestMapping(value="findOne/{id}", method=RequestMethod.GET)
	public ResponseEntity<Restore> findOne(@PathVariable Long id) throws ServletException {
		Restore restore = restoreService.getRestoreRepo().findOne(id);
		return new ResponseEntity<Restore>(restore, HttpStatus.OK);
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public ResponseEntity<RestoreLite> save(@RequestBody Restore restore, HttpSession session, HttpServletRequest request) throws ServletException, GeneralSecurityException, IOException, RestClientException, URISyntaxException {
		RestoreLite restoreLite = restoreService.save(restore, session, request);
		return new ResponseEntity<RestoreLite>(restoreLite, HttpStatus.OK);
	}
	
	@RequestMapping(value="confirmGamer", method=RequestMethod.POST)
	public ResponseEntity<Loan> confirmGamer(@RequestBody Restore restore) throws ServletException {
		Loan loan = restoreService.confirmRestore(restore, true);
		return new ResponseEntity<Loan>(loan, HttpStatus.OK);
	}
	
	@RequestMapping(value="confirmLender", method=RequestMethod.POST)
	public ResponseEntity<Loan> confirmLender(@RequestBody Restore restore) throws ServletException {
		Loan loan = restoreService.confirmRestore(restore, false);
		return new ResponseEntity<Loan>(loan, HttpStatus.OK);
	}
	
	private static class Search {
		public String lender = "";
		
		public String gamer = "";
		
		public Catalog shippingStatus;
		
		public String tracking = "";
		
		public Date startDate = new Date(0);
		
		public Date endDate = new Date();
		
		public Integer page = 0;
	}
}