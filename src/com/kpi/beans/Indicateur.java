package com.kpi.beans;

import com.kpi.beans.BeanException;

public class Indicateur {
	
	protected int id;
	protected String libelle;
	protected String status;
	protected String code;
	protected String calcul;
	
	public String getCalcul() {
		return calcul;
	}
	public void setCalcul(String calcul) {
		this.calcul = calcul;
	}
	protected int id_unite;
	protected String libelle_unite;
	
	protected String libelle_frequence;
	protected int id_frequence;
	
	protected String libelle_souscategorie;
	protected int id_sous_categorie;
	
	protected String nom_responsable;
	protected String prenom_responsable;
	protected int id_utilisateur;
	
	protected String libelle_service;
	
	protected String libelle_categorie;
	protected int id_categorie;
	
	public int getId_categorie() {
		return id_categorie;
	}
	public void setId_categorie(int id_categorie) {
		this.id_categorie = id_categorie;
	}
	protected Double valeur_indicateur;
	protected String version;
	
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) throws BeanException {
		if (version.length() > 150) {
			throw new BeanException("Le nom de la version est trop grande.");
		} else {
			this.version = version;
		}
	}
	public Double getValeur_indicateur() {
		return valeur_indicateur;
	}
	public void setValeur_indicateur(Double valeur_indicateur) {
		this.valeur_indicateur = valeur_indicateur;
	}
	public String getLibelle_categorie() {
		return libelle_categorie;
	}
	public void setLibelle_categorie(String libelle_categorie) throws BeanException {
		if (libelle_categorie.length() > 150) {
			throw new BeanException("Le nom de la catégorie est trop grande.");
		} else {
			this.libelle_categorie = libelle_categorie;
		}
	}
	public String getLibelle_unite() {
		return libelle_unite;
	}
	public void setLibelle_unite(String libelle_unite) throws BeanException {
		if (libelle_unite.length() > 150) {
			throw new BeanException("Le libellé de l'unité est trop grand.");
		} else {
			this.libelle_unite = libelle_unite;
		}
	}
	public String getLibelle_frequence() {
		return libelle_frequence;
	}
	public void setLibelle_frequence(String libelle_frequence) throws BeanException {
		this.libelle_frequence = libelle_frequence;
	}
	public String getLibelle_souscategorie() {
		return libelle_souscategorie;
	}
	public void setLibelle_souscategorie(String libelle_souscategorie) throws BeanException {
		this.libelle_souscategorie = libelle_souscategorie;
	}
	public String getNom_responsable() {
		return nom_responsable;
	}
	public void setNom_responsable(String nom_responsable) throws BeanException {
		this.nom_responsable = nom_responsable;
	}
	public String getPrenom_responsable() {
		return prenom_responsable;
	}
	public void setPrenom_responsable(String prenom_responsable) throws BeanException {
		this.prenom_responsable = prenom_responsable;
	}
	public String getLibelle_service() {
		return libelle_service;
	}
	public void setLibelle_service(String libelle_service) throws BeanException {
		this.libelle_service = libelle_service;
	}
	protected int id_service;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) throws BeanException {
		if (libelle.length() > 150) {
			throw new BeanException("Le libelle de l'indicateur est trop long.");
		} else {
			this.libelle = libelle;
		}
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getId_unite() {
		return id_unite;
	}
	public void setId_unite(int id_unite) {
		this.id_unite = id_unite;
	}
	public int getId_frequence() {
		return id_frequence;
	}
	public void setId_frequence(int id_frequence) {
		this.id_frequence = id_frequence;
	}
	public int getId_sous_categorie() {
		return id_sous_categorie;
	}
	public void setId_sous_categorie(int id_sous_categorie) {
		this.id_sous_categorie = id_sous_categorie;
	}
	public int getId_utilisateur() {
		return id_utilisateur;
	}
	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}
	public int getId_service() {
		return id_service;
	}
	public void setId_service(int id_service) {
		this.id_service = id_service;
	}
}
