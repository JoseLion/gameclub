package ec.com.levelap.gameclub.module.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
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
}
