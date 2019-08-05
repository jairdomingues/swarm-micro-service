package br.com.smartcarweb.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.smartcarweb.api.util.persistence.ejb3.BaseEntity;

@Entity
@Table(name = "store")
public class Store extends BaseEntity {

	private static final long serialVersionUID = 1L;

}	