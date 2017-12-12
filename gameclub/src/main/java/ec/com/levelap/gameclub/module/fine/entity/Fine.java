package ec.com.levelap.gameclub.module.fine.entity;

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
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.application.ApplicationContextHolder;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema = Const.SCHEMA, name = "fine")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Fine extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "public_user_fine", foreignKey = @ForeignKey(name = "public_user_fine_fk"))
	private PublicUser owner;

	@Column(name = "was_payed", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean wasPayed = false;

	@Column
	private Boolean apply;

	@Column(nullable = false, columnDefinition = "VARCHAR")
	private String description;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "message", foreignKey = @ForeignKey(name = "message_fk"))
	private Message message;

	@JsonIgnore
	@Column(name = "amount", nullable = false)
	private byte[] amountEnc;

	@JsonIgnore
	@Column(name = "balance_part")
	private byte[] balancePartEnc;

	@JsonIgnore
	@Column(name = "card_part")
	private byte[] cardPartEnc;

	@Transient
	private Double amount;

	@Transient
	private Double balancePart;

	@Transient
	private Double cardPart;

	public PublicUser getOwner() {
		return owner;
	}

	public void setOwner(PublicUser owner) {
		this.owner = owner;
	}

	public Boolean getWasPayed() {
		return wasPayed;
	}

	public void setWasPayed(Boolean wasPayed) {
		this.wasPayed = wasPayed;
	}

	public Boolean getApply() {
		return apply;
	}

	public void setApply(Boolean apply) {
		this.apply = apply;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public byte[] getAmountEnc() {
		return amountEnc;
	}

	public void setAmountEnc(byte[] amountEnc) {
		this.amountEnc = amountEnc;
	}

	public byte[] getBalancePartEnc() {
		return balancePartEnc;
	}

	public void setBalancePartEnc(byte[] balancePartEnc) {
		this.balancePartEnc = balancePartEnc;
	}

	public byte[] getCardPartEnc() {
		return cardPartEnc;
	}

	public void setCardPartEnc(byte[] cardPartEnc) {
		this.cardPartEnc = cardPartEnc;
	}

	public Double getAmount() throws IOException, GeneralSecurityException {
		if (owner.getPrivateKey() != null && amountEnc != null && amountEnc.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext().getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, owner.getPrivateKey());
			String decypted = cryptoService.decrypt(amountEnc, key);
			amount = Double.parseDouble(decypted);
		} else {
			amount = 0D;
		}
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBalancePart() throws IOException, GeneralSecurityException {
		if (owner.getPrivateKey() != null && balancePartEnc != null && balancePartEnc.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext().getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, owner.getPrivateKey());
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

	public Double getCardPart() throws IOException, GeneralSecurityException {
		if (owner.getPrivateKey() != null && cardPartEnc != null && cardPartEnc.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext().getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, owner.getPrivateKey());
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

}