package com.kpi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kpi.beans.BeanException;
import com.kpi.beans.Frequence;
import com.kpi.beans.Unite;

public class FrequenceDaoImpl implements FrequenceDao {
	
	private DaoFactory daoFactory;
	
	FrequenceDaoImpl (DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	/**
	 * Methode d'ajout d'une fréquence
	 */
	public void ajouter (Frequence frequence) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("insert into frequence (libelle) values(?);");
			preparedStatement.setString(1, frequence.getLibelle());
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
	
	/**
	 * Methode de modification
	 */
	public void modifier (Frequence frequence) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("update frequence set libelle = ? where id = ?;");
			
			preparedStatement.setString(1, frequence.getLibelle());
			preparedStatement.setInt(2, frequence.getId());
			
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
			preparedStatement = connexion.prepareStatement("delete from frequence where id = ?");
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
	
	public List<Frequence> lister () throws DaoException {
		List<Frequence> frequences = new ArrayList<Frequence>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("select * from frequence;");
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				String nom = resultat.getString("libelle");
				
				Frequence frequence = new Frequence();
				
				frequence.setId(id);
				frequence.setLibelle(nom);
				
				frequences.add(frequence);
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
		
		return frequences;
	}
	
	public List<Frequence> recuperer (String id) throws DaoException {
		List<Frequence> frequences = new ArrayList<Frequence>();
		Connection connexion = null; //stock la connexion à la base de données
		Statement statement = null;
		ResultSet resultat = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			
			preparedStatement = connexion.prepareStatement("select * from frequence where id = ?;");
			preparedStatement.setString(1, id);
			
			resultat = preparedStatement.executeQuery();
			while (resultat.next()) {
				int id_type = resultat.getInt("id");
				String nom = resultat.getString("libelle");
				
				Frequence frequence = new Frequence();
				
				frequence.setId(id_type);
				frequence.setLibelle(nom);
				
				frequences.add(frequence);
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
		
		return frequences;
	}
}
