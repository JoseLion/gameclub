package ec.com.levelap.gameclub.module.kushki.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.kushki.entity.BaseKushkiSubscription;

@Entity
@Table(schema = Const.SCHEMA, name = "kushki_subscription", uniqueConstraints = { @UniqueConstraint(columnNames = "public_user", name = "kushki_subscription_public_user_uk") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class KushkiSubscription extends BaseKushkiSubscription {

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "public_user", nullable = false, foreignKey = @ForeignKey(name = "public_user_kushki_subscription_fk"))
	private PublicUser publicUser;

	@JsonManagedReference("kushkiSubscription")
	@OneToMany(mappedBy = "kushkiSubscription", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<KushkiPayment> kushkiPayments;

	public PublicUser getPublicUser() {
		return publicUser;
	}

	public void setPublicUser(PublicUser publicUser) {
		this.publicUser = publicUser;
	}

	public List<KushkiPayment> getKushkiPayments() {
		return kushkiPayments;
	}

	public void setKushkiPayments(List<KushkiPayment> kushkiPayments) {
		this.kushkiPayments = kushkiPayments;
	}

}
