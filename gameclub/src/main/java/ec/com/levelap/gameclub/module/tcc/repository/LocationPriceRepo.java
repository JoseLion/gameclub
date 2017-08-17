package ec.com.levelap.gameclub.module.tcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ec.com.levelap.gameclub.module.tcc.entity.LocationPrice;

@Repository
public interface LocationPriceRepo extends JpaRepository<LocationPrice, Long> {
	public List<LocationPrice> findByOriginIdAndDestinationIdIn(Long originId, List<Long> destinationIdList);
}
