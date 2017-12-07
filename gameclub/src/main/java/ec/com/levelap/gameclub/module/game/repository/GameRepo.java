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
import ec.com.levelap.gameclub.utils.Code;
import ec.com.levelap.gameclub.utils.Const;

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
				"g.trailerUrl AS trailerUrl, " +
				"gm.rating AS rating, " +
				"g.contentRating AS contentRating, " +
				"g.cover AS cover, " +
				"g.diamond AS diamond, " +
				"g.releaseDate AS releaseDate " +
			"FROM Game g, GameMagazine gm " +
				"LEFT JOIN g.consoles cn " +
				"LEFT JOIN g.categories ct " +
				"LEFT JOIN gm.magazine m " +
			"WHERE " +
				"g.status=TRUE AND " +
				"(gm.game=g AND m.name='" + Const.RATING_MAGAZINE + "') AND " +
				"UPPER(g.name) LIKE UPPER('%' || :name || '%') AND " +
				"(:categoryId IS NULL OR ct.category.id=:categoryId) AND " +
				"cn.console.id=:consoleId " +
			"ORDER BY g.releaseDate DESC, g.name ASC")
	public Page<GameOpen> findGamesOpen(@Param("name") String name, @Param("categoryId") Long categoryId, @Param("consoleId") Long consoleId, Pageable page);
	
	public List<GameOpen> findByStatusIsTrueAndCategoriesCategoryIdOrderByName(Long categoryId);
	
	@Query("SELECT DISTINCT g.name FROM Game g WHERE g.status=TRUE AND UPPER(g.name) LIKE '%' || UPPER(?1) || '%'")
	public List<String> findAutocomplete(String name);
	
	@Query(	"SELECT " +
				"g.id AS id, " +
				"g.name AS name, " +
				"g.trailerUrl AS trailerUrl, " +
				"gm.rating AS rating, " +
				"g.contentRating AS contentRating, " +
				"g.cover AS cover, " +
				"g.diamond AS diamond " +
			"FROM Loan l, GameMagazine gm " +
				"LEFT JOIN l.publicUserGame pg " +
				"LEFT JOIN pg.game g " +
				"LEFT JOIN g.contentRating r " +
				"LEFT JOIN g.cover c " +
				"LEFT JOIN g.diamond d " +
				"LEFT JOIN gm.magazine m " +
			"WHERE " +
				"g.status=TRUE AND " +
				"(gm.game=g AND m.name='" + Const.RATING_MAGAZINE + "') " +
				"GROUP BY g, gm, r, c, d " +
			"ORDER BY COUNT(g) DESC")
	public Page<GameOpen> findMostPlayed(Pageable page);
	
	public Page<GameOpen> findByStatusIsTrueOrderByName(Pageable page);
	
	@Query("SELECT s.value FROM Setting s WHERE s.code='" + Code.SETTING_NATIONALIZACION + "'")
	public String priceChartinNationalitation();
	
}
