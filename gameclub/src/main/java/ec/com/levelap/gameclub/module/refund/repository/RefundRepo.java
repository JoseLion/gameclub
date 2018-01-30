package ec.com.levelap.gameclub.module.refund.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;

@Repository
public interface RefundRepo extends JpaRepository<Transaction, Long> {

	@Query("SELECT t FROM Transaction t WHERE t.debitCardEnc IS NOT NULL ")
	public List<Transaction> findRefounds();
//	public List<Transaction> findRefounds(@Param("owner") PublicUser owner);
}
