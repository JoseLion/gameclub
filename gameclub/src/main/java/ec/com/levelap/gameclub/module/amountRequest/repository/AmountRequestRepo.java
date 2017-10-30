package ec.com.levelap.gameclub.module.amountRequest.repository;

import java.util.List;import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.module.amountRequest.entity.AmountRequest;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface AmountRequestRepo extends JpaRepository<AmountRequest, Long> {

	@Query("SELECT ar FROM AmountRequest ar ORDER BY ar.id DESC")
	public List<AmountRequest> findAll();
	
	@Query("SELECT ar FROM AmountRequest ar " +
						"WHERE	ar.requestStatus.id=:catalogId AND " +
						"		UPPER(ar.publicUser.name) LIKE UPPER('%' || :name || '%') AND "+
						"		UPPER(ar.publicUser.lastName) LIKE UPPER('%' || :lastName || '%') AND " +
						"		DATE(ar.creationDate) BETWEEN DATE(:startDate) AND DATE(:endDate) " +
						"ORDER BY ar.publicUser DESC "
						)
	public List<AmountRequest> findAmountRequest(@Param("catalogId") Long catalogId,
												  @Param("name") String name,
												  @Param("lastName") String lastName,
												  @Param("startDate") Date startDate,
												  @Param("endDate") Date endDate
												  );
}
