package br.com.smartcarweb.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.smartcarweb.api.util.persistence.ejb3.BaseEntity;

@Entity
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@NamedQueries({
		@javax.persistence.NamedQuery(name = "Usuario.consultarPorEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
		@javax.persistence.NamedQuery(name = "Usuario.listarPorPapel", query = "SELECT u FROM Usuario u INNER JOIN u.papeis r WHERE r.nomeDoPapel = :nomeDoPapel") })
public class Usuario extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Size(min = 3, max = 60, message = "BASE_E_11")
	@NotNull(message = "BASE_E_7")
	@Pattern(regexp = "[^0-9]*", message = "BASE_E_12")
	private String nome;

	@NotNull(message = "BASE_E_7")
	private String email;

	private String password;
	private Boolean admin;
	private Boolean ativo;
	private Boolean validado;
	private Boolean bloqueado;
	private String deviceId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUltimoLogin;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "papeis_do_usuario", joinColumns = {
			@JoinColumn(name = "idusuario", nullable = true, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "idpapel", nullable = true, updatable = false) })
	private List<Papel> papeis;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public Date getDataUltimoLogin() {
		return dataUltimoLogin;
	}

	public void setDataUltimoLogin(Date dataUltimoLogin) {
		this.dataUltimoLogin = dataUltimoLogin;
	}

	public List<Papel> getPapeis() {
		return papeis;
	}

	public void setPapeis(List<Papel> papeis) {
		this.papeis = papeis;
	}

	public Boolean getValidado() {
		return validado;
	}

	public void setValidado(Boolean validado) {
		this.validado = validado;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}