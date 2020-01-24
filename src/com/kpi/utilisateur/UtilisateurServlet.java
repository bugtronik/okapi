package com.kpi.utilisateur;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kpi.beans.SousCategorie;
import com.kpi.beans.Utilisateur;
import com.kpi.dao.DaoException;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.ProfilDao;
import com.kpi.dao.UtilisateurDao;

/**
 * Servlet implementation class UtilisateurServlet
 */
@WebServlet("/UtilisateurServlet")
public class UtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UtilisateurDao utilisateurDao;
	private ProfilDao profilDao;
	
	//Method d'initialisation
	public void init () throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.utilisateurDao = daoFactory.getUtilisateurDao();
		this.profilDao = daoFactory.getProfilDao();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UtilisateurServlet() {
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
		//On récupère la liste des profils et des utilisateurs
		try {
			if (username == null) {
				 response.sendRedirect( request.getContextPath() + "/connexion" );
			} else {
				request.setAttribute("profils", profilDao.lister());
				request.setAttribute("utilisateurs", utilisateurDao.lister());
				this.getServletContext().getRequestDispatcher("/WEB-INF/utilisateur.jsp").forward(request, response);
			}
		} catch (DaoException e) {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try 
			{
			
			Utilisateur utilisateur = new Utilisateur();
			
			String tag_update = request.getParameter("tag_update");
			
			//On verifie s'il s'agit d'une modification
			if (tag_update != null) {
				int id = Integer.parseInt(request.getParameter("id_update"));
				String originalPassword = request.getParameter("password_update");
				String passwordCrypt = generateStorngPasswordHash(originalPassword);
				
				//On verifie si les mot de passe sont identiques
				if (request.getParameter("password_update").equals(request.getParameter("password_update_confirm"))) {
					utilisateur.setId(id);
					utilisateur.setNom(request.getParameter("nom_update"));
					utilisateur.setPrenom(request.getParameter("prenom_update"));
					utilisateur.setPassword(passwordCrypt);
					utilisateur.setId_profil(Integer.parseInt(request.getParameter("id_profil_update")));
					utilisateur.setUsername(request.getParameter("username_update"));
					utilisateurDao.modifier(utilisateur);
					request.setAttribute("message", "Modification réussi");
				}
				else {
					request.setAttribute("erreur", "Echec de la modification");
				}
			}
			else {
				
				String originalPassword = request.getParameter("password");
				String passwordConfirm = request.getParameter("password_confirm");
				String passwordCrypt = generateStorngPasswordHash(originalPassword);
				//String passwordCrypt = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
				if (originalPassword.equals(passwordConfirm) ) {
					utilisateur.setNom(request.getParameter("nom"));
					utilisateur.setPrenom(request.getParameter("prenom"));
					utilisateur.setPassword(passwordCrypt);
					utilisateur.setId_profil(Integer.parseInt(request.getParameter("id_profil")));
					utilisateur.setUsername(request.getParameter("username"));
					utilisateurDao.ajouter(utilisateur);
				}
				else {
					request.setAttribute("erreur", "Les mots de passe ne sont pas identique");
				}
			}
			//On récupère la liste des profils
			request.setAttribute("profils", profilDao.lister());
			
			//on recupère la liste de toutes les sous categories
			request.setAttribute("utilisateurs", utilisateurDao.lister());
			
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/utilisateur.jsp").forward(request, response);
	}
	
	private static String generateStorngPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();
         
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }
	
	private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
     
    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

}
