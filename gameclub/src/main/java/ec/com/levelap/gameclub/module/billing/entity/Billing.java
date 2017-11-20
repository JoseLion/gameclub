package ec.com.levelap.gameclub.module.billing.entity;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.io.FileUtils;
import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.application.ApplicationContextHolder;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="billing")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Immutable
public class Billing {

	@Id
	@Column
	private Long id;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="document_type")
	private String documentType;
	
	@Column
	private String document;
	
	@Column(name="billing_address")
	private String billingAddress;
	
	@Column(name="contact_phone")
	private String contactPhone;
	
	@Column(name="contact_cell")
	private String contactCell;
	
	@Column
	private String mail;
	
	@Column(name="special_contributor")
	private String specialContributor;
	
	@Column
	private String game;
	
	@Column(name="loan_date")
	private Date loanDate;
	
	@Column(name = "id_gamer")
	private Long gamerKey;
	
//	@Transient
//	private Double gamerKey;
	
	@Column
	private Double cost;
	
	@Column(name = "gamer_key")
	private Integer weeks;
	
	@JsonIgnore
	@Column(name = "shipping_cost")
	private byte[] shippingCostEnc;
	
	@Transient
	private Double shippingCost;
	
	@JsonIgnore
	@Column(name = "feeGgame_club")
	private byte[] feeGgameClubEnc;
	
	@Transient
	private Double feeGgameClub;
	
	@JsonIgnore
	@Column
	private byte[] taxesEnc;
	
	@Transient
	private Double taxes;
	
	@JsonIgnore
	@Column(name="privateKey_gamer")
	private byte[] privateKeyGamer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactCell() {
		return contactCell;
	}

	public void setContactCell(String contactCell) {
		this.contactCell = contactCell;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSpecialContributor() {
		return specialContributor;
	}

	public void setSpecialContributor(String specialContributor) {
		this.specialContributor = specialContributor;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public Double getCost() {
		return cost;
	}

	public Long getGamerKey() {
		return gamerKey;
	}

	public void setGamerKey(Long gamerKey) {
		this.gamerKey = gamerKey;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Integer getWeeks() {
		return weeks;
	}

	public void setWeeks(Integer weeks) {
		this.weeks = weeks;
	}

	public byte[] getShippingCostEnc() {
		return shippingCostEnc;
	}

	public void setShippingCostEnc(byte[] shippingCostEnc) {
		this.shippingCostEnc = shippingCostEnc;
	}

	public byte[] getFeeGgameClubEnc() {
		return feeGgameClubEnc;
	}

	public void setFeeGgameClubEnc(byte[] feeGgameClubEnc) {
		this.feeGgameClubEnc = feeGgameClubEnc;
	}

	public byte[] getTaxesEnc() {
		return taxesEnc;
	}

	public void setTaxesEnc(byte[] taxesEnc) {
		this.taxesEnc = taxesEnc;
	}

	public byte[] getPrivateKeyGamer() {
		return privateKeyGamer;
	}

	public void setPrivateKeyGamer(byte[] privateKeyGamer) {
		this.privateKeyGamer = privateKeyGamer;
	}

	public Double getShippingCost() throws IOException, GeneralSecurityException {
		if (privateKeyGamer != null && shippingCostEnc.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext().getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, privateKeyGamer);
			String decypted = cryptoService.decrypt(shippingCostEnc, key);
			taxes = Double.parseDouble(decypted);
		}
		return shippingCost;
	}

	public Double getFeeGgameClub() throws IOException, GeneralSecurityException {
		if (privateKeyGamer != null && feeGgameClubEnc.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext().getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, privateKeyGamer);
			String decypted = cryptoService.decrypt(feeGgameClubEnc, key);
			taxes = Double.parseDouble(decypted);
		}
		return feeGgameClub;
	}

	public Double getTaxes() throws IOException, GeneralSecurityException {
		if (privateKeyGamer != null && taxesEnc.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext().getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, privateKeyGamer);
			String decypted = cryptoService.decrypt(taxesEnc, key);
			taxes = Double.parseDouble(decypted);
		}
		return taxes;
	}
	
	
}
