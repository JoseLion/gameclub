package ec.com.levelap.gameclub.module.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;

@Repository
public interface PublicUserGameRepo extends JpaRepository<PublicUserGame, Long> {
	@Query(	"SELECT pg FROM PublicUserGame pg LEFT JOIN pg.console c WHERE pg.publicUser=:publicUser AND (:consoleId IS NULL OR c.id=:consoleId)")
	public Page<PublicUserGame> findMyGames(@Param("publicUser") PublicUser publicUser, @Param("consoleId") Long consoleId, Pageable page);
	
	@Query(	"SELECT pg FROM PublicUserGame pg " +
				"LEFT JOIN pg.publicUser p " +
				"LEFT JOIN pg.console c " +
				"LEFT JOIN pg.game g " +
			"WHERE " +
				"p.id<>:publicUserId AND " +
				"g.id=:gameId AND " +
				"c.id=:consoleId " +
			"ORDER BY pg.status DESC, p.rating DESC, pg.integrity DESC")
	public Page<PublicUserGame> findAvailableGames(@Param("publicUserId") Long publicUserId, @Param("gameId") Long gameId, @Param("consoleId") Long consoleId, Pageable page);
}