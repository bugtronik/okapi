package com.kpi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kpi.beans.BeanException;
import com.kpi.beans.Service;
import com.kpi.beans.Unite;

public class ServiceDaoImpl implements ServiceDao {
	//Instance du dao
	private DaoFactory daoFactory;
		
	//Constructeur de la classe
	ServiceDaoImpl (DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public void ajouter (Service service) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("insert into service (libelle) values(?);");
			preparedStatement.setString(1, service.getLibelle());
			preparedStatement.executeUpdate();
			connexion.commit();
			
		} catch (SQLException e) {
			
			try {
				if(connexion != null) {
					connexion.rollback();
				}
			} catch (SQLException e2) {
				
			}
			throw new DaoException("Impossible de communiquer avec la base de données.");
		} finally {
			try {
				if (connexion != null) {
					connexion.close();
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données.");
			}
		}
	}
	
	public void modifier (Service service) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("update service set libelle = ? where id = ?;");
			
			preparedStatement.setString(1, service.getLibelle());
			preparedStatement.setInt(2, service.getId());
			
			preparedStatement.executeUpdate();
			connexion.commit();
		} catch (SQLException e) {
			try {
				if (connexion != null) {
					connexion.rollback();
				}
			} catch (SQLException e2) {
				
			}
			throw new DaoException("Impossible de communiquer avec la base de données.");
		} finally {
			try {
				if (connexion != null)
					connexion.close();
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données.");
			}
		}
	}
	
	public void supprimer (int id) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("delete from service where id = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			connexion.commit();
		} catch (SQLException e) {
			try {
				if (connexion != null) {
					connexion.close();
				}
			} catch (SQLException e2) {
				
			}
		} finally {
			try {
				if (connexion != null) {
					connexion.close();
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données.");
			}
		}
	}
	
	public List<Service> lister() throws DaoException {
		List<Service> services = new ArrayList<Service>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("select * from service;");
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				String nom = resultat.getString("libelle");
				
				Service service = new Service();
				
				service.setId(id);
				service.setLibelle(nom);
				
				services.add(service);
			}
		} catch (SQLException e) {
			throw new DaoException("Impossible de communiquer avec la base de données.");
		} catch (BeanException e) {
			throw new DaoException("Les données de base sont invalides");
		} finally {
			try {
				if (connexion != null ) {
					connexion.close();
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données.");
			}
		}
		
		return services;
	}
	
	public List<Service> recuperer(String id) throws DaoException {
		List<Service> services = new ArrayList<Service>();
		Connection connexion = null; //stock la connexion à la base de données
		Statement statement = null;
		ResultSet resultat = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			
			preparedStatement = connexion.prepareStatement("select * from service where id = ?;");
			preparedStatement.setString(1, id);
			
			resultat = preparedStatement.executeQuery();
			while (resultat.next()) {
				int id_service = resultat.getInt("id");
				String libelle = resultat.getString("libelle");
				
				Service service = new Service();
				
				service.setId(id_service);
				service.setLibelle(libelle);
				
				services.add(service);
			}
			
		} catch (SQLException e) {
			throw new DaoException("Impossible de communiquer avec la base de données.");
		} catch (BeanException e) {
			throw new DaoException("Les données de base sont invalides");
		} finally {
			try {
				if (connexion != null ) {
					connexion.close();
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données.");
			}
		}
		
		return services;
	}
}
