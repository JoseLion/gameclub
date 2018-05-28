package ec.com.levelap.gameclub.module.reports.registeredUsers.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="registered_users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Immutable
public class RegisteredUsers {

	@Id
	@Column
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="public_user_id", foreignKey=@ForeignKey(name="public_user_fk"))
	private PublicUser publicUserId;
	
	@Column(name="total_games")
	private Double totalGames;
	
	@Column(name="total_requested_rentals")
	private Double totalRequestedRentals;
	
	@Column(name="total_rent_aceppted")
	private Double totalRentAceppted;
	
	@Column(name="total_rent_requested")
	private Double totalRentRequested;
	
	@Column(name="total_applications_received")
	private Double totalApplicationsReceived;
	
	@Column(name="total_request_accepted")
	private Double totalRequestAccepted;
	
	@Column(name="total_request_rejected")
	private Double totalRequestRejected;
	
	@Column(name="creation_date")
	private Date creationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PublicUser getPublicUserId() {
		return publicUserId;
	}

	public void setPublicUserId(PublicUser publicUserId) {
		this.publicUserId = publicUserId;
	}

	public Double getTotalGames() {
		return totalGames;
	}

	public void setTotalGames(Double totalGames) {
		this.totalGames = totalGames;
	}

	public Double getTotalRequestedRentals() {
		return totalRequestedRentals;
	}

	public void setTotalRequestedRentals(Double totalRequestedRentals) {
		this.totalRequestedRentals = totalRequestedRentals;
	}

	public Double getTotalRentAceppted() {
		return totalRentAceppted;
	}

	public void setTotalRentAceppted(Double totalRentAceppted) {
		this.totalRentAceppted = totalRentAceppted;
	}

	public Double getTotalRentRequested() {
		return totalRentRequested;
	}

	public void setTotalRentRequested(Double totalRentRequested) {
		this.totalRentRequested = totalRentRequested;
	}

	public Double getTotalApplicationsReceived() {
		return totalApplicationsReceived;
	}

	public void setTotalApplicationsReceived(Double totalApplicationsReceived) {
		this.totalApplicationsReceived = totalApplicationsReceived;
	}

	public Double getTotalRequestAccepted() {
		return totalRequestAccepted;
	}

	public void setTotalRequestAccepted(Double totalRequestAccepted) {
		this.totalRequestAccepted = totalRequestAccepted;
	}

	public Double getTotalRequestRejected() {
		return totalRequestRejected;
	}

	public void setTotalRequestRejected(Double totalRequestRejected) {
		this.totalRequestRejected = totalRequestRejected;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
