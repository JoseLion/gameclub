package ec.com.levelap.gameclub.application;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ec.com.levelap.base.LevelapBaseConfig;
import ec.com.levelap.security.AuthenticatedUser;

public class GameclubWsBaseJpa implements LevelapBaseConfig {

	@Override
	public Long getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			AuthenticatedUser user = (AuthenticatedUser) auth.getPrincipal();
			return user.getId();
		}
		
		return -1L;
	}

}
