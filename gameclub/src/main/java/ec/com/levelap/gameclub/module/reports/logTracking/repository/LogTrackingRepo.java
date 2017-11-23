package ec.com.levelap.gameclub.module.reports.logTracking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.reports.logTracking.entity.LogTracking;

@Repository
public interface LogTrackingRepo extends JpaRepository<LogTracking, Long>{

	@Query("SELECT lt FROM LogTracking lt ")
	public Page<LogTracking> logTrackingPage(Pageable page);
	
//	@Query("SELECT SUM((b.cost*b.weeks)+b.shippingCost+b.feeGgameClub+b.taxes) FROM Billing b ")
//	public Double total();
}
