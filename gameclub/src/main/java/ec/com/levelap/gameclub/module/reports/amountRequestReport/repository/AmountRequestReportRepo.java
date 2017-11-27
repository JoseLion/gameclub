package ec.com.levelap.gameclub.module.reports.amountRequestReport.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.reports.amountRequestReport.entity.AmountRequestReport;

@Repository
public interface AmountRequestReportRepo extends JpaRepository<AmountRequestReport, Long> {
	
	@Query("SELECT pu.privateKey FROM PublicUser pu WHERE pu.id=:userId")
	public byte[] userKey(@Param("userId") Long userId);
	
	@Query("SELECT amr FROM AmountRequestReport amr")
	public Page<AmountRequestReport> amountRequestPage(Pageable page);

}
