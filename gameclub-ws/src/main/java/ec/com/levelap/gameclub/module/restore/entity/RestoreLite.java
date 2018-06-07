package ec.com.levelap.gameclub.module.restore.entity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;

public interface RestoreLite {
	public Long getId();
	
	public Date getCreationDate();
	
	public PublicUserGame getPublicUserGame();
	
	public PublicUser getGamer();
	
	public String getTracking();
	
	public Catalog getShippingStatus();
	
	@Value("#{target.deliveryDate}")
	public Date getDeliveryDate();
	
	@Value("#{target.daysToAdd}")
	public Integer getDaysToAdd();
	
}
