package ec.com.levelap.gameclub.module.reports.leaseCosts.repository;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.reports.leaseCosts.entity.LeaseCost;

@Repository
public interface LeaseCostRepo extends JpaRepository<LeaseCost, Long>{
	
	@Query(	"SELECT lc FROM LeaseCost lc WHERE " +
				"(UPPER(lc.name) LIKE UPPER('%' || :name || '%') OR " +
				"UPPER(lc.lastName) LIKE UPPER('%' || :name || '%') OR " +
				"UPPER(lc.name || ' ' || lc.lastName) LIKE UPPER('%' || :name || '%')) AND " +
				"UPPER(lc.document) LIKE UPPER('%' || :document || '%') AND " +
				"(lc.total BETWEEN :totalStart AND :totalEnd) AND " +
				"(DATE(lc.creationDate) BETWEEN DATE(:dateStart) AND DATE(:dateEnd)) " +
			"ORDER BY lc.creationDate DESC")
	public Page<LeaseCost> findLeaseCosts(
			@Param("name") String name,
			@Param("document") String document,
			@Param("totalStart") Double totalStart,
			@Param("totalEnd") Double totalEnd,
			@Param("dateStart") Date dateStart,
			@Param("dateEnd") Date dateEnd,
			Pageable page);
}
