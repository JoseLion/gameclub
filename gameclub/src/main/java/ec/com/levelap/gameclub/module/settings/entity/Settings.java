package ec.com.levelap.gameclub.module.settings.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;

@Entity
@Table(schema="commons", name="settings", uniqueConstraints={@UniqueConstraint(columnNames={"code"}, name="code_uk")})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Settings extends BaseEntity {
	@Column(nullable=false, columnDefinition="VARCHAR")
	private String code;
	
	@Column(nullable=false, columnDefinition="VARCHAR")
	private String name;
	
	@Column(nullable=false, columnDefinition="VARCHAR")
	private String value;
	
	@Column(nullable=false, columnDefinition="VARCHAR")
	private String type;
	
	@Column(nullable=false, columnDefinition="VARCHAR")
	private String category;
	
	@Column(name="is_editable", nullable=true, columnDefinition="BOOLEAN DEFAULT TRUE")
	private Boolean isEditable = null;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}
}
