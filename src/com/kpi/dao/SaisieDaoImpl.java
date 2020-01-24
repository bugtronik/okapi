package com.kpi.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;


import com.csvreader.CsvReader;
import com.kpi.beans.BeanException;
import com.kpi.beans.Saisie;

public class SaisieDaoImpl implements SaisieDao {
	
	private DaoFactory daoFactory;
	private SaisieDao saisieDao;
	
	SaisieDaoImpl (DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public void ajouter (Saisie saisie) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("insert into saisie(valeur, version, date_indicateur, date_saisie, id_utilisateur, id_indicateur) values(?, ?, ?, ?, ?, ?);");
			
			preparedStatement.setDouble(1, saisie.getValeur());
			preparedStatement.setString(2,  saisie.getVersion());
			preparedStatement.setString(3, saisie.getDate_indicateur());
			preparedStatement.setString(4, saisie.getDate_saisie());
			preparedStatement.setInt(5, saisie.getId_utilisateur());
			preparedStatement.setInt(6, saisie.getId_indicateur());
			
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
	
	public void modifier (Saisie saisie) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("update saisie set valeur = ? where id = ?;");
			
			preparedStatement.setDouble(1, saisie.getValeur());
			preparedStatement.setInt(2, saisie.getId());
			
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
	
	public List<Saisie> lister () throws DaoException {
		List<Saisie> saisies = new ArrayList<Saisie>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("select s.id,\r\n" + 
					"	   s.id_indicateur,\r\n" + 
					"	   s.valeur,\r\n" + 
					"	   s.date_indicateur,\r\n" + 
					"	   s.date_saisie,\r\n" + 
					"	   i.libelle libelle_indicateur\r\n" + 
					"	from saisie s\r\n" + 
					"	inner join indicateur i\r\n" + 
					"	on s.id_indicateur = i.id\r\n" + 
					"	inner join utilisateur u\r\n" + 
					"	on u.id = i.id_utilisateur \r\n" + 
					"	order by s.date_indicateur desc;");
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				Double valeur = resultat.getDouble("valeur");
				String date_indicateur = resultat.getString("date_indicateur");
				String date_saisie = resultat.getString("date_saisie");
				int id_indicateur = resultat.getInt("id_indicateur");
				String libelle_indicateur = resultat.getString("libelle_indicateur");
				
				Saisie saisie = new Saisie();
				
				saisie.setId(id);
				saisie.setValeur(valeur); 
				saisie.setDate_indicateur(date_indicateur);
				saisie.setDate_saisie(date_saisie);
				saisie.setId_indicateur(id_indicateur);
				saisie.setLibelle_indicateur(libelle_indicateur);
				
				saisies.add(saisie);
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
		
		return saisies;
	}
	
	public List<Saisie> listerSaisie () throws DaoException {
		List<Saisie> saisies = new ArrayList<Saisie>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("select s.id,\r\n" + 
					"		s.id_indicateur,\r\n" + 
					"        s.valeur,\r\n" + 
					"        s.date_indicateur,\r\n" + 
					"        s.date_saisie,\r\n" + 
					"       i.libelle libelle_indicateur\r\n" + 
					"from saisie s\r\n" + 
					"inner join indicateur i\r\n" + 
					"on s.id_indicateur = i.id order by s.date_indicateur desc limit 0, 20;");
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				Double valeur = resultat.getDouble("valeur");
				String date_indicateur = resultat.getString("date_indicateur");
				String date_saisie = resultat.getString("date_saisie");
				int id_indicateur = resultat.getInt("id_indicateur");
				String libelle_indicateur = resultat.getString("libelle_indicateur");
				
				Saisie saisie = new Saisie();
				
				saisie.setId(id);
				saisie.setValeur(valeur); 
				saisie.setDate_indicateur(date_indicateur);
				saisie.setDate_saisie(date_saisie);
				saisie.setId_indicateur(id_indicateur);
				saisie.setLibelle_indicateur(libelle_indicateur);
				
				saisies.add(saisie);
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
		
		return saisies;
	}
	
	public List<Saisie> listConsultation (String username) throws DaoException {
		List<Saisie> saisies = new ArrayList<Saisie>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("select s.id,\r\n" + 
					"	   s.id_indicateur,\r\n" + 
					"	   s.valeur,\r\n" + 
					"	   s.date_indicateur,\r\n" + 
					"	   s.date_saisie,\r\n" + 
					"	   i.libelle libelle_indicateur\r\n" + 
					"	from saisie s\r\n" + 
					"	inner join indicateur i\r\n" + 
					"	on s.id_indicateur = i.id\r\n" + 
					"	inner join utilisateur u\r\n" + 
					"	on u.id = i.id_utilisateur \r\n" +
					"	where u.username = '"+username+"' \r\n" +
					"	order by s.date_indicateur desc;");
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				Double valeur = resultat.getDouble("valeur");
				String date_indicateur = resultat.getString("date_indicateur");
				String date_saisie = resultat.getString("date_saisie");
				int id_indicateur = resultat.getInt("id_indicateur");
				String libelle_indicateur = resultat.getString("libelle_indicateur");
				
				Saisie saisie = new Saisie();
				
				saisie.setId(id);
				saisie.setValeur(valeur); 
				saisie.setDate_indicateur(date_indicateur);
				saisie.setDate_saisie(date_saisie);
				saisie.setId_indicateur(id_indicateur);
				saisie.setLibelle_indicateur(libelle_indicateur);
				
				saisies.add(saisie);
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
		
		return saisies;
	}
	
	/**
	 * Données le tableau de bord
	 */
	public List<Saisie> listeDashboard () throws DaoException {
		List<Saisie> saisies = new ArrayList<Saisie>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("select c.*,\r\n" + 
					"	   s.*,\r\n" + 
					"       i.*,\r\n" + 
					"       sa.*\r\n" + 
					"from saisie sa\r\n" + 
					"inner join indicateur i\r\n" + 
					"on sa.id_indicateur = i.id\r\n" + 
					"inner join sous_categorie s\r\n" + 
					"on i.id_sous_categorie = s.id\r\n" + 
					"inner join categorie c\r\n" + 
					"on s.id_categorie = c.id\r\n" + 
					"order by sa.date_indicateur;");
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				Double valeur = resultat.getDouble("valeur");
				String date_indicateur = resultat.getString("date_indicateur");
				String date_saisie = resultat.getString("date_saisie");
				int id_indicateur = resultat.getInt("id_indicateur");
				String libelle_indicateur = resultat.getString("libelle_indicateur");
				String libelle_categorie = resultat.getString("libelle_categorie");
				int id_categorie = resultat.getInt("id_categorie");
				String libelle_sous_categorie = resultat.getString("libelle_sous_categorie");
				int id_sous_categorie = resultat.getInt("id_sous_categorie");
				
				Saisie saisie = new Saisie();
				
				saisie.setId(id);
				saisie.setValeur(valeur); 
				saisie.setDate_indicateur(date_indicateur);
				saisie.setDate_saisie(date_saisie);
				saisie.setId_indicateur(id_indicateur);
				saisie.setLibelle_indicateur(libelle_indicateur);
				saisie.setLibelle_categorie(libelle_categorie);
				saisie.setId_categorie(id_categorie);
				saisie.setLibelle_sous_categorie(libelle_sous_categorie);
				saisie.setId_sous_categorie(id_sous_categorie);
				
				saisies.add(saisie);
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
		
		return saisies;
	}
	
	public boolean verificationSaisie (int id_indicateur, String date_indicateur) throws DaoException {
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("select * from saisie where id_indicateur = '"+id_indicateur+"' and date_indicateur = '"+date_indicateur+"';");
			boolean existe = resultat.next();
			
			if (existe) {
				return true;
			}
			else {
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
	
	/**
	 * Méthode permettant l'importation des données issues d'un fichier csv
	 * @param conn
	 * @param filename
	 */
	@SuppressWarnings("null")
	public void importData(String filename)
    {
		Connection connexion = null;
		Statement statement = null;
        String query;
 
        try
        {
        	statement = connexion.createStatement(
        	ResultSet.TYPE_SCROLL_SENSITIVE,
        	ResultSet.CONCUR_UPDATABLE);
 
        	query = "LOAD DATA INFILE '"+filename+"' INTO TABLE saisie  FIELDS TERMINATED BY ',' (valeur, version, date_indicateur, date_saisie, id_utilisateur, id_indicateur)";
 
        	statement.executeUpdate(query);
                
        }
        catch(Exception e)
        {
            e.printStackTrace();
            statement = null;
        }
    }
	
	
	
	public void importCSV(String filename) throws DaoException {
		
		try {
			CsvReader objectifs = new CsvReader("/fichiers/"+filename);
			
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			
			//Date de la saisie
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Calendar date = Calendar.getInstance();
			String date_saisie = df.format(date.getTime());
			
			objectifs.readHeaders();
			
			while (objectifs.readRecord()) {
				String libelle = objectifs.get("libelle");
				String date_indicateur = objectifs.get("date_indicateur");
				
				connexion = daoFactory.getConnection();
				preparedStatement = connexion.prepareStatement("insert into import(libelle, date_indicateur) values(?, ?);");
				
				preparedStatement.setString(1,libelle);
				preparedStatement.setString(2, date_indicateur);
				
				preparedStatement.executeUpdate();
				connexion.commit();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	double ParseDouble(String strNumber) {
		if (strNumber != null && strNumber.length() > 0) {
			try {
		          return Double.parseDouble(strNumber);
		    } catch(Exception e) {
		          return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
		    }
		}
		else return 0;
	}
	
	/**
	 * Renvoi la date d'hier
	 * @throws DaoException 
	 */
	public String dateHier () throws DaoException {
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			
			resultat = statement.executeQuery("select CURDATE() - INTERVAL 1 DAY date_hier;");
			
			return resultat.getString("date_hier");
			
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
	
	public List<Saisie> verificationSaisie (String username) throws DaoException {
		List<Saisie> saisies = new ArrayList<Saisie>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("select i.id,\r\n" + 
					"	   s.id_indicateur,\r\n" + 
					"       s.valeur,\r\n" + 
					"       s.date_indicateur,\r\n" + 
					"       u.id\r\n" + 
					"from indicateur i\r\n" + 
					"inner join saisie s\r\n" + 
					"on i.id = s.id_indicateur\r\n" + 
					"inner join utilisateur u\r\n" + 
					"on s.id_utilisateur = u.id\r\n" + 
					"where i.id = s.id_indicateur and s.date_indicateur = curdate() and u.username = '"+username+"';");
			
			while (resultat.next()) {
				Double valeur = resultat.getDouble("valeur");
				String date_indicateur = resultat.getString("date_indicateur");
				int id_indicateur = resultat.getInt("id_indicateur");
				
				Saisie saisie = new Saisie();
				
				saisie.setValeur(valeur); 
				saisie.setDate_indicateur(date_indicateur);
				saisie.setId_indicateur(id_indicateur);
				
				saisies.add(saisie);
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
		
		return saisies;
	}
}
