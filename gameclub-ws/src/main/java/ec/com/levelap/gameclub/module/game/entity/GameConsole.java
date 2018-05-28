package ec.com.levelap.gameclub.module.game.entity;

import javax.persistence.CascadeType;
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
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="game_console")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GameConsole extends BaseEntity {
	@JsonBackReference("gameConsole")
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="game", foreignKey=@ForeignKey(name="game_fk"))
	private Game game;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="console", foreignKey=@ForeignKey(name="console_fk"))
	private Console console;

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
}
