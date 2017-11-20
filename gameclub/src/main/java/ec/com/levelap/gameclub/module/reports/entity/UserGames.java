package ec.com.levelap.gameclub.module.reports.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.gameclub.module.game.entity.Game;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="user_games")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Immutable
public class UserGames {
	
	@Id
	@Column
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="public_user", foreignKey=@ForeignKey(name="public_user_fk"))
	private PublicUser publicUser;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="game", foreignKey=@ForeignKey(name="game_fk"))
	private Game game;
	
	@Column(name="upload_date")
	private Date uploadDate;
	
	@Column
	private Long loans;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Long getLoans() {
		return loans;
	}

	public void setLoans(Long loans) {
		this.loans = loans;
	}
	
}
