package ec.com.levelap.gameclub.module.reports.platformGames.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="platform_games")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Immutable
public class PlatformGames {
	
	@Id
	@Column
	private Long id;
	
	@Column
	private String game;
	
	@Column
	private String console;
	
	@Column(name="release_date")
	private Date releaseDate;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="upload_date")
	private Date uploadDate;
	
	@Column
	private Boolean status;
	
	@Column(name="recommended_price")
	private Double recommended_price;
	
	@Column
	private Double cost;
    
	@Column(name="total_requested_rentals")
	private Double totalRequestedRentals;
    
	@Column(name="total_rent_aceppted")
	private Double totalRentAceppted;
    
	@Column(name="total_rent_requested")
	private Double totalRentRequested;
	
	@Column(name="creation_date")
	private Date creationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Double getRecommended_price() {
		return recommended_price;
	}

	public void setRecommended_price(Double recommended_price) {
		this.recommended_price = recommended_price;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getTotalRequestedRentals() {
		return totalRequestedRentals;
	}

	public void setTotalRequestedRentals(Double totalRequestedRentals) {
		this.totalRequestedRentals = totalRequestedRentals;
	}

	public Double getTotalRentAceppted() {
		return totalRentAceppted;
	}

	public void setTotalRentAceppted(Double totalRentAceppted) {
		this.totalRentAceppted = totalRentAceppted;
	}

	public Double getTotalRentRequested() {
		return totalRentRequested;
	}

	public void setTotalRentRequested(Double totalRentRequested) {
		this.totalRentRequested = totalRentRequested;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
