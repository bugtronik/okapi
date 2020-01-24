package com.kpi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kpi.beans.BeanException;
import com.kpi.beans.Profil;
import com.kpi.beans.SousCategorie;

public class ProfilDaoImpl implements ProfilDao {
	
	private DaoFactory daoFactory;
	
	ProfilDaoImpl (DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public void ajouter(Profil profil) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("insert into profil(libelle, id_privilege) values(?, ?);");
			
			preparedStatement.setString(1, profil.getLibelle());
			preparedStatement.setInt(2,  profil.getId_privilege());
			
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
	
	public List<Profil> lister() throws DaoException {
		List<Profil> profils = new ArrayList<Profil>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("select * from profil;");
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				String libelle = resultat.getString("libelle");
				int id_privilege = resultat.getInt("id_privilege");
				
				Profil profil = new Profil();
				
				profil.setId(id);
				profil.setLibelle(libelle);
				profil.setId_privilege(id_privilege);
				
				profils.add(profil);
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
		
		return profils;
	}
	
	public List<Profil> recuperer(String id) throws DaoException {
		List<Profil> profils = new ArrayList<Profil>();
		Connection connexion = null; //stock la connexion à la base de données
		Statement statement = null;
		ResultSet resultat = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			
			preparedStatement = connexion.prepareStatement("select * from profil where id = ?;");
			preparedStatement.setString(1, id);
			
			resultat = preparedStatement.executeQuery();
			while (resultat.next()) {
				int id_sous = resultat.getInt("id");
				String libelle = resultat.getString("libelle");
				int id_privilege = resultat.getInt("id_privilege");
				
				Profil profil = new Profil();
				
				profil.setId(id_sous);
				profil.setLibelle(libelle);
				
				profils.add(profil);
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
		
		return profils;
	}
	
	public void modifier(Profil profil) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("update profil set libelle = ?, id_privilege = ? where id = ?;");
			
			preparedStatement.setString(1, profil.getLibelle());
			preparedStatement.setInt(3, profil.getId());
			preparedStatement.setInt(2, profil.getId_privilege());
			
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
			preparedStatement = connexion.prepareStatement("delete from profil where id = ?");
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
	 * Methode listant les profils et les les privilèges associés
	 */
	public List<Profil> listes () throws DaoException {
		List<Profil> profils = new ArrayList<Profil>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("select p.id id, p.libelle libelle_profil, pv.libelle libelle_privilege, pv.id id_privilege from profil p inner join privilege pv on p.id_privilege = pv.id;");
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				String libelle = resultat.getString("libelle_profil");
				int id_privilege = resultat.getInt("id_privilege");
				String libelle_privilege = resultat.getString("libelle_privilege");
				
				Profil profil = new Profil();
				
				profil.setId(id);
				profil.setLibelle(libelle);
				profil.setId_privilege(id_privilege);
				profil.setLibelle_privilege(libelle_privilege);
				
				profils.add(profil);
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
		
		return profils;
	}
	
}
