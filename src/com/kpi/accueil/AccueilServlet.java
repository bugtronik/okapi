package com.kpi.accueil;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kpi.dao.CategorieDao;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.IndicateurDao;
import com.kpi.dao.ObjectifDao;
import com.kpi.dao.SaisieDao;
import com.kpi.dao.SousCategorieDao;

/**
 * Servlet implementation class AccueilServlet
 */
@WebServlet("/AccueilServlet")
public class AccueilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Liste des DAO pour la persistances des données en base MySQL
	protected SaisieDao saisieDao;
	protected CategorieDao categorieDao;
	protected SousCategorieDao souscategorieDao;
	protected IndicateurDao indicateurDao;
	protected ObjectifDao objectifDao;
	
	public void init () throws ServletException {
		
    	DaoFactory daoFactory = DaoFactory.getInstance();
		this.saisieDao = daoFactory.getSaisieDao();
		this.categorieDao = daoFactory.getCategorieDao();
		this.souscategorieDao = daoFactory.getSousCategorieDao();
		this.indicateurDao = daoFactory.getIndicateurDao();
		
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccueilServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Demarrage de la session de l'utilisateur
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		//String profil = (String)session.getAttribute("profil");
		
		try {
			if (username == null) {
				 response.sendRedirect( request.getContextPath() + "/connexion" );
			} else {
				//Date de la saisie
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Calendar date = Calendar.getInstance();
				String date_saisie = df.format(date.getTime());
				
				
				DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
				String date_actuelle = df2.format(date.getTime());
				
				System.out.println(date_actuelle);
				
				
				//Liste des indicateurs
				request.setAttribute("date_actuelle", date_saisie);
				request.setAttribute("date_saisie", date_actuelle);
				request.setAttribute("saisies", saisieDao.listerSaisie());
				request.setAttribute("categories", categorieDao.lister());
				request.setAttribute("souscategories", souscategorieDao.lister());
				request.setAttribute("indicateurs", indicateurDao.listerDashboard());
				request.setAttribute("objectifs", indicateurDao.listeObjectifs());
				request.setAttribute("date_precedente", this.getYesterdayDateString());
				
				System.out.println(this.getYesterdayDateString());
				this.getServletContext().getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
			}
		} catch (Exception e) {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
	}
	
	private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(yesterday());
	}
	
	private Date yesterday() {
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -1);
	    return cal.getTime();
	}

}
