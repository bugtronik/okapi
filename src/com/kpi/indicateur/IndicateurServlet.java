package com.kpi.indicateur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kpi.beans.Indicateur;
import com.kpi.beans.SousCategorie;
import com.kpi.dao.CategorieDao;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.FrequenceDao;
import com.kpi.dao.IndicateurDao;
import com.kpi.dao.ServiceDao;
import com.kpi.dao.SousCategorieDao;
import com.kpi.dao.UniteDao;
import com.kpi.dao.UtilisateurDao;

/**
 * Servlet implementation class IndicateurServlet
 */
@WebServlet("/IndicateurServlet")
public class IndicateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DaoFactory daoFactory;
	private IndicateurDao indicateurDao;
	private CategorieDao categorieDao;
	private SousCategorieDao souscategorieDao;
	private UniteDao uniteDao;
	private FrequenceDao frequenceDao;
	private ServiceDao serviceDao;
	private UtilisateurDao utilisateurDao;
	
	
	//Method d'initialisation
	public void init () throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.indicateurDao = daoFactory.getIndicateurDao();
		this.categorieDao = daoFactory.getCategorieDao();
		this.souscategorieDao = daoFactory.getSousCategorieDao();
		this.uniteDao = daoFactory.getUniteDao();
		this.frequenceDao = daoFactory.getFrequenceDao();
		this.serviceDao = daoFactory.getServiceDao();
		this.utilisateurDao = daoFactory.getUtilisateurDao();
		
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndicateurServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			
			//Demarrage de la session de l'utilisateur
			HttpSession session = request.getSession();
			String username = (String)session.getAttribute("username");
			String profil = (String)session.getAttribute("profil");
			
			if (username == null) {
				 response.sendRedirect( request.getContextPath() + "/connexion" );
			} else {
				
				//Liste des categories, sous-categories, frequence, unités et services
				request.setAttribute("categories", categorieDao.lister());
				request.setAttribute("souscategories", souscategorieDao.lister());
				request.setAttribute("frequences", frequenceDao.lister());
				request.setAttribute("services", serviceDao.lister());
				request.setAttribute("unites", uniteDao.lister());
				request.setAttribute("utilisateurs", utilisateurDao.lister());
				
				//Liste des indicateurs
				request.setAttribute("indicateurs", indicateurDao.lister());
				
				this.getServletContext().getRequestDispatcher("/WEB-INF/indicateur.jsp").forward(request, response);
			}
			
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			Indicateur indicateur = new Indicateur();
			
			//On verifie s'il s'agit d'une modification
			if (request.getParameter("tag_update") != null) {
				
				//Identifiant de l'indicateur unique
				int id = Integer.parseInt(request.getParameter("id_indicateur_update"));
		
				indicateur.setId(id);
				indicateur.setLibelle(request.getParameter("libelle_update"));
				//indicateur.setCode(request.getParameter("code_update"));
				indicateur.setId_frequence(Integer.parseInt(request.getParameter("id_frequence_update")));
				indicateur.setId_sous_categorie(Integer.parseInt(request.getParameter("id_sous_categorie_update")));
				indicateur.setId_unite(Integer.parseInt(request.getParameter("id_unite_update")));
				indicateur.setId_service(Integer.parseInt(request.getParameter("id_service_update")));
				indicateur.setCalcul(request.getParameter("calcul_update"));
				indicateur.setId_utilisateur(Integer.parseInt(request.getParameter("id_utilisateur_update")));
				indicateur.setStatus(request.getParameter("status_indicateur"));
				
				System.out.println(indicateur.getId_service());
				indicateurDao.modifier(indicateur);
				
				request.setAttribute("message", "Modification réussi");
			} else if (request.getParameter("status_off") != null) {
				String status_off = request.getParameter("status_off");
				int id_indicateur_status = Integer.parseInt(request.getParameter("id_indicateur_status"));
				indicateurDao.unlockIndicateur(status_off, id_indicateur_status);
				
				request.setAttribute("message", "Indicateur vérouillé");
			} else if (request.getParameter("status_on") != null) {
				String status_on = request.getParameter("status_on");
				int id_indicateur_status = Integer.parseInt(request.getParameter("id_indicateur_status"));
				indicateurDao.unlockIndicateur(status_on, id_indicateur_status);
				
				request.setAttribute("message", "Indicateur déverouillé");
			}
			else {
				
				String status = "off";
				
				indicateur.setLibelle(request.getParameter("libelle"));
				indicateur.setCode(request.getParameter("code"));
				indicateur.setId_frequence(Integer.parseInt(request.getParameter("id_frequence")));
				indicateur.setId_sous_categorie(Integer.parseInt(request.getParameter("id_sous_categorie")));
				indicateur.setId_unite(Integer.parseInt(request.getParameter("id_unite")));
				indicateur.setId_service(Integer.parseInt(request.getParameter("id_service")));
				indicateur.setCalcul(request.getParameter("calcul"));
				indicateur.setId_utilisateur(Integer.parseInt(request.getParameter("id_utilisateur")));
				indicateur.setStatus(status);
				
				//On verifie si l'indicateur n'existe pas déjà
				if (indicateurDao.verificationDoublon(request.getParameter("code"))) {
					request.setAttribute("message_erreur", "un indicateur existe déjà avec ce code");
					request.setAttribute("code_erreur", request.getParameter("code"));
				}
				else {
					//Ajout dans la base de données
					indicateurDao.ajouter(indicateur);
					request.setAttribute("message", "Indicateur créer");
				}
			}
			
			//Liste des categories, sous-categories, frequence, unités et services
			request.setAttribute("categories", categorieDao.lister());
			request.setAttribute("souscategories", souscategorieDao.lister());
			request.setAttribute("frequences", frequenceDao.lister());
			request.setAttribute("services", serviceDao.lister());
			request.setAttribute("unites", uniteDao.lister());
			request.setAttribute("utilisateurs", utilisateurDao.lister());
			
			//Liste des indicateurs
			request.setAttribute("indicateurs", indicateurDao.lister());
			this.getServletContext().getRequestDispatcher("/WEB-INF/indicateur.jsp").forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
		}
	}

}
