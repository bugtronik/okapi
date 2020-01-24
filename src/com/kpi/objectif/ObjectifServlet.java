package com.kpi.objectif;

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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import javax.servlet.http.Part;

import com.kpi.beans.BeanException;
import com.kpi.beans.Objectif;
import com.kpi.dao.DaoException;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.SaisieDao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import java.io.*;
import java.util.regex.*;
import java.util.*;


/**
 * Servlet implementation class ObjectifServlet
 */
@WebServlet("/ObjectifServlet")
public class ObjectifServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final int TAILLE_TAMPON = 10240;
    public static final String CHEMIN_FICHIERS = "/fichiers/";
    protected SaisieDao saisieDao;
    private DaoFactory daoFactory;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObjectifServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init () throws ServletException {
    	DaoFactory daoFactory = DaoFactory.getInstance();
		this.saisieDao = daoFactory.getSaisieDao();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		this.getServletContext().getRequestDispatcher("/WEB-INF/objectif.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 // On récupère le champ description comme d'habitude
        String description = request.getParameter("description");
        request.setAttribute("description", description );

        // On récupère le champ du fichier

        Part part = request.getPart("fichier");

        // On vérifie qu'on a bien reçu un fichier

        String nomFichier = getNomFichier(part);
        
        //On verifie si nous avons
        if (nomFichier != null && !nomFichier.isEmpty()) {

            String nomChamp = part.getName();

            //Correction d'un bug rencontrer dans internet explorer
             nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1).substring(nomFichier.lastIndexOf('\\') + 1);


            // On écrit définitivement le fichier sur le disque
            //ecrireFichier(part, nomFichier, CHEMIN_FICHIERS);
            request.setAttribute(nomChamp, nomFichier);
            
            //On procède à l'importation des données excel dans la base de données 
            try {
				this.importCSV(nomFichier);
				System.out.print(nomFichier);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CsvValidationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BeanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }
		
        this.getServletContext().getRequestDispatcher("/WEB-INF/objectif.jsp").forward(request, response);
	}
	
	private void ecrireFichier( Part part, String nomFichier, String chemin ) throws IOException {

        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;

        try {

            entree = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);
            sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)), TAILLE_TAMPON);
            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur;
            while ((longueur = entree.read(tampon)) > 0) {
                sortie.write(tampon, 0, longueur);
            }

        } finally {

            try {
                sortie.close();
            } catch (IOException ignore) {

            }

            try {
                entree.close();
            } catch (IOException ignore) {

            }

        }

    }

    private static String getNomFichier( Part part ) {

        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {

            if ( contentDisposition.trim().startsWith( "filename" ) ) {
                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
            }
        }
        return null;
    }
    
    
    /**
     * Methode permettant d'importer un fichier csv dans une base de données
     * @throws IOException 
     * @throws BeanException 
     * @throws CsvValidationException 
     * @throws NumberFormatException 
     * @throws SQLException 
     */
    public void importCSV(String filename) throws DaoException, IOException, NumberFormatException, CsvValidationException, BeanException, SQLException {
   
		//Date de la saisie
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar date = Calendar.getInstance();
		String date_saisie = df.format(date.getTime());

		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;

		int count = 0;
		int totalcount = 0;
		String values = "(0,0,0,0,0,0,0,0,0,0,0,0)";
		String query = "";
        try {
        	connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			CSVReader reader = new CSVReader(new FileReader(filename));
			String[] nextLine;
			
			
			System.out.println(nextLine = reader.readNext());
			while ((nextLine = reader.readNext()) != null) {
				System.out.print("dans la boucle");
				values += "('" + nextLine[0]+"','"+nextLine[1]+"','"+nextLine[2]+"','"+nextLine[3]+"','"+nextLine[4]+"','"+nextLine[5]+"'')";
				count++;
				totalcount++;
				
				if (count == 2) {
					query  = "INSERT INTO  `objectif` ("
						+"`value` ,"
						+"`date_indicateur` ,"
						+"`date_saisie` ,"
						+"`id_utilisateur` ,"
						+"`id_indicateur` ,"
						+"`version`)"
						+"VALUES " + values + ";";
					
					statement.executeUpdate(query);
					count = 0;
					values = "(0,0,0,0,0,0)";
				}
			}
			//query  = "DELETE FROM `rawdata` WHERE `TRIP_NO` = '0'";
			//statement.executeUpdate(query);
			
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(ObjectifServlet.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
		
        } catch(Exception e){
			e.printStackTrace();
		} finally {
            try {
                if (statement != null) {
                	statement.close();
                }
                if (connexion != null) {
                    connexion.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(ObjectifServlet.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        System.out.println(totalcount);
        
	}
}
