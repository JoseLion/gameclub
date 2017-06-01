package ec.com.levelap.gameclub.module.user.entity;

import java.util.Date;

public interface PublicUserLite {

	public Long getId();

	public String getName();
	
	public String getLastName();

	public Boolean getStatus();

	public Date getCreationDate();
}
