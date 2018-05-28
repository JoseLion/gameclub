package ec.com.levelap.gameclub.module.process.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.process.entity.StatusProcess;

@Repository
public interface StatusProcessRepo extends JpaRepository<StatusProcess, Long> {

	List<StatusProcess> findByStatusFromCodeAndStatusIsTrue(String code);

}
