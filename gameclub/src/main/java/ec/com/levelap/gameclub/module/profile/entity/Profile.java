package ec.com.levelap.gameclub.module.profile.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ec.com.levelap.base.entity.BaseEntity;

@Entity
@Table(schema="gameclub", name="profile", uniqueConstraints=@UniqueConstraint(columnNames="name", name="name_uk"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Profile extends BaseEntity {
	@Column(columnDefinition="VARCHAR")
	private String name;
	
	@Column(columnDefinition="VARCHAR")
	private String description;
	
	@JsonManagedReference("Profile")
	@OneToMany(mappedBy="profile", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<ProfileNavigation> crossNavigation = new ArrayList<>();
	
	@Column(columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean wildcard = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ProfileNavigation> getCrossNavigation() {
		return crossNavigation;
	}

	public void setCrossNavigation(List<ProfileNavigation> crossNavigation) {
		this.crossNavigation = crossNavigation;
	}

	public Boolean getWildcard() {
		return wildcard;
	}

	public void setWildcard(Boolean wildcard) {
		this.wildcard = wildcard;
	}
}
