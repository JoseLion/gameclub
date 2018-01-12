package ec.com.levelap.gameclub.module.user.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserLite;
import ec.com.levelap.gameclub.utils.Code;

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
	
	public Long countByIdAndGamesIsBorrowedIsTrue(Long id);
	
	@Query(	"SELECT COUNT(l) FROM Loan l " +
				"LEFT JOIN l.restore r " +
				"LEFT JOIN r.shippingStatus s " +
			"WHERE " +
				"l.gamer.id=:id AND " +
				"l.status=TRUE AND " +
				"l.publicUserGame.isBorrowed=TRUE AND " +
				"(r IS NULL OR s.code<>'" + Code.SHIPPING_DELIVERED + "')")
	public Long countGamesToReturn(@Param("id") Long id);
	
	public PublicUser findByUrlToken(String urlToken);
	
	@Query("SELECT pu.privateKey FROM PublicUser pu WHERE pu.id=:id ")
	public byte[] findKey(@Param("id") Long id);
}
