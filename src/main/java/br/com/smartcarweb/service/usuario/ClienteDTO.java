package br.com.smartcarweb.service.usuario;

import br.com.smartcarweb.api.excecoes.BaseDTO;

public class ClienteDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	private String carteira;
	private String endereco;
	private UsuarioDTO usuario;

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

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

}