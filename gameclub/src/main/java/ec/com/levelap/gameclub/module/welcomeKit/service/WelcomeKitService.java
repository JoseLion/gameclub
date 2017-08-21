package ec.com.levelap.gameclub.module.welcomeKit.service;

import java.util.Date;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.catalog.CatalogRepo;
import ec.com.levelap.gameclub.module.message.repository.MessageRepo;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKit;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKitLite;
import ec.com.levelap.gameclub.module.welcomeKit.repository.WelcomeKitRepo;
import ec.com.levelap.gameclub.utils.Code;

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
		kit.getMessage().setRead(false);
		kit.setMessage(messageRepo.saveAndFlush(kit.getMessage()));
		kit = welcomeKitRepo.saveAndFlush(kit);
		WelcomeKitLite kitLite = welcomeKitRepo.findById(kit.getId());
		return kitLite;
	}

	public WelcomeKitRepo getWelcomeKitRepo() {
		return welcomeKitRepo;
	}
}
