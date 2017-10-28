package ec.com.levelap.gameclub.module.welcomeKit.service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.catalog.CatalogRepo;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.module.kushki.entity.KushkiSubscription;
import ec.com.levelap.gameclub.module.kushki.repository.KushkiSubscriptionRepo;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.repository.MessageRepo;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.repository.PublicUserRepo;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKit;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKitLite;
import ec.com.levelap.gameclub.module.welcomeKit.repository.WelcomeKitRepo;
import ec.com.levelap.gameclub.utils.Code;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.kushki.KushkiException;
import ec.com.levelap.kushki.service.KushkiService;

@Service
public class WelcomeKitService extends BaseService<WelcomeKit> {
	public WelcomeKitService() {
		super(WelcomeKit.class);
	}
	
	@Autowired
	private WelcomeKitRepo welcomeKitRepo;
	
	@Autowired
	private CatalogRepo catalogRepo;
	
	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private PublicUserRepo publicUserRepo;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@Autowired
	private LevelapCryptography cryptoService;
	
	@Autowired
	private KushkiService kushkiService;
	
	@Autowired
	private KushkiSubscriptionRepo kushkiSubscriptionRepo;
	
	@Transactional
	public WelcomeKit confirmWelcomeKit(WelcomeKit welcomeKit) throws ServletException {
		Catalog shippingStatus = catalogRepo.findByCode(Code.SHIPPING_NO_TRACKING);
		welcomeKit.setShippingStatus(shippingStatus);
		welcomeKit.setWasConfirmed(true);
		welcomeKit.setConfirmationDate(new Date());
		welcomeKit = welcomeKitRepo.save(welcomeKit);
		return welcomeKit;
	}
	
	@Transactional
	public WelcomeKitLite save(WelcomeKit kit) throws ServletException {
		WelcomeKit previous = welcomeKitRepo.findOne(kit.getId());
		
		if (!kit.getShippingStatus().equals(previous.getShippingStatus()) || (kit.getShippingNote() != null && !kit.getShippingNote().equalsIgnoreCase(previous.getShippingNote()))) {
			kit.getMessage().setRead(false);
			kit.setMessage(messageRepo.saveAndFlush(kit.getMessage()));
		}
		
		if (kit.getShippingStatus().getCode().equals(Code.SHIPPING_DELIVERED)) {
			kit.getPublicUser().setIsReady(true);
			kit.setPublicUser(publicUserRepo.saveAndFlush(kit.getPublicUser()));
		}

		kit = welcomeKitRepo.saveAndFlush(kit);
		WelcomeKitLite kitLite = welcomeKitRepo.findById(kit.getId());
		return kitLite;
	}

	@Transactional
	public PublicUser saveShippingKit(Integer quantity, Double amountBalance, Double amountCard, Long paymentId)
			throws ServletException, IOException, GeneralSecurityException {
		PublicUser publicUser = publicUserService.getCurrentUser();
		File key = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(key, publicUser.getPrivateKey());

		Message message = new Message();
		message.setIsLoan(false);
		message.setOwner(publicUser);
		message.setSubject(Const.SBJ_SHIPPING_KIT);
		message.setIsShippingKit(Boolean.TRUE);
		messageRepo.save(message);

		WelcomeKit welcomeKit = new WelcomeKit();
		welcomeKit.setPublicUser(publicUser);
		welcomeKit.setMessage(message);
		welcomeKit.setQuantity(quantity);
		welcomeKit.setAmountBalance(cryptoService.encrypt(Double.toString(amountBalance), key));
		welcomeKit.setAmountCard(cryptoService.encrypt(Double.toString(amountCard), key));
		welcomeKit.setPaymentId(paymentId);
		welcomeKitRepo.save(welcomeKit);
		
		return publicUserService.getCurrentUser();
	}

	@Transactional
	public WelcomeKit confirmShippingKit(WelcomeKit shippingKit) throws ServletException {
		Catalog shippingStatus = catalogRepo.findByCode(Code.SHIPPING_NO_TRACKING);
		WelcomeKit finalShippingKit = welcomeKitRepo.findOne(shippingKit.getId());
		finalShippingKit.setShippingStatus(shippingStatus);
		finalShippingKit.setWasConfirmed(Boolean.TRUE);
		finalShippingKit.setConfirmationDate(new Date());
		finalShippingKit.setAddress(shippingKit.getAddress());
		finalShippingKit.setGeolocation(shippingKit.getGeolocation());
		finalShippingKit.setPhone(shippingKit.getPhone());
		finalShippingKit.setReceiver(shippingKit.getReceiver());
		return welcomeKitRepo.save(finalShippingKit);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public WelcomeKitLite sendShippingKit(Long shippingKitId, String tracking, String shippingNote)
			throws ServletException, IOException, NumberFormatException, GeneralSecurityException {
		WelcomeKit shippingKit = welcomeKitRepo.findOne(shippingKitId);		
		PublicUser publicUser = publicUserService.getPublicUserRepo().findOne(shippingKit.getPublicUser().getId());
		
		File key = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(key, publicUser.getPrivateKey());
		
		Double amountBalance = Double.valueOf(cryptoService.decrypt(shippingKit.getAmountBalance(), key));
		Double amountCard = Double.valueOf(cryptoService.decrypt(shippingKit.getAmountCard(), key));
		Double balance = Double.valueOf(cryptoService.decrypt(publicUser.getBalance(), key));
		Double totalBalance = balance - amountBalance;
		
		if (totalBalance < 0) {
			amountCard += Math.abs(totalBalance);
			amountBalance = balance;
			balance = 0D;
			shippingKit.setAmountBalance(cryptoService.encrypt(Double.toString(amountBalance), key));
			shippingKit.setAmountCard(cryptoService.encrypt(Double.toString(amountCard), key));
		} else {
			balance = totalBalance;
		}

		if(amountCard > 0) {
			try {
				Map<String, Object> kushkiSubscription = new HashMap<>();
				kushkiSubscription.put("amount", amountCard);
				KushkiSubscription subscription = kushkiSubscriptionRepo.findOne(shippingKit.getPaymentId());
				kushkiService.subscriptionCharge(subscription.getSubscriptionId(), kushkiSubscription);
			} catch (KushkiException ex) {
				ex.printStackTrace();
				throw new ServletException(ex);
			}
		}
		
		Catalog shippingStatus = catalogRepo.findByCode(Code.SHIPPING_DELIVERED);
		
		shippingKit.setShippingStatus(shippingStatus);
		shippingKit.setTracking(tracking);
		shippingKit.setShippingNote(shippingNote);
		shippingKit = welcomeKitRepo.save(shippingKit);
		
		Message message = messageRepo.findOne(shippingKit.getMessage().getId());
		message.setRead(Boolean.FALSE);
		messageRepo.save(message);
		
		publicUser.setBalance(cryptoService.encrypt(Double.toString(balance), key));
		publicUserRepo.save(publicUser);
		return welcomeKitRepo.findById(shippingKit.getId());
	}

	public WelcomeKitRepo getWelcomeKitRepo() {
		return welcomeKitRepo;
	}

}
