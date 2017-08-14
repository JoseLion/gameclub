package ec.com.levelap.gameclub.module.message.entity;

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
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="message")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Message extends BaseEntity {
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="owner", foreignKey=@ForeignKey(name="owner_public_user_fk"))
	private PublicUser owner;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="from", foreignKey=@ForeignKey(name="from_public_user_fk"))
	private PublicUser from;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="to", foreignKey=@ForeignKey(name="to_public_user_fk"))
	private PublicUser to;
	
	@Column(columnDefinition="VARCHAR")
	private String subject;
	
	@Column(name="date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date date = new Date();
	
	@Column(columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean read = false;
	
	@Column(name="is_loan", columnDefinition="BOOLEAN DEFAULT NULL")
	private Boolean isLoan;

	public PublicUser getOwner() {
		return owner;
	}

	public void setOwner(PublicUser owner) {
		this.owner = owner;
	}

	public PublicUser getFrom() {
		return from;
	}

	public void setFrom(PublicUser from) {
		this.from = from;
	}

	public PublicUser getTo() {
		return to;
	}

	public void setTo(PublicUser to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Boolean getIsLoan() {
		return isLoan;
	}

	public void setIsLoan(Boolean isLoan) {
		this.isLoan = isLoan;
	}
}