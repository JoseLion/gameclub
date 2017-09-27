package ec.com.levelap.gameclub.module.kushki.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.kushki.entity.BaseKushkiSubscription;

@Entity
@Table(schema=Const.SCHEMA, name="kushki_subscription")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class KushkiSubscription extends BaseKushkiSubscription {
	@JsonBackReference("publicUserKushkiSubscription")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
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
