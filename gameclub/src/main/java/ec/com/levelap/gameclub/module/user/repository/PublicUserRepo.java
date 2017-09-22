package ec.com.levelap.gameclub.module.user.repository;

import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserLite;

@Repository
public interface PublicUserRepo extends JpaRepository<PublicUser, Long> {
	
	public PublicUser findByUsernameIgnoreCase(String username);

	@Query(   "SELECT "
			+ "     a.id AS id, "
			+ "     a.name AS name, "
			+ "     a.lastName AS lastName, "
			+ "     a.status AS status, "
			+ "     a.creationDate AS creationDate "
			+ "FROM PublicUser a "
			+ "WHERE "
			+ "     UPPER(a.name) LIKE UPPER('%' || :name || '%') "
			+ "     AND UPPER(a.lastName) LIKE UPPER('%' || :lastName || '%') "
			+ "     AND (:status IS NULL OR a.status=:status) "
			+ "     AND DATE(a.creationDate) BETWEEN DATE(:startDate) AND DATE(:endDate) ")
	public Page<PublicUserLite> findPublicUsers(
			@Param("name") String name,
			@Param("lastName") String lastName,
			@Param("status") Boolean status,
			@Param("startDate") Date startDate,
			@Param("endDate") Date endDate,
			Pageable page);
	
	@Query(	"SELECT " +
				"(CASE WHEN ld.id=:userId THEN " +
					"('' || COUNT(ldg.isBorrewed)) " +
				"WHEN g.id=:userId THEN " +
					"('' || COUNT(gg.isBorrewed)) " +
				"ELSE '' END) AS borrowed " +
			"FROM Loan l " +
				"LEFT JOIN l.publicUserGame.publicUser ld " +
				"LEFT JOIN ld.games ldg " +
				"LEFT JOIN l.gamer g " +
				"LEFT JOIN g.games gg " +
			"WHERE " +
				"ld.id=:userId OR " +
				"g.id=:userId " +
			"GROUP BY ld, ldg, g, gg, l.gamer")
	public Map<String, String> getGamesSummary(@Param("userId") Long userId);

}
