package ec.com.levelap.gameclub.module.reports.registeredUsers.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.reports.registeredUsers.entity.RegisteredUsers;
import ec.com.levelap.gameclub.module.reports.registeredUsers.repository.RegisteredUsersRepo;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/report", produces=MediaType.APPLICATION_JSON_VALUE)
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

	@RequestMapping(value="findRegisteredUsers", method=RequestMethod.POST)
	public ResponseEntity<List<RegisteredUsers>> findRegisteredUsers(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		List<RegisteredUsers> logPlatformGames = registeredUsersRepo.findRegisteredUsers(search.name, search.document, search.username, search.startDate, search.endDate);
		return new ResponseEntity<List<RegisteredUsers>>(logPlatformGames, HttpStatus.OK);
	}
	
	private static class Search {
		
		public String name = "";
		
		public String document = "";
		
		public String username = "";
		
		public Date startDate = new Date(0);
		
		public Date endDate = new Date();
	}
}
