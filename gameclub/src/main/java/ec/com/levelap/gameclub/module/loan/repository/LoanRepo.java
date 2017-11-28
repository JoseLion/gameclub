package ec.com.levelap.gameclub.module.loan.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.entity.LoanLite;

@Repository
public interface LoanRepo extends JpaRepository<Loan, Long> {
	@Query(	"SELECT " +
				"l.id AS id, " +
				"l.creationDate AS creationDate, " +
				"l.publicUserGame AS publicUserGame, " +
				"l.gamer AS gamer, " +
				"l.tracking AS tracking, " +
				"l.shippingStatus AS shippingStatus, " +
				"l.deliveryDate AS deliveryDate, " +
				"l.weeks AS weeks " +
			"FROM Loan l " +
				"LEFT JOIN l.publicUserGame pg " +
				"LEFT JOIN pg.publicUser p " +
				"LEFT JOIN l.gamer g " +
			"WHERE " +
				"l.lenderStatusDate IS NOT NULL AND " +
				"l.gamerStatusDate IS NOT NULL AND " +
				"(UPPER(p.name) LIKE UPPER('%' || :lender || '%') OR UPPER(p.lastName) LIKE UPPER('%' || :lender || '%') OR UPPER(p.name || ' ' || p.lastName) LIKE ('%' || :lender || '%')) AND " +
				"(UPPER(g.name) LIKE UPPER('%' || :gamer || '%') OR UPPER(g.lastName) LIKE UPPER('%' || :gamer || '%') OR UPPER(g.name || ' ' || g.lastName) LIKE ('%' || :gamer || '%')) AND " +
				"(:shippingStatus IS NULL OR l.shippingStatus=:shippingStatus) AND " +
				"(l.tracking IS NULL OR UPPER(l.tracking) LIKE UPPER('%' || :tracking || '%')) AND " +
				"DATE(l.creationDate) BETWEEN DATE(:startDate) AND DATE(:endDate) " +
			"ORDER BY l.creationDate DESC")
	public Page<LoanLite> findLoans(@Param("lender") String lender,
									@Param("gamer") String gamer,
									@Param("shippingStatus") Catalog shippingStatus,
									@Param("tracking") String tracking,
									@Param("startDate") Date startDate,
									@Param("endDate") Date endDate,
									Pageable page);
	
	public Loan findByGamerMessageIdOrLenderMessageId(Long gamerMessageId, Long lenderMessageId);
	
	public List<Loan> findByShippingStatusCode(String code);
	
	public LoanLite findById(Long id);
}
