package ec.com.levelap.gameclub.module.game.repository;

import java.util.Date;
import java.util.List;

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
import ec.com.levelap.gameclub.module.game.entity.GameOpen;

@Repository
public interface GameRepo extends JpaRepository<Game, Long> {
	@Query(	"SELECT DISTINCT " +
				"g.id AS id, " +
				"g.name AS name, " +
				"g.releaseDate AS releaseDate, " +
				"g.status AS status " +
			"FROM Game g " +
				"LEFT JOIN g.consoles cn " +
				"LEFT JOIN g.categories ct " +
			"WHERE " +
				"UPPER(g.name) LIKE UPPER('%' || :name || '%') AND " +
				"(g.releaseDate BETWEEN :releaseStart AND :releaseEnd) AND " +
				"(:status IS NULL OR g.status=:status) AND " +
				"(:console IS NULL OR cn.console=:console) AND " +
				"(:category IS NULL OR ct.category=:category) " +
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
	
	@Query(	"SELECT DISTINCT " +
				"g.id AS id, " +
				"g.name AS name, " +
				"g.contentRating AS contentRating, " +
				"g.cover AS cover " +
			"FROM Game g " +
				"LEFT JOIN g.consoles cn " +
				"LEFT JOIN g.categories ct " +
			"WHERE " +
				"UPPER(g.name) LIKE UPPER('%' || :name || '%') AND " +
				"(:categoryId IS NULL OR ct.category.id=:categoryId) AND " +
				"cn.console.id=:consoleId " +
			"ORDER BY g.name DESC")
	public Page<GameOpen> findGamesOpen(@Param("name") String name, @Param("categoryId") Long categoryId, @Param("consoleId") Long consoleId, Pageable page);
	
	public List<GameOpen> findByCategoriesCategoryIdOrderByName(Long categoryId);
}
