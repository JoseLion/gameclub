package ec.com.levelap.gameclub.module.restore.service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.catalog.CatalogRepo;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.repository.LoanRepo;
import ec.com.levelap.gameclub.module.message.service.MessageService;
import ec.com.levelap.gameclub.module.restore.entity.Restore;
import ec.com.levelap.gameclub.module.restore.entity.RestoreLite;
import ec.com.levelap.gameclub.module.restore.repository.RestoreRepo;
import ec.com.levelap.gameclub.module.settings.service.SettingService;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.repository.TransactionRepo;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Code;
import ec.com.levelap.kushki.KushkiException;

@Service
public class RestoreService {
	@Autowired
	private RestoreRepo restoreRepo;
	
	@Autowired
	private LoanRepo loanRepo;
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private LevelapCryptography cryptoService;

	@Autowired
	private TransactionRepo transactionRepo;
	
	@Autowired
	private CatalogRepo catalogRepo;
	
	@Transactional
	public RestoreLite save(Restore restore) throws ServletException, GeneralSecurityException, IOException, KushkiException {
		Restore previous = restoreRepo.findOne(restore.getId());
		restore.setLoan(previous.getLoan());
		
		Catalog shippingStatus = catalogRepo.findByCode(restore.getShippingStatus().getCode());
		
		if (!shippingStatus.equals(previous.getShippingStatus()) || (restore.getShippingNote() != null && !restore.getShippingNote().equalsIgnoreCase(previous.getShippingNote()))) {
			restore.setLenderStatusDate(new Date());
			restore.setGamerStatusDate(new Date());
			
			restore.getLenderMessage().setRead(false);
			restore.getGamerMessage().setRead(false);
			messageService.getMessageRepo().save(restore.getLenderMessage());
			messageService.getMessageRepo().save(restore.getGamerMessage());
		}
		
		PublicUser gamer = publicUserService.getPublicUserRepo().findOne(restore.getGamer().getId());
		File keyGamer = File.createTempFile("keyGamer", ".tmp");
		FileUtils.writeByteArrayToFile(keyGamer, gamer.getPrivateKey());
		
		PublicUser lender = publicUserService.getPublicUserRepo().findOne(restore.getPublicUserGame().getPublicUser().getId());
		File keyLender = File.createTempFile("keyLender", ".tmp");
		FileUtils.writeByteArrayToFile(keyLender, lender.getPrivateKey());
		
		Transaction transaction;
		
		if (shippingStatus.getCode().equals(Code.SHIPPING_GAMER_DIDNT_DELIVER)) {
			Double value = Double.valueOf(settingService.getSettingValue(Code.SETTING_GAMER_DIDNT_DELIVER));
			gamer =  publicUserService.substractFromUserBalance(restore.getGamer().getId(), value);
			lender = publicUserService.addToUserBalance(restore.getPublicUserGame().getPublicUser().getId(), value);
			
			transaction = new Transaction(
					gamer,
					"MULTA - " + shippingStatus.getName(),
					restore.getPublicUserGame().getGame().getName(),
					restore.getLoan().getWeeks(),
					null,
					cryptoService.encrypt(Double.toString(value), keyGamer),
					null);
			transactionRepo.save(transaction);
			
			transaction = new Transaction(
					lender,
					"DEVOLICION",
					restore.getPublicUserGame().getGame().getName(),
					restore.getLoan().getWeeks(),
					cryptoService.encrypt(Double.toString(value), keyLender),
					null,
					null);
			transactionRepo.save(transaction);
			
		} else if (shippingStatus.getCode().equals(Code.SHIPPING_GAMER_DIDNT_DELIVER_2ND)) {
			// TODO: en vez de cobrar el valor del balance se debe cobrar de la tarjeta.
			Double value = restore.getPublicUserGame().getGame().getUploadPayment();
			gamer = publicUserService.substractFromUserBalance(restore.getGamer().getId(), value);
			
			transaction = new Transaction(
					gamer,
					"MULTA - " + shippingStatus.getName(),
					restore.getPublicUserGame().getGame().getName(),
					restore.getLoan().getWeeks(),
					null,
					cryptoService.encrypt(Double.toString(value), keyGamer),
					null);
			transactionRepo.save(transaction);
		}
		
		restoreRepo.save(restore);
		RestoreLite restoreLite = restoreRepo.findById(restore.getId());
		
		return restoreLite;
	}
	
	@Transactional
	public Loan confirmRestore(Restore restore, boolean isGamer) throws ServletException {
		Restore previous = restoreRepo.findOne(restore.getId());
		restore.setLoan(previous.getLoan());
		
		if (isGamer) {
			restore.setGamerConfirmDate(new Date());
		} else {
			restore.setLenderConfirmDate(new Date());
		}
		
		restore = restoreRepo.save(restore);
		return loanRepo.findOne(restore.getLoan().getId());
	}

	public RestoreRepo getRestoreRepo() {
		return restoreRepo;
	}
}
