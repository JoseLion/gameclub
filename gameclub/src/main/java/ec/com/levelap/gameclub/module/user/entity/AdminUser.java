package ec.com.levelap.gameclub.module.user.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.gameclub.module.profile.entity.Profile;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="admin_user", uniqueConstraints=@UniqueConstraint(columnNames="username", name="admin_username_uk"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AdminUser extends BaseEntity {
	@Column(columnDefinition="VARCHAR", nullable=false)
	private String username;
	
	@Column(columnDefinition="VARCHAR", nullable=false)
	private String password;
	
	@Column(name="has_temp_password", columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean hasTempPassword = false;
	
	@Column(name="full_name", columnDefinition="VARCHAR", nullable=false)
	private String fullName;
	
	@Column(name="cell_phone", columnDefinition="VARCHAR")
	private String cellPhone;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="profile", foreignKey=@ForeignKey(name="profile_fk"))
	private Profile profile;
	
	@Transient
	private String profileName;

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

	public Boolean getHasTempPassword() {
		return hasTempPassword;
	}

	public void setHasTempPassword(Boolean hasTempPassword) {
		this.hasTempPassword = hasTempPassword;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getProfileName() {
		if (profile != null) {
			profileName = profile.getName();
		}
		
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
}
