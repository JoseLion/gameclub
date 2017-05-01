package ec.com.levelap.gameclub.module.game.entity;

import java.util.Date;
import java.util.List;

import ec.com.levelap.commons.archive.Archive;

public interface GameLite {
	public Long getId();
	
	public String getName();
	
	public Date getReleaseDate();
	
	public Archive getCover();
	
	public Boolean getStatus();
	
	public List<GameConsole> getConsoles();
	
	public List<GameCategory> getCategories();
	
	public void setConsoles(List<GameConsole> consoles);
	
	public void setCategories(List<GameCategory> categories);
}
