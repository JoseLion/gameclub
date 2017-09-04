package ec.com.levelap.gameclub.module.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.commons.location.Location;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGameOpen;

@Repository
public interface PublicUserGameRepo extends JpaRepository<PublicUserGame, Long> {
	@Query(	"SELECT pg FROM PublicUserGame pg LEFT JOIN pg.console c WHERE pg.publicUser=:publicUser AND (:consoleId IS NULL OR c.id=:consoleId)")
	public Page<PublicUserGame> findMyGames(@Param("publicUser") PublicUser publicUser, @Param("consoleId") Long consoleId, Pageable page);
	
	@Query(	"SELECT " +
				"pg.id AS id, " +
				"pg.status AS status, " +
				"lp.cost AS shippingCost, " +
				"pg.publicUser AS publicUser, " +
				"pg.cost AS cost, " +
				"pg.integrity AS integrity, " +
				"pg.observations AS observations " +
			"FROM PublicUserGame pg, LocationPrice lp " +
				"LEFT JOIN pg.publicUser p " +
				"LEFT JOIN pg.console c " +
				"LEFT JOIN pg.game g " +
			"WHERE " +
				"(lp.origin=:origin AND lp.destination=p.location) AND " +
				"pg.status=TRUE AND " +
				"p.isReady=TRUE AND " +
				"(:publicUser IS NULL OR p<>:publicUser) AND " +
				"g.id=:gameId AND " +
				"c.id=:consoleId " +
			"ORDER BY pg.status DESC, p.rating DESC, pg.integrity DESC")
	public Page<PublicUserGameOpen> findAvailableGames(@Param("publicUser") PublicUser publicUser, @Param("origin") Location origin, @Param("gameId") Long gameId, @Param("consoleId") Long consoleId, Pageable page);
	
	@Query(	"SELECT " +
			"pg.id AS id, " +
			"pg.status AS status, " +
			"'' AS shippingCost, " +
			"pg.publicUser AS publicUser, " +
			"pg.cost AS cost, " +
			"pg.integrity AS integrity, " +
			"pg.observations AS observations " +
		"FROM PublicUserGame pg " +
			"LEFT JOIN pg.publicUser p " +
			"LEFT JOIN pg.console c " +
			"LEFT JOIN pg.game g " +
		"WHERE " +
			"pg.status=TRUE AND " +
			"p.isReady=TRUE AND " +
			"g.id=:gameId AND " +
			"c.id=:consoleId " +
		"ORDER BY pg.status DESC, p.rating DESC, pg.integrity DESC")
	public Page<PublicUserGameOpen> findAvailableGamesOpen(@Param("gameId") Long gameId, @Param("consoleId") Long consoleId, Pageable page);
}