/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.entities.Utente;

/**
 *
 * @author davide
 */
public interface UtenteDAO extends DAO<Utente, String> {
    public Utente getUserAuthentication(String userEmail, String password) throws DAOException;
}
