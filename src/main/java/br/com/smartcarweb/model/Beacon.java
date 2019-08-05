package br.com.smartcarweb.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.smartcarweb.api.util.persistence.ejb3.BaseEntity;

@Entity
@Table(name = "beacon")
@NamedQueries({
		@javax.persistence.NamedQuery(name = "Beacon.consultarPorUuidBeacon", query = "SELECT b FROM Beacon b WHERE b.uuidBeacon = :uuid") })
public class Beacon extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String uuidBeacon;

	@ManyToOne
	@JoinColumn(name = "idplace")
	@NotNull(message = "Preenchimento obrigatório.")
	private Place place;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "beacon_rule", joinColumns = @JoinColumn(name = "idbeacon"), inverseJoinColumns = @JoinColumn(name = "idrule"))
	@NotNull(message = "Preenchimento obrigatório.")
	private Rule rule;

	public String getUuidBeacon() {
		return uuidBeacon;
	}

	public void setUuidBeacon(String uuidBeacon) {
		this.uuidBeacon = uuidBeacon;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

}