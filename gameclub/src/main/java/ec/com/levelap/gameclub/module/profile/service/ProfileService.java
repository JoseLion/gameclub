package ec.com.levelap.gameclub.module.profile.service;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.profile.entity.Profile;
import ec.com.levelap.gameclub.module.profile.repository.ProfileRepo;

@Service
public class ProfileService extends BaseService<Profile> {
	public ProfileService() {
		super(Profile.class);
	}

	@Autowired
	private ProfileRepo profileRepo;
	
	@Transactional
	public ResponseEntity<?> save(Profile profile) throws ServletException {
		if (profile.getId() == null) {
			Profile found = profileRepo.findByName(profile.getName());
			
			if (found != null) {
				return new ResponseEntity<ErrorControl>(new ErrorControl("El perfil con nombre \"" + profile.getName() + "\" ya existe", true), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		profileRepo.save(profile);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public ProfileRepo getProfileRepo() {
		return profileRepo;
	}
}
