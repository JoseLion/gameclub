package ec.com.levelap.gameclub.module.welcomeKit.entity;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
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

import org.apache.commons.io.FileUtils;
import org.postgresql.geometric.PGpoint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.application.ApplicationContextHolder;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="welcome_kit")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WelcomeKit extends BaseEntity {
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="message", foreignKey=@ForeignKey(name="message_fk"))
	private Message message;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="public_user", foreignKey=@ForeignKey(name="public_user_fk"))
	private PublicUser publicUser;
	
	@Column(columnDefinition="VARCHAR")
	private String address;
	
	@Column(columnDefinition="VARCHAR")
	private String phone;
	
	@Column(columnDefinition="VARCHAR")
	private String receiver;
	
	@Column(name="geolocation")
	private PGpoint geolocation;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="shipping_status", foreignKey=@ForeignKey(name="shipping_status_catalog_fk"))
	private Catalog shippingStatus;
	
	@Column(name="was_confirmed", columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean wasConfirmed = false;
	
	@Column(name="confirmation_date")
	private Date confirmationDate;
	
	@Column(columnDefinition="VARCHAR")
	private String tracking;
	
	@Column(name="shipping_note", columnDefinition="VARCHAR")
	private String shippingNote;
	
	@JsonIgnore
	@Column(name = "amount_balance")
	private byte[] amountBalance;

	@Transient
	private Double amountBalanceValue;

	@JsonIgnore
	@Column(name = "amount_card")
	private byte[] amountCard;

	@Transient
	private Double amountCardValue;

	@Column(name="card_reference", columnDefinition="VARCHAR")
	private String cardReference;
	
	@Column(name="transaction_id", columnDefinition="VARCHAR")
	private String transactionId;
	
	@Column(name = "auth_code", columnDefinition = "VARCHAR")
	private String authCode;
	
	@Column(columnDefinition="INTEGER DEFAULT 0")
	private Integer quantity = 0;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public PublicUser getPublicUser() {
		return publicUser;
	}

	public void setPublicUser(PublicUser publicUser) {
		this.publicUser = publicUser;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public PGpoint getGeolocation() {
		return geolocation;
	}

	public void setGeolocation(PGpoint geolocation) {
		this.geolocation = geolocation;
	}

	public Catalog getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(Catalog shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public Boolean getWasConfirmed() {
		return wasConfirmed;
	}

	public void setWasConfirmed(Boolean wasConfirmed) {
		this.wasConfirmed = wasConfirmed;
	}

	public Date getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
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

	public byte[] getAmountBalance() {
		return amountBalance;
	}

	public void setAmountBalance(byte[] amountBalance) {
		this.amountBalance = amountBalance;
	}

	public Double getAmountBalanceValue() throws IOException, GeneralSecurityException {
		if (publicUser.getPrivateKey() != null && amountBalance != null && amountBalance.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext()
					.getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, publicUser.getPrivateKey());
			String decypted = cryptoService.decrypt(amountBalance, key);
			amountBalanceValue = Double.parseDouble(decypted);
		} else {
			amountBalanceValue = 0D;
		}
		return amountBalanceValue;
	}

	public void setAmountBalanceValue(Double amountBalanceValue) {
		this.amountBalanceValue = amountBalanceValue;
	}

	public byte[] getAmountCard() {
		return amountCard;
	}

	public void setAmountCard(byte[] amountCard) {
		this.amountCard = amountCard;
	}

	public Double getAmountCardValue() throws IOException, GeneralSecurityException {
		if (publicUser.getPrivateKey() != null && amountCard !=null && amountCard.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext()
					.getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, publicUser.getPrivateKey());
			String decypted = cryptoService.decrypt(amountCard, key);
			amountCardValue = Double.parseDouble(decypted);
		} else {
			amountCardValue = 0D;
		}
		return amountCardValue;
	}

	public void setAmountCardValue(Double amountCardValue) {
		this.amountCardValue = amountCardValue;
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

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}