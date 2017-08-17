package ec.com.levelap.gameclub.module.tcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ec.com.levelap.gameclub.module.tcc.entity.LocationsPrices;

/**
 * The Interface LocationsPricesRepo.
 */
@Repository
public interface LocationsPricesRepo extends JpaRepository<LocationsPrices, Long> {
	public LocationsPrices findByOriginIdAndDestinationId(@Param("userOrigin") Long userOrigin, @Param("userDestination") Long userDestination);
}
