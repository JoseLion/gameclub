package ec.com.levelap.gameclub.module.fine.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="fine")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Fine extends BaseEntity {
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="owner", foreignKey=@ForeignKey(name="public_user_owner_fk"))
	private PublicUser owner;
	
	@Column(nullable=false, columnDefinition="DECIMAL(8, 4) DEFAULT 0.0")
	private Double amount = 0.0;
	
	@Column(nullable=false, columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean wasPayed;
	
	@Column
	private Boolean apply;
	
	@Column(nullable=false, columnDefinition="VARCHAR")
	private String description;

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
}