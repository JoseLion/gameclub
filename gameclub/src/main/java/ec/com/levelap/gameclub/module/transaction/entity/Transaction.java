package ec.com.levelap.gameclub.module.transaction.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="transaction")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Transaction extends BaseEntity{

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="public_user_transaction", foreignKey=@ForeignKey(name="public_user_transaction_fk"))
	private PublicUser owner;
	
	@Column(columnDefinition="VARCHAR")
	private String transaction;
	
	@Column(columnDefinition="VARCHAR")
	private String game = "";
	
	@Column(columnDefinition="INTEGER DEFAULT 0")
	private Integer weeks = 0;
	
	@Column(name="balance_part", nullable=false, columnDefinition="DECIMAL(8, 4) DEFAULT 0.0")
	private Double creditPart = 0.0;
	
	@Column(name="debit_balance", nullable=false, columnDefinition="DECIMAL(8, 4) DEFAULT 0.0")
	private Double debitBalance = 0.0;
	
	@Column(name="debit_card", nullable=false, columnDefinition="DECIMAL(8, 4) DEFAULT 0.0")
	private Double debitCard = 0.0;

	public PublicUser getOwner() {
		return owner;
	}

	public void setOwner(PublicUser owner) {
		this.owner = owner;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public Integer getWeeks() {
		return weeks;
	}

	public void setWeeks(Integer weeks) {
		this.weeks = weeks;
	}

	public Double getCreditPart() {
		return creditPart;
	}

	public void setCreditPart(Double creditPart) {
		this.creditPart = creditPart;
	}

	public Double getDebitBalance() {
		return debitBalance;
	}

	public void setDebitBalance(Double debitBalance) {
		this.debitBalance = debitBalance;
	}

	public Double getDebitCard() {
		return debitCard;
	}

	public void setDebitCard(Double debitCard) {
		this.debitCard = debitCard;
	}
	
}
