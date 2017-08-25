package ec.com.levelap.gameclub.module.restore.entity;

import java.util.Date;

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
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="restore")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Restore extends BaseEntity {
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="gamer_message", foreignKey=@ForeignKey(name="gamer_message_fk"))
	private Message gamerMessage;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="lender_message", foreignKey=@ForeignKey(name="lender_message_fk"))
	private Message lenderMessage;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="public_user_game", foreignKey=@ForeignKey(name="public_user_game_fk"))
	private PublicUserGame publicUserGame;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="gamer", foreignKey=@ForeignKey(name="gamer_public_user_fk"))
	private PublicUser gamer;
	
	@Column(name="lender_status_date")
	private Date lenderStatusDate;
	
	@Column(name="gamer_status_date")
	private Date gamerStatusDate;
	
	@Transient
	private Boolean lenderConfirmed;
	
	@Transient
	private Boolean gamerConfirmed;

	public Message getGamerMessage() {
		return gamerMessage;
	}

	public void setGamerMessage(Message gamerMessage) {
		this.gamerMessage = gamerMessage;
	}

	public Message getLenderMessage() {
		return lenderMessage;
	}

	public void setLenderMessage(Message lenderMessage) {
		this.lenderMessage = lenderMessage;
	}

	public PublicUserGame getPublicUserGame() {
		return publicUserGame;
	}

	public void setPublicUserGame(PublicUserGame publicUserGame) {
		this.publicUserGame = publicUserGame;
	}

	public PublicUser getGamer() {
		return gamer;
	}

	public void setGamer(PublicUser gamer) {
		this.gamer = gamer;
	}

	public Date getLenderStatusDate() {
		return lenderStatusDate;
	}

	public void setLenderStatusDate(Date lenderStatusDate) {
		this.lenderStatusDate = lenderStatusDate;
	}

	public Date getGamerStatusDate() {
		return gamerStatusDate;
	}

	public void setGamerStatusDate(Date gamerStatusDate) {
		this.gamerStatusDate = gamerStatusDate;
	}

	public Boolean getLenderConfirmed() {
		if (lenderStatusDate == null) {
			lenderConfirmed = false;
		} else {
			lenderConfirmed = true;
		}
		
		return lenderConfirmed;
	}

	public void setLenderConfirmed(Boolean lenderConfirmed) {
		this.lenderConfirmed = lenderConfirmed;
	}

	public Boolean getGamerConfirmed() {
		if (gamerStatusDate == null) {
			gamerConfirmed = false;
		} else {
			gamerConfirmed = true;
		}
		
		return gamerConfirmed;
	}

	public void setGamerConfirmed(Boolean gamerConfirmed) {
		this.gamerConfirmed = gamerConfirmed;
	}
}
