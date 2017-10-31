package ec.com.levelap.gameclub.module.amountRequest.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ec.com.levelap.base.entity.FileData;
import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.commons.archive.Archive;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.catalog.CatalogService;
import ec.com.levelap.commons.service.DocumentService;
import ec.com.levelap.gameclub.module.amountRequest.entity.AmountRequest;
import ec.com.levelap.gameclub.module.amountRequest.repository.AmountRequestRepo;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Code;

@Service
public class AmountRequestService extends BaseService<AmountRequest> {
	
	public AmountRequestService() {
		super(AmountRequest.class);
	}

	@Autowired
	private AmountRequestRepo amountRequesteRepo;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private DocumentService documentService;
	
	@Transactional
	public PublicUser requestBalance(AmountRequest request, MultipartFile identityPhoto) throws ServletException, IOException, GeneralSecurityException {
		PublicUser currentUser = publicUserService.getCurrentUser();
		Catalog requestStatus = catalogService.getCatalogRepo().findByCode(Code.PAYMENT_NEW_REQUEST);
		FileData fileData = documentService.saveFile(identityPhoto, AmountRequest.class.getSimpleName());
		Archive archive = new Archive();
		
		archive.setModule(AmountRequest.class.getSimpleName());
		archive.setName(fileData.getName());
		archive.setType(identityPhoto.getContentType());
		
		request.setPublicUser(currentUser);
		request.setAmount(currentUser.getShownBalance());
		request.setRequestStatus(requestStatus);
		request.setIdentityPhoto(archive);
		
		amountRequesteRepo.save(request);
		
		currentUser.setIsRequestingBalance(true);
		currentUser = publicUserService.getPublicUserRepo().save(currentUser);
		return currentUser;
	}
	
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
