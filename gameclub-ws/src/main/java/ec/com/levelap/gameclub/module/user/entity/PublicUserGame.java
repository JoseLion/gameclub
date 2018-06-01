package ec.com.levelap.gameclub.module.user.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.module.console.entity.Console;
import ec.com.levelap.gameclub.module.game.entity.Game;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="public_user_game")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PublicUserGame extends BaseEntity {
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.DETACH)
	@JoinColumn(name="public_user", foreignKey=@ForeignKey(name="public_user_fk"))
	private PublicUser publicUser;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.DETACH)
	@JoinColumn(name="game", foreignKey=@ForeignKey(name="game_fk"))
	private Game game;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.DETACH)
	@JoinColumn(name="console", foreignKey=@ForeignKey(name="console_fk"))
	private Console console;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="integrity", foreignKey=@ForeignKey(name="integrity_catalog_fk"))
	private Catalog integrity;
	
	@Column(columnDefinition="VARCHAR")
	private String observations;
	
	@Column(columnDefinition="DECIMAL(9, 2) DEFAULT 0.0")
	private Double cost = 0.0;
	
	@Column(name="is_borrowed", columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean isBorrowed = false;
	
	@Transient
	private Double shippingCost;

	public PublicUser getPublicUser() {
		return publicUser;
	}

	public void setPublicUser(PublicUser publicUser) {
		this.publicUser = publicUser;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Console getConsole() {
		return console;
	}

	public void setConsole(Console console) {
		this.console = console;
	}

	public Catalog getIntegrity() {
		return integrity;
	}

	public void setIntegrity(Catalog integrity) {
		this.integrity = integrity;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Boolean getIsBorrowed() {
		return isBorrowed;
	}

	public void setIsBorrowed(Boolean isBorrewed) {
		this.isBorrowed = isBorrewed;
	}

	public Double getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(Double shippingCost) {
		this.shippingCost = shippingCost;
	}
}