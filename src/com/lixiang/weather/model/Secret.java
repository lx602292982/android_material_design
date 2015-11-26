package com.lixiang.weather.model;

public class Secret {
	private String idfs;
	private String secret;
	
	public Secret(String idfs, String secret) {
		super();
		this.idfs = idfs;
		this.secret = secret;
	}
	public String getIdfs() {
		return idfs;
	}
	public void setIdfs(String idfs) {
		this.idfs = idfs;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	
}
