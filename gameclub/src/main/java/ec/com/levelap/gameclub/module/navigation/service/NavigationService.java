package ec.com.levelap.gameclub.module.navigation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.gameclub.module.navigation.repository.NavigationRepo;

@Service
public class NavigationService {
	@Autowired
	private NavigationRepo navigationRepo;

	public NavigationRepo getNavigationRepo() {
		return navigationRepo;
	}
}
