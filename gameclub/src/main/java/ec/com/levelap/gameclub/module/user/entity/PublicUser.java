package ec.com.levelap.gameclub.module.user.entity;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
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

import org.apache.commons.io.FileUtils;
import org.hibernate.annotations.Where;
import org.postgresql.geometric.PGpoint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.commons.location.Location;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.application.ApplicationContextHolder;
import ec.com.levelap.gameclub.module.avatar.entity.Avatar;
import ec.com.levelap.gameclub.module.kushki.entity.KushkiSubscription;
import ec.com.levelap.gameclub.module.message.entity.Message;
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
	
	@JsonIgnore
	@Column
	private byte[] privateKey;
	
	@JsonIgnore
	@Column
	private byte[] balance;

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
	@OneToMany(mappedBy="publicUser", fetch = FetchType.LAZY)
	private List<PublicUserGame> games = new ArrayList<>();

	@Column(name = "facebook_token", columnDefinition = "VARCHAR")
	private String facebookToken;

	@Column(name = "facebook_name", columnDefinition = "VARCHAR")
	private String facebookName;

	@Column(name = "billing_address", columnDefinition = "VARCHAR")
	private String billingAddress;

	@Column(name = "contact_phone", columnDefinition = "VARCHAR")
	private String contactPhone;
	
	@Column
	private PGpoint geolocation;
	
	@Column(columnDefinition="VARCHAR")
	private String receiver;
	
	@Column(name="is_subscriber", columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean isSubscriber = false;
	
	@Where(clause="status")
	@JsonManagedReference("publicUserKushkiSubscription")
	@OneToMany(mappedBy="publicUser", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<KushkiSubscription> paymentMethods = new ArrayList<>();
	
	@Column(columnDefinition="DECIMAL(5, 2) DEFAULT 0.0")
	private Double rating = 0.0;
	
	@Column(name="games_limit", columnDefinition="INTEGER DEFAULT 5")
	private Integer gamesLimit = 5;
	
	@JsonIgnore
	@OneToMany(mappedBy="owner", fetch=FetchType.LAZY)
	private List<Message> messages = new ArrayList<>();
	
	@Column(name="is_ready", columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean isReady = false;
	
	@Column(name="document_ruc", columnDefinition = "VARCHAR")
	private String documentRuc;
	
	@Column(name="name_ruc", columnDefinition = "VARCHAR")
	private String nameRuc;
	
	@Column(name="address_ruc", columnDefinition = "VARCHAR")
	private String addressRuc;
	
	@Column(name="phone_ruc", columnDefinition = "VARCHAR")
	private String phoneRuc;
	
	@Column(name="has_ruc", columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean hasRuc = false;
	
	@Column(name="url_token", columnDefinition="VARCHAR")
	private String urlToken;

	@Transient
	private Integer numberOfGames;
	
	@Transient
	private Integer unreadMessages = 0;
	
	@Transient
	private Double shownBalance;

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

	public byte[] getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(byte[] privateKey) {
		this.privateKey = privateKey;
	}

	public byte[] getBalance() {
		return balance;
	}

	public void setBalance(byte[] balance) {
		this.balance = balance;
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

	public PGpoint getGeolocation() {
		return geolocation;
	}

	public void setGeolocation(PGpoint geolocation) {
		this.geolocation = geolocation;
	}

	public String getReceiver() {
		if (receiver == null) {
			return name + " " + lastName;
		} else {
			return receiver;
		}
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
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
	
	public Double getRating() {
		return rating * 100.0 / 5.0;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Integer getGamesLimit() {
		return gamesLimit;
	}

	public void setGamesLimit(Integer gamesLimit) {
		this.gamesLimit = gamesLimit;
	}
	
	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Boolean getIsReady() {
		return isReady;
	}

	public void setIsReady(Boolean isReady) {
		this.isReady = isReady;
	}
	
	public String getDocumentRuc() {
		return documentRuc;
	}

	public void setDocumentRuc(String documentRuc) {
		this.documentRuc = documentRuc;
	}

	public String getNameRuc() {
		return nameRuc;
	}

	public void setNameRuc(String nameRuc) {
		this.nameRuc = nameRuc;
	}

	public String getAddressRuc() {
		return addressRuc;
	}

	public void setAddressRuc(String addressRuc) {
		this.addressRuc = addressRuc;
	}

	public String getPhoneRuc() {
		return phoneRuc;
	}

	public void setPhoneRuc(String phoneRuc) {
		this.phoneRuc = phoneRuc;
	}

	public Boolean getHasRuc() {
		return hasRuc;
	}

	public void setHasRuc(Boolean hasRuc) {
		this.hasRuc = hasRuc;
	}

	public String getUrlToken() {
		return urlToken;
	}

	public void setUrlToken(String urlToken) {
		this.urlToken = urlToken;
	}

	public Integer getNumberOfGames() {
		return this.games.size();
	}

	public void setNumberOfGames(Integer numberOfGames) {
		this.numberOfGames = numberOfGames;
	}

	public Integer getUnreadMessages() {
		unreadMessages = 0;
		
		for (Message message : messages) {
			if (!message.getRead()) {
				unreadMessages++;
			}
		}
		
		return unreadMessages;
	}

	public void setUnreadMessages(Integer unreadMessages) {
		this.unreadMessages = unreadMessages;
	}

	public Double getShownBalance() throws IOException, GeneralSecurityException {
		if (privateKey != null && balance.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext().getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, privateKey);
			String decypted = cryptoService.decrypt(balance, key);
			shownBalance = Double.parseDouble(decypted);
		}
		
		return shownBalance;
	}

	public void setShownBalance(Double shownBalance) {
		this.shownBalance = shownBalance;
	}
}
