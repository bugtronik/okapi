package com.kpi.compte;

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

import com.kpi.beans.Utilisateur;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.UtilisateurDao;

/**
 * Servlet implementation class CompteServlet
 */
@WebServlet("/CompteServlet")
public class CompteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UtilisateurDao utilisateurDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
  //Method d'initialisation
  	public void init () throws ServletException {
  		DaoFactory daoFactory = DaoFactory.getInstance();
  		this.utilisateurDao = daoFactory.getUtilisateurDao();

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
				//On récupère les infos de l'utilisateur
				request.setAttribute("utilisateur", utilisateurDao.userConnect(username));
				
				this.getServletContext().getRequestDispatcher("/WEB-INF/compte.jsp").forward(request, response);
			}
			
		} catch (Exception e) {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		
		try {
			//On créé l'instance de l'utilisateur
			Utilisateur utilisateur = new Utilisateur();
			
			//On récupère les mots de passe
			String password = request.getParameter("password");
			String password2 = request.getParameter("password2");
			
			//On verifie que les mots de passe sont identiques
			if (password.equals(password2)) {
				//On récupère les données saisies de l'utilisateur et l'on assigné les valeurs aux attributs
				utilisateur.setId(Integer.parseInt(request.getParameter("id_utilisateur")));
				utilisateur.setNom(request.getParameter("nom"));
				utilisateur.setPrenom(request.getParameter("prenom"));
				utilisateur.setUsername(request.getParameter("username"));
				utilisateur.setId_profil(Integer.parseInt(request.getParameter("id_profil")));
				
				//On crypte le nouveau mot de passe 
				String passwordCrypt = generateStorngPasswordHash(password);
				utilisateur.setPassword(passwordCrypt);
				System.out.println(utilisateur.getUsername());
				utilisateurDao.modifier(utilisateur);
				request.setAttribute("message", "Modification réussi");
			} else {
				request.setAttribute("erreur", "Les mots de passe ne sont pas identiques");
			}
			
			request.setAttribute("utilisateur", utilisateurDao.userConnect(username));
			
		} catch (Exception e) {
			
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/compte.jsp").forward(request, response);
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
