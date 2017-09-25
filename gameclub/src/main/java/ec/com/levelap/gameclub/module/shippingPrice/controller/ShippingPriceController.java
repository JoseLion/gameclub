package ec.com.levelap.gameclub.module.shippingPrice.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ec.com.levelap.gameclub.module.shippingPrice.entity.ShippingPrice;
import ec.com.levelap.gameclub.module.shippingPrice.service.ShippingPriceService;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/shippingPrice", produces=MediaType.APPLICATION_JSON_VALUE)
public class ShippingPriceController {
	@Autowired
	private ShippingPriceService shippingPriceService;
	
	@RequestMapping(value="findShippingPrices", method=RequestMethod.POST)
	public ResponseEntity<Page<ShippingPrice>> findShippingPrices(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		Page<ShippingPrice> shippingPrices = shippingPriceService.getShippingPriceRepo().findShippingPrices(search.origin, search.destination, search.cost, new PageRequest(search.page, Const.TABLE_SIZE));
		return new ResponseEntity<Page<ShippingPrice>>(shippingPrices, HttpStatus.OK);
	}
	
	@RequestMapping(value="updatePrices", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updatePrices(@RequestPart MultipartFile file) throws ServletException, IOException {
		return shippingPriceService.updatePrices(file);
	}
	
	private static class Search {
		public String origin = "";
		
		public String destination = "";
		
		public Double cost;
		
		public Integer page = 0;
	}
}
