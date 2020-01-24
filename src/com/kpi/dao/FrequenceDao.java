package com.kpi.dao;

import java.util.List;

import com.kpi.beans.Frequence;

public interface FrequenceDao {
	
	void ajouter (Frequence frequence) throws DaoException;
	void modifier (Frequence frequence) throws DaoException;
	void supprimer (int id) throws DaoException;
	
	List<Frequence> lister () throws DaoException;
	List<Frequence> recuperer (String id) throws DaoException;
}
