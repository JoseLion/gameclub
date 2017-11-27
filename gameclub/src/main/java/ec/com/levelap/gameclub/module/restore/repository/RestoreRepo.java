package ec.com.levelap.gameclub.module.restore.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.restore.entity.Restore;
import ec.com.levelap.gameclub.module.restore.entity.RestoreLite;

@Repository
public interface RestoreRepo extends JpaRepository<Restore, Long> {
	@Query(	"SELECT " +
				"r.id AS id, " +
				"r.creationDate AS creationDate, " +
				"r.publicUserGame AS publicUserGame, " +
				"r.gamer AS gamer, " +
				"r.tracking AS tracking, " +
				"r.shippingStatus AS shippingStatus " +
			"FROM Restore r " +
				"LEFT JOIN r.publicUserGame pg " +
				"LEFT JOIN pg.publicUser p " +
				"LEFT JOIN r.gamer g " +
			"WHERE " +
				"r.lenderConfirmDate IS NOT NULL AND " +
				"r.gamerConfirmDate IS NOT NULL AND " +
				"(UPPER(p.name) LIKE UPPER('%' || :lender || '%') OR UPPER(p.lastName) LIKE UPPER('%' || :lender || '%') OR UPPER(p.name || ' ' || p.lastName) LIKE ('%' || :lender || '%')) AND " +
				"(UPPER(g.name) LIKE UPPER('%' || :gamer || '%') OR UPPER(g.lastName) LIKE UPPER('%' || :gamer || '%') OR UPPER(g.name || ' ' || g.lastName) LIKE ('%' || :gamer || '%')) AND " +
				"(:shippingStatus IS NULL OR r.shippingStatus=:shippingStatus) AND " +
				"UPPER(r.tracking) LIKE UPPER('%' || :tracking || '%') AND " +
				"DATE(r.creationDate) BETWEEN DATE(:startDate) AND DATE(:endDate) " +
			"ORDER BY r.creationDate DESC")
	public Page<RestoreLite> findRestores(@Param("lender") String lender,
			@Param("gamer") String gamer,
			@Param("shippingStatus") Catalog shippingStatus,
			@Param("tracking") String tracking,
			@Param("startDate") Date startDate,
			@Param("endDate") Date endDate,
			Pageable page);
	
	public Restore findByLoan(Loan loan);
	
	public RestoreLite findById(Long id);
}
