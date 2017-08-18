package ec.com.levelap.gameclub.module.welcomeKit.service;

import java.util.Date;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKit;
import ec.com.levelap.gameclub.module.welcomeKit.repository.WelcomeKitRepo;

@Service
public class WelcomeKitService extends BaseService<WelcomeKit> {
	public WelcomeKitService() {
		super(WelcomeKit.class);
	}
	
	@Autowired
	private WelcomeKitRepo welcomeKitRepo;
	
	@Value("${tcc-configuration.key}")
	private String tccKey;
	
	@Value("${tcc-configuration.account}")
	private String tccAccount;
	
	@Value("${tcc-configuration.nit}")
	private String tccNit;
	
	@Transactional
	public WelcomeKit confirmWelcomeKit(WelcomeKit welcomeKit) throws ServletException {
		welcomeKit.setWasConfirmed(true);
		welcomeKit.setConfirmationDate(new Date());
		welcomeKit = welcomeKitRepo.save(welcomeKit);
		return welcomeKit;
	}

	public WelcomeKitRepo getWelcomeKitRepo() {
		return welcomeKitRepo;
	}
}
