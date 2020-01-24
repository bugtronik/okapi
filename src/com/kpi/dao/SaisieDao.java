package com.kpi.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.kpi.beans.Saisie;

public interface SaisieDao {
	
	void ajouter (Saisie saisie) throws DaoException;
	void modifier (Saisie saisie) throws DaoException;
	
	List<Saisie> lister () throws DaoException;
	List<Saisie> listerSaisie () throws DaoException;
	boolean verificationSaisie (int id_indicateur, String date_indicateur) throws DaoException;
	void importData(String filename) throws DaoException;
	List<Saisie> listeDashboard () throws DaoException;
	
	public void importCSV(String filename) throws DaoException;
	List<Saisie> listConsultation (String username) throws DaoException;
	
	/**
	 * Permet de verifier si l'indicateur à déjà été saisie 
	 * Cela permettra de le retirer de la liste des saisies du jour en cours
	 * @param username
	 * @return
	 * @throws DaoException
	 */
	List<Saisie> verificationSaisie (String username) throws DaoException;
	String dateHier () throws DaoException;
}
