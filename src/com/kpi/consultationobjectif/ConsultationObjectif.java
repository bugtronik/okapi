package com.kpi.consultationobjectif;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kpi.dao.DaoException;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.IndicateurDao;
import com.kpi.dao.SaisieDao;
import com.kpi.dao.UtilisateurDao;

/**
 * Servlet implementation class ConsultationObjectif
 */
@WebServlet("/ConsultationObjectif")
public class ConsultationObjectif extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	private DaoFactory daoFactory;
	private IndicateurDao indicateurDao;
	private SaisieDao saisieDao;
	private UtilisateurDao utilisateurDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultationObjectif() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init () throws ServletException {
    	DaoFactory daoFactory = DaoFactory.getInstance();
		this.saisieDao = daoFactory.getSaisieDao();
		this.indicateurDao = daoFactory.getIndicateurDao();
		this.utilisateurDao = daoFactory.getUtilisateurDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		
		try {
			if (username == null) {
				response.sendRedirect( request.getContextPath() + "/connexion" );
			} else {
				request.setAttribute("objectifs", indicateurDao.consultationObjectifs());
				this.getServletContext().getRequestDispatcher("/WEB-INF/objectif.jsp").forward(request, response);
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
