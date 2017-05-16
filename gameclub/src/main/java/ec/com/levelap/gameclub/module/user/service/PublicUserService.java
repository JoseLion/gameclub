package ec.com.levelap.gameclub.module.user.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.base.entity.FileData;
import ec.com.levelap.commons.archive.Archive;
import ec.com.levelap.commons.service.DocumentService;
import ec.com.levelap.gameclub.module.kushki.entity.KushkiSubscription;
import ec.com.levelap.gameclub.module.kushki.repository.KushkiSubscriptionRepo;
import ec.com.levelap.gameclub.module.mail.service.MailService;
import ec.com.levelap.gameclub.module.user.controller.PublicUserController.Password;
import ec.com.levelap.gameclub.module.user.controller.PublicUserOpenController.ContactUs;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.module.user.repository.PublicUserGameRepo;
import ec.com.levelap.gameclub.module.user.repository.PublicUserRepo;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.kushki.KushkiException;
import ec.com.levelap.kushki.object.KushkiAmount;
import ec.com.levelap.kushki.object.KushkiContact;
import ec.com.levelap.kushki.service.KushkiService;
import ec.com.levelap.mail.MailParameters;

@Service
public class PublicUserService {

	@Autowired
	private PublicUserRepo publicUserRepo;
	
	@Autowired
	private PublicUserGameRepo publicUserGameRepo;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private DocumentService documentService;

	@Autowired
	private KushkiSubscriptionRepo kushkiSubscriptionRepo;

	@Autowired
	private KushkiService kushkiService;

	@Transactional
	public ResponseEntity<?> signIn(PublicUser publicUser, String baseUrl) throws ServletException, MessagingException {
		PublicUser found = publicUserRepo.findByUsername(publicUser.getUsername());
		
		if (found != null) {
			return new ResponseEntity<ErrorControl>(new ErrorControl("El correo ingresado ya se encuentra registrado", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(Const.ENCODER_STRENGTH);
		publicUser.setPassword(encoder.encode(publicUser.getPassword()));
		publicUser.setToken(UUID.randomUUID().toString());
		publicUser = publicUserRepo.save(publicUser);
		
		MailParameters mailParameters = new MailParameters();
		mailParameters.setRecipentTO(Arrays.asList(publicUser.getUsername()));
		Map<String, String> params = new HashMap<>();
		params.put("link", baseUrl + "/gameclub/validate/" + publicUser.getToken() + "/" + publicUser.getId());
		
		mailService.sendMailWihTemplate(mailParameters, "ACNVRF", params);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Transactional
	public PublicUser getCurrentUser() throws ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PublicUser user = publicUserRepo.findByUsername(auth.getName());
		
		return user;
	}
	
	@Transactional
	public void resendVerification(String baseUrl) throws ServletException, MessagingException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PublicUser publicUser = publicUserRepo.findByUsername(auth.getName());
		publicUser.setToken(UUID.randomUUID().toString());
		publicUser = publicUserRepo.save(publicUser);
		
		MailParameters mailParameters = new MailParameters();
		mailParameters.setRecipentTO(Arrays.asList(publicUser.getUsername()));
		Map<String, String> params = new HashMap<>();
		params.put("link", baseUrl + "/gameclub/validate/" + publicUser.getToken() + "/" + publicUser.getId());
		
		mailService.sendMailWihTemplate(mailParameters, "ACNVRF", params);
	}
	
	@Transactional
	public PublicUser save(PublicUser user, MultipartFile avatar) throws ServletException, IOException {
		PublicUser original = publicUserRepo.findOne(user.getId());
		
		if (avatar != null) {
			Archive archive = new Archive();
			
			if (original.getAvatar() != null) {
				documentService.deleteFile(original.getAvatar().getName(), PublicUser.class.getSimpleName());
				archive = original.getAvatar();
			}
			
			FileData fileData = documentService.saveFile(avatar, PublicUser.class.getSimpleName());
			
			archive.setModule(PublicUser.class.getSimpleName());
			archive.setName(fileData.getName());
			archive.setType(avatar.getContentType());
			user.setAvatar(archive);
		} else {
			if (original.getAvatar() != null) {
				documentService.deleteFile(original.getAvatar().getName(), PublicUser.class.getSimpleName());
			}
			
			user.setAvatar(null);
		}
		
		user = publicUserRepo.save(user);
		return user;
	}
	
	@Transactional
	public void sendContactUs(ContactUs contactUs) throws ServletException, MessagingException {
		MailParameters mailParameters = new MailParameters();
		mailParameters.setRecipentTO(Arrays.asList("info@gameclub.com.ec"));
		Map<String, String> params = new HashMap<>();
		params.put("name", contactUs.name);
		params.put("email", contactUs.email);
		params.put("message", contactUs.message);
		
		if (contactUs.phone != null && !contactUs.phone.isEmpty()) {
			params.put("phone", contactUs.phone);
		} else {
			params.put("phone", "N/A");
		}
		
		mailService.sendMailWihTemplate(mailParameters, "CNCTUS", params);
	}
	
	@Transactional
	public Page<PublicUserGame> saveGame(PublicUserGame myGame) throws ServletException {
		PublicUser user = this.getCurrentUser();
		myGame.setPublicUser(user);
		publicUserGameRepo.saveAndFlush(myGame);
		
		Page<PublicUserGame> gameList = publicUserGameRepo.findMyGames(user, null, new PageRequest(0, Const.TABLE_SIZE, new Sort("game.name")));
		return gameList;
	}
	
	@Transactional
	public ResponseEntity<?> changePassword(Password password) throws ServletException {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(Const.ENCODER_STRENGTH);
		PublicUser user = this.getCurrentUser();
		
		if (encoder.matches(password.current, user.getPassword())) {
			user.setPassword(encoder.encode(password.change));
			user.setHasTempPassword(false);
			user = publicUserRepo.save(user);
			
			return new ResponseEntity<PublicUser>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<ErrorControl>(new ErrorControl("Contraseña incorrecta. Por favor intentelo nuevamente", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Transactional
	public void deleteAccount() throws ServletException {
		PublicUser user = this.getCurrentUser();
		user.setUsername(getRevokedUsername(user.getUsername(), 0));
		user.setPassword("********************");
		user.setStatus(false);
		
		publicUserRepo.save(user);
	}
	
	private String getRevokedUsername(String username, int i) {
		if (i == 0) {
			username += "(Revoked)";
		} else {
			int index = username.indexOf("(");
			username = username.substring(0, index) + "(Revoked#" + i + ")";
		}
		
		PublicUser found = publicUserRepo.findByUsername(username);
		
		if (found != null) {
			i++;
			username = getRevokedUsername(username, i);
		}
		
		return username;
	}

	@Transactional
	public Map<String, Object> createUpdateKushkiSubscription(final String token, final String firstName, final String lastName, final String email, final String cardFinale) throws ServletException {
		PublicUser publicUser = this.publicUserRepo.findByUsername(email);
		if (!publicUser.getKushkiSubscriptionActive()) {
			return this.createKushkiSubscription(token, firstName, lastName, email, publicUser, cardFinale);
		} else {
			KushkiSubscription kushkiSubscription = this.kushkiSubscriptionRepo.findByPublicUser(publicUser);
			return this.updateKushkiSubscription(token, kushkiSubscription.getSubscriptionId(), publicUser, cardFinale);
		}
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public Map<String, Object> createKushkiSubscription(final String token, String firstName, String lastName, String email, PublicUser publicUser, String cardFinale) throws ServletException {
		try {
			String subscriptionId = this.kushkiService.subscriptionCreate(token, Const.KUSHKI_PLAN_NAME, Const.KUSHKI_PERIODICITY, new KushkiContact(firstName, lastName, email), new KushkiAmount());
			publicUser.setKushkiSubscriptionActive(true);
			KushkiSubscription kushkiSubscription = new KushkiSubscription();
			kushkiSubscription.setFirstName(firstName.toUpperCase());
			kushkiSubscription.setLastName(lastName.toUpperCase());
			kushkiSubscription.setEmail(email);
			kushkiSubscription.setSubscriptionId(subscriptionId);
			kushkiSubscription.setPublicUser(publicUser);
			kushkiSubscription.setCardFinale(cardFinale);
			kushkiSubscription = this.kushkiSubscriptionRepo.save(kushkiSubscription);
			
			Map<String, Object> kushkiResponse = new HashMap<>();
			kushkiResponse.put("publicUser", publicUser);
			kushkiResponse.put("extraData", kushkiSubscription.getCardFinale());
			return kushkiResponse;
		} catch (KushkiException ex) {
			// TODO Registrar log en archivo.
			throw new ServletException(ex.getCause());
		}
	}

	@Transactional
	public Map<String, Object> updateKushkiSubscription(final String token, String subscriptionId, PublicUser publicUser, String cardFinale) throws ServletException {
		try {
			this.kushkiService.suscriptionUpdateCard(subscriptionId, token);
			publicUser.setKushkiSubscriptionActive(Boolean.TRUE);
		} catch (KushkiException ex) {
			publicUser.setKushkiSubscriptionActive(Boolean.FALSE);
			// TODO Registrar log en archivo.
		}
		KushkiSubscription kushkiSubscription = this.kushkiSubscriptionRepo.findBySubscriptionId(subscriptionId);
		kushkiSubscription.setCardFinale(publicUser.getKushkiSubscriptionActive() ? cardFinale : "----");
		this.kushkiSubscriptionRepo.save(kushkiSubscription);
		
		Map<String, Object> kushkiResponse = new HashMap<>();
		kushkiResponse.put("publicUser", publicUser);
		kushkiResponse.put("extraData", kushkiSubscription.getCardFinale());
		return kushkiResponse;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public PublicUser removeKushkiSubscription(Long id) throws ServletException {
		PublicUser publicUser = this.publicUserRepo.findOne(id);
		try{
			KushkiSubscription kushkiSubscription = this.kushkiSubscriptionRepo.findByPublicUser(publicUser);
			this.kushkiService.subscriptionCancel(kushkiSubscription.getSubscriptionId());
			this.kushkiSubscriptionRepo.delete(kushkiSubscription);
		}catch(KushkiException ex) {
			// TODO Registrar log en archivo.
			throw new ServletException(ex.getCause());
		}
		publicUser.setKushkiSubscriptionActive(Boolean.FALSE);
		this.publicUserRepo.save(publicUser);
		return publicUser;
	}

	public PublicUserRepo getPublicUserRepo() {
		return publicUserRepo;
	}

	public PublicUserGameRepo getPublicUserGameRepo() {
		return publicUserGameRepo;
	}

	public KushkiSubscriptionRepo getKushkiSubscriptionRepo() {
		return kushkiSubscriptionRepo;
	}

}
