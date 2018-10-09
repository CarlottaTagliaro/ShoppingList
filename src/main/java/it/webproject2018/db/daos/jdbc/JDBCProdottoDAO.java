/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import it.webproject2018.db.daos.ProdottoDAO;
import it.webproject2018.db.entities.CategoriaProdotti;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.exceptions.DAOException;

/**
 *
 * @author davide
 */
public class JDBCProdottoDAO extends JDBCDAO<Prodotto, Integer> implements ProdottoDAO {
    
    public JDBCProdottoDAO(Connection con) {
        super(con);
    }
    
    public JDBCProdottoDAO(ServletContext sc) {
        super(sc);
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
    
    
    public ArrayList<Prodotto> getAllProductsByCategory(String catName) throws DAOException{
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti where Categoria = ?")) {
            stm.setString(1, catName);
            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){                
                    Integer id_prodotto = rs.getInt("ID");
                    Prodotto pro = getByPrimaryKey(id_prodotto);
                    prodotti.add(pro);
                }
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Error while getting all Products by Category", ex);
        }
        
        return prodotti;
    }
    
    @Override
    public ArrayList<Prodotto> getUserProducts(String userEmail) throws DAOException{
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti where Owner = ?")) {
            stm.setString(1, userEmail);
            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){                
                    Integer id_prodotto = rs.getInt("ID");
                    Prodotto pro = getByPrimaryKey(id_prodotto);
                    prodotti.add(pro);
                }
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Error while getting all user Products", ex);
        }
        
        return prodotti;
    }
    
    @Override
    public List<Prodotto> getAll() throws DAOException {        
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        
        //prendo solo prodotto creati da admin
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti JOIN Utenti ON Owner = Email WHERE IsAdmin = true")) {
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
    public ArrayList<Prodotto> getAllVisibleProducts(String srcQry, String orderBy) throws DAOException {
        if(srcQry == null)
            srcQry = "";
        
        if(orderBy == null || orderBy.equals("byName") || orderBy.equals(""))
            orderBy = "Nome";
        else if(orderBy.equals("byShop"))
            orderBy = "Categoria";
        
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        
        //prendo solo prodotto creati da admin
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti JOIN Utenti ON Owner = Email WHERE IsAdmin = true and Prodotti.Nome LIKE ? ORDER BY Prodotti." + orderBy)) {
            stm.setString(1, "%" + srcQry + "%");
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
    public ArrayList<Prodotto> getAllUserVisibleProducts(String userEmail, String srcQry, String orderBy) throws DAOException {
        if(srcQry == null)
            srcQry = "";
        
        if(orderBy == null || orderBy.equals("byName") || orderBy.equals(""))
            orderBy = "Nome";
        else if(orderBy.equals("byShop"))
            orderBy = "Categoria";
        
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        
        //TODO: da sistemare orderby e like
        
        //prendo solo prodotti creati da admin
        try (PreparedStatement stm = CON.prepareStatement("Select * from ((select Prodotti.* from Prodotti JOIN Utenti ON Owner = Email WHERE IsAdmin = true or Owner = ?)"
                + "UNION"
                + "(SELECT Prodotti.* FROM Utenti_Prodotti JOIN Prodotti ON ID_prodotto = ID Where Email = ?)) as a WHERE Nome LIKE ? ORDER BY " +  orderBy)) {
            stm.setString(1, userEmail);
            stm.setString(2, userEmail);
            stm.setString(3, "%" + srcQry + "%");
            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){                
                    Integer id_prodotto = rs.getInt("ID");
                    Prodotto pro = getByPrimaryKey(id_prodotto);
                    prodotti.add(pro);
                }
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Error while getting All User Visible Products", ex);
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
	
	@Override
    public Prodotto update(Prodotto product) throws DAOException {
        if (product == null) {
            throw new DAOException("Parameter 'product' not valid for update",
                    new IllegalArgumentException("The passed product is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE Prodotti "
                + "SET Nome = ?, Note = ?, Logo = ?, Categoria = ?, Owner = ? WHERE ID = ?")) {
            std.setString(1, product.getNome());
            std.setString(2, product.getNote());
            std.setString(3, product.getLogo());
            std.setString(4, product.getCategoria().getNome());
            std.setString(5, product.getOwner().getEmail());
            std.setInt(6, product.getId());
            if (std.executeUpdate() == 1) {
                return product;
            } else {
                throw new DAOException("Impossible to update the product");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the product", ex);
        }
    }
    
    @Override
    public Prodotto insert(Prodotto entity) throws DAOException{
        if (entity == null) {
            throw new DAOException("product parameter is null");
        }
        try {
            PreparedStatement stm = CON.prepareStatement("INSERT INTO Prodotti (ID, Nome, Note, Logo, Categoria, Owner) VALUES (null, ?, ?, ?, ?, ?)");
            stm.setString(1, entity.getNome());
            stm.setString(2, entity.getNote());
            stm.setString(3, "");
            stm.setString(4, entity.getCategoria().getNome());
            stm.setString(5, entity.getOwner().getEmail());
            Integer rs = stm.executeUpdate();
            
            ResultSet rsi = stm.getGeneratedKeys();
            if (rsi.next()) {
                return getByPrimaryKey(rsi.getInt(1));
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}