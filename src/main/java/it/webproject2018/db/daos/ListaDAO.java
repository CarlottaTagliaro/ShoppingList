/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.Prodotto;
import java.util.ArrayList;

/**
 * * DAO interface for Lista
 * 
 * @author davide
 */
public interface ListaDAO extends DAO<Lista, Integer> {
    
	/**
	 * Retrieves the list of lists associated to some user
	 * 
	 * @param userEmail the owner's email
	 * @return the list
	 * @throws DAOException if an error occurred during the information
	 * retrieving
	 */
	public ArrayList<Lista> getUserLists(String userEmail) throws DAOException;
    
	/**
	 * Retrieves the list of products associated to some list
	 * 
	 * @param listID the ID of the list
	 * @return the list of products
	 * @throws DAOException if an error occurred during the information
	 * retrieving
	 */
    public ArrayList<Prodotto> getListProducts(Integer listID) throws DAOException;
	
	/**
	 * Inserts into some list a given amount of some product
	 * 
	 * @param listID the ID of the list
	 * @param prodID the ID of the product
	 * @param amount the amout of the product
	 * @return the boolean result of the operation
	 * @throws DAOException if an error occurred during the action
	 */
	public Boolean insertListProduct(Integer listID, Integer prodID, Integer amount) throws DAOException;

	/**
	 * Retrieves the amount of some product in some list
	 * 
	 * @param listID the ID of the list
	 * @param prodID the ID of the product
	 * @param amount the amount of product
	 * @return the amount of product
	 * @throws DAOException if an error occurred during the information
	 * retrieving
	 */
	public Integer getProductQuantity(Integer listID, Integer prodID, Integer amount) throws DAOException;

	/**
	 * Puts some product and its amount of some list into the purchased list
	 * 
	 * @param listID the ID of the list
	 * @param prodID the ID of the product
	 * @param amount the amount of product
	 * @return the boolean result of the operation
	 * @throws DAOException if an error occurred during the action
	 */
    public Boolean buyProduct(Integer listID, Integer prodID, Integer amount) throws DAOException;

	/**
	 * Updates the list of products to buy
	 * 
	 * @param listID the ID of the list
	 * @param prodID the ID of the product
	 * @param amount 
	 * @param quantity
	 * @return the boolean result of the operation
	 * @throws DAOException if an error occurred during the update
	 */
    public Boolean updateListProductToBuy(Integer listID, Integer prodID, Integer amount, Integer quantity) throws DAOException;

	/**
	 * Updates some list of products with some amount
	 * 
	 * @param listID the ID of the list
	 * @param prodID the ID of the product
	 * @param amount the amount of the product
	 * @return the boolean result of the operation
	 * @throws DAOException if an error occurred during the update
	 */
    public Boolean updateListProduct(Integer listID, Integer prodID, Integer amount) throws DAOException;

}
