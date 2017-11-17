package ec.com.levelap.gameclub.module.reportAmountRequest.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.reportAmountRequest.entity.AmountRequestReport;

@Repository
public interface AmountRequestReportRepo extends JpaRepository<AmountRequestReport, Long> {
	
	@Query("SELECT pu.privateKey FROM PublicUser pu WHERE pu.id=:userId")
	public byte[] userKey(@Param("userId") Long userId);

}
