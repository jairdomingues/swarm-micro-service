package br.com.smartcarweb.service.usuario;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("private")		
	private String privateAddress;

	@JsonProperty("public")		
	private String publicAddress;
	private String address;
	private String wif;

	public String getPrivateAddress() {
		return privateAddress;
	}

	public void setPrivateAddress(String privateAddress) {
		this.privateAddress = privateAddress;
	}

	public String getPublicAddress() {
		return publicAddress;
	}

	public void setPublicAddress(String publicAddress) {
		this.publicAddress = publicAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWif() {
		return wif;
	}

	public void setWif(String wif) {
		this.wif = wif;
	}

}
