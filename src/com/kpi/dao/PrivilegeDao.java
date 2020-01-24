package com.kpi.dao;

import java.util.List;

import com.kpi.beans.Privilege;

public interface PrivilegeDao {
	
	//void ajouter (Privilege privilege) throws DaoException;
	//void modifier (Privilege privilege) throws DaoException;
	//void supprimer (int id) throws DaoException;
	
	List<Privilege> lister() throws DaoException;
	//List<Privilege> recuperer(String id) throws DaoException;
}
