package com.kpi.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kpi.beans.Service;
import com.kpi.beans.Unite;
import com.kpi.dao.DaoException;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.ServiceDao;
import com.kpi.dao.UniteDao;

/**
 * Servlet implementation class ServiceServ
 */
@WebServlet("/ServiceServ")
public class ServiceServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServiceDao serviceDao;
    
	
	//Method d'initialisation
		public void init () throws ServletException {
			DaoFactory daoFactory = DaoFactory.getInstance();
			this.serviceDao = daoFactory.getServiceDao();
		}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceServ() {
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
				String id_service = request.getParameter("id_service");
				String id_delete = request.getParameter("id_delete");
				
				if (id_service != null) {
					request.setAttribute("service_unique", serviceDao.recuperer(id_service));
				}
				
				if (id_delete != null) {
					
					serviceDao.supprimer(Integer.parseInt(id_delete));
					request.setAttribute("message", "Suppression réussi");
				}
				request.setAttribute("services", serviceDao.lister());
				this.getServletContext().getRequestDispatcher("/WEB-INF/service.jsp").forward(request, response);
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
			Service service = new Service();
					
			String tag = request.getParameter("tag_update");
			if (tag != null) {
				int id = Integer.parseInt(request.getParameter("id_update"));
				service.setId(id);
				service.setLibelle(request.getParameter("libelle_update"));
						
				//Modification du type d'indicateur
				serviceDao.modifier(service);
						
				request.setAttribute("message", "Modification réussi");
			}
			else {
				service.setLibelle(request.getParameter("libelle"));
				serviceDao.ajouter(service);
			}
					
			request.setAttribute("services", serviceDao.lister());
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/service.jsp").forward(request, response);
	}

}
