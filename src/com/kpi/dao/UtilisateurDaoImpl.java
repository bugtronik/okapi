package com.kpi.dao;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.kpi.beans.BeanException;
import com.kpi.beans.Utilisateur;

public class UtilisateurDaoImpl implements UtilisateurDao {

	private DaoFactory daoFactory;
	
	//Constructeur
	UtilisateurDaoImpl (DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	/**
	 * Methode d'ajout d'un utilisateur
	 */
	public void ajouter (Utilisateur utilisateur) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("insert into utilisateur(nom, prenom, password, id_profil, username) values(?, ?, ?, ?, ?);");
			
			preparedStatement.setString(1, utilisateur.getNom());
			preparedStatement.setString(2,  utilisateur.getPrenom());
			preparedStatement.setString(3, utilisateur.getPassword());
			preparedStatement.setInt(4, utilisateur.getId_profil());
			preparedStatement.setString(5, utilisateur.getUsername());
			
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
	
	public List<Utilisateur> lister() throws DaoException {
		List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("select u.nom nom_utilisateur, u.prenom prenom_utilisateur, u.id id_utilisateur, u.username username, p.libelle libelle_profil, p.id id_profil from utilisateur u inner join profil p on u.id_profil = p.id;");
			
			while (resultat.next()) {
				int id = resultat.getInt("id_utilisateur");
				String nom = resultat.getString("nom_utilisateur");
				String prenom = resultat.getString("prenom_utilisateur");
				String profil = resultat.getString("libelle_profil");
				int id_profil = resultat.getInt("id_profil");
				String username = resultat.getString("username");
				
				Utilisateur utilisateur = new Utilisateur();
				
				utilisateur.setId(id);
				utilisateur.setNom(nom);
				utilisateur.setPrenom(prenom);
				utilisateur.setLibelle_profil(profil);
				utilisateur.setId_profil(id_profil);
				utilisateur.setUsername(username);
				
				utilisateurs.add(utilisateur);
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
		
		return utilisateurs;
	}
	
	public void modifier(Utilisateur utilisateur) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("update utilisateur set nom = ?, prenom = ?, password = ?, id_profil = ?, username = ? where id = ?;");
			
			preparedStatement.setString(1, utilisateur.getNom());
			preparedStatement.setString(2, utilisateur.getPrenom());
			preparedStatement.setString(3, utilisateur.getPassword());
			preparedStatement.setInt(4, utilisateur.getId_profil());
			preparedStatement.setString(5, utilisateur.getUsername());
			preparedStatement.setInt(6, utilisateur.getId());
			
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
	
	public List<Utilisateur> recuperer(String id) throws DaoException {
		List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		Connection connexion = null; //stock la connexion à la base de données
		ResultSet resultat = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			
			preparedStatement = connexion.prepareStatement("select * from utilisateur where id = ?;");
			preparedStatement.setString(1, id);
			
			resultat = preparedStatement.executeQuery();
			while (resultat.next()) {
				int id_utilisateur = resultat.getInt("id");
				String nom = resultat.getString("nom");
				String prenom = resultat.getString("prenom");
				int id_profil = resultat.getInt("id_profil");
				
				Utilisateur utilisateur = new Utilisateur();
				
				utilisateur.setId(id_utilisateur);
				utilisateur.setNom(nom);
				utilisateur.setPrenom(prenom);
				utilisateur.setId_profil(id_profil);
				
				utilisateurs.add(utilisateur);
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
		
		return utilisateurs;
	}
	
	public List<Utilisateur> userConnect (String username) throws DaoException {
		List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		Connection connexion = null; //stock la connexion à la base de données
		ResultSet resultat = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			
			//Cryptage du mot de passe de l'utilisateur
			//String passwordCrypt = generateStorngPasswordHash(password);
			
			//On récupère les infos de l'utilisateur connecté
			preparedStatement = connexion.prepareStatement("select u.*,\r\n" + 
														   "	   p.libelle\r\n" + 
														   "from utilisateur u\r\n" + 
														   "inner join profil p\r\n" + 
														   "on u.id_profil = p.id where u.username = ?;");
			preparedStatement.setString(1, username);
			//preparedStatement.setString(2, generateStorngPasswordHash(password));
			
			resultat = preparedStatement.executeQuery();
			while (resultat.next()) {
				int id_utilisateur = resultat.getInt("id");
				String nom = resultat.getString("nom");
				String prenom = resultat.getString("prenom");
				int id_profil = resultat.getInt("id_profil");
				String passwordUser = resultat.getString("password");
				String libelle_profil = resultat.getString("libelle");
				String name_user = resultat.getString("username"); 
				
				Utilisateur utilisateur = new Utilisateur();
				
				utilisateur.setId(id_utilisateur);
				utilisateur.setNom(nom);
				utilisateur.setPrenom(prenom);
				utilisateur.setId_profil(id_profil);
				utilisateur.setPassword(passwordUser);
				utilisateur.setLibelle_profil(libelle_profil);
				utilisateur.setUsername(name_user);
				
				utilisateurs.add(utilisateur);
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
		
		return utilisateurs;
	}
	
	private static String generateStorngPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();
         
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }
	
	private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
     
    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
	
}
