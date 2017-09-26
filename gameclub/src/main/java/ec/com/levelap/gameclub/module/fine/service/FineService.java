package ec.com.levelap.gameclub.module.fine.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.fine.repository.FineRepo;

@Service
public class FineService extends BaseService<Fine> {
	public FineService() {
		super(Fine.class);
	}

	@Autowired
	private FineRepo fineRepo;
	
	@Transactional
	public ResponseEntity<?> save(Fine fineObj) throws ServletException, IOException{
		if(fineObj.getId() == null) {
			Fine found = fineRepo.findOne(fineObj.getId());
			if(found != null) {
				return new ResponseEntity<ErrorControl>(new ErrorControl("Par�metro ya existe ", true), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		fineRepo.save(fineObj);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public FineRepo getFineRepo() {
		return fineRepo;
	}
}
