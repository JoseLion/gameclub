package ec.com.levelap.gameclub.module.reports.logisticsKits.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.reports.logisticsKits.entity.LogisticsKits;

@Repository
public interface LogisticsKitsRepo extends JpaRepository<LogisticsKits, Long> {

	@Query("SELECT lk FROM LogisticsKits lk ORDER BY lk.id DESC")
	public Page<LogisticsKits> logisticsKits(Pageable page);
	
	@Query("SELECT COUNT(lk) FROM LogisticsKits lk WHERE lk.transactionType='Shipping Kits' AND lk.status='Entregada' ")
	public Long totalShippingKits();
	
	@Query("SELECT COUNT(lk) FROM LogisticsKits lk WHERE lk.transactionType='Shipping Kits' ")
	public Long totalShippingKitsSold();
	
	@Query("SELECT COUNT(lk) FROM LogisticsKits lk WHERE lk.transactionType='Welcome Kits' AND lk.status='Entregada' ")
	public Long totalWelcomeKits();
	
	@Query("SELECT lk FROM LogisticsKits lk " +
			"WHERE	 " + 
			"		(UPPER(lk.name) LIKE UPPER('%' || :name || '%') OR  "+
			"		UPPER(lk.lastName) LIKE UPPER('%' || :name || '%') OR " +
			"		UPPER(lk.name || ' ' || lk.lastName) LIKE UPPER('%' || :name || '%')) AND " +
			"		(:document IS NULL OR lk.document like ('%' || :document || '%')) AND" +
			"		(:transaction IS NULL OR UPPER(lk.transactionType) like UPPER('%' || :transaction || '%')) AND" +
			"		(DATE(lk.creationDate) BETWEEN DATE(:startDate) AND DATE(:endDate)) " 
			)
	public List<LogisticsKits> findLogisticsKit(
									  @Param("name") String name,
									  @Param("document") String document,
									  @Param("transaction") String transaction,
									  @Param("startDate") Date startDate,
									  @Param("endDate") Date endDate
									  );
}
