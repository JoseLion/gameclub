package ec.com.levelap.gameclub.module.game.entity;

import java.util.List;

import ec.com.levelap.commons.archive.Archive;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.module.category.entity.Category;
import ec.com.levelap.gameclub.module.console.entity.Console;

public interface GameOpen {
	public Long getId();
	
	public String getName();
	
	public Archive getCover();
	
	public Catalog getContentRating();
	
	public List<Console> getConsoles();
	
	public List<Category> getCategories();
	
	public void setConsoles(List<GameConsole> consoles);
	
	public void setCategories(List<GameCategory> categories);
}
