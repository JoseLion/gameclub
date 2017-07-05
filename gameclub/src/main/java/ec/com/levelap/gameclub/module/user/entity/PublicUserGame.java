package ec.com.levelap.gameclub.module.user.entity;

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
import ec.com.levelap.gameclub.module.console.entity.Console;
import ec.com.levelap.gameclub.module.game.entity.Game;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="public_user_game")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PublicUserGame extends BaseEntity {
	@JsonBackReference
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="public_user", foreignKey=@ForeignKey(name="public_user_fk"))
	private PublicUser publicUser;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="game", foreignKey=@ForeignKey(name="game_fk"))
	private Game game;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="console", foreignKey=@ForeignKey(name="console_fk"))
	private Console console;
	
	@Column
	private Integer integrity;
	
	@Column(columnDefinition="VARCHAR")
	private String observations;
	
	@Column(columnDefinition="INTEGER DEFAULT 0")
	private Integer cost = 0;

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

	public Integer getIntegrity() {
		return integrity;
	}

	public void setIntegrity(Integer integrity) {
		this.integrity = integrity;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}
}
