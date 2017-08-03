package ec.com.levelap.gameclub.module.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.repository.MessageRepo;

@Service
public class MessageService extends BaseService<Message> {
	public MessageService() {
		super(Message.class);
	}
	
	@Autowired
	private MessageRepo messageRepo;

	public MessageRepo getMessageRepo() {
		return messageRepo;
	}

	public void setMessageRepo(MessageRepo messageRepo) {
		this.messageRepo = messageRepo;
	}
}