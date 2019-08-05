package br.com.smartcarweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.smartcarweb.api.util.persistence.ejb3.BaseEntity;

@Entity
@Table(name = "situacao")
public class Situacao extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static enum SituacaoProcesso {
		EmAndamento("Em Andamento"), Desmembrado("Desmembrado"), EmRecurso("EmRecurso"), Finalizado("Finalizado"),
		Arquivado("Arquivado");

		private final String value;

		private SituacaoProcesso(final String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	@NotNull(message = "Preenchimento obrigatório.")
	@Enumerated(EnumType.STRING)
	@Column(name = "situacaoprocesso")
	private SituacaoProcesso situacao;

	@NotNull(message = "Preenchimento obrigatório.")
	private Boolean finalizado;

	public SituacaoProcesso getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoProcesso situacao) {
		this.situacao = situacao;
	}

	public Boolean getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(Boolean finalizado) {
		this.finalizado = finalizado;
	}

}