/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.entities.Prodotto;
import java.util.ArrayList;

/**
 *
 * @author davide
 */
public interface ProdottoDAO extends DAO<Prodotto, Integer> {
    
    public ArrayList<Prodotto> getAllProducts(String orderBy) throws DAOException;
    
    public ArrayList<Prodotto> getAllProducts(String filter, String orderBy) throws DAOException;
    
    public ArrayList<String> getProductImages(Integer productID) throws DAOException;
	
    public ArrayList<Prodotto> getUserProducts(String userEmail) throws DAOException;
    
    public ArrayList<Prodotto> getAllUserVisibleProducts(String userEmail, String srcQry, String orderBy, Integer count, Integer start) throws DAOException;
    
    public ArrayList<Prodotto> getAllVisibleProducts(String srcQry, String orderBy, Integer count, Integer start) throws DAOException;

    public Long getCountUserVisibleProducts(String userEmail, String srcQry) throws DAOException;
    
    public Long getCountVisibleProducts(String srcQry) throws DAOException;
}
