package br.com.smartcarweb.service.usuario;

import br.com.smartcarweb.api.excecoes.BaseDTO;
import br.com.smartcarweb.model.Papel.NomeDoPapel;

public class PapelDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

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