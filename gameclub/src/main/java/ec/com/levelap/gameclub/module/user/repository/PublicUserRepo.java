package ec.com.levelap.gameclub.module.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.user.entity.PublicUser;

@Repository
public interface PublicUserRepo extends JpaRepository<PublicUser, Long> {
	public PublicUser findByUsernameIgnoreCase(String username);
}
