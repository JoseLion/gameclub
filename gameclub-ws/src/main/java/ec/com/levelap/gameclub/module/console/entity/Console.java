package ec.com.levelap.gameclub.module.console.entity;

import javax.persistence.CascadeType;
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
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="console", uniqueConstraints=@UniqueConstraint(columnNames="name", name="name_uk"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Console extends BaseEntity {
	@Column(columnDefinition="VARCHAR", nullable=false)
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="white_logo", foreignKey=@ForeignKey(name="white_logo_archive_fk"))
	private Archive whiteLogo;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="black_logo", foreignKey=@ForeignKey(name="black_logo_archive_fk"))
	private Archive blackLogo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Archive getWhiteLogo() {
		return whiteLogo;
	}

	public void setWhiteLogo(Archive whiteLogo) {
		this.whiteLogo = whiteLogo;
	}

	public Archive getBlackLogo() {
		return blackLogo;
	}

	public void setBlackLogo(Archive blackLogo) {
		this.blackLogo = blackLogo;
	}
}
