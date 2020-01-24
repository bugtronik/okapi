package com.kpi.dao;

import java.util.List;

import com.kpi.beans.Categorie;
import com.kpi.dao.DaoException;

public interface CategorieDao {
	
	void ajouter(Categorie type) throws DaoException;
	void modifier(Categorie type) throws DaoException;
	void supprimerType(int id) throws DaoException;
	List<Categorie> lister() throws DaoException;
	List<Categorie> recupererType(String id) throws DaoException;
}
