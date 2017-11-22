package ec.com.levelap.gameclub.module.registeredUsers.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.registeredUsers.entity.RegisteredUsers;
import ec.com.levelap.gameclub.module.registeredUsers.repository.RegisteredUsersRepo;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/registeredUsers", produces=MediaType.APPLICATION_JSON_VALUE)
public class RegisteredUsersCtrl {
	
	@Autowired
	private RegisteredUsersRepo registeredUsersRepo;
	
	@RequestMapping(value="registeredUsersAll", method=RequestMethod.GET)
	public ResponseEntity<?> registeredUsersAll() throws ServletException, IOException, GeneralSecurityException {
		Page<RegisteredUsers> registeredUsersAll = registeredUsersRepo.registeredUsersPage(new PageRequest(0, Const.TABLE_SIZE));
		return new ResponseEntity<>(registeredUsersAll, HttpStatus.OK);
	}
	
	@RequestMapping(value="totalUsers", method=RequestMethod.GET)
	public ResponseEntity<?> totalUsers() throws ServletException, IOException, GeneralSecurityException {
		Long totalUsers = registeredUsersRepo.totalUsers();
		return new ResponseEntity<>(totalUsers, HttpStatus.OK);
	}

}
