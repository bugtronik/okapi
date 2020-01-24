package com.kpi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.kpi.beans.Affectation;

public class AffectationDaoImpl implements AffectationDao {
	
private DaoFactory daoFactory;
	
	AffectationDaoImpl (DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public void ajouter (Affectation affectation) throws DaoException {
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("insert into affectation(id_utilisateur, id_indicateur, date_affectation) values(?, ?, NOW());");
			
			preparedStatement.setInt(1, affectation.getId_utilisateur());
			preparedStatement.setInt(2, affectation.getId_indicateur());
			
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
	
	public void modifier (Affectation affectation) throws DaoException {
		
	}
	
	public void supprimer (int id) throws DaoException {
		
	}
	
	
}
