package com.kpi.beans;

import com.kpi.beans.BeanException;

public class Saisie {
	
	protected int id;
	protected double valeur;
	protected String version;
	protected String date_indicateur;
	protected String date_saisie;
	protected int id_utilisateur;
	protected int id_indicateur;
	protected String libelle_indicateur;
	protected String unite_indicateur;
	protected String frequence_indicateur;
	protected int id_categorie;
	protected String libelle_categorie;
	protected int id_sous_categorie;
	protected String libelle_sous_categorie;
	
	
	public int getId_categorie() {
		return id_categorie;
	}
	public void setId_categorie(int id_categorie) {
		this.id_categorie = id_categorie;
	}
	public String getLibelle_categorie()  {
		return libelle_categorie;
	}
	public void setLibelle_categorie(String libelle_categorie) throws BeanException {
		if (libelle_categorie.length() > 150) {
			throw new BeanException ("Le libelle de l'indicateur est trop long. (150 minimum)");
		}
		else {
			this.libelle_categorie = libelle_categorie;
		}
	}
	public int getId_sous_categorie() {
		return id_sous_categorie;
	}
	public void setId_sous_categorie(int id_sous_categorie) {
		this.id_sous_categorie = id_sous_categorie;
	}
	public String getLibelle_sous_categorie() {
		return libelle_sous_categorie;
	}
	public void setLibelle_sous_categorie(String libelle_sous_categorie) throws BeanException {
		if (libelle_sous_categorie.length() > 150) {
			throw new BeanException ("Le libelle de l'indicateur est trop long. (150 minimum)");
		}
		else {
			this.libelle_sous_categorie = libelle_sous_categorie;
		}
	}
	public String getLibelle_indicateur() {
		return libelle_indicateur;
	}
	public void setLibelle_indicateur(String libelle_indicateur) throws BeanException {
		if (libelle_indicateur.length() > 150) {
			throw new BeanException ("Le libelle de l'indicateur est trop long. (150 minimum)");
		}
		else {
			this.libelle_indicateur = libelle_indicateur;
		}
	}
	public String getUnite_indicateur() {
		return unite_indicateur;
	}
	public void setUnite_indicateur(String unite_indicateur) throws BeanException {
		if (unite_indicateur.length() > 150) {
			throw new BeanException ("L'unité de l'indicateur est trop long. (150 minimum)");
		}
		else {
			this.unite_indicateur = unite_indicateur;
		}
	}
	public String getFrequence_indicateur() {
		return frequence_indicateur;
	}
	public void setFrequence_indicateur(String frequence_indicateur) throws BeanException {
		this.frequence_indicateur = frequence_indicateur;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getValeur() {
		return valeur;
	}
	public void setValeur(double valeur) {
		this.valeur = valeur;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) throws BeanException {
		if (version.length() > 150) {
			throw new BeanException("Le nom de version est trop long. (150 minimum)");
		} else {
			this.version = version;
		}
	}
	public String getDate_indicateur() {
		return date_indicateur;
	}
	public void setDate_indicateur(String date_indicateur) throws BeanException {
		if (date_indicateur.length() > 150) {
			throw new BeanException ("La date est trop longue.");
		}
		else {
			this.date_indicateur = date_indicateur;
		}
	}
	public String getDate_saisie() {
		return date_saisie;
	}
	public void setDate_saisie(String date_saisie) throws BeanException {
		if (date_saisie.length() > 150) {
			throw new BeanException ("La date est trop longue.");
		}
		else {
			this.date_saisie = date_saisie;
		}
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
	
	
}
