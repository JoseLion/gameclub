package ec.com.levelap.gameclub.module.profile.controller;

import java.util.List;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.profile.entity.Profile;
import ec.com.levelap.gameclub.module.profile.entity.ProfileLite;
import ec.com.levelap.gameclub.module.profile.service.ProfileService;

@RestController
@RequestMapping(value="api/profile", produces=MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {
	@Autowired
	private ProfileService profileService;
	
	@RequestMapping(value="findProfiles", method=RequestMethod.POST)
	public ResponseEntity<List<ProfileLite>> findProfiles(@RequestBody(required=false) Object name) throws ServletException {
		String search = "";
		if (name != null) {
			search = (String)name;
		}
		
		List<ProfileLite> profiles = profileService.getProfileRepo().findByNameContainingOrderByName(search);
		return new ResponseEntity<List<ProfileLite>>(profiles, HttpStatus.OK);
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody Profile profile) throws ServletException {
		return profileService.save(profile);
	}
	
	@RequestMapping(value="findOne/{id}", method=RequestMethod.GET)
	public ResponseEntity<Profile> findOne(@PathVariable Long id) throws ServletException {
		Profile profile = profileService.getProfileRepo().findOne(id);
		return new ResponseEntity<Profile>(profile, HttpStatus.OK);
	}
	
	@Transactional
	@RequestMapping(value="changeStatus/{id}", method=RequestMethod.GET)
	public ResponseEntity<Boolean> changeStatus(@PathVariable Long id) throws ServletException {
		Profile profile = profileService.getProfileRepo().findOne(id);
		profile = profileService.changeStatus(profile);
		profile = profileService.getProfileRepo().save(profile);
		
		return new ResponseEntity<Boolean>(profile.getStatus(), HttpStatus.OK);
	}
}
