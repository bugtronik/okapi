package com.kpi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kpi.beans.BeanException;
import com.kpi.beans.Privilege;

public class PrivilegeDaoImpl implements PrivilegeDao {
	//Instance du dao
	private DaoFactory daoFactory;
	
	PrivilegeDaoImpl (DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public List<Privilege> lister() throws DaoException {
		List<Privilege> privileges = new ArrayList<Privilege>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("select * from privilege;");
			
			while (resultat.next()) {
				int id = resultat.getInt("id");
				String nom = resultat.getString("libelle");
				
				Privilege privilege = new Privilege();
				
				privilege.setId(id);
				privilege.setLibelle(nom);
				
				privileges.add(privilege);
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
		
		return privileges;
	}
}
