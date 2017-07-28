package ec.com.levelap.gameclub.module.user.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.commons.location.Location;
import ec.com.levelap.gameclub.module.avatar.entity.Avatar;
import ec.com.levelap.gameclub.module.kushki.entity.KushkiSubscription;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema = Const.SCHEMA, name = "public_user", uniqueConstraints = @UniqueConstraint(columnNames = "username", name = "public_username_uk"))
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PublicUser extends BaseEntity {
	@Column(columnDefinition="VARCHAR", nullable=false)
	private String username;

	@Column(columnDefinition="VARCHAR")
	private String password;

	@Column(columnDefinition="VARCHAR", nullable=false)
	private String name;

	@Column(name = "last_name", columnDefinition="VARCHAR", nullable=false)
	private String lastName;

	@Column(name = "has_temp_password", columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean hasTempPassword = false;

	@Column(name = "is_facebook_user", columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean isFacebookUser = false;

	@Column(columnDefinition = "INTEGER DEFAULT 0")
	private Integer coins = 0;

	@Column(columnDefinition = "VARCHAR")
	private String token;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "avatar", foreignKey = @ForeignKey(name = "avatar_archive_fk"))
	private Avatar avatar;

	@Column(name = "last_connection")
	private Date lastConnection;

	@Column(columnDefinition = "VARCHAR")
	private String document;

	@Column(name = "birth_date")
	private Date birthDate;

	@Column(columnDefinition = "VARCHAR")
	private String profession;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "location", foreignKey = @ForeignKey(name = "location_fk"))
	private Location location;

	@JsonIgnore
	@OneToMany(mappedBy = "publicUser", fetch = FetchType.LAZY)
	private List<PublicUserGame> games = new ArrayList<>();

	@Column(name = "facebook_token", columnDefinition = "VARCHAR")
	private String facebookToken;

	@Column(name = "facebook_name", columnDefinition = "VARCHAR")
	private String facebookName;

	@Column(name = "billing_address", columnDefinition = "VARCHAR")
	private String billingAddress;

	@Column(name = "contact_phone", columnDefinition = "VARCHAR")
	private String contactPhone;
	
	@Column(name="is_subscriber", columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean isSubscriber = false;
	
	@JsonManagedReference("publicUserKushkiSubscription")
	@OneToMany(mappedBy="publicUser", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<KushkiSubscription> paymentMethods = new ArrayList<>();
	
	@Column(columnDefinition="INTEGER DEFAULT 0")
	private Integer rating = 0;
	
	@Column(name="games_limit", columnDefinition="INTEGER DEFAULT 5")
	private Integer gamesLimit = 5;

	@Transient
	private Integer numberOfGames;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Boolean getHasTempPassword() {
		return hasTempPassword;
	}

	public void setHasTempPassword(Boolean hasTempPassword) {
		this.hasTempPassword = hasTempPassword;
	}

	public Boolean getIsFacebookUser() {
		return isFacebookUser;
	}

	public void setIsFacebookUser(Boolean isFacebookUser) {
		this.isFacebookUser = isFacebookUser;
	}

	public Integer getCoins() {
		return coins;
	}

	public void setCoins(Integer coins) {
		this.coins = coins;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	public Date getLastConnection() {
		return lastConnection;
	}

	public void setLastConnection(Date lastConnection) {
		this.lastConnection = lastConnection;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<PublicUserGame> getGames() {
		return games;
	}

	public void setGames(List<PublicUserGame> games) {
		this.games = games;
	}

	public String getFacebookToken() {
		return facebookToken;
	}

	public void setFacebookToken(String facebookToken) {
		this.facebookToken = facebookToken;
	}

	public String getFacebookName() {
		return facebookName;
	}

	public void setFacebookName(String facebookName) {
		this.facebookName = facebookName;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Boolean getIsSubscriber() {
		return isSubscriber;
	}

	public void setIsSubscriber(Boolean isSubscriber) {
		this.isSubscriber = isSubscriber;
	}

	public List<KushkiSubscription> getPaymentMethods() {
		return paymentMethods;
	}

	public void setPaymentMethods(List<KushkiSubscription> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	
	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getGamesLimit() {
		return gamesLimit;
	}

	public void setGamesLimit(Integer gamesLimit) {
		this.gamesLimit = gamesLimit;
	}

	public Integer getNumberOfGames() {
		return this.games.size();
	}

	public void setNumberOfGames(Integer numberOfGames) {
		this.numberOfGames = numberOfGames;
	}
}
