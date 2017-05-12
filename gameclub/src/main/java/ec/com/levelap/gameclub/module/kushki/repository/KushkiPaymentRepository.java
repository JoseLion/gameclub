package ec.com.levelap.gameclub.module.kushki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.kushki.entity.KushkiPayment;

@Repository
public interface KushkiPaymentRepository extends JpaRepository<KushkiPayment, Long> {

}
