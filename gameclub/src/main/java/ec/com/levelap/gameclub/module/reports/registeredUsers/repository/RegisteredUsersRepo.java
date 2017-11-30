package ec.com.levelap.gameclub.module.reports.registeredUsers.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.reports.platformGames.entity.PlatformGames;
import ec.com.levelap.gameclub.module.reports.registeredUsers.entity.RegisteredUsers;

@Repository
public interface RegisteredUsersRepo extends JpaRepository<RegisteredUsers, Long> {

	@Query("SELECT count(ru) FROM RegisteredUsers ru")
	public Long totalUsers();
	
	@Query("SELECT ru FROM RegisteredUsers ru ORDER BY ru.id DESC")
	public Page<RegisteredUsers> registeredUsersPage(Pageable page);
	
	@Query("SELECT ru FROM RegisteredUsers ru WHERE " +
			"		(UPPER(ru.publicUserId.name) LIKE UPPER('%' || :name || '%') OR  "+
			"		UPPER(ru.publicUserId.lastName) LIKE UPPER('%' || :name || '%') OR " +
			"		UPPER(ru.publicUserId.name || ' ' || ru.publicUserId.lastName) LIKE UPPER('%' || :name || '%')) AND " +
			"		(:document IS NULL OR ru.publicUserId.document like ('%' || :document || '%')) AND " +
			"		(:username IS NULL OR UPPER(ru.publicUserId.username) like UPPER('%' || :username || '%')) AND " +
			"		(DATE(ru.creationDate) BETWEEN DATE(:startDate) AND DATE(:endDate)) " +
			"ORDER BY ru.creationDate DESC " 
			)
	public List<RegisteredUsers> findRegisteredUsers(
									  @Param("name") String name,
									  @Param("document") String document,
									  @Param("username") String username,
									  @Param("startDate") Date startDate,
									  @Param("endDate") Date endDate
									  );
}
