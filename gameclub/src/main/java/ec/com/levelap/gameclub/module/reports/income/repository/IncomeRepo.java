package ec.com.levelap.gameclub.module.reports.income.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.reports.income.entity.Income;

@Repository
public interface IncomeRepo extends JpaRepository<Income, Long>  {

	@Query("SELECT inc FROM Income inc ORDER BY inc.id DESC")
	public Page<Income> incomePage(Pageable page);
	
//	@Query("SELECT SUM((b.cost*b.weeks)+b.shippingCost+b.feeGgameClub+b.taxes) FROM Billing b ")
//	public Double total();
	
	@Query("SELECT inc FROM Income inc " +
			"WHERE	(UPPER(inc.name) LIKE UPPER('%' || :name || '%') OR "+
			"		UPPER(inc.lastName) LIKE UPPER('%' || :lastName || '%')) AND " +
			"		DATE(inc.creationDate) BETWEEN DATE(:startDate) AND DATE(:endDate) " +
			"ORDER BY inc.id DESC "
			)
	public List<Income> findIncome(@Param("name") String name,
									  @Param("lastName") String lastName,
									  @Param("startDate") Date startDate,
									  @Param("endDate") Date endDate
									  );
}
