package ec.com.levelap.gameclub.module.game.entity;

import java.util.List;

import ec.com.levelap.commons.archive.Archive;
import ec.com.levelap.commons.catalog.Catalog;

public interface GameOpen {
	public Long getId();
	
	public String getName();
	
	public Archive getCover();
	
	public Integer getRating();
	
	public Catalog getContentRating();
	
	public List<GameConsole> getConsoles();
	
	public List<GameCategory> getCategories();
	
	public void setConsoles(List<GameConsole> consoles);
	
	public void setCategories(List<GameCategory> categories);
	
	public Archive getDiamond();
	
}
