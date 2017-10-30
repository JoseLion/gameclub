package ec.com.levelap.gameclub.module.process.entity;

import javax.persistence.CascadeType;
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
@Table(schema = Const.SCHEMA, name = "status_process", uniqueConstraints = @UniqueConstraint(columnNames = {
		"status_from", "status_to" }, name = "status_process_uk"))
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class StatusProcess extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "status_from", foreignKey = @ForeignKey(name = "status_process_from_fk"))
	private Catalog statusFrom;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "status_to", foreignKey = @ForeignKey(name = "status_process_to_fk"))
	private Catalog statusTo;

	public Catalog getStatusFrom() {
		return statusFrom;
	}

	public void setStatusFrom(Catalog statusFrom) {
		this.statusFrom = statusFrom;
	}

	public Catalog getStatusTo() {
		return statusTo;
	}

	public void setStatusTo(Catalog statusTo) {
		this.statusTo = statusTo;
	}

}
