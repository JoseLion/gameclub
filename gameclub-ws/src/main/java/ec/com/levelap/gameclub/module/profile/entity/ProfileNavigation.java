package ec.com.levelap.gameclub.module.profile.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.gameclub.module.navigation.entity.Navigation;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="profile_navigation")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProfileNavigation extends BaseEntity {
	@JsonBackReference("Profile")
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="profile", foreignKey=@ForeignKey(name="profile_fk"))
	private Profile profile;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="navigation", foreignKey=@ForeignKey(name="navigation_fk"))
	private Navigation navigation;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Navigation getNavigation() {
		return navigation;
	}

	public void setNavigation(Navigation navigation) {
		this.navigation = navigation;
	}
}
