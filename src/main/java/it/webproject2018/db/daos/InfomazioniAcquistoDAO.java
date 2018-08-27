/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.entities.InformazioniAcquisto;
import java.util.ArrayList;

/**
 *
 * @author davide
 */
public interface InfomazioniAcquistoDAO extends DAO<InformazioniAcquisto, Integer> {
    public ArrayList<InformazioniAcquisto> getListProductsBuyInfo(Integer listID, Integer productID) throws DAOException;
}
