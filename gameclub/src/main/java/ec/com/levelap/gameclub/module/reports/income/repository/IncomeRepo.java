package ec.com.levelap.gameclub.module.reports.income.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.reports.income.entity.Income;

@Repository
public interface IncomeRepo extends JpaRepository<Income, Long>  {
	
	@Query(	"SELECT i FROM Income i WHERE " +
				"(UPPER(i.name) LIKE UPPER('%' || :name || '%') OR "+
				"UPPER(i.lastName) LIKE UPPER('%' || :lastName || '%') OR " +
				"UPPER(i.name || ' ' || i.lastName) LIKE UPPER('%' || :name || '%')) AND " +
				"UPPER(i.document) LIKE UPPER('%' || :document || '%') AND " +
				"(i.total BETWEEN :totalStart AND :totalEnd) AND " +
				"(DATE(i.creationDate) BETWEEN DATE(:dateStart) AND DATE(:dateEnd)) " +
			"ORDER BY i.creationDate DESC")
	public Page<Income> findIncome(
			@Param("name") String name,
			@Param("document") String document,
			@Param("dateStart") Date dateStart,
			@Param("dateEnd") Date dateEnd,
			@Param("totalStart") Double totalStart,
			@Param("totalEnd") Double totalEnd,
			Pageable page);
}
