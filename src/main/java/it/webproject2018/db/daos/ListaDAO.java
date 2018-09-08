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
 *
 * @author davide
 */
public interface ListaDAO extends DAO<Lista, Integer> {
    public ArrayList<Lista> getUserLists(String userEmail) throws DAOException;
    
    public ArrayList<Prodotto> getListProducts(Integer listID) throws DAOException;
}
