package ec.com.levelap.gameclub.module.loan.entity;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.io.FileUtils;
import org.postgresql.geometric.PGpoint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.application.ApplicationContextHolder;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.restore.entity.Restore;
import ec.com.levelap.gameclub.module.review.entity.Review;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema = Const.SCHEMA, name = "loan")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Loan extends BaseEntity {
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "gamer_message", foreignKey = @ForeignKey(name = "gamer_message_fk"))
	private Message gamerMessage;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "lender_message", foreignKey = @ForeignKey(name = "lender_message_fk"))
	private Message lenderMessage;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "public_user_game", foreignKey = @ForeignKey(name = "public_user_game_fk"))
	private PublicUserGame publicUserGame;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "gamer", foreignKey = @ForeignKey(name = "gamer_public_user_fk"))
	private PublicUser gamer;

	@Column(columnDefinition = "INTEGER DEFAULT 1")
	private Integer weeks = 1;

	@Column(name = "gamer_address", columnDefinition = "VARCHAR")
	private String gamerAddress;

	@Column(name = "gamer_geolocation")
	private PGpoint gamerGeolocation;

	@Column(name = "gamer_receiver", columnDefinition = "VARCHAR")
	private String gamerReceiver;

	@Column(name = "lender_address", columnDefinition = "VARCHAR")
	private String lenderAddress;

	@Column(name = "lender_geolocation")
	private PGpoint lenderGeolocation;

	@Column(name = "lender_receiver", columnDefinition = "VARCHAR")
	private String lenderReceiver;

	@JsonIgnore
	@Column(name = "cost")
	private byte[] costEnc;

	@Transient
	private Double cost;
	
	@JsonIgnore
	@Column(name = "shippning_cost")
	private byte[] shippningCostEnc;

	@Transient
	private Double shippningCost;
	
	@JsonIgnore
	@Column(name = "fee_game_club")
	private byte[] feeGameClubEnc;

	@Transient
	private Double feeGameClub;
	
	@JsonIgnore
	@Column(name = "taxes")
	private byte[] taxesEnc;
	
	@Transient
	private Double taxes;

	@JsonIgnore
	@Column(name = "balance_part")
	private byte[] balancePartEnc;

	@Transient
	private Double balancePart;

	@JsonIgnore
	@Column(name = "card_part")
	private byte[] cardPartEnc;

	@Transient
	private Double cardPart;
	
	@JsonIgnore
	@Column(name="privateKey_gamer")
	private byte[] privateKeyGamer;
	
	@Column(name="card_reference", columnDefinition="VARCHAR")
	private String cardReference;
	
	@Column(name = "transaction_id", columnDefinition = "VARCHAR")
	private String transactionId;

	@Column(name = "was_accepted", columnDefinition = "BOOLEAN DEFAULT NULL")
	private Boolean wasAccepted;

	@Column(name = "accepted_date")
	private Date acceptedDate;

	@Column(name = "box_number", columnDefinition = "VARCHAR")
	private String boxNumber;

	@Column(name = "lender_status_date")
	private Date lenderStatusDate;

	@Column(name = "gamer_status_date")
	private Date gamerStatusDate;

	@Column(columnDefinition = "VARCHAR")
	private String tracking;

	@Column(name = "shipping_note", columnDefinition = "VARCHAR")
	private String shippingNote;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "shipping_status", foreignKey = @ForeignKey(name = "shipping_status_catalog_fk"))
	private Catalog shippingStatus;

	@Column(name = "delivery_date")
	private Date deliveryDate;

	@JsonManagedReference("LoanRestore")
	@OneToOne(mappedBy="loan", fetch=FetchType.LAZY)
	private Restore restore;

	@JsonManagedReference("LoanReview")
	@OneToOne(mappedBy = "loan", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Review review;

	@Transient
	private Boolean lenderConfirmed = false;

	@Transient
	private Boolean gamerConfirmed = false;

	@Transient
	private Date returnDate;

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

	public String getCardReference() {
		return cardReference;
	}

	public void setCardReference(String cardReference) {
		this.cardReference = cardReference;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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

	public String getShippingNote() {
		return shippingNote;
	}

	public void setShippingNote(String shippingNote) {
		this.shippingNote = shippingNote;
	}

	public Catalog getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(Catalog shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Restore getRestore() {
		return restore;
	}

	public void setRestore(Restore restore) {
		this.restore = restore;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
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

	public Date getReturnDate() {
		if (deliveryDate != null) {
			boolean realTimes = Boolean.parseBoolean(ApplicationContextHolder.getContext().getEnvironment().getProperty("game-club.real-times"));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(deliveryDate);

			if (realTimes) {
				calendar.add(Calendar.DATE, 7 * weeks);
			} else {
				calendar.add(Calendar.MINUTE, 3);
			}

			returnDate = calendar.getTime();
		}

		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public byte[] getCostEnc() {
		return costEnc;
	}

	public void setCostEnc(byte[] costEnc) {
		this.costEnc = costEnc;
	}

	public Double getCost() throws IOException, GeneralSecurityException {
		if (cost != null)
			return cost;
		if (gamer.getPrivateKey() != null && costEnc != null && costEnc.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext()
					.getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, gamer.getPrivateKey());
			String decypted = cryptoService.decrypt(costEnc, key);
			cost = Double.parseDouble(decypted);
		} else {
			cost = 0D;
		}
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public byte[] getBalancePartEnc() {
		return balancePartEnc;
	}

	public void setBalancePartEnc(byte[] balancePartEnc) {
		this.balancePartEnc = balancePartEnc;
	}

	public Double getBalancePart() throws IOException, GeneralSecurityException {
		if (balancePart != null)
			return balancePart;
		if (gamer.getPrivateKey() != null && balancePartEnc != null && balancePartEnc.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext()
					.getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, gamer.getPrivateKey());
			String decypted = cryptoService.decrypt(balancePartEnc, key);
			balancePart = Double.parseDouble(decypted);
		} else {
			balancePart = 0D;
		}
		return balancePart;
	}

	public void setBalancePart(Double balancePart) {
		this.balancePart = balancePart;
	}

	public byte[] getCardPartEnc() {
		return cardPartEnc;
	}

	public void setCardPartEnc(byte[] cardPartEnc) {
		this.cardPartEnc = cardPartEnc;
	}

	public Double getCardPart() throws IOException, GeneralSecurityException {
		if (cardPart != null)
			return cardPart;
		if (gamer.getPrivateKey() != null && cardPartEnc != null && cardPartEnc.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext()
					.getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, gamer.getPrivateKey());
			String decypted = cryptoService.decrypt(cardPartEnc, key);
			cardPart = Double.parseDouble(decypted);
		} else {
			cardPart = 0D;
		}
		return cardPart;
	}

	public void setCardPart(Double cardPart) {
		this.cardPart = cardPart;
	}

	public byte[] getShippningCostEnc() {
		return shippningCostEnc;
	}
	
	

	public byte[] getPrivateKeyGamer() {
		return privateKeyGamer;
	}

	public void setPrivateKeyGamer(byte[] privateKeyGamer) {
		this.privateKeyGamer = privateKeyGamer;
	}

	public void setShippningCostEnc(byte[] shippningCostEnc) {
		this.shippningCostEnc = shippningCostEnc;
	}

	public Double getShippningCost() throws IOException, GeneralSecurityException {
		if (shippningCost != null)
			return shippningCost;
		if (gamer.getPrivateKey() != null && shippningCostEnc != null && shippningCostEnc.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext().getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, gamer.getPrivateKey());
			String decypted = cryptoService.decrypt(shippningCostEnc, key);
			shippningCost = Double.parseDouble(decypted);
		} else {
			shippningCost = 0D;
		}
		return shippningCost;
	}

	public void setShippningCost(Double shippningCost) {
		this.shippningCost = shippningCost;
	}

	public byte[] getFeeGameClubEnc() {
		return feeGameClubEnc;
	}

	public void setFeeGameClubEnc(byte[] feeGameClubEnc) {
		this.feeGameClubEnc = feeGameClubEnc;
	}

	public Double getFeeGameClub() throws IOException, GeneralSecurityException {
		
		if (feeGameClub != null)
			return feeGameClub;
		if (gamer.getPrivateKey() != null && feeGameClubEnc != null && feeGameClubEnc.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext()
					.getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, gamer.getPrivateKey());
			String decypted = cryptoService.decrypt(feeGameClubEnc, key);
			feeGameClub = Double.parseDouble(decypted);
		} else {
			feeGameClub = 0D;
		}
		return feeGameClub;
	}

	public void setFeeGameClub(Double feeGameClub) {
		this.feeGameClub = feeGameClub;
	}

	public byte[] getTaxesEnc() {
		return taxesEnc;
	}

	public void setTaxesEnc(byte[] taxesEnc) {
		this.taxesEnc = taxesEnc;
	}

	public Double getTaxes() throws IOException, GeneralSecurityException {
		
		if (taxes != null)
			return taxes;
		if (gamer.getPrivateKey() != null && taxesEnc != null && taxesEnc.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext()
					.getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, gamer.getPrivateKey());
			String decypted = cryptoService.decrypt(taxesEnc, key);
			taxes = Double.parseDouble(decypted);
		} else {
			taxes = 0D;
		}
		return taxes;
	}

	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}
	
}
