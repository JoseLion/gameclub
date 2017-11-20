package ec.com.levelap.gameclub.module.reports.controller;

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

import ec.com.levelap.gameclub.module.reports.entity.UserGames;
import ec.com.levelap.gameclub.module.reports.repository.UserGamesRepo;

@RestController
@RequestMapping(value="api/userGames", produces=MediaType.APPLICATION_JSON_VALUE)
public class UserGamesCoontroller {

//	@Autowired
//	private UserGamesService userGamesService;
	
	@Autowired
	private UserGamesRepo userGamesRepo;
		
	@RequestMapping(value="findUserGames", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<List<?>> findUserGames() throws ServletException, IOException, GeneralSecurityException {
		List<UserGames> userGames =  userGamesRepo.findAll();
		return new ResponseEntity<List<?>>(userGames, HttpStatus.OK);
	}
	
}
