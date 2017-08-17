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
@Table(schema = Const.SCHEMA, name = "location_price")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class LocationPrice extends BaseEntity{
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="origin", foreignKey=@ForeignKey(name="origin_location_fk"))
	private Location origin;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="destination", foreignKey=@ForeignKey(name="destination_location_fk"))
	private Location destination;
	
	private Integer cost;
	
	public Location getOrigin() {
		return origin;
	}
	
	public void setOrigin(Location origin) {
		this.origin = origin;
	}
	
	public Location getDestination() {
		return destination;
	}
	
	public void setDestination(Location destination) {
		this.destination = destination;
	}
	
	public Integer getCost() {
		return cost;
	}
	
	public void setCost(Integer cost) {
		this.cost = cost;
	}
}
