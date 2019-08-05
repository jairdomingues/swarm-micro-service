package br.com.smartcarweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.smartcarweb.api.util.persistence.ejb3.BaseEntity;

@Entity
@Table(name = "papel", uniqueConstraints = @UniqueConstraint(columnNames = "nomedopapel"))
@NamedQueries({
		@javax.persistence.NamedQuery(name = "Papel.consultarPorPapel", query = "SELECT r FROM Papel r where r.nomeDoPapel = :nomeDoPapel"), })
public class Papel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static enum NomeDoPapel {
		Admin("Admin"), Usuario("Usuario"), Cliente("Cliente");

		private final String value;

		private NomeDoPapel(final String value) {
			this.value = value;
		}

		
		public String getValue() {
			return value;
		}

	}

	@Enumerated(EnumType.STRING)
	@Column(name = "nomedopapel", nullable = false)
	private NomeDoPapel nomeDoPapel;

	private String descricao;
	private Boolean ativo;

	public NomeDoPapel getNomeDoPapel() {
		return nomeDoPapel;
	}

	public void setNomeDoPapel(NomeDoPapel nomeDoPapel) {
		this.nomeDoPapel = nomeDoPapel;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}