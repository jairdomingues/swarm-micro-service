package br.com.smartcarweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import br.com.smartcarweb.api.util.persistence.ejb3.BaseEntity;

@Entity
@Table(name = "responsavel")
@NamedQueries({
	@javax.persistence.NamedQuery(name = "Responsavel.consultarPorCpf", query = "SELECT r FROM Responsavel r WHERE r.cpf = :cpf") 
})
public class Responsavel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "Preenchimento obrigatório.")
	@Column(length = 13)
	private String nome;

	@NotNull(message = "Preenchimento obrigatório.")
	@Column(unique = true)
	private String cpf;

	@Email(message = "Precisa ser um email válido")
	@NotNull(message = "Preenchimento obrigatório.")
	@Column(length = 40)
	private String email;

	private String foto;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

}