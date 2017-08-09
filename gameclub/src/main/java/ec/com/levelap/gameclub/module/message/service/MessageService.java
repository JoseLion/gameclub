package ec.com.levelap.gameclub.module.message.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.repository.MessageRepo;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
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
	private WelcomeKitRepo welcomeKitRepo;
	
	@Transactional
	public void requestWelcomeKit(PublicUser user, Message message) {
		if (message == null) {
			message = new Message();
			message.setIsPromo(true);
			message.setOwner(user);
			message.setSubject(Const.SBJ_WELCOME_KIT);
			messageRepo.save(message);
		}
		
		
		WelcomeKit welcomeKit = new WelcomeKit();
		welcomeKit.setPublicUser(user);
		welcomeKit.setMessage(message);
		welcomeKitRepo.save(welcomeKit);
	}
	
	public MessageRepo getMessageRepo() {
		return messageRepo;
	}

	public WelcomeKitRepo getWelcomeKitRepo() {
		return welcomeKitRepo;
	}
}