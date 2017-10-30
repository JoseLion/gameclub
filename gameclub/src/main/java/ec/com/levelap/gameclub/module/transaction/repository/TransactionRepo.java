package ec.com.levelap.gameclub.module.transaction.repository;


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

<<<<<<< HEAD
	@Query("SELECT t FROM Transaction t WHERE t.owner.id = :owner ORDER BY t.id DESC")
	public Page<Transaction> findFiveTransactions(@Param("owner") Long owner, Pageable page);
=======
	@Query("SELECT t FROM Transaction t WHERE t.owner=:owner ORDER BY t.id DESC")
	public Page<Transaction> findFiveTransactions(@Param("owner") PublicUser owner, Pageable page);
>>>>>>> Licuadora

}
