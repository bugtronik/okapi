package com.kpi.dao;

import java.util.List;

import com.kpi.beans.Indicateur;
import com.kpi.beans.Objectif;

public interface IndicateurDao {

	void ajouter (Indicateur indicateur) throws DaoException;
	void modifier (Indicateur indicateur) throws DaoException;
	//void supprimer (int id) throws DaoException;
	
	List<Indicateur> lister () throws DaoException;
	void unlockIndicateur (String status, int id) throws DaoException;
	boolean verificationDoublon (String code) throws DaoException;
	public List<Indicateur> listerActif (String username) throws DaoException;
	public List<Indicateur> listerDashboard () throws DaoException;
	public List<Objectif> listeObjectifs () throws DaoException;
	List<Objectif> consultationObjectifs () throws DaoException;
}
