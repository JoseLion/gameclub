package ec.com.levelap.gameclub.module.user.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;

@RestController
@RequestMapping(value="open/publicUser", produces=MediaType.APPLICATION_JSON_VALUE)
public class PublicUserOpenController {
	@Autowired
	private PublicUserService publicUserService;
	
	@RequestMapping(value="signIn", method=RequestMethod.POST)
	public ResponseEntity<?> signIn(@RequestBody SignObj signObj) throws ServletException, MessagingException, IOException, GeneralSecurityException {
		return publicUserService.signIn(signObj.publicUser, signObj.baseUrl, signObj.token);
	}
	
	@RequestMapping(value="verifyAccount/{token}/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> verifyAccount(@PathVariable String token, @PathVariable Long id, HttpServletResponse response) throws ServletException, IOException {
		PublicUser user = publicUserService.getPublicUserRepo().findOne(id);
		
		if (user != null) {
			if (user.getToken() == null) {
				return new ResponseEntity<ErrorControl>(new ErrorControl("Ya se verificó la cuenta anteriormente", true), HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				if (user.getToken().equals(token)) {
					user.setToken(null);
					user = publicUserService.getPublicUserRepo().save(user);
					
					return new ResponseEntity<PublicUser>(user, HttpStatus.OK);
				}
			}
		}
		
		return new ResponseEntity<ErrorControl>(new ErrorControl("No se pudo verificar el correo. Por favor inténtelo nuevamente", true), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="sendContactUs", method=RequestMethod.POST)
	public ResponseEntity<?> sendContactUs(@RequestBody ContactUs contactUs) throws ServletException, MessagingException {
		publicUserService.sendContactUs(contactUs);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="saveSubscriber", method=RequestMethod.POST)
	public ResponseEntity<?> saveSubscriber(@RequestBody PublicUser publicUser) throws ServletException, MessagingException {
		return publicUserService.saveSubscriber(publicUser);
	}
	
	public static class ContactUs {
		public String name;
		
		public String email;
		
		public String phone;
		
		public String message;
	}
	
	private static class SignObj {
		public PublicUser publicUser;
		
		public String baseUrl;
		
		public String token;
	}
}
