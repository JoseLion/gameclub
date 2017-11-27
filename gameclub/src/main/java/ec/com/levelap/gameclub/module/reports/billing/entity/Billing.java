package ec.com.levelap.gameclub.module.reports.billing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	
	@Column
	private Double cost;
	
	@Column
	private Integer weeks;

	@Column(name = "shipping_cost")
	private Double shippingCost;

	@Column(name = "fee_game_club")
	private Double feeGgameClub;

	@Column
	private Double taxes;

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

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Integer getWeeks() {
		return weeks;
	}

	public void setWeeks(Integer weeks) {
		this.weeks = weeks;
	}

	public Double getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(Double shippingCost) {
		this.shippingCost = shippingCost;
	}

	public Double getFeeGgameClub() {
		return feeGgameClub;
	}

	public void setFeeGgameClub(Double feeGgameClub) {
		this.feeGgameClub = feeGgameClub;
	}

	public Double getTaxes() {
		return taxes;
	}

	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}
	
}
