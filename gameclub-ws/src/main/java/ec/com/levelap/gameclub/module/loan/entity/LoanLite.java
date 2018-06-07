package ec.com.levelap.gameclub.module.loan.entity;

import java.util.Date;

import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;

public interface LoanLite {
	public Long getId();
	
	public Date getCreationDate();
	
	public PublicUserGame getPublicUserGame();
	
	public PublicUser getGamer();
	
	public String getTracking();
	
	public Catalog getShippingStatus();
	
	public Date getDeliveryDate();
	
	public Integer getWeeks();
	
	public Date getPaymentDate();
}