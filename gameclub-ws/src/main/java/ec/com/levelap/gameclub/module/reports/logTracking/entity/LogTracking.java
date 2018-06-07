package ec.com.levelap.gameclub.module.reports.logTracking.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="log_tracking")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Immutable
public class LogTracking {
	
	@Id
	@Column
	private Long id;
	
	@Column(name="box_number")
	private String boxNumber;
	
	@Column
	private String game;
	
	@Column(name="price_game")
	private String priceGame;
	
	@Column(name="referral_guide")
	private String referralGuide;
	
	@Column
	private String name;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column
	private String city;
	
	@Column
	private String address;
	
	@Column
	private String mail;
	
	@Column
	private String document;
	
	@Column(name="contact_phone")
	private String contactPhone;
	
	@Column(name="name_lender")
	private String nameLender;
	
	@Column(name="last_name_lender")
	private String lastNameLender;
	
	@Column(name="city_lender")
	private String cityLender;
	
	@Column(name="address_lender")
	private String addressLender;
	
	@Column(name="mail_lender")
	private String mailLender;
	
	@Column(name="document_lender")
	private String documentLender;
	
	@Column(name="contact_phone_lender")
	private String contactPhoneLender;
	
	@Column(name="transaction_type")
	private String transactionType;
	
	@Column
	private String status;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="payment_date")
	private Date paymentDate;
	
	@Column(name="delivery_date")
	private Date deliveryDate;
	
	@Column(name="return_date")
	private Date returnDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}

	public String getGame() {
		return game;
	}

	public String getPriceGame() {
		return priceGame;
	}

	public void setPriceGame(String priceGame) {
		this.priceGame = priceGame;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getReferralGuide() {
		return referralGuide;
	}

	public void setReferralGuide(String referralGuide) {
		this.referralGuide = referralGuide;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getNameLender() {
		return nameLender;
	}

	public void setNameLender(String nameLender) {
		this.nameLender = nameLender;
	}

	public String getLastNameLender() {
		return lastNameLender;
	}

	public void setLastNameLender(String lastNameLender) {
		this.lastNameLender = lastNameLender;
	}

	public String getCityLender() {
		return cityLender;
	}

	public void setCityLender(String cityLender) {
		this.cityLender = cityLender;
	}

	public String getAddressLender() {
		return addressLender;
	}

	public void setAddressLender(String addressLender) {
		this.addressLender = addressLender;
	}

	public String getMailLender() {
		return mailLender;
	}

	public void setMailLender(String mailLender) {
		this.mailLender = mailLender;
	}

	public String getDocumentLender() {
		return documentLender;
	}

	public void setDocumentLender(String documentLender) {
		this.documentLender = documentLender;
	}

	public String getContactPhoneLender() {
		return contactPhoneLender;
	}

	public void setContactPhoneLender(String contactPhoneLender) {
		this.contactPhoneLender = contactPhoneLender;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the paymentDate
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * @return the deliveryDate
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * @param deliveryDate the deliveryDate to set
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * @return the returnDate
	 */
	public Date getReturnDate() {
		return returnDate;
	}

	/**
	 * @param returnDate the returnDate to set
	 */
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

}
