package ec.com.levelap.gameclub.module.loan.entity;

import java.util.Date;

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
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.module.kushki.entity.KushkiSubscription;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="loan")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Loan extends BaseEntity {
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
	
	@Column(name="accepted_date")
	private Date acceptedDate;
	
	@Column(name="box_number", columnDefinition="VARCHAR")
	private String boxNumber;
	
	@Column(name="lender_confirmed", columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean lenderConfirmed = false;
	
	@Column(name="gamer_confirmed", columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean gamerConfirmed = false;
	
	@Column(name="lender_status_date")
	private Date lenderStatusDate;
	
	@Column(name="gamer_status_date")
	private Date gamerStatusDate;
	
	@Column(columnDefinition="VARCHAR")
	private String tracking;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="shipping_status", foreignKey=@ForeignKey(name="shipping_status_catalog_fk"))
	private Catalog shippingStatus;
	
	@Column(name="transaction_ticket", columnDefinition="VARCHAR")
	private String transactionTicket;

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

	public Date getAcceptedDate() {
		return acceptedDate;
	}

	public void setAcceptedDate(Date acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	public String getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}

	public Boolean getLenderConfirmed() {
		return lenderConfirmed;
	}

	public void setLenderConfirmed(Boolean lenderConfirmed) {
		this.lenderConfirmed = lenderConfirmed;
	}

	public Boolean getGamerConfirmed() {
		return gamerConfirmed;
	}

	public void setGamerConfirmed(Boolean gamerConfirmed) {
		this.gamerConfirmed = gamerConfirmed;
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

	public String getTracking() {
		return tracking;
	}

	public void setTracking(String tracking) {
		this.tracking = tracking;
	}

	public Catalog getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(Catalog shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public String getTransactionTicket() {
		return transactionTicket;
	}

	public void setTransactionTicket(String transactionTicket) {
		this.transactionTicket = transactionTicket;
	}
}
