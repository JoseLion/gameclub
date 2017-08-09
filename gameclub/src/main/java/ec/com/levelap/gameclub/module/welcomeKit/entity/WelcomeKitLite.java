package ec.com.levelap.gameclub.module.welcomeKit.entity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;

public interface WelcomeKitLite {
	public Long getId();
	
	public Date getCreationDate();
	
	@Value("#{target.publicUser}")
	public PublicUser getPublicUser();
	
	@Value("#{target.shippingStatus}")
	public Catalog getShippingStatus();
	
	public Boolean getWasConfirmed();
}
