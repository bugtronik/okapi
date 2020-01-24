package com.kpi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kpi.beans.BeanException;
import com.kpi.beans.SousCategorie;

public class SousCategorieDaoImpl implements SousCategorieDao {
	//Instance dao
		private DaoFactory daoFactory;
				
		SousCategorieDaoImpl (DaoFactory daoFactory) {
			this.daoFactory = daoFactory;
		}
		
		public void ajouter(SousCategorie souscategorie) throws DaoException {
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			
			try {
				connexion = daoFactory.getConnection();
				preparedStatement = connexion.prepareStatement("insert into sous_categorie(libelle, id_categorie) values(?, ?);");
				
				preparedStatement.setString(1, souscategorie.getLibelle());
				preparedStatement.setInt(2,  souscategorie.getId_categorie());
				
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
		
		public List<SousCategorie> lister() throws DaoException {
			List<SousCategorie> souscategories = new ArrayList<SousCategorie>();
			Connection connexion = null;
			Statement statement = null;
			ResultSet resultat = null;
			
			
			try {
				connexion = daoFactory.getConnection();
				statement = connexion.createStatement();
				resultat = statement.executeQuery("select * from sous_categorie;");
				
				while (resultat.next()) {
					int id = resultat.getInt("id");
					String libelle = resultat.getString("libelle");
					int id_categorie = resultat.getInt("id_categorie");
					
					SousCategorie souscategorie = new SousCategorie();
					
					souscategorie.setId(id);
					souscategorie.setLibelle(libelle);
					souscategorie.setId_categorie(id_categorie);
					
					souscategories.add(souscategorie);
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
			
			return souscategories;
		}
		
		public List<SousCategorie> recuperer(String id) throws DaoException {
			List<SousCategorie> souscategories = new ArrayList<SousCategorie>();
			Connection connexion = null; //stock la connexion à la base de données
			ResultSet resultat = null;
			PreparedStatement preparedStatement = null;
			
			try {
				connexion = daoFactory.getConnection();
				
				preparedStatement = connexion.prepareStatement("select * from sous_categorie where id = ?;");
				preparedStatement.setString(1, id);
				
				resultat = preparedStatement.executeQuery();
				while (resultat.next()) {
					int id_sous = resultat.getInt("id");
					String libelle = resultat.getString("libelle");
					
					SousCategorie souscategorie = new SousCategorie();
					
					souscategorie.setId(id_sous);
					souscategorie.setLibelle(libelle);
					
					souscategories.add(souscategorie);
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
			
			return souscategories;
		}
		
		public void modifier(SousCategorie souscategorie) throws DaoException {
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			
			try {
				connexion = daoFactory.getConnection();
				preparedStatement = connexion.prepareStatement("update sous_categorie set libelle = ?, id_categorie = ? where id = ?;");
				
				preparedStatement.setString(1, souscategorie.getLibelle());
				preparedStatement.setInt(3, souscategorie.getId());
				preparedStatement.setInt(2, souscategorie.getId_categorie());
				
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
				preparedStatement = connexion.prepareStatement("delete from sous_categorie where id = ?");
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
		
		/**
		 * Methode listant les sous-categories et les les catégories associées
		 */
		public List<SousCategorie> listes () throws DaoException {
			List<SousCategorie> souscategories = new ArrayList<SousCategorie>();
			Connection connexion = null;
			Statement statement = null;
			ResultSet resultat = null;
			
			
			try {
				connexion = daoFactory.getConnection();
				statement = connexion.createStatement();
				resultat = statement.executeQuery("select s.id id, s.libelle libelle_sous, c.libelle libelle_categorie, c.id id_categorie from sous_categorie s inner join categorie c on s.id_categorie = c.id;");
				
				while (resultat.next()) {
					int id = resultat.getInt("id");
					String libelle = resultat.getString("libelle_sous");
					int id_categorie = resultat.getInt("id_categorie");
					String libelle_categorie = resultat.getString("libelle_categorie");
					
					SousCategorie souscategorie = new SousCategorie();
					
					souscategorie.setId(id);
					souscategorie.setLibelle(libelle);
					souscategorie.setId_categorie(id_categorie);
					souscategorie.setLibelle_categorie(libelle_categorie);
					
					souscategories.add(souscategorie);
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données.");
			} catch (BeanException e) {
				throw new DaoException("Les données de la base sont invalides");
			} finally {
				try {
					if (connexion != null ) {
						connexion.close();
					}
				} catch (SQLException e) {
					throw new DaoException("Impossible de communiquer avec la base de données.");
				}
			}
			
			return souscategories;
		}
		
		public boolean verification (String id) throws DaoException {
			Connection connexion = null; //stock la connexion à la base de données
			ResultSet resultat = null;
			PreparedStatement preparedStatement = null;
			
			try {
				connexion = daoFactory.getConnection();
				
				preparedStatement = connexion.prepareStatement("select * from indicateur where id_sous_categorie = ?;");
				preparedStatement.setString(1, id);
				
				resultat = preparedStatement.executeQuery();
				
				if (resultat != null) {
					return true;
				} else {
					return false;
				}
				
				
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données.");
			} finally {
				try {
					if (connexion != null ) {
						connexion.close();
					}
				} catch (SQLException e) {
					throw new DaoException("Impossible de communiquer avec la base de données.");
				}
			}
			
		}
		
}
