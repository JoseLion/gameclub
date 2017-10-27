package ec.com.levelap.gameclub.module.welcomeKit.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Map;

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
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKit;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKitLite;
import ec.com.levelap.gameclub.module.welcomeKit.service.WelcomeKitService;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.kushki.KushkiException;

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
	
	@RequestMapping(value = "requestShippingKit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> requestShippingKit(@RequestBody Map<String, Object> amounts) throws ServletException {
		if (amounts.containsKey("amountBalance") && amounts.containsKey("amountCard") && amounts.containsKey("paymentId")) {
			PublicUser publicUser = null;
			try {
				publicUser = welcomeKitService.saveShippingKit(
						Double.valueOf(amounts.get("amountBalance").toString()),
						Double.valueOf(amounts.get("amountCard").toString()),
						Long.valueOf(amounts.get("paymentId").toString()));
			} catch (IOException | GeneralSecurityException ex) {
				ex.printStackTrace();
				return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
			}
			return new ResponseEntity<>(publicUser, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
	}
	
	@RequestMapping(value = "confirmShippingKit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> confirmShippingKit(@RequestBody Long shippingKitId) throws ServletException {
		try {
			welcomeKitService.confirmShippingKit(shippingKitId);
		} catch (IOException | GeneralSecurityException | KushkiException ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
		return new ResponseEntity<>(HttpStatus.OK);
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
