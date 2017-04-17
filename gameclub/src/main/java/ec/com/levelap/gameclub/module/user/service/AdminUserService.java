package ec.com.levelap.gameclub.module.user.service;

import java.util.HashMap;

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
import ec.com.levelap.gameclub.module.user.entity.AdminUser;
import ec.com.levelap.gameclub.module.user.repository.AdminUserRepo;
import ec.com.levelap.gameclub.utils.Const;

@Service
public class AdminUserService {
	@Autowired
	private AdminUserRepo adminUserRepo;
	
	@Transactional
	public AdminUser getCurrentUser() throws ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			AdminUser adminUser = adminUserRepo.findByUsername(auth.getName());
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
			adminUserRepo.save(user);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<ErrorControl>(new ErrorControl("La contraseña temporal es incorrecta. Intente nuevamente", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public AdminUserRepo getAdminUserRepo() {
		return adminUserRepo;
	}
}
