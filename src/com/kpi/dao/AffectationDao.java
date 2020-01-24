package com.kpi.dao;

import com.kpi.beans.Affectation;

public interface AffectationDao {
	
	void ajouter (Affectation affectation) throws DaoException;
	void modifier (Affectation affectation) throws DaoException;
	void supprimer (int id) throws DaoException;
	
	//List <Affectation> listAffectation () throws DaoException;
	//List<Indicateur> lister () throws DaoException;
}
