package com.kpi.affectationlist;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kpi.dao.DaoException;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.IndicateurDao;

/**
 * Servlet implementation class AffectationListServlet
 */
@WebServlet("/AffectationListServlet")
public class AffectationListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected IndicateurDao indicateurDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AffectationListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init () throws ServletException {
  		DaoFactory daoFactory = DaoFactory.getInstance();
  		this.indicateurDao = daoFactory.getIndicateurDao();
  	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			request.setAttribute("indicateurs", indicateurDao.lister());
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("maj_affectation.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
