package ec.com.levelap.gameclub.module.reports.platformGames.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

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

import ec.com.levelap.gameclub.module.reports.platformGames.entity.PlatformGames;
import ec.com.levelap.gameclub.module.reports.platformGames.repository.PlatformGamesRepo;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/report", produces=MediaType.APPLICATION_JSON_VALUE)
public class PlatfomGamesCtrl {

	@Autowired
	private PlatformGamesRepo platformGamesRepo;
	
	@RequestMapping(value="platformGames", method=RequestMethod.GET)
	public ResponseEntity<?> platformGames() throws ServletException, IOException, GeneralSecurityException {
		Page<PlatformGames> platformGames = platformGamesRepo.platformGamesPage(new PageRequest(0, Const.TABLE_SIZE));
		return new ResponseEntity<>(platformGames, HttpStatus.OK);
	}
	
	@RequestMapping(value="totalGames", method=RequestMethod.GET)
	public ResponseEntity<?> totalGames() throws ServletException, IOException, GeneralSecurityException {
		Long totalGames = platformGamesRepo.totalGames();
		return new ResponseEntity<>(totalGames, HttpStatus.OK);
	}
}
