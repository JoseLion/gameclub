package ec.com.levelap.gameclub.module.amountRequest.service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.module.amountRequest.entity.AmountRequest;
import ec.com.levelap.gameclub.module.amountRequest.repository.AmountRequestRepo;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.repository.MessageRepo;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.repository.TransactionRepo;
import ec.com.levelap.gameclub.module.transaction.service.TransactionService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Const;

@Service
public class AmountRequestService extends BaseService<AmountRequest> {
	
	public AmountRequestService() {
		super(AmountRequest.class);
	}

	@Autowired
	private AmountRequestRepo amountRequesteRepo;
	
	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	@Autowired
	private LevelapCryptography cryptoService;
	
	@Transactional
	public AmountRequest save(AmountRequest amountRequestObj) {
		Message message = new Message();
		PublicUser usr = new PublicUser();
		Transaction transaction = new Transaction();
		AmountRequest amountRequest = new AmountRequest();
		amountRequest = amountRequestObj;
		
		if(amountRequest.getId() == null) {
			AmountRequest found = amountRequesteRepo.findOne(amountRequest.getId());
			if(found != null) {
//				return new ResponseEntity<ErrorControl>(new ErrorControl("Parámetro ya existe ", true), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		try {
			usr = publicUserService.getPublicUserRepo().findOne(amountRequest.getPublicUser().getId());
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, usr.getPrivateKey());
			if(amountRequest.getRequestStatus().getCode().equals("PGSPGD") && usr.getShownBalance() > 0) {
				amountRequest.setPublicUser(usr);
				amountRequest.setAmount(usr.getBalance());
				transaction.setDebitBalanceEnc(usr.getBalance());
				transaction.setOwner(amountRequest.getPublicUser());
				transaction.setTransaction("Retiro Balance");
				transaction = transactionRepo.save(transaction);
				usr = publicUserService.substractFromUserBalance(amountRequest.getPublicUser().getId(), usr.getShownBalance());
				message.setIsLoan(false);
				message.setIsLoan(Boolean.FALSE);
				message.setIsFine(Boolean.FALSE);
				message.setIsAmountRequest(Boolean.TRUE);
				message.setOwner(amountRequestObj.getPublicUser());
				message.setDate(new Date());
				message.setSubject(Const.SBJ_AMOUNT_REQUEST);
				message = messageRepo.save(message);
				
				amountRequest.setMessage(message);
			} else if(amountRequest.getRequestStatus().getCode().equals("PGSNVS") || amountRequest.getRequestStatus().getCode().equals("PGSPRS")){
				if(usr.getShownBalance() > 0) {
					amountRequest.setAmount(cryptoService.encrypt(Double.toString(usr.getShownBalance()), key));
				} else {
					amountRequest.setAmount(cryptoService.encrypt(Double.toString(0.00D), key));
				}
			} 
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			amountRequest = amountRequesteRepo.save(amountRequest);
		}
		return amountRequest;
	}

	public AmountRequestRepo getAmountRequesteRepo() {
		return amountRequesteRepo;
	}
	
}
