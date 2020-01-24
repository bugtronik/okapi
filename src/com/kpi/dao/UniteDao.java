package com.kpi.dao;

import java.util.List;

import com.kpi.beans.Unite;

public interface UniteDao {
	
	void ajouter (Unite unite) throws DaoException;
	void modifier (Unite unite) throws DaoException;
	void supprimer (int id) throws DaoException;
	
	List<Unite> lister() throws DaoException;
	List<Unite> recupererUnite(String id) throws DaoException;
}
