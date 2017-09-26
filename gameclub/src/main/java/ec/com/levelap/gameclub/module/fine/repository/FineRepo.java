/**
 * 
 */
package ec.com.levelap.gameclub.module.fine.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.fine.entity.Fine;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Levelap
 *
 */
@Repository
public interface FineRepo extends JpaRepository<Fine, Long>{

	@Query("SELECT f FROM Fine f Order By f.date DESC ")
	public List<Fine> findFines();
	
	@Query(	"SELECT f "
			+ "FROM Fine f "
			+ "WHERE 	(UPPER(f.publicUserFine.name) LIKE UPPER('%' || :name || '%') AND "
			+ "		UPPER(f.publicUserFine.lastName) LIKE UPPER('%' || :lastName || '%')) "
			+ "		AND (f.apply=:apply and f.wasPayed=:wasPayed)  "
			+ "		AND DATE(f.date) BETWEEN DATE(:startDate) AND DATE(:endDate) " 
			+ "ORDER BY f.date DESC")
	public List<Fine> findFinesFilter(
		@Param("name") String name,
		@Param("lastName") String lastName,
		@Param("apply") Boolean apply,
		@Param("wasPayed") Boolean wasPayed,
		@Param("startDate") Date startDate,
		@Param("endDate") Date endDate);
	
}
