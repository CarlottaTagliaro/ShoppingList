/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import it.webproject2018.db.daos.InfomazioniAcquistoDAO;
import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.entities.InformazioniAcquisto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author davide
 */
public class JDBCInformazioniAcquistoDAO  extends JDBCDAO<InformazioniAcquisto, Integer> implements InfomazioniAcquistoDAO  {    
    public JDBCInformazioniAcquistoDAO(Connection con) {
        super(con);
    }    
    
    @Override
    public ArrayList<InformazioniAcquisto> getListProductsBuyInfo(Integer listID, Integer productID) throws DAOException {
        if (listID == null) {
            throw new DAOException("listID is null");
        }
        if (productID == null) {
            throw new DAOException("productID is null");
        }

        ArrayList<InformazioniAcquisto> info = new ArrayList<>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Liste_Prodotti_Acquistati where ID_lista = ? and ID_prodotto = ? ")) {
            stm.setInt(1, listID);
            stm.setInt(2, productID);
            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){
                
                    Date data_acq = rs.getDate("Data_acquisto");
                    Integer quantità = rs.getInt("Quantita");
                    info.add(new InformazioniAcquisto(data_acq, quantità));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the buy info", ex);
        }
        
        return info;
    }
    
    @Override
    public InformazioniAcquisto getByPrimaryKey(Integer primaryKey) throws DAOException {        
        return null;
    }
    
    @Override
    public List<InformazioniAcquisto> getAll() throws DAOException {
        return null;
    }
    
    @Override
    public Long getCount() throws DAOException {
        return 0L;
    }
}
