package ec.com.levelap.gameclub.module.tcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ec.com.levelap.gameclub.module.tcc.entity.LocationsPrices;

/**
 * The Interface LocationsPricesRepo.
 */
@Repository
public interface LocationsPricesRepo extends JpaRepository<LocationsPrices, Long> {
	
	/**
	 * Find cost shipping.
	 *
	 * @param userOrigin the user origin
	 * @param userDestination the user destination
	 * @return the int
	 */
	@Query(	"SELECT " +
			"g.origin AS origin, " +
			"g.destin AS destination, " +
			"g.cost AS costShipping"+
		"FROM LocationsPrices g " +
		"WHERE " +
			"(g.origin = :userOrigin) AND " +
			"(g.destin = :userDestination)")
	public int findCostShipping(
			@Param("userOrigin") Long userOrigin,
			@Param("userDestination") Long userDestination);
			
		

}
