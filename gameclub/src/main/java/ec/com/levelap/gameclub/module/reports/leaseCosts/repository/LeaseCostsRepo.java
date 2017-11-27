package ec.com.levelap.gameclub.module.reports.leaseCosts.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.reports.leaseCosts.entity.LeaseCosts;

@Repository
public interface LeaseCostsRepo extends JpaRepository<LeaseCosts, Long>{

	@Query("SELECT lc FROM LeaseCosts lc ORDER BY lc.id DESC")
	public Page<LeaseCosts> leaseCostsPage(Pageable page);
	
//	@Query("SELECT SUM((b.cost*b.weeks)+b.shippingCost+b.feeGgameClub+b.taxes) FROM Billing b ")
//	public Double total();
}
