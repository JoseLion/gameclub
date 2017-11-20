package ec.com.levelap.gameclub.module.registeredUsers.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.registeredUsers.entity.RegisteredUsers;
import ec.com.levelap.gameclub.module.registeredUsers.repository.RegisteredUsersRepo;

@RestController
@RequestMapping(value="api/registeredUsers", produces=MediaType.APPLICATION_JSON_VALUE)
public class RegisteredUsersCtrl {
	
	@Autowired
	private RegisteredUsersRepo registeredUsersRepo;
	
	@RequestMapping(value="registeredUsersAll", method=RequestMethod.POST)
	public ResponseEntity<List<RegisteredUsers>> registeredUsersAll() throws ServletException, IOException, GeneralSecurityException {
		List<RegisteredUsers> registeredUsersAll = registeredUsersRepo.findAll();
		return new ResponseEntity<List<RegisteredUsers>>(registeredUsersAll, HttpStatus.OK);
	}

}
