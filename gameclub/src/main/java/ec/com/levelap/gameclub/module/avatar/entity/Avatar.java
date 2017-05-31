package ec.com.levelap.gameclub.module.avatar.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.base.entity.BaseEntity;
import ec.com.levelap.commons.archive.Archive;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema = Const.SCHEMA, name = "avatar")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Avatar extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "image", foreignKey = @ForeignKey(name = "avatar_image_fk"))
	private Archive image;

	public Archive getImage() {
		return image;
	}

	public void setImage(Archive image) {
		this.image = image;
	}

}
