package br.com.smartcarweb.api.excecoes;

import java.io.Serializable;

import javax.inject.Named;

@Named
public class BaseApi implements Serializable {

	private static final long serialVersionUID = 1L;
	private Detalhe detalhe = new Detalhe();
	private Long idEmpresa;
	
	public Detalhe getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(Detalhe detalhe) {
		this.detalhe = detalhe;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	
}
