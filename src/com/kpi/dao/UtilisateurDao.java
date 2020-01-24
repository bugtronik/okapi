package com.kpi.dao;

import java.util.List;

import com.kpi.beans.Utilisateur;

public interface UtilisateurDao {
	
	void ajouter (Utilisateur utilisateur) throws DaoException;
	void modifier (Utilisateur utilisateur) throws DaoException;
	//void supprimer (int id) throws DaoException;
	
	List<Utilisateur> recuperer(String id) throws DaoException;
	List<Utilisateur> lister() throws DaoException;
	
	List<Utilisateur> userConnect (String username) throws DaoException;
}
