package ec.com.levelap.gameclub.module.user.controller;

import java.io.IOException;

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

import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;

@RestController
@RequestMapping(value="open/publicUser", produces=MediaType.APPLICATION_JSON_VALUE)
public class PublicUserOpenController {
	@Autowired
	private PublicUserService publicUserService;
	
	@RequestMapping(value="signIn", method=RequestMethod.POST)
	public ResponseEntity<?> signIn(@RequestBody SignObj signObj) throws ServletException, MessagingException {
		return publicUserService.signIn(signObj.publicUser, signObj.baseUrl);
	}
	
	@RequestMapping(value="verifyAccount/{token}/{id}", method=RequestMethod.GET)
	public void verifyAccount(@PathVariable String token, @PathVariable Long id, HttpServletResponse response) throws ServletException, IOException {
		PublicUser user = publicUserService.getPublicUserRepo().findOne(id);
		
		if (user != null) {
			if (user.getToken().equals(token)) {
				user.setToken(null);
				publicUserService.getPublicUserRepo().save(user);
				
				response.sendRedirect("http://localhost/Gameclub/gameclub-web/public/#!/gameclub/login");
				response.flushBuffer();
			}
		}
	}
	
	@RequestMapping(value="sendContactUs", method=RequestMethod.POST)
	public ResponseEntity<?> sendContactUs(@RequestBody ContactUs contactUs) throws ServletException, MessagingException {
		publicUserService.sendContactUs(contactUs);
		return new ResponseEntity<>(HttpStatus.OK);
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
	}
}
