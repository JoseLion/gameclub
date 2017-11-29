package ec.com.levelap.gameclub.module.reports.amountRequest.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.reports.amountRequest.entity.AmountRequestReport;

@Repository
public interface AmountRequestReportRepo extends JpaRepository<AmountRequestReport, Long> {
	
	@Query(	"SELECT ar FROM AmountRequestReport ar WHERE " +
				"UPPER(ar.fullName) LIKE UPPER('%' || :name || '%') AND " +
				"UPPER(ar.document) LIKE UPPER('%' || :document || '%') AND " +
				"(DATE(ar.applicationDate) BETWEEN DATE(:dateStart) AND DATE(:dateEnd)) AND " +
				"(ar.amount BETWEEN :amontStart AND :amountEnd) " +
			"ORDER BY ar.applicationDate")
	public Page<AmountRequestReport> findAmountRequests(
			@Param("name") String name,
			@Param("document") String document,
			@Param("dateStart") Date dateStart,
			@Param("dateEnd") Date dateEnd,
			@Param("amontStart") Double amountStart,
			@Param("amountEnd") Double amountEnd,
			Pageable page);

}
