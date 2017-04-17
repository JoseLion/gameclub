package ec.com.levelap.gameclub.module.profile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.gameclub.module.profile.repository.ProfileRepo;

@Service
public class ProfileService {
	@Autowired
	private ProfileRepo profileRepo;

	public ProfileRepo getProfileRepo() {
		return profileRepo;
	}
}
