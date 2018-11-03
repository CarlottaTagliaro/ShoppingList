/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.entities.CategoriaListe;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO interface for CategoriaListe
 * 
 * @author davide
 */
public interface CategoriaListeDAO extends DAO<CategoriaListe, String> {
    
	/**
	 * Function that returns the list of images of some specific category
	 * 
	 * @param catNome the name of the category/shop
	 * @return the list of images
	 * @throws DAOException if an error occurred during the information
	 * retrieving
	 */
    public ArrayList<String> getListCategoryImages(String catNome) throws DAOException;
	
	/**
	 * Function that returns a list with the categories' names
	 * 
	 * @return the list of names
	 * @throws DAOException if an error occurred during the information
     * retrieving
	 */
	public List<String> getAllNames() throws DAOException;
	
	/**
	 * Inserts the image passed as parameter in the list of images of 
	 * some category
	 * 
	 * @param entity the category object
	 * @param img the imagine to insert
	 * @return the boolean result of the operation
	 * @throws DAOException if an error occurred during the action.
	 */
	public Boolean insertImage(CategoriaListe entity, String img) throws DAOException;
}
