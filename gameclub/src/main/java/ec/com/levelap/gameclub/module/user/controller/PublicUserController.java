package ec.com.levelap.gameclub.module.user.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
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
import ec.com.levelap.gameclub.module.kushki.entity.KushkiSubscription;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.module.user.entity.PublicUserLite;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.kushki.KushkiException;

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
	public ResponseEntity<?> resendVerification(@RequestBody Object baseUrl) throws ServletException, MessagingException {
		publicUserService.resendVerification((String) baseUrl);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody PublicUser user) throws ServletException, IOException {
		return this.publicUserService.save(user);
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
		Page<PublicUserGame> gamesList = publicUserService.getPublicUserGameRepo().findMyGames(user, filter.consoleId, new PageRequest(filter.page, Const.TABLE_SIZE, new Sort(filter.sort)));

		return new ResponseEntity<Page<PublicUserGame>>(gamesList, HttpStatus.OK);
	}

	@RequestMapping(value = "saveGame", method = RequestMethod.POST)
	public ResponseEntity<Page<PublicUserGame>> saveGame(@RequestBody PublicUserGame myGame) throws ServletException {
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

	@RequestMapping(value = "addKushkiSubscription", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PublicUser> addKushkiSubscription(@RequestBody KushkiSubscription subscription) throws ServletException, KushkiException {
		PublicUser currentUser = publicUserService.addKushkiSubscription(subscription);
		return new ResponseEntity<PublicUser>(currentUser, HttpStatus.OK);
	}

	@RequestMapping(value = "deletePaymentMethod/{subscriptionId}", method = RequestMethod.GET)
	public ResponseEntity<PublicUser> removeKushkiSubscription(@PathVariable Long subscriptionId) throws ServletException, KushkiException {
		PublicUser publicUser = publicUserService.removeKushkiSubscription(subscriptionId);
		return new ResponseEntity<PublicUser>(publicUser, HttpStatus.OK);
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
	public ResponseEntity<?> changeMail(@RequestBody ChangeUsernameObj usernameObj) throws ServletException, MessagingException {
		PublicUser publicUser = this.publicUserService.getPublicUserRepo().findByUsernameIgnoreCase(usernameObj.newUsername);
		if (publicUser != null) {
			return new ResponseEntity<ErrorControl>(new ErrorControl("El correo ingresado ya se encuentra registrado", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		publicUser = this.publicUserService.getPublicUserRepo().findByUsernameIgnoreCase(usernameObj.oldUsername);
		publicUser.setToken(UUID.randomUUID().toString());
		publicUser.setUsername(usernameObj.newUsername);
		return this.publicUserService.save(publicUser, usernameObj.baseUrl);
	}
	
	@RequestMapping(value="getGamesSummary", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Long>> getGamesSummary() throws ServletException {
		Map<String, Long> summary = publicUserService.getGamesSummary();
		return new ResponseEntity<Map<String,Long>>(summary, HttpStatus.OK);
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

		public String baseUrl;
	}

}
