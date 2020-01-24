package com.kpi.beans;

import com.kpi.beans.BeanException;

public class Profil {

	protected int id;
	protected String libelle;
	protected int id_privilege;
	protected String libelle_privilege;
	public String getLibelle_privilege() {
		return libelle_privilege;
	}
	public void setLibelle_privilege(String libelle_privilege) {
		this.libelle_privilege = libelle_privilege;
	}
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
			throw new BeanException("Le libelle est trop grand. (150 maximum)");
		} else {
			this.libelle = libelle;
		}
	}
	public int getId_privilege() {
		return id_privilege;
	}
	public void setId_privilege(int id_privilege) {
		this.id_privilege = id_privilege;
	}
}
