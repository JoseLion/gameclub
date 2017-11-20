package ec.com.levelap.gameclub.module.logisticsKits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="logistics_kits")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Immutable
public class LogisticsKits {

	@Id
	@Column
	private Long id;
		
	@Column
	private Long name;
	
	@Column
	private Long lastName;
	
	@Column
	private Long city;
	
	@Column
	private Long address;

	@Column
	private Long mail;
	
	@Column
	private Long document;

	@Column(name="contact_phone")
	private Long contactPhone;
	
	@Column(name="transaction_type")
	private Long transactionType;
	
	@Column
	private Long status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getName() {
		return name;
	}

	public void setName(Long name) {
		this.name = name;
	}

	public Long getLastName() {
		return lastName;
	}

	public void setLastName(Long lastName) {
		this.lastName = lastName;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public Long getAddress() {
		return address;
	}

	public void setAddress(Long address) {
		this.address = address;
	}

	public Long getMail() {
		return mail;
	}

	public void setMail(Long mail) {
		this.mail = mail;
	}

	public Long getDocument() {
		return document;
	}

	public void setDocument(Long document) {
		this.document = document;
	}

	public Long getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(Long contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Long transactionType) {
		this.transactionType = transactionType;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	
}
