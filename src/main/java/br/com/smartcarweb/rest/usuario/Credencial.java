package br.com.smartcarweb.rest.usuario;

import java.io.Serializable;

public class Credencial implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String fusohorario;
	private String token_fcm;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFusohorario() {
		return fusohorario;
	}

	public void setFusohorario(String fusohorario) {
		this.fusohorario = fusohorario;
	}

	public String getToken_fcm() {
		return token_fcm;
	}

	public void setToken_fcm(String token_fcm) {
		this.token_fcm = token_fcm;
	}

}