/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import de.scravy.pair.Pair;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.entities.Utente;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.gmbal.generic.Triple;

/**
 * DAO interface for Prodotto
 *
 * @author davide
 */
public interface ProdottoDAO extends DAO<Prodotto, Integer> {

    public ArrayList<Prodotto> getAllProducts(String orderBy) throws DAOException;

    public ArrayList<Prodotto> getAllProducts(String filter, String orderBy) throws DAOException;

    public ArrayList<String> getProductImages(Integer productID) throws DAOException;

    public ArrayList<Prodotto> getUserProducts(String userEmail, Integer start, Integer offset) throws DAOException;

    public Long getUserProductsCount(String userEmail) throws DAOException;

    public ArrayList<Prodotto> getAllUserVisibleProducts(String userEmail, String srcQry, String orderBy, Integer count, Integer start) throws DAOException;

    public ArrayList<Prodotto> getAllVisibleProducts(String srcQry, String orderBy, Integer count, Integer start) throws DAOException;

    public Long getCountUserVisibleProducts(String userEmail, String srcQry) throws DAOException;

    public Long getCountVisibleProducts(String srcQry) throws DAOException;

    public List<Triple<Integer, String, Integer>> getProductListAmount(Prodotto entity, Utente user) throws DAOException;

    public Integer getProductOfListAmount(Prodotto product, Lista list) throws DAOException;

    public ArrayList<Prodotto> getAllProductsByCategory(String catName, String qry) throws DAOException;

    public Boolean insertImage(Prodotto entity, String img) throws DAOException;

    public Boolean shareProduct(Integer idProduct, String email) throws DAOException;

    public Boolean deleteShareProduct(Integer idProduct, String email) throws DAOException;

    public ArrayList<Pair<Utente, Boolean>> getUserToShareWith(Integer idProdotto, Utente user, String qry) throws DAOException;

    public ArrayList<Utente> getUserSharedProduct(Integer idProdotto) throws DAOException;

    public Boolean deleteFromList(Integer ID_product, Integer ID_list) throws DAOException;
}
