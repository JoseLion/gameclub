package ec.com.levelap.gameclub.module.game.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import ec.com.levelap.commons.archive.Archive;
import ec.com.levelap.commons.catalog.Catalog;

public interface GameOpen {
	public Long getId();
	
	public String getName();
	
	public String getTrailerUrl();
	
	public Archive getCover();
	
	@Value("#{target.rating}")
	public Integer getRating();
	
	public Catalog getContentRating();
	
	public List<GameConsole> getConsoles();
	
	public List<GameCategory> getCategories();
	
	public void setConsoles(List<GameConsole> consoles);
	
	public void setCategories(List<GameCategory> categories);
	
	public Archive getDiamond();
	
}
