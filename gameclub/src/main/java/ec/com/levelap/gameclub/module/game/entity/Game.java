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

import org.postgresql.geometric.PGline;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.commons.archive.Archive;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="game", uniqueConstraints=@UniqueConstraint(columnNames="name", name="name_uk"))
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
	
	@Column(name="diamond_crop")
	private PGline diamondCrop;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="banner", foreignKey=@ForeignKey(name="banner_archive_fk"))
	private Archive banner;
	
	@Column(name="trailer_url", columnDefinition="VARCHAR")
	private String trailerUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Catalog getContentRating() {
		return contentRating;
	}

	public void setContentRating(Catalog contentRating) {
		this.contentRating = contentRating;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public List<GameMagazine> getMagazineRatings() {
		return magazineRatings;
	}

	public void setMagazineRatings(List<GameMagazine> magazineRatings) {
		this.magazineRatings = magazineRatings;
	}

	public List<GameConsole> getConsoles() {
		return consoles;
	}

	public void setConsoles(List<GameConsole> consoles) {
		this.consoles = consoles;
	}

	public List<GameCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<GameCategory> categories) {
		this.categories = categories;
	}

	public Integer getAverageWeekCost() {
		return averageWeekCost;
	}

	public void setAverageWeekCost(Integer averageWeekCost) {
		this.averageWeekCost = averageWeekCost;
	}

	public Integer getUploadPayment() {
		return uploadPayment;
	}

	public void setUploadPayment(Integer uploadPayment) {
		this.uploadPayment = uploadPayment;
	}

	public Archive getCover() {
		return cover;
	}

	public void setCover(Archive cover) {
		this.cover = cover;
	}

	public PGline getDiamondCrop() {
		return diamondCrop;
	}

	public void setDiamondCrop(PGline diamondCrop) {
		this.diamondCrop = diamondCrop;
	}

	public Archive getBanner() {
		return banner;
	}

	public void setBanner(Archive banner) {
		this.banner = banner;
	}

	public String getTrailerUrl() {
		return trailerUrl;
	}

	public void setTrailerUrl(String trailerUrl) {
		this.trailerUrl = trailerUrl;
	}
}
