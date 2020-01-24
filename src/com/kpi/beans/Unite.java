package com.kpi.beans;
import com.kpi.beans.BeanException;

public class Unite {
	
	/**
	 * Attribut stockant l'identifiant de l'unite
	 */
	protected int id;
	
	/**
	 * Attribut stockant le libelle de l'unite
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
			throw new BeanException("Le nom de l'unité est trop long (150 minimum)");
		}
		else {
			this.libelle = libelle;
		}
	}
}
