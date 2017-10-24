package ec.com.levelap.gameclub.module.user.entity;

import org.springframework.beans.factory.annotation.Value;

import ec.com.levelap.commons.catalog.Catalog;

public interface PublicUserGameOpen {
	public Long getId();
	
	public Boolean getStatus();
	
	@Value("#{target.shippingCost}")
	public Double getShippingCost();
	
	@Value("#{target.publicUser}")
	public PublicUser getPublicUser();
	
	public Double getCost();
	
	@Value("#{target.integrity}")
	public Catalog getIntegrity();
	
	public String getObservations();
}
