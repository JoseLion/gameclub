package ec.com.levelap.gameclub.module.user.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.user.entity.AdminUser;
import ec.com.levelap.gameclub.module.user.entity.AdminUserLite;

@Repository
public interface AdminUserRepo extends JpaRepository<AdminUser, Long> {
	public AdminUser findByUsername(String username);
	
	@Query(	"SELECT a.id AS id, " + 
				"a.fullName AS fullName, " +
				"a.profile.name AS profileName, " +
				"a.status AS status, " +
				"a.creationDate AS creationDate " +
			"FROM AdminUser a WHERE " +
				"UPPER(a.fullName) LIKE UPPER('%' || :fullName || '%') AND " +
				"(:profileId IS NULL OR a.profile.id=:profileId) AND " +
				"(:status IS NULL OR a.status=:status) AND " +
				"DATE(a.creationDate) BETWEEN DATE(:startDate) AND DATE(:endDate) " +
			"ORDER BY a.fullName")
	public List<AdminUserLite> findAdminUsers(
			@Param("fullName") String fullName,
			@Param("profileId") Long profileId,
			@Param("status") Boolean status,
			@Param("startDate") Date startDate,
			@Param("endDate") Date endDate);
}
