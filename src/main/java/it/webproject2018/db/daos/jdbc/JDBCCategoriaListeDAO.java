/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import it.webproject2018.db.daos.CategoriaListeDAO;
import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.entities.CategoriaListe;
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
public class JDBCCategoriaListeDAO extends JDBCDAO<CategoriaListe, String> implements CategoriaListeDAO  {    
    public JDBCCategoriaListeDAO(Connection con) {
        super(con);
    }    
    
    @Override
    public CategoriaListe getByPrimaryKey(String primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Liste_categorie WHERE Nome = ?")) {
            stm.setString(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {

                rs.next();
                String nome = rs.getString("Nome");
                String descrizione = rs.getString("Descrizione");
                ArrayList<String> immagini = getListCategoryImages(nome);

                CategoriaListe cat = new CategoriaListe(nome, descrizione, immagini); 

                return cat;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the shopping_list for the passed primary key", ex);
        }
    }
    
    //TODO: forse da spostare in classe apposita
    @Override
    public ArrayList<String> getListCategoryImages(String catNome) throws DAOException{
        if (catNome == null) {
            throw new DAOException("catNome is null");
        }
        
        ArrayList<String> immagini = new ArrayList<>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Liste_categorie_immagini where Nome = ? ")) {
            stm.setString(1, catNome);
            try (ResultSet rs = stm.executeQuery()) {
                while(rs.next()){
                    String immagine = rs.getString("Immagine");
                
                    immagini.add(immagine);
                }
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Error while getting Category of the List", ex);
        }
        
        return immagini;        
    }
    
    @Override
    public List<CategoriaListe> getAll() throws DAOException {
        List<CategoriaListe> shoppingLists = new ArrayList<>();

        try (Statement stm = CON.createStatement()) {
            try (ResultSet rs = stm.executeQuery("SELECT * FROM Liste_categorie ORDER BY name")) {

                while (rs.next()) {
                    String nome = rs.getString("Nome");
                    String descrizione = rs.getString("Descrizione");
                    ArrayList<String> immagini = getListCategoryImages(nome);

                    CategoriaListe cat = new CategoriaListe(nome, descrizione, immagini); 

                    shoppingLists.add(cat);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of list category", ex);
        }

        return shoppingLists;
    }
    
    @Override
    public Long getCount() throws DAOException {
        try (Statement stmt = CON.createStatement()) {
            ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM Liste_categorie");
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count list category", ex);
        }

        return 0L;
    }
}