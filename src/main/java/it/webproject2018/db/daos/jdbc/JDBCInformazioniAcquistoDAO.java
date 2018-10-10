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
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletContext;

/**
 *
 * @author davide
 */
public class JDBCInformazioniAcquistoDAO  extends JDBCDAO<InformazioniAcquisto, Integer> implements InfomazioniAcquistoDAO  {    
    public JDBCInformazioniAcquistoDAO(Connection conn) {
        super(conn);
    }    
    
    public JDBCInformazioniAcquistoDAO(ServletContext sc) {
        super(sc);
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
                    Integer id_lista = rs.getInt("ID_lista");
                    Integer id_prodotto = rs.getInt("ID_prodotto");
                    info.add(new InformazioniAcquisto(data_acq, quantità, id_lista, id_prodotto));
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
    
    @Override
    public InformazioniAcquisto insert(InformazioniAcquisto entity) throws DAOException{
        if (entity == null) {
            throw new DAOException("InformazioniAcquisto parameter is null");
        }
        try {
            PreparedStatement stm = CON.prepareStatement("INSERT INTO Liste_Prodotti_Acquistati (ID_lista, ID_prodotto, Data_acquisto, Quantita) VALUES (?, ?, ?, ?);");
            stm.setInt(1, entity.getId_lista());
            stm.setInt(2, entity.getId_prodotto());
            stm.setDate(3, entity.getData());
            stm.setInt(4, entity.getQuantità());
            Integer rs = stm.executeUpdate();
            
            if (rs > 0)
                return entity;
            
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public InformazioniAcquisto update(InformazioniAcquisto entity) throws DAOException{
        if (entity == null) {
            throw new DAOException("Parameter 'InformazioniAcquisto' not valid for update",
                    new IllegalArgumentException("The passed InformazioniAcquisto is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE Liste_Prodotti_Acquistati "
                + "SET Data_acquisto = ?, Quantita = ? WHERE I, ID_prodotto = ?")) {
            std.setDate(1, entity.getData());
            std.setInt(2, entity.getQuantità());
            std.setInt(3, entity.getId_lista());
            std.setInt(4, entity.getId_prodotto());
            if (std.executeUpdate() == 1) {
                return entity;
            } else {
                throw new DAOException("Impossible to update the InformazioniAcquisto");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the InformazioniAcquisto", ex);
        }
    }
	
	@Override
	public Boolean delete(Integer primaryKey) throws DAOException {
		if (primaryKey == null) {
			throw new DAOException("Lista prodotti acquistati is null");
		}
		/*try (PreparedStatement stm = CON.prepareStatement("DELETE FROM Liste_Prodotti_Acquistati WHERE ? = ? ")) {
			stm.setInt(1, primaryKey);
			
		int res = stm.executeUpdate();
			if (res >= 1) {
				return true;
			}
			return false;
		} catch (SQLException ex) {
			return false;
		}*/
		return false;
	}
}
