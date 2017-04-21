package ec.com.levelap.gameclub.module.navigation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.navigation.entity.Navigation;

@Repository
public interface NavigationRepo extends JpaRepository<Navigation, Long> {
	public List<Navigation> findByParentIsNull();
}
