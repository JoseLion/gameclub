package ec.com.levelap.gameclub.module.user.controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;

@RestController
@RequestMapping(value="api/publicUser", produces=MediaType.APPLICATION_JSON_VALUE)
public class PublicUserController {
	@Autowired
	private PublicUserService publicUserService;
	
	@RequestMapping(value="getCurrentUser", method=RequestMethod.GET)
	public ResponseEntity<PublicUser> getCurrentUser() throws ServletException {
		PublicUser user = publicUserService.getCurrentUser();
		return new ResponseEntity<PublicUser>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="resendVerification", method=RequestMethod.GET)
	public ResponseEntity<?> resendVerification(HttpServletRequest request) throws ServletException, MessagingException {
		publicUserService.resendVerification(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PublicUser> save(@RequestPart PublicUser user, @RequestPart(required=false) MultipartFile avatar) throws ServletException, IOException {
		user = publicUserService.save(user, avatar);
		return new ResponseEntity<PublicUser>(user, HttpStatus.OK);
	}
}
