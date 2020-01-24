package com.kpi.affectation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.kpi.beans.Affectation;
import com.kpi.dao.AffectationDao;
import com.kpi.dao.DaoException;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.IndicateurDao;
import com.kpi.dao.ProfilDao;
import com.kpi.dao.UtilisateurDao;

/**
 * Servlet implementation class AffectationServlet
 */
@WebServlet("/AffectationServlet")
public class AffectationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected IndicateurDao indicateurDao;
	private UtilisateurDao utilisateurDao;
	private ProfilDao profilDao;
	private AffectationDao affectationDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AffectationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
  //Method d'initialisation
  	public void init () throws ServletException {
  		DaoFactory daoFactory = DaoFactory.getInstance();
  		this.utilisateurDao = daoFactory.getUtilisateurDao();
  		this.profilDao = daoFactory.getProfilDao();
  		this.indicateurDao = daoFactory.getIndicateurDao();
  		this.affectationDao = daoFactory.getAffectationDao();
  	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		
		//On récupère la liste des profils et des utilisateurs
		try {
			if (username == null) {
				response.sendRedirect( request.getContextPath() + "/connexion" );
			} else {
				
				request.setAttribute("profils", profilDao.lister());
				request.setAttribute("utilisateurs", utilisateurDao.lister());
				request.setAttribute("indicateurs", indicateurDao.lister());
				this.getServletContext().getRequestDispatcher("/WEB-INF/affectation.jsp").forward(request, response);
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Affectation affectation = new Affectation();
		
		affectation.setId_utilisateur(Integer.parseInt(request.getParameter("id_utilisateur")));
		affectation.setId_indicateur(Integer.parseInt(request.getParameter("id_indicateur")));
		
		System.out.println(Integer.parseInt(request.getParameter("id_utilisateur")));
		
		try {
			affectationDao.ajouter(affectation);
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/affectation.jsp").forward(request, response);
		
	}

}
