package ec.com.levelap.gameclub.module.console.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.console.entity.Console;

@Repository
public interface ConsoleRepo extends JpaRepository<Console, Long> {
	public List<Console> findAllByOrderByName();
	
	public List<Console> findAllByOrderByIdAsc();
	
	public Console findByName(String name);
	
	public Console findByNameAndNameIsNot(String name, String notName);
	
	public List<Console> findByStatusIsTrueOrderByName();
}
