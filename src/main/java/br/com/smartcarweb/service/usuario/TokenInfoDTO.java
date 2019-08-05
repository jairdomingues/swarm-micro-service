package br.com.smartcarweb.service.usuario;

import java.io.Serializable;
import java.util.List;

public class TokenInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String token;
	private String name;
	private List<String> addresses;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}

}
