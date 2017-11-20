package ec.com.levelap.gameclub.module.platformGames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.platformGames.entity.PlatformGames;

@Repository
public interface PlatformGamesRepo extends JpaRepository<PlatformGames, Long> {

}
