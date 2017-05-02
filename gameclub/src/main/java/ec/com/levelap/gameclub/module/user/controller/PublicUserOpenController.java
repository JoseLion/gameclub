package ec.com.levelap.gameclub.module.user.controller;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> signIn(@RequestBody PublicUser publicUser) throws ServletException {
		return publicUserService.signIn(publicUser);
	}
}
