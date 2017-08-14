package ec.com.levelap.gameclub.module.loan.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.postgresql.geometric.PGpoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.gameclub.module.kushki.entity.KushkiSubscription;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="loan")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Loan extends BaseEntity {
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="public_user_game", foreignKey=@ForeignKey(name="public_user_game_fk"))
	private PublicUserGame publicUserGame;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="gamer", foreignKey=@ForeignKey(name="gamer_public_user_fk"))
	private PublicUser gamer;
	
	@Column(columnDefinition="INTEGER DEFAULT 1")
	private Integer weeks = 1;
	
	@Column(name="gamer_address", columnDefinition="VARCHAR")
	private String gamerAddress;
	
	@Column(name="gamer_geolocation")
	private PGpoint gamerGeolocation;
	
	@Column(name="gamer_receiver", columnDefinition="VARCHAR")
	private String gamerReceiver;
	
	@Column(name="lender_address", columnDefinition="VARCHAR")
	private String lenderAddress;
	
	@Column(name="lender_geolocation")
	private PGpoint lenderGeolocation;
	
	@Column(name="lender_receiver", columnDefinition="VARCHAR")
	private String lenderReceiver;
	
	@Column(columnDefinition="DECIMAL(8, 4) DEFAULT 0.0")
	private Double cost = 0.0;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="payment", foreignKey=@ForeignKey(name="payment_kushki_subscription_fk"))
	private KushkiSubscription payment;
	
	@Column(name="was_accepted", columnDefinition="BOOLEAN DEFAULT NULL")
	private Boolean wasAccepted;

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

	public Integer getWeeks() {
		return weeks;
	}

	public void setWeeks(Integer weeks) {
		this.weeks = weeks;
	}

	public String getGamerAddress() {
		return gamerAddress;
	}

	public void setGamerAddress(String gamerAddress) {
		this.gamerAddress = gamerAddress;
	}

	public PGpoint getGamerGeolocation() {
		return gamerGeolocation;
	}

	public void setGamerGeolocation(PGpoint gamerGeolocation) {
		this.gamerGeolocation = gamerGeolocation;
	}

	public String getGamerReceiver() {
		return gamerReceiver;
	}

	public void setGamerReceiver(String gamerReceiver) {
		this.gamerReceiver = gamerReceiver;
	}

	public String getLenderAddress() {
		return lenderAddress;
	}

	public void setLenderAddress(String lenderAddress) {
		this.lenderAddress = lenderAddress;
	}

	public PGpoint getLenderGeolocation() {
		return lenderGeolocation;
	}

	public void setLenderGeolocation(PGpoint lenderGeolocation) {
		this.lenderGeolocation = lenderGeolocation;
	}

	public String getLenderReceiver() {
		return lenderReceiver;
	}

	public void setLenderReceiver(String lenderReceiver) {
		this.lenderReceiver = lenderReceiver;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public KushkiSubscription getPayment() {
		return payment;
	}

	public void setPayment(KushkiSubscription payment) {
		this.payment = payment;
	}

	public Boolean getWasAccepted() {
		return wasAccepted;
	}

	public void setWasAccepted(Boolean wasAccepted) {
		this.wasAccepted = wasAccepted;
	}
}
