package br.com.smartcarweb.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.smartcarweb.api.util.persistence.ejb3.BaseEntity;

@Entity
@Table(name = "place")
public class Place extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	@ManyToOne
	@JoinColumn(name = "idstore")
	@NotNull(message = "Preenchimento obrigatório.")
	private Store store;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

}