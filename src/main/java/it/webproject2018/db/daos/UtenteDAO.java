/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.entities.Utente;

/**
 * All concrete DAOs must implement this interface to handle the persistence 
 * system that interact with {@link User users}.
 * 
 * @author davide
 */
public interface UtenteDAO extends DAO<Utente, String> {
	
	/**
	 * Returns the {@link Utente user} with the given {@code email} and
	 * {@code password} as authentication.
	 * 
	 * @param userEmail the email of the user to get.
	 * @param password the password of the user to get.
	 * @return the {@link Utente user} with the given {@code username} and
	 * {@code password}.
	 * @throws DAOException if an error occurred during the information
	 * retrieving.
	 */
	public Utente getUserAuthentication(String userEmail, String password) throws DAOException;
	
	/**
	 * Updates the user passed as parameter and returns it.
	 * 
	 * @param user the user used to update the persistence system.
	 * @return the updated user.
	 * @throws DAOException if an error occurred during the action.
	 */
	public Utente update(Utente user) throws DAOException;
}
