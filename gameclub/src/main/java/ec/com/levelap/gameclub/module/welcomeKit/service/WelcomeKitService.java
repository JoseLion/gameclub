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
	public PublicUser saveShippingKit(Double amountBalance, Double amountCard, Long paymentId)
			throws ServletException, IOException, GeneralSecurityException {
		PublicUser publicUser = publicUserService.getCurrentUser();
		File key = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(key, publicUser.getPrivateKey());

		Message message = new Message();
		message.setIsLoan(false);
		message.setOwner(publicUser);
		message.setSubject(Const.SBJ_SHIPPING_KIT);
		messageRepo.save(message);

		WelcomeKit welcomeKit = new WelcomeKit();
		welcomeKit.setPublicUser(publicUser);
		welcomeKit.setMessage(message);
		welcomeKit.setAmountBalance(cryptoService.encrypt(Double.toString(amountBalance), key));
		welcomeKit.setAmountCard(cryptoService.encrypt(Double.toString(amountCard), key));
		welcomeKit.setPaymentId(paymentId);
		welcomeKitRepo.save(welcomeKit);
		
		return publicUserService.getCurrentUser();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public void confirmShippingKit(Long shippingKitId)
			throws ServletException, IOException, GeneralSecurityException, KushkiException {
		PublicUser publicUser = publicUserService.getCurrentUser();
		File key = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(key, publicUser.getPrivateKey());

		WelcomeKit shippingKit = welcomeKitRepo.findOne(shippingKitId);
		Double amountBalance = Double.valueOf(cryptoService.decrypt(shippingKit.getAmountBalance(), key));
		Double amountCard = Double.valueOf(cryptoService.decrypt(shippingKit.getAmountCard(), key));

		Double balance = Double.valueOf(cryptoService.decrypt(publicUser.getBalance(), key));
		publicUser.setBalance(cryptoService.encrypt(Double.toString(balance - amountBalance), key));
		publicUserRepo.save(publicUser);

		Map<String, Object> kushkiSubscription = new HashMap<>();
		kushkiSubscription.put("amount", amountCard);
		kushkiService.subscriptionCharge(shippingKit.getPaymentId().toString(), kushkiSubscription);
	}

	public WelcomeKitRepo getWelcomeKitRepo() {
		return welcomeKitRepo;
	}

}
