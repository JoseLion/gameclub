package ec.com.levelap.gameclub.module.profile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.profile.entity.Profile;
import ec.com.levelap.gameclub.module.profile.entity.ProfileLite;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Long> {
	public List<ProfileLite> findByNameContainingOrderByName(String name);
	
	public Profile findByName(String name);
}
