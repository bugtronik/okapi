package com.kpi.categorie;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kpi.beans.Categorie;
import com.kpi.dao.CategorieDao;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.DaoException;

/**
 * Servlet implementation class CategorieServlet
 */
@WebServlet("/CategorieServlet")
public class CategorieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CategorieDao categorieDao;
	
	
	//Method d'initialisation
	public void init () throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.categorieDao = daoFactory.getCategorieDao();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategorieServlet() {
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
		String profil = (String)session.getAttribute("profil");
		
		//On récupère la liste des types d'indicateurs
				try {
					
					if (username == null) {
						 response.sendRedirect( request.getContextPath() + "/connexion" );
					} else {
						String id_categorie = request.getParameter("id_categorie");
						String id_delete = request.getParameter("id_delete");
						
						
						if (id_categorie != null) {
							request.setAttribute("categorie_unique", categorieDao.recupererType(id_categorie));
						}
						
						if (id_delete != null) {
							
							categorieDao.supprimerType(Integer.parseInt(id_delete));
							request.setAttribute("message", "Suppression réussi");
						}
						request.setAttribute("categories", categorieDao.lister());
						this.getServletContext().getRequestDispatcher("/WEB-INF/categorie.jsp").forward(request, response);
					}
					
				} catch (DaoException e) {
					request.setAttribute("erreur", e.getMessage());
				}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Demarrage de la session de l'utilisateur
		HttpSession session = request.getSession();
		// TODO Auto-generated method stub
		try {
			
			Categorie categorie = new Categorie();
			if (session == null) {
				 response.sendRedirect( request.getContextPath() + "/connexion" );
			} else {
				String tag = request.getParameter("tag_update");
				if (tag != null) {
					int id = Integer.parseInt(request.getParameter("id_update"));
					categorie.setId(id);
					categorie.setLibelle(request.getParameter("libelle_update"));
					
					//Modification du type d'indicateur
					categorieDao.modifier(categorie);
					
					request.setAttribute("message", "Modification réussi");
					List<Categorie> categorie_unique = null;
					request.setAttribute("categorie_unique", categorie_unique);
				}
				
				else {
					categorie.setLibelle(request.getParameter("libelle"));
					categorieDao.ajouter(categorie);
				}
				
				request.setAttribute("categories", categorieDao.lister());
				this.getServletContext().getRequestDispatcher("/WEB-INF/categorie.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
		}
	}

}
