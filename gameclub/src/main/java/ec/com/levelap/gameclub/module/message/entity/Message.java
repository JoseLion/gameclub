package ec.com.levelap.gameclub.module.message.entity;

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
	@JoinColumn(name="sender", foreignKey=@ForeignKey(name="sender_public_user_fk"))
	private PublicUser sender;
	
	@Column(columnDefinition="VARCHAR")
	private String subject;
	
	@Column(columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean read = false;
	
	@Column(name="is_promo", columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean isPromo = false;

	public PublicUser getSender() {
		return sender;
	}

	public void setSender(PublicUser sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Boolean getIsPromo() {
		return isPromo;
	}

	public void setIsPromo(Boolean isPromo) {
		this.isPromo = isPromo;
	}
}
