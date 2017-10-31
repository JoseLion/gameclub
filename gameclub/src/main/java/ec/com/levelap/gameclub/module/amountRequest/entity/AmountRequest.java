package ec.com.levelap.gameclub.module.amountRequest.entity;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.commons.archive.Archive;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.location.Location;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.application.ApplicationContextHolder;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="amount_request")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class AmountRequest extends BaseEntity{
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="public_user_amount_request", foreignKey=@ForeignKey(name="public_user_amount_request_fk"))
	private PublicUser publicUser;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "request_status_amount_request", foreignKey = @ForeignKey(name = "request_status_amount_request_fk"))
	private Catalog requestStatus;
	
	@JsonIgnore
	@Column(name = "amount")
	private byte[] amount;
	
	@Column(name="account_full_name", nullable=false, columnDefinition="VARCHAR")
	private String accountFullName;
	
	@Column(name="account_document", nullable=false, columnDefinition="VARCHAR")
	private String accountDocument;
	
	@Column(name="account_bank", nullable=false, columnDefinition="VARCHAR")
	private String accountBank;
	
	@Column(name="account_number", nullable=false, columnDefinition="VARCHAR")
	private String accountNumber;
	
	@Column(name="account_email", columnDefinition="VARCHAR")
	private String accountEmail;
	
	@Column(name="account_is_saving", nullable=false, columnDefinition="BOOLEAN DEFAULT TRUE")
	private Boolean accountIsSaving;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "identity_photo", foreignKey = @ForeignKey(name = "identity_photo_fk"))
	private Archive identityPhoto;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "location_destiny", foreignKey = @ForeignKey(name = "location_destiny_fk"))
	private Location locationDestiny;
	
	@Column(name="address", columnDefinition="VARCHAR")
	private String address;
	
	@Column(name="contact_phone", columnDefinition="VARCHAR")
	private String contactPhone;
	
	@Transient
	private Double showAmount;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="message_amount_request", foreignKey=@ForeignKey(name="message_amount_request_fk"))
	private Message message;

	public PublicUser getPublicUser() {
		return publicUser;
	}

	public void setPublicUser(PublicUser publicUser) {
		this.publicUser = publicUser;
	}

	public Catalog getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(Catalog requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getAccountFullName() {
		return accountFullName;
	}

	public void setAccountFullName(String accountFullName) {
		this.accountFullName = accountFullName;
	}

	public String getAccountDocument() {
		return accountDocument;
	}

	public void setAccountDocument(String accountDocument) {
		this.accountDocument = accountDocument;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountEmail() {
		return accountEmail;
	}

	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}

	public Boolean getAccountIsSaving() {
		return accountIsSaving;
	}

	public void setAccountIsSaving(Boolean accountIsSaving) {
		this.accountIsSaving = accountIsSaving;
	}

	public Archive getIdentityPhoto() {
		return identityPhoto;
	}

	public void setIdentityPhoto(Archive identityPhoto) {
		this.identityPhoto = identityPhoto;
	}

	public Location getLocationDestiny() {
		return locationDestiny;
	}

	public void setLocationDestiny(Location locationDestiny) {
		this.locationDestiny = locationDestiny;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public byte[] getAmount() {
		return amount;
	}

	public void setAmount(byte[] amount) {
		this.amount = amount;
	}

	public Double getShowAmount() throws IOException, GeneralSecurityException {
		
		if (publicUser.getPrivateKey() != null && amount != null && amount.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext()
					.getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, publicUser.getPrivateKey());
			String decypted = cryptoService.decrypt(amount, key);
			showAmount = Double.parseDouble(decypted);
		} else {
			showAmount = 0D;
		}
		return showAmount;
	}

	public void setShowAmount(Double showAmount) {
		this.showAmount = showAmount;
	}
		
}
