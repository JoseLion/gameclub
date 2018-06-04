package ec.com.levelap.gameclub.module.faq.entity;

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

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="faq", uniqueConstraints=@UniqueConstraint(columnNames="question", name="question_uk"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Faq extends BaseEntity {
	@Column(columnDefinition="VARCHAR", nullable=true)
	private String question;
	
	@Column(columnDefinition="VARCHAR", nullable=false)
	private String answer;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name="category", foreignKey=@ForeignKey(name="category_catalog_fk"))
	private Catalog category;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Catalog getCategory() {
		return category;
	}

	public void setCategory(Catalog category) {
		this.category = category;
	}
}
