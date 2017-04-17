package ec.com.levelap.gameclub.module.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.user.entity.AdminUser;

@Repository
public interface AdminUserRepo extends JpaRepository<AdminUser, Long> {
	public AdminUser findByUsername(String username);
}
