/**
 * 
 */
package ec.com.levelap.gameclub.module.settings.entity;


import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.LevelapBase;
import ec.com.levelap.base.LevelapBaseContextHolder;

/**
 * @author Levelap
 *
 */
@Entity
@Table(schema="commons", name="settings", uniqueConstraints={@UniqueConstraint(columnNames={"code"}, name="code_uk")})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Settings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name="id", nullable=false)
	protected Long id;
	
	public Settings() {
		
	}

	@Column(nullable=false, columnDefinition="BOOLEAN DEFAULT TRUE")
	protected Boolean status = true;

	@Column(name="creation_date", nullable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	protected Date creationDate = new Date();

	@Column(name="creation_user", nullable=false, columnDefinition="BIGINT DEFAULT 0")
	protected Long creationUser = 0L;

	@Column(name="update_date")
	protected Date updateDate;

	@Column(name="update_user")
	protected Long updateUser;
	
	@Column(nullable=false, columnDefinition="VARCHAR")
	private String code;
	
	@Column(nullable=false, columnDefinition="VARCHAR")
	private String name;
	
	@Column(nullable=false, columnDefinition="VARCHAR")
	private String value;
	
	/** The type. */
	@Column(nullable=false, columnDefinition="VARCHAR")
	private String type;
	
	/** The type. */
	@Column(nullable=false, columnDefinition="VARCHAR")
	private String category;
	
	@Column(name="is_editable", nullable=true, columnDefinition="BOOLEAN DEFAULT TRUE")
	protected Boolean isEditable = null;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the creationUser
	 */
	public Long getCreationUser() {
		return creationUser;
	}

	/**
	 * @param creationUser the creationUser to set
	 */
	public void setCreationUser(Long creationUser) {
		this.creationUser = creationUser;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the updateUser
	 */
	public Long getUpdateUser() {
		return updateUser;
	}

	/**
	 * @param updateUser the updateUser to set
	 */
	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * @return the isEditable
	 */
	public Boolean getIsEditable() {
		return isEditable;
	}

	/**
	 * @param isEditable the isEditable to set
	 */
	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	@PrePersist
	protected void onCreate() {
		LevelapBase levelap = LevelapBaseContextHolder.getContext().getBean(LevelapBase.class);
		try {
			this.setCreationUser(levelap.getConfig().getCurrentUser());
		} catch (Exception ex) {
			this.setCreationUser(-1L);
		}
	}

	@PreUpdate
	protected void onUpdate() {
		LevelapBase levelap = LevelapBaseContextHolder.getContext().getBean(LevelapBase.class);
		this.setUpdateDate(new Date());
		try {
			this.setUpdateUser(levelap.getConfig().getCurrentUser());
		} catch (Exception ex) {
			this.setUpdateUser(-1L);
		}
	}

}
