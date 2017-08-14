package ec.com.levelap.gameclub.module.message.service;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.repository.MessageRepo;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKit;
import ec.com.levelap.gameclub.module.welcomeKit.repository.WelcomeKitRepo;
import ec.com.levelap.gameclub.utils.Const;

@Service
public class MessageService extends BaseService<Message> {
	public MessageService() {
		super(Message.class);
	}
	
	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@Autowired
	private WelcomeKitRepo welcomeKitRepo;
	
	@Transactional
	public void requestWelcomeKit(PublicUser user, Message message) {
		if (message == null) {
			message = new Message();
			message.setIsLoan(false);
			message.setOwner(user);
			message.setSubject(Const.SBJ_WELCOME_KIT);
			messageRepo.save(message);
		}
		
		
		WelcomeKit welcomeKit = new WelcomeKit();
		welcomeKit.setPublicUser(user);
		welcomeKit.setMessage(message);
		welcomeKitRepo.save(welcomeKit);
	}
	
	@Transactional
	public void createLoanMessages(PublicUser lender) throws ServletException {
		PublicUser gamer = publicUserService.getCurrentUser();
		
		Message gamerMessage = new Message();
		gamerMessage.setOwner(gamer);
		gamerMessage.setTo(lender);
		gamerMessage.setSubject(Const.SBJ_GAME_REQUEST);
		gamerMessage.setIsLoan(true);
		messageRepo.save(gamerMessage);
		
		Message lenderMessage = new Message();
		lenderMessage.setOwner(lender);
		lenderMessage.setFrom(gamer);
		lenderMessage.setSubject(Const.SBJ_LOAN_REQUEST);
		lenderMessage.setIsLoan(true);
		messageRepo.save(lenderMessage);
	}
	
	public MessageRepo getMessageRepo() {
		return messageRepo;
	}

	public WelcomeKitRepo getWelcomeKitRepo() {
		return welcomeKitRepo;
	}
}