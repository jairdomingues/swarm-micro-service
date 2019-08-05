package br.com.smartcarweb.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.smartcarweb.api.util.persistence.ejb3.BaseEntity;

@Entity
@Table(name = "cliente")
@NamedQueries({
		@javax.persistence.NamedQuery(name = "Cliente.consultarPorUsuario", query = "SELECT c FROM Cliente c WHERE c.usuario.id = :idUsuario") })
public class Cliente extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String carteira;
	private String endereco;

	@ManyToOne
	@JoinColumn(name = "idusuario")
	@NotNull(message = "BASE_E_6")
	private Usuario usuario;

	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}