package ec.com.levelap.gameclub.module.user.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.module.mail.service.GameClubMailService;
import ec.com.levelap.gameclub.module.message.service.MessageService;
import ec.com.levelap.gameclub.module.settings.entity.Setting;
import ec.com.levelap.gameclub.module.settings.service.SettingService;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.service.TransactionService;
import ec.com.levelap.gameclub.module.user.controller.PublicUserController.Password;
import ec.com.levelap.gameclub.module.user.controller.PublicUserOpenController.ContactUs;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.module.user.repository.PublicUserGameRepo;
import ec.com.levelap.gameclub.module.user.repository.PublicUserRepo;
import ec.com.levelap.gameclub.utils.Code;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.mail.entity.LevelapMail;

@Service
public class PublicUserService extends BaseService<PublicUser> {

	@Autowired
	private PublicUserRepo publicUserRepo;

	@Autowired
	private PublicUserGameRepo publicUserGameRepo;

	@Autowired
	private GameClubMailService mailService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private LevelapCryptography cryptoService;

	public PublicUserService() {
		super(PublicUser.class);
	}

	@Transactional
	public ResponseEntity<?> signIn(PublicUser publicUser, String token, HttpServletRequest request) throws ServletException, MessagingException, IOException, GeneralSecurityException {
		PublicUser found = publicUserRepo.findByUsernameIgnoreCase(publicUser.getUsername());
		
		if (found != null && found.getStatus()) {
			return new ResponseEntity<ErrorControl>(new ErrorControl("El correo ingresado ya se encuentra registrado", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (found == null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(Const.ENCODER_STRENGTH);
			publicUser.setPassword(encoder.encode(publicUser.getPassword()));
			publicUser.setToken(UUID.randomUUID().toString());
			
			File key = cryptoService.generateKeyFile();
			publicUser.setPrivateKey(IOUtils.toByteArray(new FileInputStream(key)));
			publicUser.setBalance(cryptoService.encrypt("0.0", key));
			
			if (token != null && !token.isEmpty()) {
				publicUser.setReferrer(token);
			}
			
			publicUser = publicUserRepo.save(publicUser);
		} else {
			found.setStatus(true);
			publicUser = publicUserRepo.save(found);
		}
		
		URL referrer = new URL(request.getHeader("referer"));
		LevelapMail levelapMail = new LevelapMail();
		levelapMail.setRecipentTO(Arrays.asList(publicUser.getUsername()));
		Map<String, String> params = new HashMap<>();
		params.put("link", referrer.getProtocol() + "://" + referrer.getHost() + "/gameclub/validate?token=" + publicUser.getToken() + "&id=" + publicUser.getId());

		mailService.sendMailWihTemplate(levelapMail, "ACNVRF", params);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	public PublicUser getCurrentUser() throws ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PublicUser user = publicUserRepo.findByUsernameIgnoreCase(auth.getName());
		
		return user;
	}

	@Transactional
	public void resendVerification(HttpServletRequest request) throws ServletException, MessagingException, MalformedURLException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PublicUser publicUser = publicUserRepo.findByUsernameIgnoreCase(auth.getName());
		publicUser.setToken(UUID.randomUUID().toString());
		publicUser = publicUserRepo.save(publicUser);
		
		URL referrer = new URL(request.getHeader("referer"));
		LevelapMail levelapMail = new LevelapMail();
		levelapMail.setRecipentTO(Arrays.asList(publicUser.getUsername()));
		Map<String, String> params = new HashMap<>();
		params.put("link", referrer.getProtocol() + "://" + referrer.getHost() + "/gameclub/validate?token=" + publicUser.getToken() + "&id=" + publicUser.getId());

		mailService.sendMailWihTemplate(levelapMail, "ACNVRF", params);
	}

	@Transactional
	public ResponseEntity<?> save(PublicUser publicUser, Boolean sendVerification, HttpServletRequest request) throws ServletException, MalformedURLException {
		if (sendVerification) {
			try {
				URL referrer = new URL(request.getHeader("referer"));
				LevelapMail levelapMail = new LevelapMail();
				levelapMail.setRecipentTO(Arrays.asList(publicUser.getUsername()));
				Map<String, String> params = new HashMap<>();
				params.put("link", referrer.getProtocol() + "://" + referrer.getHost() + "/gameclub/validate?token=" + publicUser.getToken() + "&id=" + publicUser.getId());
				mailService.sendMailWihTemplate(levelapMail, "ACNVRF", params);
			} catch (MessagingException ex) {
				return new ResponseEntity<>(new ErrorControl("No se pudo enviar el código al correo indicado, por favor vuelve a intentarlo", true), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		if (publicUser.getId() != null) {
			PublicUser original = publicUserRepo.findOne(publicUser.getId());
			publicUser.setPrivateKey(original.getPrivateKey());
			publicUser.setBalance(original.getBalance());
		}
		
		publicUser = publicUserRepo.save(publicUser);
		return new ResponseEntity<>(publicUser, HttpStatus.OK);
	}

	@Transactional
	public void sendContactUs(ContactUs contactUs) throws ServletException, MessagingException {
		LevelapMail levelapMail = new LevelapMail();
		levelapMail.setRecipentTO(Arrays.asList(Const.EMAIL_INFO));
		
		Map<String, String> params = new HashMap<>();
		params.put("name", contactUs.name);
		params.put("email", contactUs.email);
		params.put("message", contactUs.message);

		if (contactUs.phone != null && !contactUs.phone.isEmpty()) {
			params.put("phone", contactUs.phone);
		} else {
			params.put("phone", "N/A");
		}

		mailService.sendMailWihTemplate(levelapMail, "CNCTUS", params);
	}
	
	public void sendWorkForUs(Map<String, String> work, MultipartFile file) throws ServletException, MessagingException, IllegalStateException, IOException {
		File attachment = new File(file.getOriginalFilename());
		file.transferTo(attachment);
		
		LevelapMail levelapMail = new LevelapMail();
		levelapMail.setRecipentTO(Arrays.asList(Const.EMAIL_INFO));
		levelapMail.setAttachments(Arrays.asList(attachment));
		
		Map<String, String> params = new HashMap<>();
		params.put("name", work.get("name"));
		params.put("email", work.get("email"));
		params.put("message", work.get("message"));

		mailService.sendMailWihTemplate(levelapMail, "WRKWUS", params);
	}

	@Transactional
	public Page<PublicUserGame> saveGame(PublicUserGame myGame) throws ServletException, NumberFormatException, GeneralSecurityException, IOException {
		PublicUser user = this.getCurrentUser();
		
		if (myGame.getId() == null && user.getGames().size() == 0) {
			messageService.requestWelcomeKit(user, null);
		}
		
		if (user.getReferrer() != null && !user.getReferrer().isEmpty()) {
			PublicUser refferer = publicUserRepo.findByUrlToken(user.getReferrer());
			
			if (refferer != null) {
				Setting setting = settingService.getSettingsRepo().findByCode(Code.SETTING_REFFERED_REWARD);
				this.addToUserPromoBalance(refferer.getId(), Double.parseDouble(setting.getValue()));
				user = this.addToUserPromoBalance(user.getId(), Double.parseDouble(setting.getValue()));
				
				File userKey = File.createTempFile("userKey", "tmp");
				FileUtils.writeByteArrayToFile(userKey, user.getPrivateKey());
				File referrerKey = File.createTempFile("referrerKey", ".tmp");
				FileUtils.writeByteArrayToFile(referrerKey, refferer.getPrivateKey());
				Transaction reffererTransaction = new Transaction(refferer, Const.TRS_REFFERED_BONUS, null, null, null, cryptoService.encrypt(setting.getValue(), referrerKey), null, null);
				Transaction transaction = new Transaction(user, Const.TRS_REFFERED_BONUS, null, null, null, cryptoService.encrypt(setting.getValue(), userKey), null, null);
				
				transactionService.getTransactionRepo().save(reffererTransaction);
				transactionService.getTransactionRepo().save(transaction);
				
				user.setReferrer(null);
				user = publicUserRepo.save(user);
			}
		}
		
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
		user.setPassword("");
		user.setStatus(false);

		publicUserRepo.save(user);
	}
	
	@Transactional
	public ResponseEntity<?> saveSubscriber(PublicUser publicUser) throws ServletException, MessagingException {
		PublicUser found = publicUserRepo.findByUsernameIgnoreCase(publicUser.getUsername());
		
		if (found != null) {
			return new ResponseEntity<ErrorControl>(new ErrorControl("Ya te encuentras participando con este correo", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		publicUserRepo.save(publicUser);
		
		LevelapMail levelapMail = new LevelapMail();
		levelapMail.setRecipentTO(Arrays.asList(Const.EMAIL_INFO));
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Map<String, String> params = new HashMap<>();
		params.put("name", publicUser.getName() + " " + publicUser.getLastName());
		params.put("email", publicUser.getUsername());
		params.put("birthDate", df.format(publicUser.getBirthDate()));
		params.put("province", publicUser.getLocation().getParent().getName());
		params.put("city", publicUser.getLocation().getName());

		mailService.sendMailWihTemplate(levelapMail, "SUBCBR", params);
		
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
	
	@Transactional
	public PublicUser addToUserBalance(Long id, Double ammount) throws ServletException, GeneralSecurityException, IOException {
		PublicUser user = publicUserRepo.findOne(id);
		File key = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(key, user.getPrivateKey());
		
		Double balance = Double.parseDouble(cryptoService.decrypt(user.getBalance(), key));
		byte[] encrypted = cryptoService.encrypt(Double.toString(balance + ammount), key);
		user.setBalance(encrypted);
		
		user = publicUserRepo.save(user);
		return user;
	}
	
	@Transactional
	public PublicUser substractFromUserBalance(Long id, Double ammount) throws ServletException, GeneralSecurityException, IOException {
		PublicUser user = publicUserRepo.findOne(id);
		File key = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(key, user.getPrivateKey());
		
		Double balance = Double.parseDouble(cryptoService.decrypt(user.getBalance(), key));
		byte[] encrypted = cryptoService.encrypt(Double.toString(balance - ammount), key);
		user.setBalance(encrypted);
		
		user = publicUserRepo.save(user);
		return user;
	}
	
	@Transactional
	public PublicUser setUserPromoBalance(Long id, Double balance) throws ServletException, GeneralSecurityException, IOException {
		PublicUser user = publicUserRepo.findOne(id);
		File key = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(key, user.getPrivateKey());
		
		byte[] encrypted = cryptoService.encrypt(Double.toString(balance), key);
		user.setPromoBalance(encrypted);
		
		user = publicUserRepo.save(user);
		return user;
	}
	
	@Transactional
	public PublicUser addToUserPromoBalance(Long id, Double ammount) throws ServletException, GeneralSecurityException, IOException {
		PublicUser user = publicUserRepo.findOne(id);
		File key = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(key, user.getPrivateKey());
		Double balance;
		
		if (user.getPromoBalance() != null) {
			balance = Double.parseDouble(cryptoService.decrypt(user.getPromoBalance(), key));
		} else {
			balance = 0.0;
		}
		
		byte[] encrypted = cryptoService.encrypt(Double.toString(balance + ammount), key);
		user.setPromoBalance(encrypted);
		
		user = publicUserRepo.save(user);
		return user;
	}
	
	@Transactional
	public Map<String, Long> getGamesSummary() throws ServletException {
		PublicUser currentUser = getCurrentUser();
		Map<String, Long> summary = new HashMap<>();
		Date today = new Date();
		
		summary.put("borrowed",publicUserRepo.countByIdAndGamesIsBorrowedIsTrue(currentUser.getId()));
		summary.put("toReturn", publicUserRepo.countGamesToReturn(currentUser.getId()));
		summary.put("updateDate", today.getTime());
		
		return summary;
	}
	
	@Transactional
	public String generateUrlToken() throws ServletException {
		PublicUser currentUser = getCurrentUser();
		String token = UUID.randomUUID().toString();
		Random random = new Random();
		int pos = random.nextInt(token.length());
		token = token.substring(0, pos) + currentUser.getId().toString() + token.substring(pos);
		currentUser.setUrlToken(token);
		publicUserRepo.save(currentUser);
		
		return token;
	}

	public PublicUserRepo getPublicUserRepo() {
		return publicUserRepo;
	}

	public PublicUserGameRepo getPublicUserGameRepo() {
		return publicUserGameRepo;
	}
}
