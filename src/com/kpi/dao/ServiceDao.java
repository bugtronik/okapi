package com.kpi.dao;

import java.util.List;

import com.kpi.beans.Service;

	public interface ServiceDao {
	void ajouter (Service service) throws DaoException;
	void modifier (Service service) throws DaoException;
	void supprimer (int id) throws DaoException;
	
	List<Service> lister() throws DaoException;
	List<Service> recuperer(String id) throws DaoException;
}
