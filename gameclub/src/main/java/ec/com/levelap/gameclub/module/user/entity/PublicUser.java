package ec.com.levelap.gameclub.module.user.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.commons.archive.Archive;
import ec.com.levelap.commons.location.Location;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="public_user", uniqueConstraints=@UniqueConstraint(columnNames="username", name="public_username_uk"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PublicUser extends BaseEntity {
	@Column(columnDefinition="VARCHAR", nullable=false)
	private String username;
	
	@Column(columnDefinition="VARCHAR", nullable=false)
	private String password;
	
	@Column(columnDefinition="VARCHAR", nullable=false)
	private String name;
	
	@Column(name="last_name", columnDefinition="VARCHAR", nullable=false)
	private String lastName;
	
	@Column(name="has_temp_password", columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean hasTempPassword = false;
	
	@Column(name="is_facebook_user", columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean isFacebookUser = false;
	
	@Column(columnDefinition="INTEGER DEFAULT 0")
	private Integer coins = 0;
	
	@Column(columnDefinition="VARCHAR")
	private String token;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="avatar", foreignKey=@ForeignKey(name="avatar_archive_fk"))
	private Archive avatar;
	
	@Column(name="last_connection")
	private Date lastConnection;
	
	@Column(columnDefinition="VARCHAR")
	private String document;
	
	@Column(name="birth_date")
	private Date birthDate;
	
	@Column(columnDefinition="VARCHAR")
	private String profession;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="location", foreignKey=@ForeignKey(name="location_fk"))
	private Location location;
	
	@Column(name="first_address", columnDefinition="VARCHAR")
	private String firstAddress;
	
	@Column(name="first_receiver", columnDefinition="VARCHAR")
	private String firstReceiver;
	
	@Column(name="first_morning_start_time", columnDefinition="VARCHAR")
	private String firstMorningStartTime;
	
	@Column(name="first_morning_end_time", columnDefinition="VARCHAR")
	private String firstMorningEndTime;
	
	@Column(name="first_noon_start_time", columnDefinition="VARCHAR")
	private String firstNoonStartTime;
	
	@Column(name="first_noon_end_time", columnDefinition="VARCHAR")
	private String firstNoonEndTime;
	
	@Column(name="second_address", columnDefinition="VARCHAR")
	private String secondAddress;
	
	@Column(name="second_receiver", columnDefinition="VARCHAR")
	private String secondReceiver;
	
	@Column(name="second_morning_start_time", columnDefinition="VARCHAR")
	private String secondMorningStartTime;
	
	@Column(name="second_morning_end_time", columnDefinition="VARCHAR")
	private String secondMorningEndTime;
	
	@Column(name="second_noon_start_time", columnDefinition="VARCHAR")
	private String secondNoonStartTime;
	
	@Column(name="second_noon_end_time", columnDefinition="VARCHAR")
	private String secondNoonEndTime;

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

	public Archive getAvatar() {
		return avatar;
	}

	public void setAvatar(Archive avatar) {
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

	public String getFirstAddress() {
		return firstAddress;
	}

	public void setFirstAddress(String firstAddress) {
		this.firstAddress = firstAddress;
	}

	public String getFirstReceiver() {
		return firstReceiver;
	}

	public void setFirstReceiver(String firstReceiver) {
		this.firstReceiver = firstReceiver;
	}

	public String getFirstMorningStartTime() {
		return firstMorningStartTime;
	}

	public void setFirstMorningStartTime(String firstMorningStartTime) {
		this.firstMorningStartTime = firstMorningStartTime;
	}

	public String getFirstMorningEndTime() {
		return firstMorningEndTime;
	}

	public void setFirstMorningEndTime(String firstMorningEndTime) {
		this.firstMorningEndTime = firstMorningEndTime;
	}

	public String getFirstNoonStartTime() {
		return firstNoonStartTime;
	}

	public void setFirstNoonStartTime(String firstNoonStartTime) {
		this.firstNoonStartTime = firstNoonStartTime;
	}

	public String getFirstNoonEndTime() {
		return firstNoonEndTime;
	}

	public void setFirstNoonEndTime(String firstNoonEndTime) {
		this.firstNoonEndTime = firstNoonEndTime;
	}

	public String getSecondAddress() {
		return secondAddress;
	}

	public void setSecondAddress(String secondAddress) {
		this.secondAddress = secondAddress;
	}

	public String getSecondReceiver() {
		return secondReceiver;
	}

	public void setSecondReceiver(String secondReceiver) {
		this.secondReceiver = secondReceiver;
	}

	public String getSecondMorningStartTime() {
		return secondMorningStartTime;
	}

	public void setSecondMorningStartTime(String secondMorningStartTime) {
		this.secondMorningStartTime = secondMorningStartTime;
	}

	public String getSecondMorningEndTime() {
		return secondMorningEndTime;
	}

	public void setSecondMorningEndTime(String secondMorningEndTime) {
		this.secondMorningEndTime = secondMorningEndTime;
	}

	public String getSecondNoonStartTime() {
		return secondNoonStartTime;
	}

	public void setSecondNoonStartTime(String secondNoonStartTime) {
		this.secondNoonStartTime = secondNoonStartTime;
	}

	public String getSecondNoonEndTime() {
		return secondNoonEndTime;
	}

	public void setSecondNoonEndTime(String secondNoonEndTime) {
		this.secondNoonEndTime = secondNoonEndTime;
	}
}
