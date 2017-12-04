package ec.com.levelap.gameclub.module.mail.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.mail.entity.LevelapMail;
import ec.com.levelap.mail.service.MailService;

@Service
public class GameClubMailService {
	@Autowired
	private MailService mailService;
	
	public Boolean sendMailWihTemplate(LevelapMail levelapMail, String template, Map<String, String> params) throws MessagingException {
		return mailService.sendMailWihTemplate(levelapMail, template, params);
	}
	
	public Boolean sendMailWihTemplate(LevelapMail levelapMail, String template, Map<String, String> params, HttpServletRequest request) throws MessagingException, MalformedURLException, ServletException {
		URL referrer = new URL(request.getHeader("referer"));
		params.put("baseUrl", referrer.getProtocol() + "://" + referrer.getHost());
		return mailService.sendMailWihTemplate(levelapMail, template, params);
	}
}
