package ec.com.levelap.gameclub.module.fine.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="fine")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Fine extends BaseEntity {
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="public_user_fine", foreignKey=@ForeignKey(name="public_user_fine_fk"))
	private PublicUser owner;
	
	@Column(nullable=false, columnDefinition="DECIMAL(8, 4) DEFAULT 0.0")
	private Double amount = 0.0;
	
	@Column(name="was_payed", nullable=false, columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean wasPayed = false;
	
	@Column
	private Boolean apply;
	
	@Column(nullable=false, columnDefinition="VARCHAR")
	private String description;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="message", foreignKey=@ForeignKey(name="message_fk"))
	private Message message;
	
	@Column(name="balance_part", columnDefinition="DECIMAL(8, 4) DEFAULT 0.0")
	private Double balancePart = 0.0;
	
	@Column(name="card_part", columnDefinition="DECIMAL(8, 4) DEFAULT 0.0")
	private Double cardPart = 0.0;

	public PublicUser getOwner() {
		return owner;
	}

	public void setOwner(PublicUser owner) {
		this.owner = owner;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	public Double getBalancePart() {
		return balancePart;
	}

	public void setBalancePart(Double balancePart) {
		this.balancePart = balancePart;
	}

	public Double getCardPart() {
		return cardPart;
	}

	public void setCardPart(Double cardPart) {
		this.cardPart = cardPart;
	}
	
}