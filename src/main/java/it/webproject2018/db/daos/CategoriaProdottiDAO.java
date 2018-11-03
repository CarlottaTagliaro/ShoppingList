/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.entities.CategoriaProdotti;
import it.webproject2018.db.exceptions.DAOException;
import java.util.List;

/**
 * DAO interface for CategoriaProdotti
 * 
 * @author davide
 */
public interface CategoriaProdottiDAO extends DAO<CategoriaProdotti, String> {
    
	/**
	 * Function that returns the list of tuples representing the product
	 * categories of some specific shop
	 * 
	 * @param catName the name of the shop
	 * @return the list
	 * @throws DAOException if an error occurred during the information
	 * retrieving
	 */
	public List<CategoriaProdotti> getAllByShop(String catName) throws DAOException;
	
	/**
	 * Function that returns a list with the names of the categories of products
	 * 
	 * @return the list of names
	 * @throws DAOException if an error occurred during the information
     * retrieving
	 */
	public List<String> getAllNames() throws DAOException;
}
