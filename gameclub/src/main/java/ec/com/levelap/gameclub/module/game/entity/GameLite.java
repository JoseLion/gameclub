package ec.com.levelap.gameclub.module.game.entity;

import java.util.Date;
import java.util.List;

public interface GameLite {
	public Long getId();
	
	public String getName();
	
	public Date getReleaseDate();
	
	public Boolean getStatus();
	
	public List<GameConsole> getConsoles();
	
	public List<GameCategory> getCategories();
	
	public void setConsoles(List<GameConsole> consoles);
	
	public void setCategories(List<GameCategory> categories);
}
