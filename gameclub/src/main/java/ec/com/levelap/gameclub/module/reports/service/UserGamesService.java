package ec.com.levelap.gameclub.module.reports.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.reports.entity.UserGames;
import ec.com.levelap.gameclub.module.reports.repository.UserGamesRepo;

@Service
public class UserGamesService extends BaseService<UserGames> {

	public UserGamesService() {
		super(UserGames.class);
	}

	@Autowired
	private UserGamesRepo userGamesRepo;

	public UserGamesRepo getUserGamesRepo() {
		return userGamesRepo;
	}
	
	
}
