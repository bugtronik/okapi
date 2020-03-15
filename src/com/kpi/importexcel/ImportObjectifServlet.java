package com.kpi.importexcel;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kpi.dao.DaoFactory;
import com.kpi.dao.IndicateurDao;
import com.kpi.dao.ServiceDao;

/**
 * Servlet implementation class ImportObjectifServlet
 */
@WebServlet("/ImportObjectifServlet")
public class ImportObjectifServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServiceDao serviceDao;
	private IndicateurDao indicateurDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImportObjectifServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init () throws ServletException {
    	DaoFactory daoFactory = DaoFactory.getInstance();
		this.serviceDao = daoFactory.getServiceDao();
		this.indicateurDao = daoFactory.getIndicateurDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		
		try {
			if (username == null) {
				response.sendRedirect( request.getContextPath() + "/connexion" );
			} else {
				request.setAttribute("username", username);
				
				//On récupère la liste des services
				request.setAttribute("services", serviceDao.lister());
				
				//On récupère la liste des indicateurs
				request.setAttribute("indicateurs", indicateurDao.lister());
				
				this.getServletContext().getRequestDispatcher("/WEB-INF/importObjectif.jsp").forward(request, response);
			}
		} catch (Exception e) {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			
			//On récupère les valeurs renseigner au sein du formulaire
			String username = request.getParameter("username");
			int id_indicateur = Integer.parseInt(request.getParameter("id_indicateur"));
			
		} catch (Exception e) {
			
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/importObjectif.jsp").forward(request, response);
	}

}
