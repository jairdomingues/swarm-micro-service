package br.com.smartcarweb.api.excecoes;

public class Detalhe {

	protected String usuarioInclusao;
	protected String usuarioAlteracao;
	protected String dataHoraInclusao;
	protected String dataHoraAlteracao;
	protected Long versao;
	protected String uuid;
	protected Long id;

	public String getUsuarioInclusao() {
		return usuarioInclusao;
	}

	public void setUsuarioInclusao(String usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}

	public String getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(String usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDataHoraInclusao() {
		return dataHoraInclusao;
	}

	public void setDataHoraInclusao(String dataHoraInclusao) {
		this.dataHoraInclusao = dataHoraInclusao;
	}

	public String getDataHoraAlteracao() {
		return dataHoraAlteracao;
	}

	public void setDataHoraAlteracao(String dataHoraAlteracao) {
		this.dataHoraAlteracao = dataHoraAlteracao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
