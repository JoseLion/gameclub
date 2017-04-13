package ec.com.levelap.gameclub.module.mail.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;

@Entity
@Table(schema="commons", name="mail", uniqueConstraints={@UniqueConstraint(columnNames={"acronym"}, name="mail_acronym_uk")})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Mail extends BaseEntity {
	@Column(columnDefinition="VARCHAR")
	private String acronym;

	@Column(nullable=false, columnDefinition="VARCHAR")
	private String subject;

	@Column(nullable=false, columnDefinition="VARCHAR")
	private String content;

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
