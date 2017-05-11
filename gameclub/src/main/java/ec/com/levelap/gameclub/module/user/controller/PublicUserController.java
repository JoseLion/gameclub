package ec.com.levelap.gameclub.module.user.controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Const;

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
	
	@RequestMapping(value="resendVerification", method=RequestMethod.POST)
	public ResponseEntity<?> resendVerification(@RequestBody Object baseUrl) throws ServletException, MessagingException {
		publicUserService.resendVerification((String)baseUrl);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PublicUser> save(@RequestPart PublicUser user, @RequestPart(required=false) MultipartFile avatar) throws ServletException, IOException {
		user = publicUserService.save(user, avatar);
		return new ResponseEntity<PublicUser>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="getGamesList/{page}", method=RequestMethod.GET)
	public ResponseEntity<Page<PublicUserGame>> getGamesList(@PathVariable(required=false) Integer page) throws ServletException {
		if (page == null) {
			page = 0;
		}
		
		PublicUser user = publicUserService.getCurrentUser();
		Page<PublicUserGame> gamesList = publicUserService.getPublicUserGameRepo().findByPublicUserOrderByGameName(user, new PageRequest(page, Const.TABLE_SIZE));
		
		return new ResponseEntity<Page<PublicUserGame>>(gamesList, HttpStatus.OK);
	}
	
	@RequestMapping(value="saveGame", method=RequestMethod.POST)
	public ResponseEntity<Page<PublicUserGame>> saveGame(@RequestBody PublicUserGame myGame) throws ServletException {
		Page<PublicUserGame> gameList = publicUserService.saveGame(myGame);
		return new ResponseEntity<Page<PublicUserGame>>(gameList, HttpStatus.OK);
	}
}
