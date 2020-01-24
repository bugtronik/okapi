package com.kpi.souscategorie;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kpi.beans.SousCategorie;
import com.kpi.dao.CategorieDao;
import com.kpi.dao.DaoException;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.SousCategorieDao;


/**
 * Servlet implementation class SousCategorieServ
 */
@WebServlet("/SousCategorieServ")
public class SousCategorieServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CategorieDao categorieDao;
	private SousCategorieDao souscategorieDao;
	
	//Method d'initialisation
		public void init () throws ServletException {
			DaoFactory daoFactory = DaoFactory.getInstance();
			this.categorieDao = daoFactory.getCategorieDao();
			this.souscategorieDao = daoFactory.getSousCategorieDao();
		}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SousCategorieServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//On envoi la liste des categorie à la vue et des sous-categories
		try {
			
			//Demarrage de la session de l'utilisateur
			HttpSession session = request.getSession();
			String username = (String)session.getAttribute("username");
			String profil = (String)session.getAttribute("profil");
			
			if (username == null) {
				 response.sendRedirect( request.getContextPath() + "/connexion" );
			} else {
				//On recupère les variables de modification et de suppression de sous-categories
				String id_souscategorie = request.getParameter("id_souscategorie");
				String id_delete = request.getParameter("id_delete");
				
				//On verifie s'il s'agit d'une modification
				if (id_souscategorie != null) {
					request.setAttribute("souscategorie_unique", souscategorieDao.recuperer(id_souscategorie));
				}
				
				//On verifie si la suppression a été lancée
				if (id_delete != null) {
					if(!souscategorieDao.verification(id_delete)) {
						request.setAttribute("erreur_suppression", "La sous-catégorie est associée aux indicateurs.");
					}
					else {
						souscategorieDao.supprimer(Integer.parseInt(id_delete));
						request.setAttribute("message", "Suppression réussi.");
					}
				}
				request.setAttribute("categories", categorieDao.lister());
				request.setAttribute("souscategories", souscategorieDao.listes());
				this.getServletContext().getRequestDispatcher("/WEB-INF/souscategorie.jsp").forward(request, response);
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			request.setAttribute("erreur", e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			SousCategorie souscategorie = new SousCategorie();
			
			String tag_update = request.getParameter("tag_update");
			
			//On verifie s'il s'agit d'une modification
			if (tag_update != null) {
				int id = Integer.parseInt(request.getParameter("id_update"));
				souscategorie.setId(id);
				souscategorie.setLibelle(request.getParameter("libelle_update"));
				souscategorie.setId_categorie(Integer.parseInt(request.getParameter("id_categorie_update")));
				
				souscategorieDao.modifier(souscategorie);
				
				request.setAttribute("message", "Modification réussi");
			}
			else {
				souscategorie.setLibelle(request.getParameter("libelle"));
				souscategorie.setId_categorie(Integer.parseInt(request.getParameter("id_categorie")));
				souscategorieDao.ajouter(souscategorie);
			}
			
			//on recupère la liste de toutes les sous categories
			request.setAttribute("souscategories", souscategorieDao.listes());
			request.setAttribute("categories", categorieDao.lister());
			
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/souscategorie.jsp").forward(request, response);;
	}

}
