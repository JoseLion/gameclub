package ec.com.levelap.gameclub.module.reports.billing.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.reports.billing.entity.Billing;

@Repository
public interface BillingRepo extends JpaRepository<Billing, Long>  {
	
	@Query(	"SELECT b FROM Billing b WHERE " +
				"UPPER(b.fullName) LIKE UPPER('%' || :name || '%') AND " +
				"(b.document IS NULL OR UPPER(b.document) LIKE UPPER('%' || :document || '%')) AND " +
				"UPPER(b.mail) LIKE UPPER('%' || :mail || '%') AND " +
				"(DATE(b.loanDate) BETWEEN DATE(:dateStart) AND DATE(:dateEnd)) " +
			"ORDER BY b.loanDate DESC")
	public Page<Billing> findBilling(
			@Param("name") String name,
			@Param("document") String document,
			@Param("mail") String mail,
			@Param("dateStart") Date dateStart,
			@Param("dateEnd") Date dateEnd,
			Pageable page);
	
	@Query("SELECT SUM((b.cost*b.weeks)+b.shippingCost+b.feeGgameClub+b.taxes) FROM Billing b ")
	public Double getTotal();
	

}
