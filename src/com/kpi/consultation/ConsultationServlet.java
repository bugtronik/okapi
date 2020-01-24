package com.kpi.consultation;

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

import com.kpi.dao.DaoFactory;
import com.kpi.dao.IndicateurDao;
import com.kpi.dao.SaisieDao;

/**
 * Servlet implementation class ConsultationServlet
 */
@WebServlet("/ConsultationServlet")
public class ConsultationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	private DaoFactory daoFactory;
	private IndicateurDao indicateurDao;
	private SaisieDao saisieDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultationServlet() {
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
		try {
			
			
			//Date de la saisie
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar date = Calendar.getInstance();
			String date_saisie = df.format(date.getTime());
			
			HttpSession session = request.getSession();
			String username = (String)session.getAttribute("username");
			String profil = (String)session.getAttribute("profil");
			if (username == null) {
				response.sendRedirect( request.getContextPath() + "/connexion" );
			} else {
				if ((profil.equals("Administrateur")) || (profil.equals("Collaborateur"))) {
					//Liste des indicateurs
					request.setAttribute("date_actuelle", date_saisie);
					request.setAttribute("indicateurs", indicateurDao.listerActif(username));
					request.setAttribute("saisies", saisieDao.listConsultation(username));
					System.out.println(username);
					System.out.println(profil);
				} else if(profil.equals("Lecteur")) {
					request.setAttribute("date_actuelle", date_saisie);
					//request.setAttribute("indicateurs", indicateurDao.listerActif(username));
					request.setAttribute("saisies", saisieDao.lister());
				}
				
				this.getServletContext().getRequestDispatcher("/WEB-INF/consultation.jsp").forward(request, response);
				
				
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
		doGet(request, response);
	}

}
