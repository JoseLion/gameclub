package ec.com.levelap.gameclub.application;

import ec.com.levelap.base.LevelapBaseConfig;

public class GameClubBaseJpa implements LevelapBaseConfig {

	@Override
	public Long getCurrentUser() {
		/*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			AuthenticatedUser user = (AuthenticatedUser) auth.getPrincipal();
			return user.getId();
		}*/
		
		return -1L;
	}

}
