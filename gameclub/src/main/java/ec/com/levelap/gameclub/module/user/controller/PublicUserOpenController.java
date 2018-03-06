package ec.com.levelap.gameclub.module.user.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;

@RestController
@RequestMapping(value="open/publicUser", produces=MediaType.APPLICATION_JSON_VALUE)
public class PublicUserOpenController {
	@Autowired
	private PublicUserService publicUserService;
	
	@RequestMapping(value="signIn", method=RequestMethod.POST)
	public ResponseEntity<?> signIn(@RequestBody SignObj signObj, HttpServletRequest request) throws ServletException, MessagingException, IOException, GeneralSecurityException {
		return publicUserService.signIn(signObj.publicUser, signObj.token, request);
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
	
	@RequestMapping(value="sendWorkForUs", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> sendWorkForUs(@RequestPart Map<String, String> work, @RequestPart MultipartFile file) throws ServletException, IllegalStateException, MessagingException, IOException {
		publicUserService.sendWorkForUs(work, file);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "findOne/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findOne(@PathVariable Long id) throws ServletException {
		PublicUser publicUser = this.publicUserService.getPublicUserRepo().findOne(id);
		return new ResponseEntity<>(publicUser, HttpStatus.OK);
	}
	
	public static class ContactUs {
		public String name;
		
		public String email;
		
		public String phone;
		
		public String message;
	}
	
	private static class SignObj {
		
		public PublicUser publicUser;
		
		public String token;
	}
}
