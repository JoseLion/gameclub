package ec.com.levelap.gameclub.module.shippingPrice.entity;

import javax.persistence.Column;
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

@Entity
@Table(schema=Const.SCHEMA, name="shipping_price")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShippingPrice extends BaseEntity{
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="origin", foreignKey=@ForeignKey(name="origin_location_fk"))
	private Location origin;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="destination", foreignKey=@ForeignKey(name="destination_location_fk"))
	private Location destination;
	
	@Column(columnDefinition="DECIMAL(8, 4) DEFAULT 0.0")
	private Double cost = 0.0;
	
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

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}
}
