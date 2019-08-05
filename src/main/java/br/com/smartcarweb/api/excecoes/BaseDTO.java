package br.com.smartcarweb.api.excecoes;

import java.io.Serializable;

import javax.inject.Named;

@Named
public class BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Detalhe detalhe = new Detalhe();

	public Detalhe getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(Detalhe detalhe) {
		this.detalhe = detalhe;
	}


}
