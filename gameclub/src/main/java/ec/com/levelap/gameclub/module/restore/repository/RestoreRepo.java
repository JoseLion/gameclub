package ec.com.levelap.gameclub.module.restore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.restore.entity.Restore;

@Repository
public interface RestoreRepo extends JpaRepository<Restore, Long> {
	
}
