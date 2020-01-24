package com.kpi.beans;

public class Connexion {
	
	protected int id;
	protected String username;
	protected String password;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) throws BeanException {
		if (username.length() > 255) {
			throw new BeanException("Le nom d'utilisateur ne doit pas dépasser 255 caractères.");
		} else {
			this.username = username;
		}
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) throws BeanException {
		if (password.length() > 255) {
			throw new BeanException("Le mot de passe ne doit pas dépasser 255 caractères.");
		} else {
			this.password = password;
		}
	}
	
	

}
