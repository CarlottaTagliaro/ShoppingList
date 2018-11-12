/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.entities.Utente;

/**
 * DAO interface for Utente
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

    public Boolean registerUser(String name, String surname, String userEmail, String password, String confirmString) throws DAOException;

    public Utente getUserByRememberMeID(String id) throws DAOException;
    
    public String createRememberMeID(String userEmail) throws DAOException;
	
    public Utente updatePassword(Utente user, String password) throws DAOException;
		
    public Boolean updateUserLastAccess(Utente user) throws DAOException;
    
    public Utente getUserByConfString(String confString) throws DAOException;

}
