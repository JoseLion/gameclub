package ec.com.levelap.gameclub.module.faqs.controller;

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

import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.module.faqs.entity.Faq;
import ec.com.levelap.gameclub.module.faqs.service.FaqService;

@RestController
@RequestMapping(value="api/faq", produces=MediaType.APPLICATION_JSON_VALUE)
public class FaqController {
	@Autowired
	private FaqService faqService;
	
	@RequestMapping(value="findFaqs", method=RequestMethod.POST)
	public ResponseEntity<List<Faq>> findFaqs(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		List<Faq> faqs = faqService.getFaqRepo().findFaqs(search.text, search.category, search.status);
		return new ResponseEntity<List<Faq>>(faqs, HttpStatus.OK);
	}
	
	@RequestMapping(value="findOne/{id}", method=RequestMethod.GET)
	public ResponseEntity<Faq> findOne(@PathVariable Long id) throws ServletException {
		Faq faq = faqService.getFaqRepo().findOne(id);
		return new ResponseEntity<Faq>(faq, HttpStatus.OK);
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody Faq faq) throws ServletException {
		return faqService.save(faq);
	}
	
	@RequestMapping(value="changeStatus/{id}", method=RequestMethod.GET)
	public ResponseEntity<Boolean> changeStatus(@PathVariable Long id) throws ServletException {
		Faq faq = faqService.getFaqRepo().findOne(id);
		faq = faqService.changeStatus(faq);
		faq = faqService.getFaqRepo().save(faq);
		
		return new ResponseEntity<Boolean>(faq.getStatus(), HttpStatus.OK);
	}
	
	private static class Search {
		public String text = "";
		
		public Catalog category;
		
		public Boolean status;
	}
}
