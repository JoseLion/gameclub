package ec.com.levelap.gameclub.module.mail.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.gameclub.module.mail.entity.Mail;
import ec.com.levelap.gameclub.module.mail.repository.MailRepo;
import ec.com.levelap.mail.entity.LevelapMail;

@Service
public class GameClubMailService {
	
	@Autowired
	private MailRepo mailRepo;
	
	@Autowired
	private ec.com.levelap.mail.service.MailService mailService;
	
	public Boolean sendMailWihTemplate(LevelapMail levelapMail, String template, Map<String, String> params) throws MessagingException {
		Mail mail = this.mailRepo.findByAcronym(template);
		
		if (mail != null) {
			levelapMail.setSubject(mail.getSubject());
			return mailService.sendMailWihTemplate(levelapMail, template, params);
		}
		
		return false;
	}
	
	public Boolean sendMailWihTemplate(LevelapMail levelapMail, String template, Map<String, String> params, HttpServletRequest request) throws MessagingException, MalformedURLException {
		URL referrer = new URL(request.getHeader("referer"));
		Mail mail = this.mailRepo.findByAcronym(template);
		
		params.put("baseUrl", referrer.getProtocol() + "://" + referrer.getHost());
		
		if (mail != null) {
			levelapMail.setSubject(mail.getSubject());
			return mailService.sendMailWihTemplate(levelapMail, template, params);
		}
		
		return false;
	}
}
