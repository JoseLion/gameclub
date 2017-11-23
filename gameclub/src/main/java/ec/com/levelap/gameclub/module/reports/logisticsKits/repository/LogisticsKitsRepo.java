package ec.com.levelap.gameclub.module.reports.logisticsKits.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
