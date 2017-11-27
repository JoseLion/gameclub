package ec.com.levelap.gameclub.module.registeredUsers.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.registeredUsers.entity.RegisteredUsers;

@Repository
public interface RegisteredUsersRepo extends JpaRepository<RegisteredUsers, Long> {

	@Query("SELECT count(ru) FROM RegisteredUsers ru")
	public Long totalUsers();
	
	@Query("SELECT ru FROM RegisteredUsers ru ORDER BY ru.id DESC")
	public Page<RegisteredUsers> registeredUsersPage(Pageable page);
}
