package com.kpi.dao;

import java.util.List;

import com.kpi.beans.Categorie;
import com.kpi.beans.SousCategorie;

public interface SousCategorieDao {
	void ajouter(SousCategorie souscategorie) throws DaoException;
	void modifier(SousCategorie souscategorie) throws DaoException;
	void supprimer(int id) throws DaoException;
	List<SousCategorie> lister() throws DaoException;
	List<SousCategorie> listes() throws DaoException;
	List<SousCategorie> recuperer(String id) throws DaoException;
	boolean verification (String id) throws DaoException;
}
