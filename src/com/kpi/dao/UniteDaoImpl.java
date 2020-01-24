package com.kpi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kpi.beans.BeanException;
import com.kpi.beans.Unite;
import com.kpi.dao.DaoException;
import com.kpi.dao.DaoFactory;

public class UniteDaoImpl implements UniteDao {
	
	//Instance du dao
	private DaoFactory daoFactory;
	
	//Constructeur de la classe
	UniteDaoImpl (DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	/**
	 * Methode d'ajout d'unités
	 */
	public void ajouter (Unite unite) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("insert into unite (libelle) values(?);");
			preparedStatement.setString(1, unite.getLibelle());
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
	
	public List<Unite> lister() throws DaoException {
		List<Unite> unites = new ArrayList<Unite>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("select * from unite;");
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				String nom = resultat.getString("libelle");
				
				Unite unite = new Unite();
				
				unite.setId(id);
				unite.setLibelle(nom);
				
				unites.add(unite);
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
		
		return unites;
	}
	
	public List<Unite> recupererUnite(String id) throws DaoException {
		List<Unite> unites = new ArrayList<Unite>();
		Connection connexion = null; //stock la connexion à la base de données
		Statement statement = null;
		ResultSet resultat = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			
			preparedStatement = connexion.prepareStatement("select * from unite where id = ?;");
			preparedStatement.setString(1, id);
			
			resultat = preparedStatement.executeQuery();
			while (resultat.next()) {
				int id_type = resultat.getInt("id");
				String nom = resultat.getString("libelle");
				
				Unite unite = new Unite();
				
				unite.setId(id_type);
				unite.setLibelle(nom);
				
				unites.add(unite);
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
		
		return unites;
	}
	
	public void modifier(Unite unite) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("update unite set libelle = ? where id = ?;");
			
			preparedStatement.setString(1, unite.getLibelle());
			preparedStatement.setInt(2, unite.getId());
			
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
	
	public void supprimer(int id) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("delete from unite where id = ?");
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


}
