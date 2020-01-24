package com.kpi.profil;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kpi.beans.Profil;
import com.kpi.beans.SousCategorie;
import com.kpi.dao.DaoException;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.PrivilegeDao;
import com.kpi.dao.ProfilDao;

/**
 * Servlet implementation class ProfilServ
 */
@WebServlet("/ProfilServ")
public class ProfilServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PrivilegeDao privilegeDao;
	private ProfilDao profilDao;
	
	//Method d'initialisation
	public void init () throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.privilegeDao = daoFactory.getPrivilegeDao();
		this.profilDao = daoFactory.getProfilDao();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfilServ() {
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
		 
		try {
			
			if (username == null) {
				 response.sendRedirect( request.getContextPath() + "/connexion" );
			} else {
				//On recupère les variables de modification et de suppression d'un profil
				String id_profil = request.getParameter("id_profil");
				String id_delete = request.getParameter("id_delete");
				
				//On verifie s'il s'agit d'une modification
				if (id_profil != null) {
					request.setAttribute("profil_unique", profilDao.recuperer(id_profil));
				}
			
				request.setAttribute("privileges", privilegeDao.lister());
				request.setAttribute("profiles", profilDao.listes());
				this.getServletContext().getRequestDispatcher("/WEB-INF/profil.jsp").forward(request, response);
			}
		} catch (DaoException e) {
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			try {
			
				Profil profil = new Profil();
				
				String tag_update = request.getParameter("tag_update");
				
				//On verifie s'il s'agit d'une modification
				if (tag_update != null) {
					int id = Integer.parseInt(request.getParameter("id_update"));
					profil.setId(id);
					profil.setLibelle(request.getParameter("libelle_update"));
					profil.setId_privilege(Integer.parseInt(request.getParameter("id_privilege_update")));
					
					profilDao.modifier(profil);
					
					request.setAttribute("message", "Modification réussi");
				}
				else {
					profil.setLibelle(request.getParameter("libelle"));
					profil.setId_privilege(Integer.parseInt(request.getParameter("id_categorie")));
					profilDao.ajouter(profil);
				}
				
				//on recupère la liste de toutes les sous categories
				request.setAttribute("profiles", profilDao.listes());
				request.setAttribute("privileges", privilegeDao.lister());
				
			} catch (Exception e) {
				request.setAttribute("erreur", e.getMessage());
			}
		this.getServletContext().getRequestDispatcher("/WEB-INF/profil.jsp").forward(request, response);
	}

}
