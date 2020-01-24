package com.kpi.connexion;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kpi.beans.BeanException;
import com.kpi.beans.Utilisateur;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.UtilisateurDao;
import com.kpi.forms.ConnexionForm;

/**
 * Servlet implementation class ConnexionServlet
 */
@WebServlet("/ConnexionServlet")
public class ConnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UtilisateurDao utilisateurDao;
	
	//Method d'initialisation
		public void init () throws ServletException {
			DaoFactory daoFactory = DaoFactory.getInstance();
			this.utilisateurDao = daoFactory.getUtilisateurDao();
		}
	
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnexionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Utilisateur utilisateur = new Utilisateur();
			
			//On recupère le username et le password
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			boolean matched = false;
			
			
			String generatedSecuredPasswordHash = generateStorngPasswordHash(password);
			
			//On verifie si les données issues du formulaire ne sont pas vides
			if (!username.isEmpty() & !password.isEmpty()) {
				
				//On récupère les infos du user
				
				if (!utilisateurDao.userConnect(username).isEmpty()) {
					HttpSession session = request.getSession();
					

			        for(Utilisateur u : utilisateurDao.userConnect(username)) {
			        	matched = validatePassword(password, u.getPassword());
			        	session.setAttribute("username", username);
				        session.setAttribute("password", password);
				        session.setAttribute("profil", u.getLibelle_profil());
				        
			        	
			        }
			        
			        if (matched) {
			        	//request.setAttribute("utilisateur", utilisateurDao.userConnect(username, passwordCrypt));
				        response.sendRedirect( request.getContextPath() + "/accueil" );
			        } else {
			        	request.setAttribute("erreur", "Les identifiants de connexion sont erronés");
						this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
			        }
				}
				else {
					request.setAttribute("erreur", "Les identifiants de connexion sont erronés");
					this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
				}
			} else {
				this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
			}
		} catch (Exception e) {
			
		}
	}
	
	
	/**
	 * Les methodes suivantes sont chargés de cryptés le mot de passe de l'utilisateur
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
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
	
	/**
	 * Les méthodes suivantes sont chargés de décrypter les mots de passes
	 */
	private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);
         
        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();
         
        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

}
