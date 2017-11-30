package ec.com.levelap.gameclub.module.reports.logTracking.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.reports.logTracking.entity.LogTracking;

@Repository
public interface LogTrackingRepo extends JpaRepository<LogTracking, Long>{

	@Query("SELECT lt FROM LogTracking lt ")
	public Page<LogTracking> logTrackingPage(Pageable page);
	
//	@Query("SELECT SUM((b.cost*b.weeks)+b.shippingCost+b.feeGgameClub+b.taxes) FROM Billing b ")
//	public Double total();
	
	@Query("SELECT lt FROM LogTracking lt WHERE " +
			"		(UPPER(lt.name) LIKE UPPER('%' || :name || '%') OR  "+
			"		UPPER(lt.lastName) LIKE UPPER('%' || :name || '%') OR " +
			"		UPPER(lt.name || ' ' || lt.lastName) LIKE UPPER('%' || :name || '%')) AND " +
			"		(:document IS NULL OR lt.document like ('%' || :document || '%')) AND " +
			"		(:game IS NULL OR UPPER(lt.game) like UPPER('%' || :game || '%')) AND " +
			"		(DATE(lt.creationDate) BETWEEN DATE(:startDate) AND DATE(:endDate)) " +
			"ORDER BY lt.creationDate DESC " 
			)
	public List<LogTracking> findLogTracking(
									  @Param("name") String name,
									  @Param("document") String document,
									  @Param("game") String game,
									  @Param("startDate") Date startDate,
									  @Param("endDate") Date endDate
									  );
}
