package ec.com.levelap.gameclub.module.restore.entity;

import java.util.Date;

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
import javax.persistence.UniqueConstraint;

import org.postgresql.geometric.PGpoint;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="restore", uniqueConstraints=@UniqueConstraint(columnNames="loan", name="loan_uk"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Restore extends BaseEntity {
	@JsonBackReference("LoanRestore")
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.DETACH, optional=false)
	@JoinColumn(name="loan", foreignKey=@ForeignKey(name="loan_fk"))
	private Loan loan;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="lender_message", foreignKey=@ForeignKey(name="lender_message_fk"))
	private Message lenderMessage;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="gamer_message", foreignKey=@ForeignKey(name="gamer_message_fk"))
	private Message gamerMessage;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.DETACH)
	@JoinColumn(name="public_user_game", foreignKey=@ForeignKey(name="public_user_game_fk"))
	private PublicUserGame publicUserGame;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.DETACH)
	@JoinColumn(name="gamer", foreignKey=@ForeignKey(name="gamer_public_user_fk"))
	private PublicUser gamer;
	
	@Column(name="lender_address", columnDefinition="VARCHAR")
	private String lenderAddress;
	
	@Column(name="lender_geolocation")
	private PGpoint lenderGeolocation;
	
	@Column(name="lender_receiver", columnDefinition="VARCHAR")
	private String lenderReceiver;
	
	@Column(name="gamer_address", columnDefinition="VARCHAR")
	private String gamerAddress;
	
	@Column(name="gamer_geolocation")
	private PGpoint gamerGeolocation;
	
	@Column(name="gamer_receiver", columnDefinition="VARCHAR")
	private String gamerReceiver;
	
	@Column(name="lender_confirm_date")
	private Date lenderConfirmDate;
	
	@Column(name="gamer_confirm_date")
	private Date gamerConfirmDate;
	
	@Column(name="lender_status_date")
	private Date lenderStatusDate;
	
	@Column(name="gamer_status_date")
	private Date gamerStatusDate;
	
	@Column(columnDefinition="VARCHAR")
	private String tracking;
	
	@Column(name="shipping_note", columnDefinition="VARCHAR")
	private String shippingNote;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="shipping_status", foreignKey=@ForeignKey(name="shipping_status_catalog_fk"))
	private Catalog shippingStatus;
	
	@Transient
	private Boolean lenderConfirmed;
	
	@Transient
	private Boolean gamerConfirmed;
	
	public Restore() {
		super();
	}

	public Restore(Loan loan) {
		super();
		this.loan = loan;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	
	public Message getLenderMessage() {
		return lenderMessage;
	}

	public void setLenderMessage(Message lenderMessage) {
		this.lenderMessage = lenderMessage;
	}

	public Message getGamerMessage() {
		return gamerMessage;
	}

	public void setGamerMessage(Message gamerMessage) {
		this.gamerMessage = gamerMessage;
	}

	public PublicUserGame getPublicUserGame() {
		return publicUserGame;
	}

	public void setPublicUserGame(PublicUserGame publicUserGame) {
		this.publicUserGame = publicUserGame;
	}

	public PublicUser getGamer() {
		return gamer;
	}

	public void setGamer(PublicUser gamer) {
		this.gamer = gamer;
	}

	public String getLenderAddress() {
		return lenderAddress;
	}

	public void setLenderAddress(String lenderAddress) {
		this.lenderAddress = lenderAddress;
	}

	public PGpoint getLenderGeolocation() {
		return lenderGeolocation;
	}

	public void setLenderGeolocation(PGpoint lenderGeolocation) {
		this.lenderGeolocation = lenderGeolocation;
	}

	public String getLenderReceiver() {
		return lenderReceiver;
	}

	public void setLenderReceiver(String lenderReceiver) {
		this.lenderReceiver = lenderReceiver;
	}

	public String getGamerAddress() {
		return gamerAddress;
	}

	public void setGamerAddress(String gamerAddress) {
		this.gamerAddress = gamerAddress;
	}

	public PGpoint getGamerGeolocation() {
		return gamerGeolocation;
	}

	public void setGamerGeolocation(PGpoint gamerGeolocation) {
		this.gamerGeolocation = gamerGeolocation;
	}

	public String getGamerReceiver() {
		return gamerReceiver;
	}

	public void setGamerReceiver(String gamerReceiver) {
		this.gamerReceiver = gamerReceiver;
	}

	public Date getLenderStatusDate() {
		return lenderStatusDate;
	}

	public void setLenderStatusDate(Date lenderStatusDate) {
		this.lenderStatusDate = lenderStatusDate;
	}

	public Date getGamerStatusDate() {
		return gamerStatusDate;
	}

	public void setGamerStatusDate(Date gamerStatusDate) {
		this.gamerStatusDate = gamerStatusDate;
	}

	public String getTracking() {
		return tracking;
	}

	public void setTracking(String tracking) {
		this.tracking = tracking;
	}

	public String getShippingNote() {
		return shippingNote;
	}

	public void setShippingNote(String shippingNote) {
		this.shippingNote = shippingNote;
	}

	public Date getLenderConfirmDate() {
		return lenderConfirmDate;
	}

	public void setLenderConfirmDate(Date lenderConfirmDate) {
		this.lenderConfirmDate = lenderConfirmDate;
	}

	public Date getGamerConfirmDate() {
		return gamerConfirmDate;
	}

	public void setGamerConfirmDate(Date gamerConfirmDate) {
		this.gamerConfirmDate = gamerConfirmDate;
	}

	public Catalog getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(Catalog shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public Boolean getLenderConfirmed() {
		if (lenderConfirmDate == null) {
			lenderConfirmed = false;
		} else {
			lenderConfirmed = true;
		}
		
		return lenderConfirmed;
	}

	public void setLenderConfirmed(Boolean lenderConfirmed) {
		this.lenderConfirmed = lenderConfirmed;
	}

	public Boolean getGamerConfirmed() {
		if (gamerConfirmDate == null) {
			gamerConfirmed = false;
		} else {
			gamerConfirmed = true;
		}
		
		return gamerConfirmed;
	}

	public void setGamerConfirmed(Boolean gamerConfirmed) {
		this.gamerConfirmed = gamerConfirmed;
	}
}
