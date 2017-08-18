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
				"w.deliveryNumber AS deliveryNumber " +
			"FROM WelcomeKit w " +
				"LEFT JOIN w.publicUser p " +
				"LEFT JOIN w.shippingStatus s " +
			"WHERE " +
				"w.wasComfirmed=TRUE AND " +
				"(UPPER(p.name) LIKE UPPER('%' || :name || '%') OR UPPER(p.lastName) LIKE UPPER('%' || :name || '%') OR UPPER(p.name || ' ' || p.lastName) LIKE UPPER('%' || :name || '%')) AND " +
				"UPPER(w.deliveryNumber) LIKE UPPER('%' || :deliveryNumber || '%') AND " +
				"(DATE(w.confirmationDate) BETWEEN DATE(:startDate) AND DATE(:endDate)) AND " +
				"(:province IS NULL OR p.location.parent=:province) AND " +
				"(:city IS NULL OR p.location=:city) AND " +
				"(:shippingStatus IS NULL OR w.shippingStatus=:shippingStatus) " +
			"ORDER BY w.creationDate DESC")
	Page<WelcomeKitLite> findWelcomeKits(@Param("name") String name,
										@Param("startDate") Date startDate,
										@Param("endDate") Date endDate,
										@Param("province") Location province,
										@Param("city") Location city,
										@Param("deliveryNumber") String deliveryNumber,
										@Param("shippingStatus") Catalog shippingStatus,
										Pageable page);
	
	List<WelcomeKit> findByMessageIdOrderByCreationDateDesc(Long messageId);
}
