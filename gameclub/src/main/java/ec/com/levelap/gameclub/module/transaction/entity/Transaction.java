package ec.com.levelap.gameclub.module.transaction.entity;

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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.application.ApplicationContextHolder;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema = Const.SCHEMA, name = "transaction")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Transaction extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "public_user_transaction", foreignKey = @ForeignKey(name = "public_user_transaction_fk"))
	private PublicUser owner;

	@Column(columnDefinition = "VARCHAR")
	private String transaction;

	@Column(columnDefinition = "VARCHAR")
	private String game = "";

	@Column(columnDefinition = "INTEGER DEFAULT 0")
	private Integer weeks = 0;

	@JsonIgnore
	@Column(name = "balance_part")
	private byte[] creditPartEnc;

	@JsonIgnore
	@Column(name = "debit_balance")
	private byte[] debitBalanceEnc;

	@JsonIgnore
	@Column(name = "debit_card")
	private byte[] debitCardEnc;

	@Transient
	private Double creditPart;

	@Transient
	private Double debitBalance;

	@Transient
	private Double debitCard;

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

	public byte[] getCreditPartEnc() {
		return creditPartEnc;
	}

	public void setCreditPartEnc(byte[] creditPartEnc) {
		this.creditPartEnc = creditPartEnc;
	}

	public byte[] getDebitBalanceEnc() {
		return debitBalanceEnc;
	}

	public void setDebitBalanceEnc(byte[] debitBalanceEnc) {
		this.debitBalanceEnc = debitBalanceEnc;
	}

	public byte[] getDebitCardEnc() {
		return debitCardEnc;
	}

	public void setDebitCardEnc(byte[] debitCardEnc) {
		this.debitCardEnc = debitCardEnc;
	}

	public Double getCreditPart() throws IOException, GeneralSecurityException {
		if (owner.getPrivateKey() != null && creditPartEnc !=null && creditPartEnc.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext()
					.getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, owner.getPrivateKey());
			String decypted = cryptoService.decrypt(creditPartEnc, key);
			creditPart = Double.parseDouble(decypted);
		} else {
			creditPart = 0D;
		}
		return creditPart;
	}

	public void setCreditPart(Double creditPart) {
		this.creditPart = creditPart;
	}

	public Double getDebitBalance() throws IOException, GeneralSecurityException {
		if (owner.getPrivateKey() != null && debitBalanceEnc !=null && debitBalanceEnc.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext()
					.getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, owner.getPrivateKey());
			String decypted = cryptoService.decrypt(debitBalanceEnc, key);
			debitBalance = Double.parseDouble(decypted);
		} else {
			debitBalance = 0D;
		}
		return debitBalance;
	}

	public void setDebitBalance(Double debitBalance) {
		this.debitBalance = debitBalance;
	}

	public Double getDebitCard() throws IOException, GeneralSecurityException {
		if (owner.getPrivateKey() != null && debitCardEnc !=null && debitCardEnc.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext()
					.getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, owner.getPrivateKey());
			String decypted = cryptoService.decrypt(debitCardEnc, key);
			debitCard = Double.parseDouble(decypted);
		} else {
			debitCard = 0D;
		}
		return debitCard;
	}

	public void setDebitCard(Double debitCard) {
		this.debitCard = debitCard;
	}

}