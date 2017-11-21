package ec.com.levelap.gameclub.module.amountRequest.service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ec.com.levelap.archive.Archive;
import ec.com.levelap.archive.ArchiveService;
import ec.com.levelap.base.entity.FileData;
import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.catalog.CatalogService;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.module.amountRequest.entity.AmountRequest;
import ec.com.levelap.gameclub.module.amountRequest.repository.AmountRequestRepo;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.repository.MessageRepo;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.repository.TransactionRepo;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Code;
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
	private TransactionRepo transactionRepo;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private LevelapCryptography cryptoService;
	
	@Transactional
	public PublicUser requestBalance(AmountRequest request, MultipartFile identityPhoto, MultipartFile billPhoto) throws ServletException, IOException, GeneralSecurityException {
		PublicUser currentUser = publicUserService.getCurrentUser();
		Catalog requestStatus = catalogService.getCatalogRepo().findByCode(Code.PAYMENT_NEW_REQUEST);
		FileData identityFileData = archiveService.saveFile(identityPhoto, AmountRequest.class.getSimpleName());
		Archive identity = new Archive();
		File key = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(key, currentUser.getPrivateKey());
		
		identity.setModule(AmountRequest.class.getSimpleName());
		identity.setName(identityFileData.getName());
		identity.setType(identityPhoto.getContentType());
		
		request.setPublicUser(currentUser);
		request.setAmount(cryptoService.encrypt(currentUser.getShownBalance().toString(), key));
		request.setRequestStatus(requestStatus);
		request.setIdentityPhoto(identity);
		
		if (billPhoto != null) {
			FileData billFileData = archiveService.saveFile(billPhoto, AmountRequest.class.getSimpleName());
			Archive bill = new Archive();
			bill.setModule(AmountRequest.class.getSimpleName());
			bill.setName(billFileData.getName());
			bill.setType(billPhoto.getContentType());
			
			request.setBillPhoto(bill);
		}
		
		amountRequesteRepo.save(request);
		
		currentUser.setIsRequestingBalance(true);
		currentUser = publicUserService.getPublicUserRepo().save(currentUser);
		return currentUser;
	}
	
	@Transactional
	public AmountRequest save(AmountRequest amountRequest) throws IOException, GeneralSecurityException, ServletException {
		Message message = new Message();
		PublicUser user = publicUserService.getPublicUserRepo().findOne(amountRequest.getPublicUser().getId());
		Transaction transaction = new Transaction();
		
		File key = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(key, user.getPrivateKey());
		if(amountRequest.getRequestStatus().getCode().equals(Code.PAYMENT_PAYED) && user.getShownBalance() > 0) {
			user = publicUserService.substractFromUserBalance(amountRequest.getPublicUser().getId(), user.getShownBalance());
			user.setIsRequestingBalance(Boolean.FALSE);
			user = publicUserService.getPublicUserRepo().save(user);
			
			amountRequest.setPublicUser(user);
			amountRequest.setAmount(user.getBalance());
			amountRequest.setPaymentDate(new Date());
			transaction.setDebitBalanceEnc(user.getBalance());
			transaction.setOwner(amountRequest.getPublicUser());
			transaction.setTransaction("Retiro Balance");
			transaction = transactionRepo.save(transaction);	
			
			message.setIsLoan(false);
			message.setIsLoan(Boolean.FALSE);
			message.setIsFine(Boolean.FALSE);
			message.setIsAmountRequest(Boolean.TRUE);
			message.setOwner(amountRequest.getPublicUser());
			message.setDate(new Date());
			message.setSubject(Const.SBJ_AMOUNT_REQUEST);
			message = messageRepo.save(message);
			
			amountRequest.setMessage(message);
		} else if(amountRequest.getRequestStatus().getCode().equals(Code.PAYMENT_NEW_REQUEST) || amountRequest.getRequestStatus().getCode().equals("PGSPRS")){
			if(user.getShownBalance() > 0) {
				amountRequest.setAmount(cryptoService.encrypt(Double.toString(user.getShownBalance()), key));
			} else {
				amountRequest.setAmount(cryptoService.encrypt(Double.toString(0.00D), key));
			}
		}
		
		amountRequest = amountRequesteRepo.save(amountRequest);
		return amountRequest;
	}

	public AmountRequestRepo getAmountRequesteRepo() {
		return amountRequesteRepo;
	}
	
}
