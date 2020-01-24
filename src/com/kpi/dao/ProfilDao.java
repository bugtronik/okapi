package com.kpi.dao;

import java.util.List;

import com.kpi.beans.Profil;

public interface ProfilDao {
	void ajouter (Profil profil) throws DaoException;
	void modifier (Profil profil) throws DaoException;
	void supprimer (int id) throws DaoException;
		
	List<Profil> lister() throws DaoException;
	List<Profil> recuperer(String id) throws DaoException;
	List<Profil> listes () throws DaoException;
}
