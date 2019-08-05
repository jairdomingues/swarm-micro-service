package br.com.smartcarweb.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.smartcarweb.api.util.persistence.ejb3.BaseEntity;

@Entity
@Table(name = "processo")
public class Processo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "Preenchimento obrigatório.")
	private String numeroUnificado;

	@NotNull(message = "Preenchimento obrigatório.")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDistribuicao;

	@NotNull(message = "Preenchimento obrigatório.")
	private Boolean segredoJustica;

	@NotNull(message = "Preenchimento obrigatório.")
	private String pastaFisica;

	@NotNull(message = "Preenchimento obrigatório.")
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "idsituacao")
	@NotNull(message = "Preenchimento obrigatório.")
	private Situacao situacao;

	@ManyToOne
	@JoinColumn(name = "idvinculado")
	private Processo vinculado;

	@ManyToMany
	@JoinTable(name = "processo_responsaveis", joinColumns = {
			@JoinColumn(name = "idprocesso", nullable = true, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "idresponsavel", nullable = true, updatable = false) })
	private List<Responsavel> responsaveis;

	public String getNumeroUnificado() {
		return numeroUnificado;
	}

	public void setNumeroUnificado(String numeroUnificado) {
		this.numeroUnificado = numeroUnificado;
	}

	public Date getDataDistribuicao() {
		return dataDistribuicao;
	}

	public void setDataDistribuicao(Date dataDistribuicao) {
		this.dataDistribuicao = dataDistribuicao;
	}

	public Boolean getSegredoJustica() {
		return segredoJustica;
	}

	public void setSegredoJustica(Boolean segredoJustica) {
		this.segredoJustica = segredoJustica;
	}

	public String getPastaFisica() {
		return pastaFisica;
	}

	public void setPastaFisica(String pastaFisica) {
		this.pastaFisica = pastaFisica;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public List<Responsavel> getResponsaveis() {
		return responsaveis;
	}

	public void setResponsaveis(List<Responsavel> responsaveis) {
		this.responsaveis = responsaveis;
	}

	public Processo getVinculado() {
		return vinculado;
	}

	public void setVinculado(Processo vinculado) {
		this.vinculado = vinculado;
	}

}