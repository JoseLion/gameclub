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

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.location.Location;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKit;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKitLite;
import ec.com.levelap.gameclub.module.welcomeKit.service.WelcomeKitService;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.tcc.wsdl.clientes.GrabarDespacho4Response;

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
		
		Page<WelcomeKitLite> welcomeKits = welcomeKitService.getWelcomeKitRepo().findWelcomeKits(search.name, search.startDate, search.endDate, search.province, search.city, search.wasConfirmed, search.shippingStatus, new PageRequest(search.page, Const.TABLE_SIZE));
		return new ResponseEntity<Page<WelcomeKitLite>>(welcomeKits, HttpStatus.OK);
	}
	
	@RequestMapping(value="findOne/{id}", method=RequestMethod.GET)
	public ResponseEntity<WelcomeKit> findOne(@PathVariable Long id) throws ServletException {
		WelcomeKit kit = welcomeKitService.getWelcomeKitRepo().findOne(id);
		return new ResponseEntity<WelcomeKit>(kit, HttpStatus.OK);
	}
	
	@RequestMapping(value="confirmWelcomeKit", method=RequestMethod.POST)
	public ResponseEntity<?> confirmWelcomeKit(@RequestBody ConfirmObj confirmObj) throws ServletException {
		GrabarDespacho4Response response = welcomeKitService.confirmWelcomeKit(confirmObj);
		
		if (response.getRespuesta() == 0) {
			return new ResponseEntity<GrabarDespacho4Response>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<ErrorControl>(new ErrorControl(response.getMensaje(), true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private static class Search {
		public String name = "";
		
		public Date startDate = new Date(0);
		
		public Date endDate = new Date();
		
		public Location province;
		
		public Location city;
		
		public Boolean wasConfirmed;
		
		public Catalog shippingStatus;
		
		public Integer page = 0;
	}
	
	public static class ConfirmObj {
		public Long kitId;
		
		public String address;
		
		public String phone;
		
		public String city;
		
		public String receiver;
	}
}
