package ec.com.levelap.gameclub.module.user.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.module.user.entity.PublicUserLite;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value = "api/publicUser", produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicUserController {

	@Autowired
	private PublicUserService publicUserService;

	@RequestMapping(value = "getCurrentUser", method = RequestMethod.GET)
	public ResponseEntity<PublicUser> getCurrentUser() throws ServletException {
		PublicUser user = publicUserService.getCurrentUser();
		return new ResponseEntity<PublicUser>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "resendVerification", method = RequestMethod.POST)
	public ResponseEntity<?> resendVerification(HttpServletRequest request) throws ServletException, MessagingException, MalformedURLException {
		publicUserService.resendVerification(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody PublicUser user, HttpServletRequest request) throws ServletException, IOException {
		return this.publicUserService.save(user, false, request);
	}

	@RequestMapping(value = "getGamesList", method = RequestMethod.POST)
	public ResponseEntity<Page<PublicUserGame>> getGamesList(@RequestBody(required = false) Filter filter) throws ServletException {
		if (filter == null) {
			filter = new Filter();
		}

		if (filter.sort == null || filter.sort.isEmpty()) {
			filter.sort = "game.name";
		}

		PublicUser user = publicUserService.getCurrentUser();
		Page<PublicUserGame> gamesList = publicUserService.getPublicUserGameRepo().findMyGames(user, filter.consoleId, new PageRequest(filter.page, Const.TABLE_SIZE, new Sort(Direction.DESC, filter.sort)));

		return new ResponseEntity<Page<PublicUserGame>>(gamesList, HttpStatus.OK);
	}

	@RequestMapping(value = "saveGame", method = RequestMethod.POST)
	public ResponseEntity<Page<PublicUserGame>> saveGame(@RequestBody PublicUserGame myGame) throws ServletException, NumberFormatException, GeneralSecurityException, IOException {
		Page<PublicUserGame> gameList = publicUserService.saveGame(myGame);
		return new ResponseEntity<Page<PublicUserGame>>(gameList, HttpStatus.OK);
	}

	@RequestMapping(value = "changePassword", method = RequestMethod.POST)
	public ResponseEntity<?> changePassword(@RequestBody Password password) throws ServletException {
		return publicUserService.changePassword(password);
	}

	@RequestMapping(value = "deleteAccount", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAccount() throws ServletException {
		publicUserService.deleteAccount();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "findPublicUsers", method = RequestMethod.POST)
	public ResponseEntity<?> findPublicUsers(@RequestBody(required = false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		Page<PublicUserLite> publicUsers = this.publicUserService.getPublicUserRepo().findPublicUsers(search.name, search.lastName, search.status, search.startDate, search.endDate, new PageRequest(search.page, Const.TABLE_SIZE, new Sort(new Order(Direction.ASC, "name"), new Order(Direction.ASC, "lastName"))));
		return new ResponseEntity<>(publicUsers, HttpStatus.OK);
	}

	@RequestMapping(value = "findOne/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findOne(@PathVariable Long id) throws ServletException {
		PublicUser publicUser = this.publicUserService.getPublicUserRepo().findOne(id);
		return new ResponseEntity<>(publicUser, HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(value = "changeStatus/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> changeStatus(@PathVariable Long id) throws ServletException {
		PublicUser publicUser = this.publicUserService.getPublicUserRepo().findOne(id);
		publicUser = this.publicUserService.changeStatus(publicUser);
		publicUser = this.publicUserService.getPublicUserRepo().save(publicUser);
		return new ResponseEntity<Boolean>(publicUser.getStatus(), HttpStatus.OK);
	}

	@RequestMapping(value = "changeMail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changeMail(@RequestBody ChangeUsernameObj usernameObj, HttpServletRequest request) throws ServletException, MessagingException, MalformedURLException {
		PublicUser publicUser = this.publicUserService.getPublicUserRepo().findByUsernameIgnoreCase(usernameObj.newUsername);
		if (publicUser != null) {
			return new ResponseEntity<ErrorControl>(new ErrorControl("El correo ingresado ya se encuentra registrado", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		publicUser = this.publicUserService.getPublicUserRepo().findByUsernameIgnoreCase(usernameObj.oldUsername);
		publicUser.setToken(UUID.randomUUID().toString());
		publicUser.setUsername(usernameObj.newUsername);
		return this.publicUserService.save(publicUser, true, request);
	}
	
	@RequestMapping(value="getGamesSummary", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Long>> getGamesSummary() throws ServletException {
		Map<String, Long> summary = publicUserService.getGamesSummary();
		return new ResponseEntity<Map<String,Long>>(summary, HttpStatus.OK);
	}
	
	@RequestMapping(value="generateUrlToken", method=RequestMethod.GET, produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> generateUrlToken() throws ServletException {
		String token = publicUserService.generateUrlToken();
		return new ResponseEntity<String>(token, HttpStatus.OK);
	}

	@RequestMapping(value="updateLoggedInformation", method=RequestMethod.GET)
	public ResponseEntity<?> updateLoggedInformation() throws ServletException, IOException, GeneralSecurityException {
		PublicUser publicUser = publicUserService.getCurrentUser();
		Map<String, Object> informationToUpdate = new HashMap<>();
		informationToUpdate.put("unreadMessages", publicUser.getUnreadMessages());
		informationToUpdate.put("shownBalance", publicUser.getShownBalance());
		return new ResponseEntity<>(informationToUpdate, HttpStatus.OK);
	}

	private static class Filter {
		public String sort;

		public Long consoleId;

		public Integer page = 0;
	}

	public static class Password {
		public String current;

		public String change;
	}

	private static class Search {
		public Integer page = 0;

		public String name = "";

		public String lastName = "";

		public Boolean status;

		public Date startDate = new Date(0);

		public Date endDate = new Date();
	}

	private static class ChangeUsernameObj {
		public String oldUsername;

		public String newUsername;
	}

}
