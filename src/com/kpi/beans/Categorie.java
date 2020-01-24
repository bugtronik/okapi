package com.kpi.beans;

import com.kpi.beans.BeanException;

public class Categorie {
	
	/**
	 * Attribut stockant l'identifiant d'une categorie
	 */
	protected int id;
	
	/**
	 * Attribut stockant le libelle d'une categorie
	 */
	protected String libelle;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) throws BeanException  {
		if (libelle.length() > 150) {
			throw new BeanException("Le nom de l'indicateur est trop long (150 minimum)");
		}
		else {
			this.libelle = libelle;
		}
	}
	
	
}
