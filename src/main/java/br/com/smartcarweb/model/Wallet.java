package br.com.smartcarweb.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.smartcarweb.api.util.persistence.ejb3.BaseEntity;

@Entity
@Table(name = "wallet")
@NamedQueries({
	@javax.persistence.NamedQuery(name = "Wallet.consultarPorCliente", query = "SELECT w FROM Wallet w WHERE w.cliente.usuario.id = :idUsuario") })
public class Wallet extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "idwallet")
	private List<Address> addresses;

	@ManyToOne
	@JoinColumn(name = "idcliente")
	@NotNull(message = "BASE_E_6")
	private Cliente cliente;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}