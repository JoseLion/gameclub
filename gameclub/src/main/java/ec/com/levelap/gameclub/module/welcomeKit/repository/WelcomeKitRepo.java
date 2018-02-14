package ec.com.levelap.gameclub.module.welcomeKit.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.location.Location;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKit;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKitLite;

@Repository
public interface WelcomeKitRepo extends JpaRepository<WelcomeKit, Long> {
	
	@Query(	"SELECT " +
				"w.id AS id, " +
				"w.confirmationDate AS confirmationDate, " +
				"p AS publicUser, " +
				"s AS shippingStatus, " +
				"w.tracking AS tracking, " +
				"w.quantity AS quantity " +
			"FROM WelcomeKit w " +
				"LEFT JOIN w.publicUser p " +
				"LEFT JOIN w.shippingStatus s " +
			"WHERE " +
				"w.wasConfirmed=TRUE AND " +
				"w.quantity=0 AND " +
				"(UPPER(p.name) LIKE UPPER('%' || :name || '%') OR UPPER(p.lastName) LIKE UPPER('%' || :name || '%') OR UPPER(p.name || ' ' || p.lastName) LIKE UPPER('%' || :name || '%')) AND " +
                "(w.tracking IS NULL OR UPPER(w.tracking) LIKE UPPER('%' || :tracking || '%')) AND " +
				"(DATE(w.confirmationDate) BETWEEN DATE(:startDate) AND DATE(:endDate)) AND " +
				"(:province IS NULL OR p.location.parent=:province) AND " +
				"(:city IS NULL OR p.location=:city) AND " +
				"(:shippingStatus IS NULL OR w.shippingStatus=:shippingStatus) " +
			"ORDER BY w.creationDate DESC")
	public Page<WelcomeKitLite> findWelcomeKits(@Param("name") String name,
										@Param("startDate") Date startDate,
										@Param("endDate") Date endDate,
										@Param("province") Location province,
										@Param("city") Location city,
										@Param("tracking") String tracking,
										@Param("shippingStatus") Catalog shippingStatus,
										Pageable page);
	
	@Query(	"SELECT "
			+ "     w.id AS id, "
			+ "     w.confirmationDate AS confirmationDate, "
			+ "     p AS publicUser, "
			+ "     s AS shippingStatus, "
			+ "     w.tracking AS tracking, "
			+ "     w.quantity AS quantity "
			+ "FROM WelcomeKit w "
			+ "     LEFT JOIN w.publicUser p "
			+ "     LEFT JOIN w.shippingStatus s "
			+ "WHERE "
			+ "     w.wasConfirmed=TRUE AND "
			+ "     w.quantity>0 AND "
			+ "     (UPPER(p.name) LIKE UPPER('%' || :name || '%') OR UPPER(p.lastName) LIKE UPPER('%' || :name || '%') OR UPPER(p.name || ' ' || p.lastName) LIKE UPPER('%' || :name || '%')) AND "
			+ "     (w.tracking IS NULL OR UPPER(w.tracking) LIKE UPPER('%' || :tracking || '%')) AND "
			+ "     (DATE(w.confirmationDate) BETWEEN DATE(:startDate) AND DATE(:endDate)) AND "
			+ "     (:province IS NULL OR p.location.parent=:province) AND "
			+ "     (:city IS NULL OR p.location=:city) AND "
			+ "     (:shippingStatus IS NULL OR w.shippingStatus=:shippingStatus) "
			+ "     ORDER BY w.creationDate DESC")
	public Page<WelcomeKitLite> findShippingKits(
			@Param("name") String name,
			@Param("startDate") Date startDate,
			@Param("endDate") Date endDate,
			@Param("province") Location province,
			@Param("city") Location city,
			@Param("tracking") String tracking,
			@Param("shippingStatus") Catalog shippingStatus,
			Pageable page);
	
	public WelcomeKit findByTransactionId(String transactionId);
	
	public List<WelcomeKit> findByMessageIdOrderByCreationDateDesc(Long messageId);
	
	public WelcomeKit findByMessageId(Long messageId);
	
	public WelcomeKitLite findById(Long id);
}
