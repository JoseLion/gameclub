package ec.com.levelap.gameclub.module.shippingPrice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.commons.location.Location;
import ec.com.levelap.gameclub.module.shippingPrice.entity.ShippingPrice;

@Repository
public interface ShippingPriceRepo extends JpaRepository<ShippingPrice, Long> {
	public List<ShippingPrice> findByOriginIdAndDestinationIdIn(Long originId, List<Long> destinationIdList);
	
	@Query(	"SELECT sp FROM ShippingPrice sp WHERE " +
				"UPPER(sp.origin.name) LIKE UPPER('%' || :origin || '%') AND " +
				"UPPER(sp.destination.name) LIKE UPPER('%' || :destination || '%') AND " +
				"(:cost IS NULL OR sp.cost=:cost) " +
			"ORDER BY sp.origin.name, sp.destination.name, sp.cost DESC")
	public Page<ShippingPrice> findShippingPrices(@Param("origin") String origin, @Param("destination") String destination, @Param("cost") Double cost, Pageable page);
	
	public ShippingPrice findByOriginAndDestination(Location origin, Location destination);
}
