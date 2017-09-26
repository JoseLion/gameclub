/**
 * 
 */
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


/**
 * The Class Fine.
 *
 * @author Levelap
 */
@Entity
@Table(schema=Const.SCHEMA, name="fine")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Fine extends BaseEntity {
	
	/** The public user fine. */
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="public_user_fine", foreignKey=@ForeignKey(name="public_user_fine_fk"))
	private PublicUser publicUserFine;
	
	/** The loan fine. */
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="loan_fine", foreignKey=@ForeignKey(name="loan_fine_fk"))
	private Loan loanFine;

	/** The amount. */
	@Column(columnDefinition="DECIMAL(8, 4) DEFAULT 0.0")
	private Double amount = 0.0;
	
	/** The date. */
	@Column(name="date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date date = new Date();
	
	/** The was payed. */
	@Column(nullable=false, columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean wasPayed;
	
	/** The apply. */
	@Column(nullable=true)
	private Boolean apply;
	
	/** The description. */
	@Column(nullable=false, columnDefinition="VARCHAR")
	private String description;
	
	/** The notifications. */
	@Column(columnDefinition="INTEGER DEFAULT 0")
	private Integer notifications;
	
	
	/**
	 * Gets the public user fine.
	 *
	 * @return the publicUserFine
	 */
	public PublicUser getPublicUserFine() {
		return publicUserFine;
	}

	/**
	 * Sets the public user fine.
	 *
	 * @param publicUserFine the publicUserFine to set
	 */
	public void setPublicUserFine(PublicUser publicUserFine) {
		this.publicUserFine = publicUserFine;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the was payed.
	 *
	 * @return the wasPayed
	 */
	public Boolean getWasPayed() {
		return wasPayed;
	}

	/**
	 * Sets the was payed.
	 *
	 * @param wasPayed the wasPayed to set
	 */
	public void setWasPayed(Boolean wasPayed) {
		this.wasPayed = wasPayed;
	}

	/**
	 * Gets the loan fine.
	 *
	 * @return the loanFine
	 */
	public Loan getLoanFine() {
		return loanFine;
	}

	/**
	 * Sets the loan fine.
	 *
	 * @param loanFine the loanFine to set
	 */
	public void setLoanFine(Loan loanFine) {
		this.loanFine = loanFine;
	}

	/**
	 * Gets the apply.
	 *
	 * @return the apply
	 */
	public Boolean getApply() {
		return apply;
	}

	/**
	 * Sets the apply.
	 *
	 * @param apply the apply to set
	 */
	public void setApply(Boolean apply) {
		this.apply = apply;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
