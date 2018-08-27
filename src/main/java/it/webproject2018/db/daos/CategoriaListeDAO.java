/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.entities.CategoriaListe;
import java.util.ArrayList;

/**
 *
 * @author davide
 */
public interface CategoriaListeDAO extends DAO<CategoriaListe, String> {
    
    public ArrayList<String> getListCategoryImages(String catNome) throws DAOException;
}
