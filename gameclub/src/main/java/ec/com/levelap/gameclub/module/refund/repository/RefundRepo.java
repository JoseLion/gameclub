package ec.com.levelap.gameclub.module.refund.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.transaction.entity.Transaction;

@Repository
public interface RefundRepo extends JpaRepository<Transaction, Long> {

	
}
