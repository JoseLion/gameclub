package ec.com.levelap.gameclub.module.fine.entity;

import java.util.Date;

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
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="fine")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Fine extends BaseEntity {
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="public_user_fine", foreignKey=@ForeignKey(name="public_user_fine_fk"))
	private PublicUser publicUserFine;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="loan_fine", foreignKey=@ForeignKey(name="loan_fine_fk"))
	private Loan loanFine;
	
	@Column(columnDefinition="DECIMAL(8, 4) DEFAULT 0.0")
	private Double amount = 0.0;
	
	@Column(name="date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date date = new Date();
	
	@Column(nullable=false, columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean wasPayed;
	
	@Column(nullable=true)
	private Boolean apply;
	
	@Column(nullable=false, columnDefinition="VARCHAR")
	private String description;
	
	@Column(columnDefinition="INTEGER DEFAULT 0")
	private Integer notifications;

	public PublicUser getPublicUserFine() {
		return publicUserFine;
	}

	public void setPublicUserFine(PublicUser publicUserFine) {
		this.publicUserFine = publicUserFine;
	}

	public Loan getLoanFine() {
		return loanFine;
	}

	public void setLoanFine(Loan loanFine) {
		this.loanFine = loanFine;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Integer getNotifications() {
		return notifications;
	}

	public void setNotifications(Integer notifications) {
		this.notifications = notifications;
	}
}