package com.kpi.saisie;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kpi.beans.Saisie;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.IndicateurDao;
import com.kpi.dao.SaisieDao;

/**
 * Servlet implementation class SaisieServlet
 */
@WebServlet("/SaisieServlet")
public class SaisieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DaoFactory daoFactory;
	private IndicateurDao indicateurDao;
	private SaisieDao saisieDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaisieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init () throws ServletException {
    	DaoFactory daoFactory = DaoFactory.getInstance();
		this.indicateurDao = daoFactory.getIndicateurDao();
		this.saisieDao = daoFactory.getSaisieDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		String profil = (String)session.getAttribute("profil");
		
		
		
		try {
			
			if (username == null) {
				response.sendRedirect( request.getContextPath() + "/connexion" );
			}
			else {
				System.out.println(profil);
				System.out.println(username);
				//Date de la saisie
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Calendar date = Calendar.getInstance();
				String date_saisie = df.format(date.getTime());
				
				//Liste des indicateurs
				request.setAttribute("date_actuelle", date_saisie);
				request.setAttribute("indicateurs", indicateurDao.listerActif(username));
				request.setAttribute("verifications", saisieDao.verificationSaisie(username));
				this.getServletContext().getRequestDispatcher("/WEB-INF/saisie.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//On crée l'instance de la saisie
		Saisie saisie = new Saisie();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		String date_saisie = df.format(date.getTime());
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		String profil = (String)session.getAttribute("profil");
		System.out.print(profil);
		
		//On crée les variables de sessions
		session.setAttribute("username", username);
		session.setAttribute("profil", profil);
		
		
		try {
			
				if (request.getParameter("tag_update") != null) {
					saisie.setValeur(Double.parseDouble(request.getParameter("valeur")));
					saisie.setId(Integer.parseInt(request.getParameter("id")));
					
					//On lance la requête de modification des valeurs
					saisieDao.modifier(saisie);
					request.setAttribute("message_ajout", "Modification réussi");
					request.setAttribute("date_actuelle", date_saisie);
					request.setAttribute("indicateurs", indicateurDao.listerActif(username));
					request.setAttribute("saisies", saisieDao.listerSaisie());
					this.getServletContext().getRequestDispatcher("/WEB-INF/consultation.jsp").forward(request, response);
					
			}
			else {
				
				//Avant d'ajout la valeur on verifie si ce dernier possède une valeur dans la période
				if (saisieDao.verificationSaisie(Integer.parseInt(request.getParameter("id_indicateur")), request.getParameter("date_indicateur"))) {
					request.setAttribute("message_erreur", "Valeur déjà saisie dans cette période");
					request.setAttribute("date_actuelle", date_saisie);
					request.setAttribute("indicateurs", indicateurDao.listerActif(username));
					request.setAttribute("saisies", saisieDao.listerSaisie());
					this.getServletContext().getRequestDispatcher("/WEB-INF/saisie.jsp").forward(request, response);
				}
				else {
					saisie.setValeur(Double.parseDouble(request.getParameter("valeur")));
					saisie.setVersion(request.getParameter("version"));
					saisie.setDate_indicateur(request.getParameter("date_indicateur"));
					saisie.setDate_saisie(date_saisie);
					saisie.setId_indicateur(Integer.parseInt(request.getParameter("id_indicateur")));
					saisie.setId_utilisateur(Integer.parseInt(request.getParameter("id_utilisateur")));
					
					//Ajout dans la base de données
					saisieDao.ajouter(saisie);
					request.setAttribute("message_ajout", "Opération réussi");
					
					request.setAttribute("date_actuelle", date_saisie);
					request.setAttribute("indicateurs", indicateurDao.listerActif(username));
					request.setAttribute("saisies", saisieDao.listerSaisie());
					this.getServletContext().getRequestDispatcher("/WEB-INF/saisie.jsp").forward(request, response);
				}
			}
		} catch (Exception e) {
			
		}
	}

}
