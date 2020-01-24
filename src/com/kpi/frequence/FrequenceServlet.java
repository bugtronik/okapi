package com.kpi.frequence;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kpi.beans.Frequence;
import com.kpi.beans.Unite;
import com.kpi.dao.DaoException;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.FrequenceDao;

/**
 * Servlet implementation class FrequenceServlet
 */
@WebServlet("/FrequenceServlet")
public class FrequenceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private FrequenceDao frequenceDao;
	

	//Method d'initialisation
	public void init () throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.frequenceDao = daoFactory.getFrequenceDao();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrequenceServlet() {
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
		//On récupère la liste des frequences
				try {
					
					if (username == null) {
						 response.sendRedirect( request.getContextPath() + "/connexion" );
					} else {
						String id_frequence = request.getParameter("id_frequence");
						String id_delete = request.getParameter("id_delete");
						
						if (id_frequence != null) {
							request.setAttribute("frequence_unique", frequenceDao.recuperer(id_frequence));
						}
						
						if (id_delete != null) {
							
							frequenceDao.supprimer(Integer.parseInt(id_delete));
							request.setAttribute("message", "Suppression réussi");
						}
						request.setAttribute("frequences", frequenceDao.lister());
						this.getServletContext().getRequestDispatcher("/WEB-INF/frequence.jsp").forward(request, response);
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
			Frequence frequence = new Frequence();
					
			String tag = request.getParameter("tag_update");
			if (tag != null) {
				int id = Integer.parseInt(request.getParameter("id_update"));
				frequence.setId(id);
				frequence.setLibelle(request.getParameter("libelle_update"));
						
				//Modification du type d'indicateur
				frequenceDao.modifier(frequence);
						
				request.setAttribute("message", "Modification réussi");
			}
			else {
				frequence.setLibelle(request.getParameter("libelle"));
				frequenceDao.ajouter(frequence);
			}
					
			request.setAttribute("frequences", frequenceDao.lister());
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/frequence.jsp").forward(request, response);
	}

}
