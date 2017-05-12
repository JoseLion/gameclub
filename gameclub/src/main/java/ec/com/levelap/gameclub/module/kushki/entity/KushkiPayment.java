package ec.com.levelap.gameclub.module.kushki.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.kushki.entity.BaseKushkiPayment;

@Entity
@Table(schema = Const.SCHEMA, name = "kushki_payment")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class KushkiPayment extends BaseKushkiPayment {

	@JsonBackReference("kushkiSubscription")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "kushki_subscription", foreignKey = @ForeignKey(name = "kushki_subscription_fk"))
	private KushkiSubscription kushkiSubscription;

	public KushkiSubscription getKushkiSubscription() {
		return kushkiSubscription;
	}

	public void setKushkiSubscription(KushkiSubscription kushkiSubscription) {
		this.kushkiSubscription = kushkiSubscription;
	}

}
