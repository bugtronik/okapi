package com.kpi.unite;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kpi.beans.Categorie;
import com.kpi.beans.Unite;
import com.kpi.dao.DaoException;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.UniteDao;

/**
 * Servlet implementation class UniteServ
 */
@WebServlet("/UniteServ")
public class UniteServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UniteDao uniteDao;
    
	
	//Method d'initialisation
		public void init () throws ServletException {
			DaoFactory daoFactory = DaoFactory.getInstance();
			this.uniteDao = daoFactory.getUniteDao();
		}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UniteServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Demarrage de la session de l'utilisateur
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		String profil = (String)session.getAttribute("profil");
		//On récupère la liste des types d'indicateurs
		try {
			
			if (username == null) {
				 response.sendRedirect( request.getContextPath() + "/connexion" );
			} else {
				String id_unite = request.getParameter("id_unite");
				String id_delete = request.getParameter("id_delete");
				
				if (id_unite != null) {
					request.setAttribute("unite_unique", uniteDao.recupererUnite(id_unite));
				}
				
				if (id_delete != null) {
					
					uniteDao.supprimer(Integer.parseInt(id_delete));
					request.setAttribute("message", "Suppression réussi");
				}
				request.setAttribute("unites", uniteDao.lister());
				this.getServletContext().getRequestDispatcher("/WEB-INF/unite.jsp").forward(request, response);
			}
		} catch (DaoException e) {
			request.setAttribute("erreur", e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			//Demarrage de la session de l'utilisateur
			HttpSession session = request.getSession();
			
			if (session == null) {
				 response.sendRedirect( request.getContextPath() + "/connexion" );
			} else {
				Unite unite = new Unite();
				
				String tag = request.getParameter("tag_update");
				if (tag != null) {
					int id = Integer.parseInt(request.getParameter("id_update"));
					unite.setId(id);
					unite.setLibelle(request.getParameter("libelle_update"));
							
					//Modification du type d'indicateur
					uniteDao.modifier(unite);
							
					request.setAttribute("message", "Modification réussi");
				}
				else {
					unite.setLibelle(request.getParameter("libelle"));
					uniteDao.ajouter(unite);
				}
						
				request.setAttribute("categories", uniteDao.lister());
				this.getServletContext().getRequestDispatcher("/WEB-INF/unite.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
		}
	}

}
