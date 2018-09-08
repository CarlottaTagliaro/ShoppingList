/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import it.webproject2018.db.daos.ProdottoDAO;
import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.entities.CategoriaProdotti;
import it.webproject2018.db.entities.Prodotto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author davide
 */
public class JDBCProdottoDAO extends JDBCDAO<Prodotto, Integer> implements ProdottoDAO {
    
    public JDBCProdottoDAO(Connection con) {
        super(con);
    }    

    @Override
    public Prodotto getByPrimaryKey(Integer productID) throws DAOException{
        if (productID == null) {
            throw new DAOException("productID is null");
        }
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti where ID = ? ")) {
            stm.setInt(1, productID);
            try (ResultSet rs = stm.executeQuery()) {

                if(rs.next()){

                    Integer id = rs.getInt("ID");
                    String nome = rs.getString("Nome");
                    String note = rs.getString("Note");
                    String logo = rs.getString("Logo");
                    ArrayList<String> fotografie = getProductImages(productID);
                    JDBCCategoriaProdottiDAO categoriaProdottiDao = new JDBCCategoriaProdottiDAO(CON);
                    CategoriaProdotti categoria = categoriaProdottiDao.getByPrimaryKey(rs.getString("Categoria"));

                    Prodotto product = new Prodotto(id, nome, note, logo, fotografie, categoria);
                    
                    return product;
                }
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Error while getting Product by ID", ex);
        }
        
        return null;
    }
    
    @Override
    public ArrayList<String> getProductImages(Integer productID) throws DAOException {
        if (productID == null) {
            throw new DAOException("productID is null");
        }
        
        ArrayList<String> foto = new ArrayList<>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti_immagini where ID = ? ")) {
            stm.setInt(1, productID);
            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){

                    String fotografia = rs.getString("Fotografia");
                    foto.add(fotografia);
                }
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Error while getting Product images", ex);
        }
        
        return foto;
    }
    
    @Override
    public ArrayList<Prodotto> getAllProducts(String orderBy) throws DAOException{
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti order by ?")) {
            stm.setString(1, orderBy);
            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){                
                    Integer id_prodotto = rs.getInt("ID");
                    Prodotto pro = getByPrimaryKey(id_prodotto);
                    prodotti.add(pro);
                }
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Error while getting all Products", ex);
        }
        
        return prodotti;    
    }
    
    @Override
    public ArrayList<Prodotto> getAllProducts(String filter, String orderBy) throws DAOException{
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti where Nome like '%?%' order by ?")) {
            stm.setString(1, filter);
            stm.setString(2, orderBy);
            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){                
                    Integer id_prodotto = rs.getInt("ID");
                    Prodotto pro = getByPrimaryKey(id_prodotto);
                    prodotti.add(pro);
                }
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Error while getting all Products", ex);
        }
        
        return prodotti;
    }
    
    @Override
    public List<Prodotto> getAll() throws DAOException {
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti")) {
            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){                
                    Integer id_prodotto = rs.getInt("ID");
                    Prodotto pro = getByPrimaryKey(id_prodotto);
                    prodotti.add(pro);
                }
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Error while getting all Products", ex);
        }
        
        return prodotti;
    }
    
    @Override
    public Long getCount() throws DAOException {
        try (Statement stmt = CON.createStatement()) {
            ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM Prodotti");
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count products", ex);
        }

        return 0L;
    }
}
