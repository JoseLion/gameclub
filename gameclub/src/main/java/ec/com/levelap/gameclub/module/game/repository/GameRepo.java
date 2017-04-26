package ec.com.levelap.gameclub.module.game.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.category.entity.Category;
import ec.com.levelap.gameclub.module.console.entity.Console;
import ec.com.levelap.gameclub.module.game.entity.Game;
import ec.com.levelap.gameclub.module.game.entity.GameLite;

@Repository
public interface GameRepo extends JpaRepository<Game, Long> {
	@Query(	"SELECT g.id AS id, " +
				"g.name AS name, " +
				"g.releaseDate AS releaseDate, " +
				"g.cover AS cover, " +
				"g.status AS status, " +
				"g.consoles AS consoles, " +
				"g.categories AS categories " +
			"FROM Game g WHERE " +
				"UPPER(g.name) LIKE UPPER('%' || :name || '%') AND " +
				"(g.releaseDate BETWEEN :releaseStart AND :releaseEnd) AND " +
				"(:status IS NULL OR g.status=:status) AND " +
				"(:console IS NULL OR :console IN g.consoles) AND " +
				"(:category IS NULL OR :category IN g.categories) " +
			"ORDER BY g.name DESC, g.releaseDate DESC")
	public Page<GameLite> findGames(
			@Param("name") String name,
			@Param("releaseStart") Date releaseStart,
			@Param("releaseEnd") Date releaseEnd,
			@Param("status") Boolean status,
			@Param("console") Console console,
			@Param("category") Category category,
			Pageable page);
	
	public Game findByName(String name);
	
	public Game findByNameAndNameIsNot(String name, String notName);
}
