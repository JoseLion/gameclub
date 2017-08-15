package ec.com.levelap.gameclub.module.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.loan.entity.Loan;

@Repository
public interface LoanRepo extends JpaRepository<Loan, Long> {
	Loan findByGamerMessageIdOrLenderMessageId(Long gamerMessageId, Long lenderMessageId);
}
