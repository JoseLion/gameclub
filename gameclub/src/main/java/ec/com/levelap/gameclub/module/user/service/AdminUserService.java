package ec.com.levelap.gameclub.module.user.service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.user.entity.AdminUser;
import ec.com.levelap.gameclub.module.user.repository.AdminUserRepo;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.gameclub.utils.GameClubMailService;
import ec.com.levelap.mail.entity.LevelapMail;

@Service
public class AdminUserService extends BaseService<AdminUser> {
	public AdminUserService() {
		super(AdminUser.class);
	}

	@Autowired
	private AdminUserRepo adminUserRepo;
	
	@Autowired
	private GameClubMailService mail;
	
	@Transactional
	public AdminUser getCurrentUser() throws ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			AdminUser adminUser = adminUserRepo.findByUsernameIgnoreCase(auth.getName());
			return adminUser;
		}
		
		return null;
	}
	
	@Transactional
	public ResponseEntity<?> changePassword(HashMap<String, String> passwordMap) throws ServletException {
		AdminUser user = this.getCurrentUser();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(Const.ENCODER_STRENGTH);
		
		if (user == null) {
			return new ResponseEntity<ErrorControl>(new ErrorControl("No se pudo encontrar el usuario actual. Comuniquese con el administrador del sistema", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (encoder.matches(passwordMap.get("temp"), user.getPassword())) {
			user.setPassword(encoder.encode(passwordMap.get("password")));
			user.setHasTempPassword(false);
			user = adminUserRepo.save(user);
			return new ResponseEntity<AdminUser>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<ErrorControl>(new ErrorControl("La contraseña temporal es incorrecta. Intente nuevamente", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Transactional
	public ResponseEntity<?> save(AdminUser adminUser) throws ServletException {
		if (adminUser.getId() == null) {
			AdminUser found = adminUserRepo.findByUsernameIgnoreCase(adminUser.getUsername());
			
			if (found != null) {
				return new ResponseEntity<ErrorControl>(new ErrorControl("El correo electrónico ingresado ya existe", true), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(Const.ENCODER_STRENGTH);
			SecureRandom random = new SecureRandom();
			
			String randomPassword = "";
			for (int i = 0; i < 8; i++) {
				randomPassword += Const.PASSWORD_SYMBOLS.charAt(random.nextInt(Const.PASSWORD_SYMBOLS.length()));
			}
			
			String encodedPassword = encoder.encode(randomPassword);
			adminUser.setPassword(encodedPassword);
			adminUser.setHasTempPassword(true);
			
			LevelapMail levelapMail = new LevelapMail();
			levelapMail.setRecipentTO(Arrays.asList(adminUser.getUsername()));
			Map<String, String> params = new HashMap<>();
			params.put("name", adminUser.getFullName());
			params.put("password", randomPassword);
			
			try {
				mail.sendMailWihTemplate(levelapMail, "TMPWRD", params);
			} catch (MessagingException e) {
				e.printStackTrace();
				adminUserRepo.save(adminUser);
				return new ResponseEntity<ErrorControl>(new ErrorControl("El usuario fue guardado pero el correo con la contraseña no pude ser enviado. Inténtalo más tarde desde el botón \"Reestablecer Contraseña\"", true), HttpStatus.NOT_IMPLEMENTED);
			}
		}
		
		adminUserRepo.save(adminUser);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Transactional
	public void resetPassword(Long id) throws ServletException {
		AdminUser adminUser = adminUserRepo.findOne(id);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(Const.ENCODER_STRENGTH);
		SecureRandom random = new SecureRandom();
		
		String randomPassword = "";
		for (int i = 0; i < 8; i++) {
			randomPassword += Const.PASSWORD_SYMBOLS.charAt(random.nextInt(Const.PASSWORD_SYMBOLS.length()));
		}
		
		String encodedPassword = encoder.encode(randomPassword);
		adminUser.setPassword(encodedPassword);
		adminUser.setHasTempPassword(true);
		
		LevelapMail levelapMail = new LevelapMail();
		levelapMail.setRecipentTO(Arrays.asList(adminUser.getUsername()));
		Map<String, String> params = new HashMap<>();
		params.put("name", adminUser.getFullName());
		params.put("password", randomPassword);
		
		try {
			mail.sendMailWihTemplate(levelapMail, "TMPWRD", params);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		adminUserRepo.save(adminUser);
	}

	public AdminUserRepo getAdminUserRepo() {
		return adminUserRepo;
	}
}
