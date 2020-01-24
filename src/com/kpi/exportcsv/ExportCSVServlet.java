package com.kpi.exportcsv;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileWriter;

/**
 * Servlet implementation class ExportCSVServlet
 */
@WebServlet("/ExportCSVServlet")
public class ExportCSVServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//On définit les délimiteurs du fichier CSV
	private static final String COMMA_DELIMITER = ";";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	
	//En-tête du fichier CSV
	private static final String FILE_HEADER = "date_indicateur, libelle, id_indicateur, date_saisie, id_utilisateur, valeur";
	
	public static void writeCSVFile (String filename) {
		
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportCSVServlet() {
        super();
        // TODO Auto-generated constructor stub
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
				this.getServletContext().getRequestDispatcher("/WEB-INF/exportcsv.jsp").forward(request, response);
			}
		} catch (Exception e) {
			
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
