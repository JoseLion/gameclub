package ec.com.levelap.gameclub.module.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.profile.entity.ProfileNavigation;

@Repository
public interface ProfileNavigationRepo extends JpaRepository<ProfileNavigation, Long> {
	
}
