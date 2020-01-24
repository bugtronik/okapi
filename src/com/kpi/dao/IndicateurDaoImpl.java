package com.kpi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.kpi.beans.BeanException;
import com.kpi.beans.Indicateur;
import com.kpi.beans.Objectif;

public class IndicateurDaoImpl implements IndicateurDao {
	
	private DaoFactory daoFactory;
	
	IndicateurDaoImpl (DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public void ajouter (Indicateur indicateur) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("insert into indicateur(libelle, status, calcul, code, id_unite, id_frequence, id_sous_categorie, id_utilisateur, id_service) values(?, ?, ?, ?, ?, ?, ?, ?, ?);");
			
			preparedStatement.setString(1, indicateur.getLibelle());
			preparedStatement.setString(2, indicateur.getStatus());
			preparedStatement.setString(3, indicateur.getCalcul());
			preparedStatement.setString(4, indicateur.getCode());
			preparedStatement.setInt(5, indicateur.getId_unite());
			preparedStatement.setInt(6, indicateur.getId_frequence());
			preparedStatement.setInt(7, indicateur.getId_sous_categorie());
			preparedStatement.setInt(8, indicateur.getId_utilisateur());
			preparedStatement.setInt(9, indicateur.getId_service());
			
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
				if (connexion != null) {
					connexion.close();
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base données.");
			}
		}
	}
	
	public void modifier (Indicateur indicateur) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("update indicateur set libelle = ?, status = ?, calcul = ?, id_unite = ?, id_frequence = ?, id_sous_categorie = ?, id_utilisateur = ?, id_service = ? where id = ?;");
			
			preparedStatement.setString(1, indicateur.getLibelle());
			preparedStatement.setString(2, indicateur.getStatus());
			preparedStatement.setString(3, indicateur.getCalcul());
			//preparedStatement.setString(4, indicateur.getCode());
			preparedStatement.setInt(4, indicateur.getId_unite());
			preparedStatement.setInt(5, indicateur.getId_frequence());
			preparedStatement.setInt(6, indicateur.getId_sous_categorie());
			preparedStatement.setInt(7, indicateur.getId_utilisateur());
			preparedStatement.setInt(8, indicateur.getId_service());
			preparedStatement.setInt(9, indicateur.getId());
			
			preparedStatement.executeUpdate();
			connexion.commit();
			
			System.out.println("Dans la requête");
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
	
	public List<Indicateur> lister () throws DaoException {
		List<Indicateur> indicateurs = new ArrayList<Indicateur>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			String query = "select i.*, \r\n" + 
					"	   unite.libelle libelle_unite, \r\n" +
					"	   unite.id id_unite, \r\n" + 
					"       frequence.libelle libelle_frequence, \r\n" +
					"       frequence.id id_frequence, \r\n" + 
					"       s.libelle libelle_souscategorie, \r\n" + 
					"       s.id id_sous_categorie, \r\n" + 
					"       utilisateur.nom nom_utilisateur, \r\n" + 
					"       utilisateur.prenom prenom_utilisateur, \r\n" + 
					"       service.libelle libelle_service,\r\n" + 
					"       c.id id_categorie, \r\n" + 
					"       c.libelle libelle_categorie\r\n" + 
					"from indicateur i\r\n" + 
					"inner join utilisateur \r\n" + 
					"on i.id_utilisateur = utilisateur.id \r\n" + 
					"inner join unite on i.id_unite = unite.id \r\n" + 
					"inner join frequence on i.id_frequence = frequence.id \r\n" + 
					"inner join service on i.id_service = service.id\r\n" + 
					"inner join sous_categorie s on s.id = i.id_sous_categorie\r\n" + 
					"inner join categorie c on c.id = s.id_categorie;";
			resultat = statement.executeQuery(query);
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				int id_utilisateur = resultat.getInt("id_utilisateur");
				String libelle = resultat.getString("libelle");
				String nom = resultat.getString("nom_utilisateur");
				String prenom = resultat.getString("prenom_utilisateur");
				String libelle_unite = resultat.getString("libelle_unite");
				String frequence = resultat.getString("libelle_frequence");
				String libelle_souscategorie= resultat.getString("libelle_souscategorie");
				String libelle_service = resultat.getString("libelle_service");
				String status = resultat.getString("status");
				String libelle_categorie = resultat.getString("libelle_categorie");
				int id_sous_categorie = resultat.getInt("id_sous_categorie");
				int id_frequence = resultat.getInt("id_frequence");
				int id_unite = resultat.getInt("id_unite");
				int id_service = resultat.getInt("id_service");
				String code = resultat.getString("code");
				int id_categorie = resultat.getInt("id_categorie");
				
				Indicateur indicateur = new Indicateur();
				
				indicateur.setId(id);
				indicateur.setLibelle(libelle);
				indicateur.setId_utilisateur(id_utilisateur);
				indicateur.setNom_responsable(nom);
				indicateur.setPrenom_responsable(prenom);
				indicateur.setLibelle_unite(libelle_unite);
				indicateur.setLibelle_frequence(frequence);
				indicateur.setLibelle_souscategorie(libelle_souscategorie);
				indicateur.setLibelle_service(libelle_service);
				indicateur.setStatus(status);
				indicateur.setLibelle_categorie(libelle_categorie);
				indicateur.setId_sous_categorie(id_sous_categorie);
				indicateur.setId_frequence(id_frequence);
				indicateur.setId_unite(id_unite);
				indicateur.setId_service(id_service);
				indicateur.setCode(code);
				indicateur.setId_categorie(id_categorie);
				
				
				indicateurs.add(indicateur);
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
		
		return indicateurs;
	}
	
	public void unlockIndicateur (String status, int id) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("update indicateur set status = ? where id = ?");
			
			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, id);
			
			preparedStatement.executeUpdate();
			connexion.commit();
		} catch (SQLException e) {
			throw new DaoException("Impossible de communiquer avec la base de données.");
		}
	}
	
	/**
	 * Methode verifiant si un indicateur n'existe pas via son code
	 */
	
	public boolean verificationDoublon (String code) throws DaoException {
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			
			String query = "select * from indicateur where code = '"+code+"'";
			resultat = statement.executeQuery(query);
			
			boolean doublon = resultat.next();
			
			if (doublon) {
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
	
	public List<Indicateur> listerActif (String username) throws DaoException {
		List<Indicateur> indicateurs = new ArrayList<Indicateur>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			String query = "select i.*, \r\n" + 
					"					unite.libelle libelle_unite, \r\n" + 
					"					frequence.libelle libelle_frequence, \r\n" + 
					"					s.libelle libelle_souscategorie, \r\n" + 
					"					s.libelle libelle_souscategorie, \r\n" + 
					"					utilisateur.nom nom_utilisateur, \r\n" + 
					"					utilisateur.prenom prenom_utilisateur,\r\n" + 
					"					service.libelle libelle_service,\r\n" + 
					"					c.libelle libelle_categorie\r\n" + 
					"					from indicateur i \r\n" + 
					"					inner join utilisateur\r\n" + 
					"					on i.id_utilisateur = utilisateur.id\r\n" + 
					"					inner join unite on i.id_unite = unite.id\r\n" + 
					"					inner join frequence on i.id_frequence = frequence.id\r\n" + 
					"					inner join service on i.id_service = service.id \r\n" + 
					"					inner join sous_categorie s on s.id = i.id_sous_categorie\r\n" + 
					"					inner join categorie c on c.id = s.id_categorie \r\n" + 
					"                    where i.status = 'on' and utilisateur.username = '"+username+"'";
			resultat = statement.executeQuery(query);
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				int id_utilisateur = resultat.getInt("id_utilisateur");
				String libelle = resultat.getString("libelle");
				String nom = resultat.getString("nom_utilisateur");
				String prenom = resultat.getString("prenom_utilisateur");
				String libelle_unite = resultat.getString("libelle_unite");
				String frequence = resultat.getString("libelle_frequence");
				String libelle_souscategorie= resultat.getString("libelle_souscategorie");
				String libelle_service = resultat.getString("libelle_service");
				String status = resultat.getString("status");
				String libelle_categorie = resultat.getString("libelle_categorie");
				int id_sous_categorie = resultat.getInt("id_sous_categorie");
				//Double valeur_indicateur = resultat.getDouble("valeur");
				//String version = resultat.getString("version");
				
				Indicateur indicateur = new Indicateur();
				
				indicateur.setId(id);
				indicateur.setLibelle(libelle);
				indicateur.setId_utilisateur(id_utilisateur);
				indicateur.setNom_responsable(nom);
				indicateur.setPrenom_responsable(prenom);
				indicateur.setLibelle_unite(libelle_unite);
				indicateur.setLibelle_frequence(frequence);
				indicateur.setLibelle_souscategorie(libelle_souscategorie);
				indicateur.setLibelle_service(libelle_service);
				indicateur.setStatus(status);
				indicateur.setLibelle_categorie(libelle_categorie);
				indicateur.setId_sous_categorie(id_sous_categorie);
				//indicateur.setValeur_indicateur(valeur_indicateur);
				//indicateur.setVersion(version);
				
				indicateurs.add(indicateur);
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
		
		return indicateurs;
	}
	
	public List<Indicateur> listerDashboard () throws DaoException {
		List<Indicateur> indicateurs = new ArrayList<Indicateur>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			String query = "select i.*,\r\n" + 
					"	   unite.libelle libelle_unite,\r\n" + 
					"					       frequence.libelle libelle_frequence,\r\n" + 
					"					       s.libelle libelle_souscategorie,\r\n" + 
					"					       s.libelle libelle_souscategorie,\r\n" + 
					"					       utilisateur.nom nom_utilisateur,\r\n" + 
					"					       utilisateur.prenom prenom_utilisateur,\r\n" + 
					"					    service.libelle libelle_service,\r\n" + 
					"					     c.libelle libelle_categorie,\r\n" + 
					"                         sa.version,\r\n" + 
					"                         sa.valeur\r\n" + 
					"					from indicateur i\r\n" + 
					"					inner join utilisateur\r\n" + 
					"					on i.id_utilisateur = utilisateur.id\r\n" + 
					"					inner join unite on i.id_unite = unite.id\r\n" + 
					"					inner join frequence on i.id_frequence = frequence.id\r\n" + 
					"					inner join service on i.id_service = service.id\r\n" + 
					"					inner join sous_categorie s on s.id = i.id_sous_categorie\r\n" + 
					"                    inner join saisie sa on sa.id_indicateur = i.id\r\n" + 
					"					inner join categorie c on c.id = s.id_categorie where i.status = 'on' and sa.date_indicateur = CURDATE()  - INTERVAL 1 DAY order by i.code;";
			resultat = statement.executeQuery(query);
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				int id_utilisateur = resultat.getInt("id_utilisateur");
				String libelle = resultat.getString("libelle");
				String nom = resultat.getString("nom_utilisateur");
				String prenom = resultat.getString("prenom_utilisateur");
				String libelle_unite = resultat.getString("libelle_unite");
				String frequence = resultat.getString("libelle_frequence");
				String libelle_souscategorie= resultat.getString("libelle_souscategorie");
				String libelle_service = resultat.getString("libelle_service");
				String status = resultat.getString("status");
				String libelle_categorie = resultat.getString("libelle_categorie");
				int id_sous_categorie = resultat.getInt("id_sous_categorie");
				Double valeur_indicateur = resultat.getDouble("valeur");
				String version = resultat.getString("version");
				
				Indicateur indicateur = new Indicateur();
				
				indicateur.setId(id);
				indicateur.setLibelle(libelle);
				indicateur.setId_utilisateur(id_utilisateur);
				indicateur.setNom_responsable(nom);
				indicateur.setPrenom_responsable(prenom);
				indicateur.setLibelle_unite(libelle_unite);
				indicateur.setLibelle_frequence(frequence);
				indicateur.setLibelle_souscategorie(libelle_souscategorie);
				indicateur.setLibelle_service(libelle_service);
				indicateur.setStatus(status);
				indicateur.setLibelle_categorie(libelle_categorie);
				indicateur.setId_sous_categorie(id_sous_categorie);
				indicateur.setValeur_indicateur(valeur_indicateur);
				indicateur.setVersion(version);
				
				indicateurs.add(indicateur);
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
		
		return indicateurs;
	}
	
	public List<Objectif> listeObjectifs () throws DaoException {
		List<Objectif> objectifs = new ArrayList<Objectif>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			String query = "select id, id_utilisateur, id_indicateur, round(valeur, 2) valeur, version, date_indicateur from objectif;";
			
			resultat = statement.executeQuery(query);
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				int id_utilisateur = resultat.getInt("id_utilisateur");
				int id_indicateur = resultat.getInt("id_indicateur");
				Double valeur_indicateur = resultat.getDouble("valeur");
				String version = resultat.getString("version");
				String date_indicateur = resultat.getString("date_indicateur");
				
				Objectif objectif = new Objectif();
				
				objectif.setId(id);
				objectif.setId_utilisateur(id_utilisateur);
				objectif.setId_indicateur(id_indicateur);
				objectif.setValeur(valeur_indicateur);
				objectif.setVersion(version);
				objectif.setDate_indicateur(date_indicateur);
				
				objectifs.add(objectif);
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
		
		return objectifs;
	}
	
	public List<Objectif> consultationObjectifs () throws DaoException {
		List<Objectif> objectifs = new ArrayList<Objectif>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			String query = "select o.*, i.libelle libelle_indicateur from objectif o inner join indicateur i on o.id_indicateur = i.id where version = 'objectif';";
			resultat = statement.executeQuery(query);
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				int id_utilisateur = resultat.getInt("id_utilisateur");
				int id_indicateur = resultat.getInt("id_indicateur");
				Double valeur_indicateur = resultat.getDouble("valeur");
				String version = resultat.getString("version");
				String date_indicateur = resultat.getString("date_indicateur");
				String libelle_indicateur = resultat.getString("libelle_indicateur");
				
				Objectif objectif = new Objectif();
				
				objectif.setId(id);
				objectif.setId_utilisateur(id_utilisateur);
				objectif.setId_indicateur(id_indicateur);
				objectif.setValeur(valeur_indicateur);
				objectif.setVersion(version);
				objectif.setDate_indicateur(date_indicateur);
				objectif.setLibelle_indicateur(libelle_indicateur);
				
				objectifs.add(objectif);
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
		
		return objectifs;
	}
	
	
	public List<Indicateur> listesExports (int id_indicateur, int id_user) throws DaoException {
		List<Indicateur> indicateurs = new ArrayList<Indicateur>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			String query = "SELECT \r\n" + 
					"    date_add(current_date ,INTERVAL -1*@rownum:=@rownum+1 DAY) 'date_indicateur',\r\n" + 
					"    indicateur.libelle libelle_indicateur,\r\n" + 
					"    indicateur.id id_indicateur,\r\n" + 
					"    curdate() date_saisie,\r\n" + 
					"    indicateur.id_utilisateur id_utilisateur,\r\n" + 
					"    null valeur\r\n" + 
					"    /*CAST(DATE_FORMAT(date_add(current_date ,INTERVAL -1*@rownum DAY) ,'%Y-%m-01') AS DATE) 'COD_MTH',*/\r\n" + 
					"    /*CAST(DATE_FORMAT(date_add(current_date ,INTERVAL -1*@rownum DAY) ,'%Y-01-01') AS DATE) 'COD_YRD'*/\r\n" + 
					"    FROM\r\n" + 
					"    (SELECT * FROM INFORMATION_SCHEMA.COLUMNS LIMIT 366) p,\r\n" + 
					"    (SELECT * FROM INFORMATION_SCHEMA.COLUMNS LIMIT 10) q,\r\n" + 
					"    (SELECT @rownum:=0) r,\r\n" + 
					"    (select * from indicateur where id = '"+id_indicateur+"' and id_utilisateur = '"+id_user+"') indicateur\r\n" + 
					"    WHERE date_add(current_date ,INTERVAL -1*@rownum DAY) > 20190101\r\n" + 
					"    UNION\r\n" + 
					"    SELECT \r\n" + 
					"    date_add(current_date ,INTERVAL @rownumup:=@rownumup+1 DAY) 'COD_DAY',\r\n" + 
					"    indicateur.libelle libelle_indicateur,\r\n" + 
					"    indicateur.id id_indicateur,\r\n" + 
					"    curdate() date_saisie,\r\n" + 
					"    indicateur.id_utilisateur id_utilisateur,\r\n" + 
					"    null valeur\r\n" + 
					"    /*CAST(DATE_FORMATindicateur(date_add(current_date ,INTERVAL @rownumup DAY) ,'%Y-%m-01') AS DATE) 'COD_MTH',*/\r\n" + 
					"    /*CAST(DATE_FORMAT(date_add(current_date ,INTERVAL @rownumup DAY) ,'%Y-01-01') AS DATE) 'COD_YRD'*/\r\n" + 
					"    FROM \r\n" + 
					"    (SELECT * FROM INFORMATION_SCHEMA.COLUMNS LIMIT 366) p,\r\n" + 
					"    (SELECT * FROM INFORMATION_SCHEMA.COLUMNS LIMIT 10) q, \r\n" + 
					"    (SELECT @rownumup:=0) r,\r\n" + 
					"    (select * from indicateur where id = '"+id_indicateur+"' and id_utilisateur = '"+id_user+"') indicateur\r\n" + 
					"    WHERE \r\n" + 
					"    '2019-12-31' >= date_add(current_date ,INTERVAL @rownumup+1 DAY)\r\n" + 
					"    ORDER BY 1 ASC;";
			resultat = statement.executeQuery(query);
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				int id_utilisateur = resultat.getInt("id_utilisateur");
				String libelle = resultat.getString("libelle");
				String nom = resultat.getString("nom_utilisateur");
				String prenom = resultat.getString("prenom_utilisateur");
				String libelle_unite = resultat.getString("libelle_unite");
				String frequence = resultat.getString("libelle_frequence");
				String libelle_souscategorie= resultat.getString("libelle_souscategorie");
				String libelle_service = resultat.getString("libelle_service");
				String status = resultat.getString("status");
				String libelle_categorie = resultat.getString("libelle_categorie");
				int id_sous_categorie = resultat.getInt("id_sous_categorie");
				//Double valeur_indicateur = resultat.getDouble("valeur");
				//String version = resultat.getString("version");
				
				Indicateur indicateur = new Indicateur();
				
				indicateur.setId(id);
				indicateur.setLibelle(libelle);
				indicateur.setId_utilisateur(id_utilisateur);
				indicateur.setNom_responsable(nom);
				indicateur.setPrenom_responsable(prenom);
				indicateur.setLibelle_unite(libelle_unite);
				indicateur.setLibelle_frequence(frequence);
				indicateur.setLibelle_souscategorie(libelle_souscategorie);
				indicateur.setLibelle_service(libelle_service);
				indicateur.setStatus(status);
				indicateur.setLibelle_categorie(libelle_categorie);
				indicateur.setId_sous_categorie(id_sous_categorie);
				//indicateur.setValeur_indicateur(valeur_indicateur);
				//indicateur.setVersion(version);
				
				indicateurs.add(indicateur);
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
		
		return indicateurs;
	}
	
}
