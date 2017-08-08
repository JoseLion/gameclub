package ec.com.levelap.gameclub.module.message.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.postgresql.geometric.PGpoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="welcome_kit")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WelcomeKit extends BaseEntity {
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="message", foreignKey=@ForeignKey(name="message_fk"))
	private Message message;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="public_user", foreignKey=@ForeignKey(name="public_user_fk"))
	private PublicUser publicUser;
	
	@Column(name="geolocation")
	private PGpoint geolocation;
	
	@Column(name="shipping_status", columnDefinition="VARCHAR")
	private String shippingStatus;
	
	@Column(name="was_confirmed", columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean wasConfirmed = false;
	
	@Column(name="delivery_number", columnDefinition="VARCHAR")
	private String deliveryNumber;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public PublicUser getPublicUser() {
		return publicUser;
	}

	public void setPublicUser(PublicUser publicUser) {
		this.publicUser = publicUser;
	}

	public PGpoint getGeolocation() {
		return geolocation;
	}

	public void setGeolocation(PGpoint geolocation) {
		this.geolocation = geolocation;
	}

	public String getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public Boolean getWasConfirmed() {
		return wasConfirmed;
	}

	public void setWasConfirmed(Boolean wasConfirmed) {
		this.wasConfirmed = wasConfirmed;
	}

	public String getDeliveryNumber() {
		return deliveryNumber;
	}

	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}
}