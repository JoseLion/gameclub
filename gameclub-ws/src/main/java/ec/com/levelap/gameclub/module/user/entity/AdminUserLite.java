package ec.com.levelap.gameclub.module.user.entity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public interface AdminUserLite {
	public Long getId();
	
	public String getFullName();
	
	@Value("#{target.profileName}")
	public String getProfileName();
	
	public Boolean getStatus();
	
	public Date getCreationDate();
}
