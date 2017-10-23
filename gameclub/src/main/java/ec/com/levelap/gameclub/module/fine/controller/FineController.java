/**
 * 
 */
package ec.com.levelap.gameclub.module.fine.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.fine.service.FineService;
import ec.com.levelap.gameclub.module.kushki.entity.KushkiSubscription;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.repository.MessageRepo;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKit;
import ec.com.levelap.gameclub.module.welcomeKit.repository.WelcomeKitRepo;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.kushki.KushkiException;
import ec.com.levelap.kushki.object.KushkiAmount;
import ec.com.levelap.kushki.service.KushkiService;

@RestController
@RequestMapping(value="api/fine", produces=MediaType.APPLICATION_JSON_VALUE)
public class FineController {
	@Autowired
	private FineService fineService;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@Autowired
	private KushkiService kushkiService;
	
	@Autowired
	private MessageRepo messageRepo;

	@RequestMapping(value="findFines", method=RequestMethod.GET)
	public ResponseEntity<List<Fine>> findFinesFilter(@RequestBody(required=false) Search search) throws ServletException{
		if (search == null) {
			search = new Search();
		}
		
		List<Fine> fines = fineService.getFineRepo().findFines(search.name, search.apply, search.wasPayed, search.startDate, search.endDate);
		return new ResponseEntity<List<Fine>>(fines, HttpStatus.OK);
	}
	
	@RequestMapping(value="notApplyFine", method=RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody Fine fineObj) throws ServletException, IOException{
		fineObj.setApply(false);
		return fineService.save(fineObj);
	}
	
	@RequestMapping(value="applyFine", method=RequestMethod.POST)
	public ResponseEntity<?> saveFineBalance(@RequestBody Fine fineObj) throws ServletException, IOException{
		PublicUser usr = new PublicUser();
		Double subtraction = 0D;
		Message message = new Message();
		try {
			if(fineObj.getOwner() != null) {
				subtraction = fineObj.getOwner().getShownBalance() - fineObj.getAmount();
 				if(subtraction >= 0) { 
					usr = publicUserService.substractFromUserBalance(fineObj.getOwner().getId(), fineObj.getAmount());
					fineObj.setBalancePart(fineObj.getAmount());
				} else if(subtraction < 0) {
					Map<String, Object> optionals = new HashMap<>();
					optionals.put("amount", new KushkiAmount(Math.abs(subtraction)));
					String ticket = "";
					for (KushkiSubscription kushkiObj : fineObj.getOwner().getPaymentMethods()) {
						if(kushkiObj.getStatus() == true) {
							usr = publicUserService.substractFromUserBalance(fineObj.getOwner().getId(), fineObj.getOwner().getShownBalance());
							ticket = kushkiService.subscriptionCharge(kushkiObj.getSubscriptionId(), optionals);
							fineObj.setBalancePart(Double.parseDouble(fineObj.getOwner().getShownBalance().toString()));
							fineObj.setCardPart(Math.abs(subtraction));
							break;
						}
					}
				}
 				message = new Message();
 				message.setIsLoan(false);
 				message.setOwner(fineObj.getOwner());
 				Date date = new Date();
 				message.setDate(date);
 				message.setSubject(Const.SBJ_FINE);
 				messageRepo.save(message);
 				message = messageRepo.findRecentMessage(fineObj.getOwner(), Const.SBJ_FINE, date);
			}
			
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (KushkiException ke) {
			ke.printStackTrace();
		}
		
		if(usr.getId() != null ) {
			fineObj.setOwner(usr);
			fineObj.setApply(true);
			fineObj.setWasPayed(true);
			fineObj.setMessage(message);
			return fineService.save(fineObj);
		} else {
			return fineService.save(fineObj);
		}
	}

	@RequestMapping(value="findFinesMessages", method=RequestMethod.POST)
	public ResponseEntity<List<Fine>> findFinesMessages(PublicUser owner) throws ServletException, IOException{
		List<Fine> fines = fineService.getFineRepo().findFinesMessages(owner);
		return new ResponseEntity<List<Fine>>(fines, HttpStatus.OK);
	}
	
	public PublicUserService getPublicUserService() {
		return publicUserService;
	}

	public void setPublicUserService(PublicUserService publicUserService) {
		this.publicUserService = publicUserService;
	}

	private static class Search {
		public String name = "";
		
		public Boolean apply;
		
		public Boolean wasPayed;
		
		public Date startDate = new Date(0);
		
		public Date endDate = new Date();
	}
}