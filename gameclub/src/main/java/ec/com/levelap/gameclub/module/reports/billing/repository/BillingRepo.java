package ec.com.levelap.gameclub.module.reports.billing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.reports.billing.entity.Billing;

@Repository
public interface BillingRepo extends JpaRepository<Billing, Long>  {
	
	@Query("SELECT b FROM Billing b ORDER BY b.id DESC")
	public Page<Billing> billingPage(Pageable page);
	
	@Query("SELECT SUM((b.cost*b.weeks)+b.shippingCost+b.feeGgameClub+b.taxes) FROM Billing b ")
	public Double total();
	

}
