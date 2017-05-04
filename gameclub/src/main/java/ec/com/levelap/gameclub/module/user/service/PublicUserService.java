package ec.com.levelap.gameclub.module.user.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
import ec.com.levelap.gameclub.module.mail.service.MailService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.repository.PublicUserRepo;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.mail.MailParameters;

@Service
public class PublicUserService {
	@Autowired
	private PublicUserRepo publicUserRepo;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private DocumentService documentService;
	
	@Transactional
	public ResponseEntity<?> signIn(PublicUser publicUser, HttpServletRequest request) throws ServletException, MessagingException {
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
		String baseUrl = request.getRequestURL().substring(0, request.getRequestURL().indexOf(request.getRequestURI())).toString() + "/gameclub/";
		params.put("link", baseUrl + "open/publicUser/verifyAccount/" + publicUser.getToken() + "/" + publicUser.getId());
		
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
	public void resendVerification(HttpServletRequest request) throws ServletException, MessagingException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PublicUser publicUser = publicUserRepo.findByUsername(auth.getName());
		
		MailParameters mailParameters = new MailParameters();
		mailParameters.setRecipentTO(Arrays.asList(publicUser.getUsername()));
		Map<String, String> params = new HashMap<>();
		String baseUrl = request.getRequestURL().substring(0, request.getRequestURL().indexOf(request.getRequestURI())).toString() + "/gameclub/";
		params.put("link", baseUrl + "open/publicUser/verifyAccount/" + publicUser.getToken() + "/" + publicUser.getId());
		
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

	public PublicUserRepo getPublicUserRepo() {
		return publicUserRepo;
	}
}