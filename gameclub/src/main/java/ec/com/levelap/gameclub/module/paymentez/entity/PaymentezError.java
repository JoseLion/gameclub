package ec.com.levelap.gameclub.module.paymentez.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="paymentez_error")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PaymentezError extends BaseEntity {

	@Column(columnDefinition="INTEGER")
	private Integer code;
	
	@Column(columnDefinition="VARCHAR")
	private String transaction;
	
	@Column(columnDefinition="VARCHAR")
	private String description;
	
	@Column(name = "transaction_date")
	private Date transactionDate;
	
	@Column(columnDefinition="VARCHAR")
	private String url;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
