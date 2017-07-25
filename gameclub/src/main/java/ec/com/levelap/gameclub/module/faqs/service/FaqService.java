package ec.com.levelap.gameclub.module.faqs.service;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.faqs.entity.Faq;
import ec.com.levelap.gameclub.module.faqs.repository.FaqRepo;

@Service
public class FaqService extends BaseService<Faq> {
	@Autowired
	private FaqRepo faqRepo;
	
	@Transactional
	public ResponseEntity<?> save(Faq faq) throws ServletException {
		Faq found = null;
		
		if (faq.getId() != null) {
			Faq original = faqRepo.findOne(faq.getId());
			found = faqRepo.findByQuestionIgnoreCaseAndQuestionIsNotIgnoreCase(faq.getQuestion(), original.getQuestion());
		} else {
			found = faqRepo.findByQuestionIgnoreCase(faq.getQuestion());
		}
		
		if (found != null) {
			return new ResponseEntity<ErrorControl>(new ErrorControl("La pregunta ingresada ya existe", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		faqRepo.save(faq);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public FaqService() {
		super(Faq.class);
	}

	public FaqRepo getFaqRepo() {
		return faqRepo;
	}
}
