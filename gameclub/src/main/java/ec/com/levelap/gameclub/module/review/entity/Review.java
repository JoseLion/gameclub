package ec.com.levelap.gameclub.module.review.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="review")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Review extends BaseEntity {
	@JsonBackReference("LoanReview")
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	@JoinColumn(name="loan", foreignKey=@ForeignKey(name="loan_fk"))
	private Loan loan;
	
	@Column(name="gamer_reviwed_on")
	private Date gamerReviwedOn;
	
	@Column(name="gamer_score")
	private Integer gamerScore;
	
	@Column(name="gamer_comment", columnDefinition="VARCHAR")
	private String gamerComment;
	
	@Column(name="lender_reviwed_on")
	private Date lenderReviwedOn;
	
	@Column(name="lender_score")
	private Integer lenderScore;
	
	@Column(name="lender_comment", columnDefinition="VARCHAR")
	private String lenderComment;
	
	@Transient
	private Boolean finished = false;

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Date getGamerReviwedOn() {
		return gamerReviwedOn;
	}

	public void setGamerReviwedOn(Date gamerReviwedOn) {
		this.gamerReviwedOn = gamerReviwedOn;
	}

	public Integer getGamerScore() {
		return gamerScore;
	}

	public void setGamerScore(Integer gamerScore) {
		this.gamerScore = gamerScore;
	}

	public String getGamerComment() {
		return gamerComment;
	}

	public void setGamerComment(String gamerComment) {
		this.gamerComment = gamerComment;
	}

	public Date getLenderReviwedOn() {
		return lenderReviwedOn;
	}

	public void setLenderReviwedOn(Date lenderReviwedOn) {
		this.lenderReviwedOn = lenderReviwedOn;
	}

	public Integer getLenderScore() {
		return lenderScore;
	}

	public void setLenderScore(Integer lenderScore) {
		this.lenderScore = lenderScore;
	}

	public String getLenderComment() {
		return lenderComment;
	}

	public void setLenderComment(String lenderComment) {
		this.lenderComment = lenderComment;
	}

	public Boolean getFinished() {
		if (gamerScore != null && lenderScore != null) {
			finished = true;
		}
		
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}
}
