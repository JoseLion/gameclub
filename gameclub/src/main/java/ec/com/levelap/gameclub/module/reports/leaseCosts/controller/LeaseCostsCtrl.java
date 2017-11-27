package ec.com.levelap.gameclub.module.reports.leaseCosts.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.reports.leaseCosts.entity.LeaseCosts;
import ec.com.levelap.gameclub.module.reports.leaseCosts.repository.LeaseCostsRepo;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/report", produces=MediaType.APPLICATION_JSON_VALUE)
public class LeaseCostsCtrl {
	
	@Autowired
	private LeaseCostsRepo leaseCostsRepo;
	
	@RequestMapping(value="leaseCostsPage", method=RequestMethod.GET)
	public ResponseEntity<?> leaseCostsPage() throws ServletException, IOException, GeneralSecurityException {
		Page<LeaseCosts> leaseCostsList = leaseCostsRepo.leaseCostsPage(new PageRequest(0, Const.TABLE_SIZE));
		return new ResponseEntity<>(leaseCostsList, HttpStatus.OK);
	}

}
