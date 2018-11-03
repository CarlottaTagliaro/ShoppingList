/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.entities.ListaPermessi;
import de.scravy.pair.Pair;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO interface for ListaPermessi
 * 
 * @author davide
 */
public interface ListaPermessiDAO extends DAO<ListaPermessi, Pair<String, Integer>> {
    
	/**
	 * Retrieves a list of permissions of some user associated to a certain list
	 * 
	 * @param idList the ID of the list
	 * @param emailUser the email of the user
	 * @return a list
	 * @throws DAOException if an error occurred during the information
	 * retrieving
	 */
	public ArrayList<ListaPermessi> getAllByList(Integer idList, String emailUser) throws DAOException;

    /**
	 * 
	 * 
	 * @param user the user
	 * @param qry the query
	 * @param idLista the ID of the list
	 * @return a list
	 * @throws DAOException if an error occurred during the information
	 * retrieving
	 */
	public List<Pair<Utente, ListaPermessi>> getShareUserList(Utente user, String qry, Integer idLista) throws DAOException;
}
