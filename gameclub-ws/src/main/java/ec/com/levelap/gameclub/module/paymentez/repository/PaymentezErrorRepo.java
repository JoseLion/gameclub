package ec.com.levelap.gameclub.module.paymentez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.paymentez.entity.PaymentezError;

@Repository
public interface PaymentezErrorRepo extends JpaRepository<PaymentezError, Long>{

}
