package ec.com.levelap.gameclub.module.category.entity;

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
@Table(schema=Const.SCHEMA, name="category", uniqueConstraints=@UniqueConstraint(columnNames="name", name="name_uk"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category extends BaseEntity {
	@Column(columnDefinition="VARCHAR", nullable=false)
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="white_vector", foreignKey=@ForeignKey(name="white_vector_archive_fk"))
	private Archive whiteVector;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="black_vector", foreignKey=@ForeignKey(name="black_vector_archive_fk"))
	private Archive blackVector;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Archive getWhiteVector() {
		return whiteVector;
	}

	public void setWhiteVector(Archive whiteVector) {
		this.whiteVector = whiteVector;
	}

	public Archive getBlackVector() {
		return blackVector;
	}

	public void setBlackVector(Archive blackVector) {
		this.blackVector = blackVector;
	}
}
