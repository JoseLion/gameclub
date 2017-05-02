package ec.com.levelap.gameclub.module.game.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="game_magazine")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GameMagazine extends BaseEntity {
	@JsonBackReference("gameMagazine")
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="game", foreignKey=@ForeignKey(name="game_fk"))
	private Game game;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="magazine", foreignKey=@ForeignKey(name="magazine_catalog_fk"))
	private Catalog magazine;
	
	@Column(columnDefinition="INTEGER DEFAULT 0")
	private Integer rating = 0;
	
	@Column(columnDefinition="VARCHAR")
	private String url;

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Catalog getMagazine() {
		return magazine;
	}

	public void setMagazine(Catalog magazine) {
		this.magazine = magazine;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
