package com.kpi.beans;

import com.kpi.beans.BeanException;

public class SousCategorie {
	
	protected int id;
	protected String libelle;
	protected int id_categorie;
	protected String libelle_categorie;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLibelle_categorie() {
		return libelle_categorie;
	}
	public void setLibelle_categorie(String libelle_categorie) throws BeanException {
		if (libelle_categorie.length() > 150) {
			throw new BeanException("Le libelle est trop long.");
		}
		else {
			this.libelle_categorie = libelle_categorie;
		}
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) throws BeanException {
		if (libelle.length() > 150) {
			throw new BeanException("Le libelle de la sous-categorie est trop long (150 minimum).");
		}
		else {
			this.libelle = libelle;
		}
	}
	public int getId_categorie() {
		return id_categorie;
	}
	public void setId_categorie(int id_categorie) {
		this.id_categorie = id_categorie;
	}
}
