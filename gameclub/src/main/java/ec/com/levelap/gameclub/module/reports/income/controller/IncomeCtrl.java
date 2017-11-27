package ec.com.levelap.gameclub.module.reports.income.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.reports.income.entity.Income;
import ec.com.levelap.gameclub.module.reports.income.repository.IncomeRepo;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/report", produces=MediaType.APPLICATION_JSON_VALUE)
public class IncomeCtrl {

	@Autowired
	private IncomeRepo incomeRepo;
	
	@RequestMapping(value="incomePage", method=RequestMethod.GET)
	public ResponseEntity<?> incomePage() throws ServletException, IOException, GeneralSecurityException {
		Page<Income> incomeList = incomeRepo.incomePage(new PageRequest(0, Const.TABLE_SIZE));
		return new ResponseEntity<>(incomeList, HttpStatus.OK);
	}
	
	@RequestMapping(value="findIncome", method=RequestMethod.POST)
	public ResponseEntity<List<Income>> findIncome(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		List<Income> incomeSearch = incomeRepo.findIncome(search.name, search.lastName, search.startDate, search.endDate);
		return new ResponseEntity<List<Income>>(incomeSearch, HttpStatus.OK);
	}
	
	private static class Search {
		
		public String name = "";
		
		public String lastName = "";
		
		public Date startDate = new Date(0);
		
		public Date endDate = new Date();
	}
	
}
