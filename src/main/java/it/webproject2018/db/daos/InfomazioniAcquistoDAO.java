/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.entities.InformazioniAcquisto;
import java.sql.Date;
import java.util.ArrayList;
import org.glassfish.gmbal.generic.Triple;

/**
 *
 * @author davide
 */
public interface InfomazioniAcquistoDAO extends DAO<InformazioniAcquisto, Triple<Integer, Integer, Date>> {
    public ArrayList<InformazioniAcquisto> getListProductsBuyInfo(Integer listID, Integer productID) throws DAOException;
}
