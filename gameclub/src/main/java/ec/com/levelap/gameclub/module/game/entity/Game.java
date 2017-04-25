package ec.com.levelap.gameclub.module.game.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.commons.archive.Archive;
import ec.com.levelap.commons.catalog.Catalog;

@Entity
@Table(schema="gameclub", name="game", uniqueConstraints=@UniqueConstraint(columnNames="name", name="name_uk"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Game extends BaseEntity {
	@Column(columnDefinition="VARCHAR", nullable=false)
	private String name;
	
	@Column(columnDefinition="VARCHAR")
	private String description;
	
	@Column(name="release_date")
	private Date releaseDate;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="content_rating", foreignKey=@ForeignKey(name="content_rating_catalog_fk"))
	private Catalog contentRating;
	
	@Column(columnDefinition="INTEGER DEFAULT 0")
	private Integer rating = 0;
	
	@JsonManagedReference("gameMagazine")
	@OneToMany(mappedBy="game", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<GameMagazine> magazineRatings = new ArrayList<>();
	
	@JsonManagedReference("gameConsole")
	@OneToMany(mappedBy="game", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<GameConsole> consoles = new ArrayList<>();
	
	@JsonManagedReference("gameCategory")
	@OneToMany(mappedBy="game", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<GameCategory> categories = new ArrayList<>();
	
	@Column(name="average_week_cost", columnDefinition="INTEGER DEFAULT 0")
	private Integer averageWeekCost = 0;
	
	@Column(name="upload_payment", columnDefinition="INTEGER DEFAULT 0")
	private Integer uploadPayment = 0;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="cover", foreignKey=@ForeignKey(name="cover_archive_fk"))
	private Archive cover;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="banner", foreignKey=@ForeignKey(name="banner_archive_fk"))
	private Archive banner;
	
	@Column(name="gameplay_url", columnDefinition="VARCHAR")
	private String gameplayUrl;
}
