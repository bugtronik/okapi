package com.kpi.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kpi.beans.BeanException;
import com.kpi.beans.Utilisateur;

public final class ConnexionForm {
	
	private static final String CHAMP_USER = "username";
	private static final String CHAMP_PASS = "password";
	
	
	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	
	public String getResultat () {
		return resultat;
	}
	
	public Map<String, String> getErreurs () {
		return erreurs;
	}
	
	public Utilisateur connecterUtilisateur(HttpServletRequest request) throws BeanException {
		
		/*On recup�re les champs du formulaire */
		String username = getValeurChamp(request, CHAMP_USER);
		String password = getValeurChamp(request, CHAMP_PASS);
		
		Utilisateur utilisateur = new Utilisateur();
		
		/* Validation du champ username */
		try {
			validationUsername(username);
		} catch (Exception e) {
			setErreur(CHAMP_USER, e.getMessage());
		}
		utilisateur.setUsername(username);
		
		/* Validation du champ mot de passe */
		try {
			validationMotDePasse(password);
		} catch (Exception e) {
			setErreur(CHAMP_PASS, e.getMessage());
		}
		utilisateur.setPassword(password);
		
		
		/* Initialisation du r�sultat global de la validation */
		if (erreurs.isEmpty()) {
			resultat = "Succ�s de la connexion";
		} else {
			resultat = "Echec de la connexion";
		}
		
		return utilisateur;
	}
	
	/**
	 * Valide le nom d'utilisateur
	 */
	private void validationUsername (String username) throws Exception {
		if (username != null) {
			if (username.length() < 5) {
				throw new Exception("Le mot de passe doit contenir au moins 5 caract�res.");
			}
		} else {
			throw new Exception ("Merci de saisir votre nom d'utilisateur.");
		}
	}
	
	/**
	 * Valide le mot de passe saisi.
	 */
	private void validationMotDePasse (String password) throws Exception {
		if (password != null) {
			if (password.length() < 5) {
				throw new Exception("Le mot de passe doit contenir au moins 5 caract�res.");
			}
		} else {
			throw new Exception ("Merci de saisir votre mot de passe.");
		}
	}
	
	/**
	 * Ajoute un message correspondant au champ sp�cifi� � la map des erreurs
	 */
	private void setErreur (String champ, String message) {
		erreurs.put(champ, message);
	}
	
	/**
	 * M�thode utilitaire qui retourne null si un champ est vide, et son contenu
	 * sinon
	 */
	private static String getValeurChamp(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}
}
