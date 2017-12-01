package ec.com.levelap.gameclub.module.mail.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.mail.entity.Mail;
import ec.com.levelap.gameclub.module.mail.repository.MailRepo;
import ec.com.levelap.mail.MailParameters;

@Service
public class MailService extends BaseService<Mail> {
	public MailService() {
		super(Mail.class);
	}
	
	@Autowired
	private MailRepo mailRepo;
	
	@Autowired
	private ec.com.levelap.mail.Mail levelapMail;
	
	public Boolean sendMailWihTemplate(MailParameters mailParameters, String template, Map<String, String> params) throws MessagingException {
		Mail mail = this.mailRepo.findByAcronym(template);
		
		if (mail != null) {
			String content = levelapMail.replaceParams(params, mail.getContent());
			mailParameters.setSubject(mail.getSubject());
			mailParameters.setContent(content);
			levelapMail.send(mailParameters);
			
			return true;
		}
		
		return false;
	}
	
	public Boolean sendMailWihTemplate(MailParameters mailParameters, String template, Map<String, String> params, HttpServletRequest request) throws MessagingException, MalformedURLException {
		URL referrer = new URL(request.getHeader("referer"));
		Mail mail = this.mailRepo.findByAcronym(template);
		
		params.put("baseUrl", referrer.getProtocol() + "://" + referrer.getHost());
		
		if (mail != null) {
			String content = levelapMail.replaceParams(params, mail.getContent());
			mailParameters.setSubject(mail.getSubject());
			mailParameters.setContent(content);
			levelapMail.send(mailParameters);
			
			return true;
		}
		
		return false;
	}
}
