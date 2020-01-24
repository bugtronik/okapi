package com.kpi.beans;

public class Objectif {
	
	protected int id;
	protected Double valeur;
	protected String date_indicateur;
	protected String date_saisie;
	protected int id_utilisateur;
	protected int id_indicateur;
	protected String version;
	protected String libelle_indicateur;
	public String getLibelle_indicateur() {
		return libelle_indicateur;
	}
	public void setLibelle_indicateur(String libelle_indicateur) {
		this.libelle_indicateur = libelle_indicateur;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getValeur() {
		return valeur;
	}
	public void setValeur(Double valeur) {
		this.valeur = valeur;
	}
	public String getDate_indicateur() {
		return date_indicateur;
	}
	public void setDate_indicateur(String date_indicateur) {
		this.date_indicateur = date_indicateur;
	}
	public String getDate_saisie() {
		return date_saisie;
	}
	public void setDate_saisie(String date_saisie) {
		this.date_saisie = date_saisie;
	}
	public int getId_utilisateur() {
		return id_utilisateur;
	}
	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}
	public int getId_indicateur() {
		return id_indicateur;
	}
	public void setId_indicateur(int id_indicateur) {
		this.id_indicateur = id_indicateur;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) throws BeanException {
		if (version.length() > 150) {
			throw new BeanException ("La longueur de la version est trop longue.");
		}
		else {
			this.version = version;
		}
	}
	
}
