package ec.com.levelap.gameclub.module.transaction.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

	@Query("SELECT t FROM Transaction t WHERE t.owner = :owner ORDER BY t.id DESC")
	public List<Transaction> findAll(@Param("owner") PublicUser owner);

	@Query("SELECT t FROM Transaction t WHERE t.owner.id = :owner ORDER BY t.id DESC")
	public Page<Transaction> findFiveTransactions(@Param("owner") Long owner, Pageable page);
	
	@Query("SELECT t FROM Transaction t WHERE t.ccTransaction IS NOT NULL ORDER BY t.creationDate DESC ")
	public Page<Transaction> findRefounds(Pageable page);
	
	@Query(	"SELECT t FROM Transaction t WHERE " +
				"(UPPER(t.owner.name) LIKE UPPER('%' || :name || '%') OR  " +
				"UPPER(t.owner.lastName) LIKE UPPER('%' || :name || '%') OR " +
				"UPPER(t.owner.name || ' ' || t.owner.lastName) LIKE UPPER('%' || :name || '%')) AND " +
				"(DATE(t.creationDate) BETWEEN DATE(:startDate) AND DATE(:endDate)) AND " +
				"(UPPER(t.transaction) LIKE UPPER('%' || :transaction || '%')) AND " +
				"(UPPER(t.statusRefund) LIKE UPPER(:statusRefund)) AND " +
				"t.ccTransaction IS NOT NULL " +
			"ORDER BY t.creationDate DESC "
			)
	public Page<Transaction> findRefundRequest(
			@Param("name") String name,
			@Param("startDate") Date startDate,
			@Param("endDate") Date endDate,
			@Param("transaction") String transaction,
			@Param("statusRefund") String statusRefund,
			Pageable page);
}
