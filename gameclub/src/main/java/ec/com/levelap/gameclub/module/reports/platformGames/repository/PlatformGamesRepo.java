package ec.com.levelap.gameclub.module.reports.platformGames.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.reports.platformGames.entity.PlatformGames;

@Repository
public interface PlatformGamesRepo extends JpaRepository<PlatformGames, Long> {
	
	@Query("SELECT pg FROM PlatformGames pg ORDER BY pg.id DESC")
	public Page<PlatformGames> platformGamesPage(Pageable page);
	
	@Query("SELECT COUNT(g) FROM Game g ")
	public Long totalGames();
}
