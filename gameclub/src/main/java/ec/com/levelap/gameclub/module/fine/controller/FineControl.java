package ec.com.levelap.gameclub.module.fine.controller;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.fine.service.FineService;

@RestController
@RequestMapping(value="api/fine", produces=MediaType.APPLICATION_JSON_VALUE)
public class FineControl {
	@Autowired
	private FineService fineService;
	
	@RequestMapping(value="findFnes", method=RequestMethod.GET)
	public ResponseEntity<List<Fine>> findFines() throws ServletException{
		List<Fine> fines = fineService.getFineRepo().findFines();
		return new ResponseEntity<List<Fine>>(fines, HttpStatus.OK);
	}

	public FineService getFineService() {
		return fineService;
	}

	public void setFineService(FineService fineService) {
		this.fineService = fineService;
	}
}
