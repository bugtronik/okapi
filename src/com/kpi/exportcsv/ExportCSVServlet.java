package com.kpi.exportcsv;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import com.kpi.beans.Saisie;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.SaisieDao;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Servlet implementation class ExportCSVServlet
 */
@WebServlet("/ExportCSVServlet")
public class ExportCSVServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private SaisieDao saisieDao;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ExportCSVServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init () throws ServletException {
    	DaoFactory daoFactory = DaoFactory.getInstance();
		daoFactory.getIndicateurDao();
		this.saisieDao = daoFactory.getSaisieDao();
    }
    
    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
    	
    	HSSFFont font = workbook.createFont();
    	font.setBold(true);
    	HSSFCellStyle style = workbook.createCellStyle();
    	style.setFont(font);
    	return style;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		
		//System.out.println("Aly & Petra - S'aiment");
		
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
		
		try {
			
			// On récupère la date de début et celle de fin
			String date_debut = request.getParameter("date_debut");
			String date_fin = request.getParameter("date_fin");
			
			//On s'assure que les dates ne soient pas vides
			if (date_debut != null && date_fin != null) {
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("saisie");
				
				int rownum = 0;
				Cell cell;
				Row row;
				
				HSSFCellStyle style = createStyleForTitle(workbook);
				row = sheet.createRow(rownum);
				
				//Libelle de l'indicateur
				cell = row.createCell(0, CellType.STRING);
				cell.setCellValue("indicateur");
				cell.setCellStyle(style);
				
				//Date de l'indicateur
				cell = row.createCell(1, CellType.STRING);
				cell.setCellValue("date_indicateur");
				cell.setCellStyle(style);
				
				//Valeur de l'indicateur
				cell = row.createCell(2, CellType.NUMERIC);
				cell.setCellValue("valeur");
				cell.setCellStyle(style);
				
				for(Saisie saisie : saisieDao.exportExcel(date_debut, date_fin)) {
					rownum++;
					row = sheet.createRow(rownum);
					
					//Le libelle de l'indicateur (A)
					cell =  row.createCell(0, CellType.STRING);
					cell.setCellValue(saisie.getLibelle_indicateur());
					
					//La date de l'indicateur (B)
					cell = row.createCell(1, CellType.STRING);
					cell.setCellValue(saisie.getDate_indicateur());
					
					//La valeur de l'indicateur
					cell = row.createCell(2, CellType.NUMERIC);
					cell.setCellValue(saisie.getValeur());
				}
				
				//On récupère le nom de la session du desktop de l'utilisateur
				String nomSession = System.getProperty("user.home");
				System.out.println(nomSession);
				
				File file = new File("/fichiers/exports.xls");
				file.getParentFile().mkdirs();
				
				FileOutputStream outFile = new FileOutputStream(file);
				workbook.write(outFile);
				System.out.println("Created file: " + file.getAbsolutePath());
				System.out.println("Aly & Petra - Unis pour la vie");
				
				request.setAttribute("exportation", file.getAbsolutePath());

			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/exportcsv.jsp").forward(request, response);
	}

}
