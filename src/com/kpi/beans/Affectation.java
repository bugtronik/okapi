package com.kpi.beans;

public class Affectation {
	
	protected int id;
	protected int id_utilisateur;
	protected int id_indicateur;
	protected String date_affectation;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getDate_affectation() {
		return date_affectation;
	}
	public void setDate_affectation(String date_affectation) throws BeanException {
		this.date_affectation = date_affectation;
	}
}
