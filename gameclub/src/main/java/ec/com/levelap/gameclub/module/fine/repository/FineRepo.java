package ec.com.levelap.gameclub.module.fine.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;

@Repository
public interface FineRepo extends JpaRepository<Fine, Long>{
	@Query(	"SELECT f FROM Fine f join f.owner ow WHERE " +
				"(UPPER(f.owner.name) LIKE UPPER('%' || :name || '%') OR " +
				"UPPER(f.owner.lastName) LIKE UPPER('%' || :name || '%') OR " +
				"UPPER(f.owner.name || ' ' || f.owner.lastName) LIKE UPPER('%' || :name || '%')) AND " +
				"(:apply IS NULL OR f.apply=:apply) AND " +
				"(:wasPayed IS NULL OR f.wasPayed=:wasPayed) AND " +
				"DATE(f.creationDate) BETWEEN DATE(:startDate) AND DATE(:endDate) " +
			"ORDER BY f.creationDate DESC")
	public List<Fine> findFines(@Param("name") String name,
			@Param("apply") Boolean apply,
			@Param("wasPayed") Boolean wasPayed,
			@Param("startDate") Date startDate,
			@Param("endDate") Date endDate);
	
	@Query("SELECT f FROM Fine f "
			+ "WHERE f.owner=:owner")
	public List<Fine> findFinesMessages(@Param("owner") PublicUser owner);
}
