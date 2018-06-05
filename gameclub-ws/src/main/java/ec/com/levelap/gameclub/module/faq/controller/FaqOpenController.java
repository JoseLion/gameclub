package ec.com.levelap.gameclub.module.faq.controller;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.faq.entity.Faq;
import ec.com.levelap.gameclub.module.faq.service.FaqService;

@RestController
@RequestMapping(value="open/faq", produces=MediaType.APPLICATION_JSON_VALUE)
public class FaqOpenController {
	@Autowired
	private FaqService faqService;
	
	@RequestMapping(value="getFaqsFromCategory/{categoryId}", method=RequestMethod.GET)
	public ResponseEntity<List<Faq>> getFaqsFromCategory(@PathVariable Long categoryId) throws ServletException {
		List<Faq> faqs = faqService.getFaqRepo().findByCategoryIdOrderByCreationDate(categoryId);
		return new ResponseEntity<List<Faq>>(faqs, HttpStatus.OK);
	}
}
