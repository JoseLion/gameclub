package ec.com.levelap.gameclub.module.navigation.entity;

import java.util.ArrayList;
import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ec.com.levelap.base.entity.BaseEntity;

@Entity
@Table(schema="gameclub", name="navigation", uniqueConstraints=@UniqueConstraint(columnNames="route", name="route_uk"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Navigation extends BaseEntity {
	@Column(columnDefinition="VARCHAR")
	private String name;
	
	@Column(columnDefinition="VARCHAR")
	private String route;
	
	@Column(name="is_abstract", columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean isAbstract = false;
	
	@Column(columnDefinition="VARCHAR")
	private String icon;
	
	@Column(columnDefinition="INTEGER DEFAULT 0")
	private Integer level = 0;
	
	@JsonBackReference("Recursive")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent", foreignKey=@ForeignKey(name="navigation_parent_fk"))
	private Navigation parent;
	
	@JsonManagedReference("Recursive")
	@OneToMany(mappedBy="parent", fetch=FetchType.LAZY)
	private List<Navigation> children = new ArrayList<>();
	
	@Transient
	private Long parentId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Boolean getIsAbstract() {
		return isAbstract;
	}

	public void setIsAbstract(Boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Navigation getParent() {
		return parent;
	}

	public void setParent(Navigation parent) {
		this.parent = parent;
	}

	public List<Navigation> getChildren() {
		return children;
	}

	public void setChildren(List<Navigation> children) {
		this.children = children;
	}

	public Long getParentId() {
		if (parent != null) {
			parentId = parent.getId();
		}
		
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
