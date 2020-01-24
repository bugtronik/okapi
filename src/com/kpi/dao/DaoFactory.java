package com.kpi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.kpi.dao.DaoFactory;
import com.kpi.dao.CategorieDao;
import com.kpi.dao.SousCategorieDao;

public class DaoFactory {
	
	//Attribut de connection à la base de données
	protected String url;
	protected String username;
	protected String password;
	
	DaoFactory (String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public static DaoFactory getInstance () {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			
		}
		
		DaoFactory instance = new DaoFactory(
			"jdbc:mysql://localhost:3306/kpi?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "1960Linuxgnu792"
		);
		
		return instance;
	}
	
	public Connection getConnection () throws SQLException {
		Connection connexion = DriverManager.getConnection(url, username, password);
		connexion.setAutoCommit(false);
		return connexion;
	}
	
	public CategorieDao getCategorieDao () {
		return new CategorieDaoImpl(this);
	}
	
	public SousCategorieDao getSousCategorieDao () {
		return new SousCategorieDaoImpl(this);
	}
	
	public UniteDao getUniteDao () {
		return new UniteDaoImpl(this);
	}
	
	public FrequenceDao getFrequenceDao () {
		return new FrequenceDaoImpl(this);
	}
	
	public ServiceDao getServiceDao () {
		return new ServiceDaoImpl(this);
	}
	public PrivilegeDao getPrivilegeDao () {
		return new PrivilegeDaoImpl(this);
	}
	public ProfilDao getProfilDao () {
		return new ProfilDaoImpl (this);
	}
	public UtilisateurDao getUtilisateurDao () {
		return new UtilisateurDaoImpl(this);
	}
	public IndicateurDao getIndicateurDao () {
		return new IndicateurDaoImpl(this);
	}
	public SaisieDao getSaisieDao () {
		return new SaisieDaoImpl(this);
	}
	public AffectationDao getAffectationDao () {
		return new AffectationDaoImpl(this);
	}
}
