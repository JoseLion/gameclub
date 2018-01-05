package ec.com.levelap.gameclub.module.mail.service;

import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.mail.entity.LevelapMail;
import ec.com.levelap.mail.service.MailService;

@Service
public class GameClubMailService {
	@Autowired
	private MailService mailService;
	
	private static String baseUrl;
	
	private static String port;
	
	public Boolean sendMailWihTemplate(LevelapMail levelapMail, String template, Map<String, String> params) throws MessagingException {
		if (baseUrl == null || port == null) {
			ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
			String currentRequest = builder.build().toUriString();
			String[] split = currentRequest.split(":");
			
			baseUrl = split[0] + ":" + split[1];
			port = split[0].equals("https") ? "8390" : "8090";
		}
		
		params.put("baseUrl", baseUrl);
		params.put("port", port);
		
		if (levelapMail.getFrom() == null) {
			levelapMail.setFrom(Const.EMAIL_NO_REPLY);
		}
		
		return mailService.sendMailWihTemplate(levelapMail, template, params);
	}
}
