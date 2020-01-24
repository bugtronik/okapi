package com.kpi.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExportDaoImpl implements ExportDao {

	private DaoFactory daoFactory;
	
	
	ExportDaoImpl (DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public void writeCsvFile (String filename) {
		
		//On récupère la date d'aujourd'hui
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		String date_saisie = df.format(date.getTime());
		
		//On créer une list vide
		List exports = new ArrayList();
		
		
		
	}
}
