package ec.com.levelap.gameclub.module.mail.service;

import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ec.com.levelap.mail.entity.LevelapMail;
import ec.com.levelap.mail.service.MailService;

@Service
public class GameClubMailService {
	@Autowired
	private MailService mailService;
	
	public Boolean sendMailWihTemplate(LevelapMail levelapMail, String template, Map<String, String> params) throws MessagingException {
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
		String currentRequest = builder.build().toUriString();
		String[] split = currentRequest.split(":");
		params.put("baseUrl", split[0] + ":" + split[1]);
		params.put("port", split[0].equals("https") ? "8390" : "8090");
		
		return mailService.sendMailWihTemplate(levelapMail, template, params);
	}
}
