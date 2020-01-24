package com.kpi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kpi.dao.DaoFactory;
import com.kpi.beans.Categorie;
import com.kpi.beans.BeanException;
import com.kpi.dao.DaoException;

public class CategorieDaoImpl implements CategorieDao {

	//Instance dao
	private DaoFactory daoFactory;
			
	CategorieDaoImpl (DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public void ajouter(Categorie categorie) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("insert into categorie(libelle) values(?);");
			
			preparedStatement.setString(1, categorie.getLibelle());
			
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
	
	public List<Categorie> lister() throws DaoException {
		List<Categorie> categories = new ArrayList<Categorie>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("select * from categorie;");
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				String nom = resultat.getString("libelle");
				
				Categorie categorie = new Categorie();
				
				categorie.setId(id);
				categorie.setLibelle(nom);
				
				categories.add(categorie);
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
		
		return categories;
	}
	
	public List<Categorie> recupererType(String id) throws DaoException {
		List<Categorie> categories = new ArrayList<Categorie>();
		Connection connexion = null; //stock la connexion à la base de données
		Statement statement = null;
		ResultSet resultat = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			
			preparedStatement = connexion.prepareStatement("select * from categorie where id = ?;");
			preparedStatement.setString(1, id);
			
			resultat = preparedStatement.executeQuery();
			while (resultat.next()) {
				int id_type = resultat.getInt("id");
				String nom = resultat.getString("libelle");
				
				Categorie categorie = new Categorie();
				
				categorie.setId(id_type);
				categorie.setLibelle(nom);
				
				categories.add(categorie);
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
		
		return categories;
	}
	
	public void modifier(Categorie categorie) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("update categorie set libelle = ? where id = ?;");
			
			preparedStatement.setString(1, categorie.getLibelle());
			preparedStatement.setInt(2, categorie.getId());
			
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
	
	
	public void supprimerType(int id) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("delete from categorie where id = ?");
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
