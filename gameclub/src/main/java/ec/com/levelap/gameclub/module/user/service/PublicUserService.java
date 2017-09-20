package ec.com.levelap.gameclub.module.user.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
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

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.module.kushki.entity.KushkiSubscription;
import ec.com.levelap.gameclub.module.kushki.repository.KushkiSubscriptionRepo;
import ec.com.levelap.gameclub.module.mail.service.MailService;
import ec.com.levelap.gameclub.module.message.service.MessageService;
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
public class PublicUserService extends BaseService<PublicUser> {

	@Autowired
	private PublicUserRepo publicUserRepo;

	@Autowired
	private PublicUserGameRepo publicUserGameRepo;

	@Autowired
	private MailService mailService;

	@Autowired
	private KushkiSubscriptionRepo kushkiSubscriptionRepo;

	@Autowired
	private KushkiService kushkiService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private LevelapCryptography cryptoService;

	public PublicUserService() {
		super(PublicUser.class);
	}

	@Transactional
	public ResponseEntity<?> signIn(PublicUser publicUser, String baseUrl) throws ServletException, MessagingException, IOException, GeneralSecurityException {
		PublicUser found = publicUserRepo.findByUsernameIgnoreCase(publicUser.getUsername());
		
		if (found != null) {
			return new ResponseEntity<ErrorControl>(new ErrorControl("El correo ingresado ya se encuentra registrado", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(Const.ENCODER_STRENGTH);
		publicUser.setPassword(encoder.encode(publicUser.getPassword()));
		publicUser.setToken(UUID.randomUUID().toString());
		
		File key = cryptoService.generateKeyFile();
		publicUser.setPrivateKey(IOUtils.toByteArray(new FileInputStream(key)));
		publicUser.setBalance(cryptoService.encrypt("0.0", key));
		
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
		PublicUser user = publicUserRepo.findByUsernameIgnoreCase(auth.getName());
		
		return user;
	}

	@Transactional
	public void resendVerification(String baseUrl) throws ServletException, MessagingException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PublicUser publicUser = publicUserRepo.findByUsernameIgnoreCase(auth.getName());
		publicUser.setToken(UUID.randomUUID().toString());
		publicUser = publicUserRepo.save(publicUser);

		MailParameters mailParameters = new MailParameters();
		mailParameters.setRecipentTO(Arrays.asList(publicUser.getUsername()));
		Map<String, String> params = new HashMap<>();
		params.put("link", baseUrl + "/gameclub/validate/" + publicUser.getToken() + "/" + publicUser.getId());

		mailService.sendMailWihTemplate(mailParameters, "ACNVRF", params);
	}

	@Transactional
	public ResponseEntity<?> save(PublicUser publicUser, String... baseUrl) throws ServletException {
		if (baseUrl.length > 0) {
			try {
				MailParameters mailParameters = new MailParameters();
				mailParameters.setRecipentTO(Arrays.asList(publicUser.getUsername()));
				Map<String, String> params = new HashMap<>();
				params.put("link", baseUrl[0] + "/gameclub/validate/" + publicUser.getToken() + "/" + publicUser.getId());
				mailService.sendMailWihTemplate(mailParameters, "ACNVRF", params);
			} catch (MessagingException ex) {
				return new ResponseEntity<>(new ErrorControl("No se pudo enviar el código al correo indicado, por favor vuelve a intentarlo", true), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		publicUser = publicUserRepo.save(publicUser);
		return new ResponseEntity<>(publicUser, HttpStatus.OK);
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
		
		if (user.getGames().size() == 1) {
			messageService.requestWelcomeKit(user, null);
		}

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
		
		PublicUser found = publicUserRepo.findByUsernameIgnoreCase(username);
		
		if (found != null) {
			i++;
			username = getRevokedUsername(username, i);
		}

		return username;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public PublicUser addKushkiSubscription(KushkiSubscription subscription) throws ServletException, KushkiException {
		PublicUser currentUser = getCurrentUser();
		String subscriptionId = kushkiService.subscriptionCreate(subscription.getSubscriptionId(), Const.KUSHKI_PLAN_NAME, Const.KUSHKI_PERIODICITY, new KushkiContact(subscription.getFirstName(), subscription.getLastName(), subscription.getEmail()), new KushkiAmount());
		
		subscription.setSubscriptionId(subscriptionId);
		subscription.setPublicUser(currentUser);
		kushkiSubscriptionRepo.saveAndFlush(subscription);
		currentUser = getCurrentUser();
		
		return currentUser;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public PublicUser removeKushkiSubscription(Long subscriptionId) throws ServletException, KushkiException {
		PublicUser publicUser = getCurrentUser();
		
		for (KushkiSubscription method : publicUser.getPaymentMethods()) {
			if (method.getId().longValue() == subscriptionId.longValue()) {
				kushkiService.subscriptionCancel(method.getSubscriptionId());
				publicUser.getPaymentMethods().remove(method);
				break;
			}
		}
		
		publicUserRepo.save(publicUser);
		return publicUser;
	}
	
	@Transactional
	public ResponseEntity<?> saveSubscriber(PublicUser publicUser) throws ServletException, MessagingException {
		PublicUser found = publicUserRepo.findByUsernameIgnoreCase(publicUser.getUsername());
		
		if (found != null) {
			return new ResponseEntity<ErrorControl>(new ErrorControl("Ya te encuentras participando con este correo", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		publicUserRepo.save(publicUser);
		
		MailParameters mailParameters = new MailParameters();
		mailParameters.setRecipentTO(Arrays.asList("info@gameclub.com.ec"));
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Map<String, String> params = new HashMap<>();
		params.put("name", publicUser.getName() + " " + publicUser.getLastName());
		params.put("email", publicUser.getUsername());
		params.put("birthDate", df.format(publicUser.getBirthDate()));
		params.put("province", publicUser.getLocation().getParent().getName());
		params.put("city", publicUser.getLocation().getName());

		mailService.sendMailWihTemplate(mailParameters, "SUBCBR", params);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Transactional
	public PublicUser setUserPrivateKey(Long id) throws ServletException, NoSuchAlgorithmException, IOException {
		PublicUser user = publicUserRepo.findOne(id);
		
		if (user.getPrivateKey() == null) {
			File key = cryptoService.generateKeyFile();
			user.setPrivateKey(IOUtils.toByteArray(new FileInputStream(key)));
			user = publicUserRepo.save(user);
		}
		
		return user;
	}
	
	@Transactional
	public PublicUser setUserBalance(Long id, Double balance) throws ServletException, GeneralSecurityException, IOException {
		PublicUser user = publicUserRepo.findOne(id);
		File key = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(key, user.getPrivateKey());
		
		byte[] encrypted = cryptoService.encrypt(Double.toString(balance), key);
		user.setBalance(encrypted);
		
		user = publicUserRepo.save(user);
		return user;
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
