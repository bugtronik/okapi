package com.kpi.beans;

import com.kpi.beans.BeanException;

public class Privilege {
	
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
			throw new BeanException("Le libelle de est trop long !");
		}
		else {
			this.libelle = libelle;
		}
	}
	
}
