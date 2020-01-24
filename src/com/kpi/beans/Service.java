package com.kpi.beans;
import com.kpi.beans.BeanException;

public class Service {
	
	protected int id;
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
	public void setLibelle(String libelle) throws BeanException {
		if (libelle.length() > 150) {
			throw new BeanException("Le libelle du service est trop long! (150 minimum)");
		}
		else {
			this.libelle = libelle;
		}
	}
}
