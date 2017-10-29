package ec.com.levelap.gameclub.module.amountRequest.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.amountRequest.entity.AmountRequest;
import ec.com.levelap.gameclub.module.amountRequest.repository.AmountRequestRepo;

@Service
public class AmountRequestService extends BaseService<AmountRequest> {
	
	public AmountRequestService() {
		super(AmountRequest.class);
	}

	@Autowired
	private AmountRequestRepo amountRequesteRepo;
	
	@Transactional
	public AmountRequest save(AmountRequest amountRequestObj) throws ServletException, IOException{
		if(amountRequestObj.getId() == null) {
			AmountRequest found = amountRequesteRepo.findOne(amountRequestObj.getId());
			if(found != null) {
//				return new ResponseEntity<ErrorControl>(new ErrorControl("Parámetro ya existe ", true), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		amountRequestObj = amountRequesteRepo.save(amountRequestObj);
		return amountRequestObj;
	}

	public AmountRequestRepo getAmountRequesteRepo() {
		return amountRequesteRepo;
	}
	
}
