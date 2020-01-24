package com.kpi.beans;

import com.kpi.beans.BeanException;

public class Utilisateur {
	
	protected int id;
	protected String nom;
	protected String prenom;
	protected String password;
	protected String username;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) throws BeanException {
		if (username.length() > 150) {
			throw new BeanException("Le nom d'utilisateur est trop long. (150 minimum)");
		} else {
			this.username = username;
		}
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	protected int id_profil;
	protected String libelle_profil;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) throws BeanException {
		if (nom.length() > 150) {
			throw new BeanException("Le nom de l'utilisateur est trop long (150 minimum).");
		}
		else {
			this.nom = nom;
		}
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) throws BeanException {
		if(prenom.length() > 150) {
			throw new BeanException("Le prenom de l'utilisateur est trop long (150 minimum)");
		}
		else {
			this.prenom = prenom;
		}
	}
	public int getId_profil() {
		return id_profil;
	}
	public void setId_profil(int id_profil) {
		this.id_profil = id_profil;
	}
	public String getLibelle_profil() {
		return libelle_profil;
	}
	public void setLibelle_profil(String libelle_profil) throws BeanException {
		if(prenom.length() > 150) {
			throw new BeanException("Le nom du profil est trop long (150 minimum)");
		} else {
			this.libelle_profil = libelle_profil;
		}
	}
}
