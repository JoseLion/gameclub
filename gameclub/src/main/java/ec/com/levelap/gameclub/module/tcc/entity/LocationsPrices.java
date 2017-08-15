package ec.com.levelap.gameclub.module.tcc.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.commons.location.Location;
import ec.com.levelap.gameclub.utils.Const;


/**
 * The Class LocationsPrices.
 */
/*
 * The Class LocationsPrices.
 */
@Entity
@Table(schema = Const.SCHEMA, name = "locations_prices")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class LocationsPrices extends BaseEntity{
    
  
	/** The origin. */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="origin", foreignKey=@ForeignKey(name="origin_location_fk"))
	private Location origin;
	
	/** The destination. */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="destination", foreignKey=@ForeignKey(name="destination_location_fk"))
	private Location destination;
	
	/** The cost. */
	private Integer cost;

	/**
	 * Gets the origin.
	 *
	 * @return the origin
	 */
	public Location getOrigin() {
		return origin;
	}

	/**
	 * Sets the origin.
	 *
	 * @param origin the new origin
	 */
	public void setOrigin(Location origin) {
		this.origin = origin;
	}

	/**
	 * Gets the destination.
	 *
	 * @return the destination
	 */
	public Location getDestination() {
		return destination;
	}

	/**
	 * Sets the destination.
	 *
	 * @param destination the new destination
	 */
	public void setDestination(Location destination) {
		this.destination = destination;
	}

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public Integer getCost() {
		return cost;
	}

	/**
	 * Sets the cost.
	 *
	 * @param cost the new cost
	 */
	public void setCost(Integer cost) {
		this.cost = cost;
	}

	

}
