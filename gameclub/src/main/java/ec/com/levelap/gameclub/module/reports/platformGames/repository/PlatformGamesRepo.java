package ec.com.levelap.gameclub.module.reports.platformGames.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.reports.platformGames.entity.PlatformGames;

@Repository
public interface PlatformGamesRepo extends JpaRepository<PlatformGames, Long> {
	
	@Query("SELECT pg FROM PlatformGames pg ORDER BY pg.id DESC")
	public Page<PlatformGames> platformGamesPage(Pageable page);
	
	@Query("SELECT COUNT(g) FROM PlatformGames g ")
	public Long totalGames();
	
	@Query("SELECT pg FROM PlatformGames pg WHERE " +
			"		(UPPER(pg.fullName) LIKE UPPER('%' || :name || '%')) AND " +
			"		(:game IS NULL OR UPPER(pg.game) like UPPER('%' || :game || '%')) AND " +
			"		(:console IS NULL OR UPPER(pg.console) like UPPER('%' || :console || '%')) AND " +
			"		(DATE(pg.creationDate) BETWEEN DATE(:startDate) AND DATE(:endDate)) " +
			"ORDER BY pg.creationDate DESC " 
			)
	public Page<PlatformGames> find(
									  @Param("name") String name,
									  @Param("game") String game,
									  @Param("console") String console,
									  @Param("startDate") Date startDate,
									  @Param("endDate") Date endDate,
									  Pageable page
									  );
}
