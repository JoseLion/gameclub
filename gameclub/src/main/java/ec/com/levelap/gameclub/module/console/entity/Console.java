package ec.com.levelap.gameclub.module.console.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.archive.Archive;
import ec.com.levelap.base.entity.BaseEntity;

@Entity
@Table(schema="gameclub", name="console", uniqueConstraints=@UniqueConstraint(columnNames="name", name="name_uk"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Console extends BaseEntity {
	@Column(columnDefinition="VARCHAR", nullable=false)
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="logo", foreignKey=@ForeignKey(name="logo_archive_fk"))
	private Archive logo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Archive getLogo() {
		return logo;
	}

	public void setLogo(Archive logo) {
		this.logo = logo;
	}
}
