package ec.com.levelap.gameclub.module.welcomeKit.controller;

import java.util.Date;

import javax.servlet.ServletException;

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

import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.location.Location;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKit;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKitLite;
import ec.com.levelap.gameclub.module.welcomeKit.service.WelcomeKitService;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/welcomeKit", produces=MediaType.APPLICATION_JSON_VALUE)
public class WelcomeKitController {
	@Autowired
	private WelcomeKitService welcomeKitService;
	
	@RequestMapping(value="findWelcomeKits", method=RequestMethod.POST)
	public ResponseEntity<Page<WelcomeKitLite>> findWelcomeKits(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		Page<WelcomeKitLite> welcomeKits = welcomeKitService.getWelcomeKitRepo().findWelcomeKits(search.name, search.startDate, search.endDate, search.province, search.city, search.tracking, search.shippingStatus, new PageRequest(search.page, Const.TABLE_SIZE));
		return new ResponseEntity<Page<WelcomeKitLite>>(welcomeKits, HttpStatus.OK);
	}
	
	@RequestMapping(value="findOne/{id}", method=RequestMethod.GET)
	public ResponseEntity<WelcomeKit> findOne(@PathVariable Long id) throws ServletException {
		WelcomeKit kit = welcomeKitService.getWelcomeKitRepo().findOne(id);
		return new ResponseEntity<WelcomeKit>(kit, HttpStatus.OK);
	}
	
	@RequestMapping(value="confirmWelcomeKit", method=RequestMethod.POST)
	public ResponseEntity<WelcomeKit> confirmWelcomeKit(@RequestBody WelcomeKit kit) throws ServletException {
		kit = welcomeKitService.confirmWelcomeKit(kit);
		return new ResponseEntity<WelcomeKit>(kit, HttpStatus.OK);
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public ResponseEntity<WelcomeKitLite> save(@RequestBody WelcomeKit kit) throws ServletException {
		WelcomeKitLite kitLite = welcomeKitService.save(kit);
		return new ResponseEntity<WelcomeKitLite>(kitLite, HttpStatus.OK);
	}
	
	private static class Search {
		public String name = "";
		
		public Date startDate = new Date(0);
		
		public Date endDate = new Date();
		
		public Location province;
		
		public Location city;
		
		public String tracking = "";
		
		public Catalog shippingStatus;
		
		public Integer page = 0;
	}
}
