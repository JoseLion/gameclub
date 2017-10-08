/**
 * 
 */
package ec.com.levelap.gameclub.module.fine.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.fine.service.FineService;

@RestController
@RequestMapping(value="api/fine", produces=MediaType.APPLICATION_JSON_VALUE)
public class FineController {
	@Autowired
	private FineService fineService;

	@RequestMapping(value="findFines", method=RequestMethod.GET)
	public ResponseEntity<List<Fine>> findFinesFilter(@RequestBody(required=false) Search search) throws ServletException{
		if (search == null) {
			search = new Search();
		}
		
		List<Fine> fines = fineService.getFineRepo().findFines(search.name, search.apply, search.wasPayed, search.startDate, search.endDate);
		return new ResponseEntity<List<Fine>>(fines, HttpStatus.OK);
	}
	
	private static class Search {
		public String name = "";
		
		public Boolean apply;
		
		public Boolean wasPayed;
		
		public Date startDate = new Date(0);
		
		public Date endDate = new Date();
	}
}