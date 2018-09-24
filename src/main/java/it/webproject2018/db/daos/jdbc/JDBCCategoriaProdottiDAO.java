/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import it.webproject2018.db.daos.CategoriaProdottiDAO;
import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.entities.CategoriaListe;
import it.webproject2018.db.entities.CategoriaProdotti;
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
public class JDBCCategoriaProdottiDAO extends JDBCDAO<CategoriaProdotti, String> implements CategoriaProdottiDAO  {    
    public JDBCCategoriaProdottiDAO(Connection con) {
        super(con);
    }    
    
    @Override
    public CategoriaProdotti getByPrimaryKey(String primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti_categorie where Nome = ? ")) {
            stm.setString(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {

                if(rs.next()){

                    String nome = rs.getString("Nome");
                    String descrizione = rs.getString("Descrizione");;
                    String logo = rs.getString("Logo");
                    JDBCCategoriaListeDAO categoriaListeDao = new JDBCCategoriaListeDAO(CON);
                    CategoriaListe categoria = categoriaListeDao.getByPrimaryKey(rs.getString("Nome_liste_cat"));

                    CategoriaProdotti cat = new CategoriaProdotti(nome, descrizione, logo, categoria);              

                    return cat;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the Product category for the passed primary key", ex);
        }
        
        return null;
    }
    
    
    @Override
    public List<CategoriaProdotti> getAll() throws DAOException {
        List<CategoriaProdotti> lista = new ArrayList<>();

        try (Statement stm = CON.createStatement()) {
            try (ResultSet rs = stm.executeQuery("SELECT * FROM Prodotti_categorie ORDER BY name")) {

                while (rs.next()) {
                    String nome = rs.getString("Nome");
                    String descrizione = rs.getString("Descrizione");;
                    String logo = rs.getString("Logo");
                    JDBCCategoriaListeDAO categoriaListeDao = new JDBCCategoriaListeDAO(CON);
                    CategoriaListe categoria = categoriaListeDao.getByPrimaryKey(rs.getString("Nome_liste_cat"));

                    CategoriaProdotti cat = new CategoriaProdotti(nome, descrizione, logo, categoria);

                    lista.add(cat);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of list category", ex);
        }

        return lista;
    }
    
    @Override
    public Long getCount() throws DAOException {
        try (Statement stmt = CON.createStatement()) {
            ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM Prodotti_categorie");
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count product category", ex);
        }

        return 0L;
    }
    
    
    @Override
    public CategoriaProdotti insert(CategoriaProdotti entity) throws DAOException{
        if (entity == null) {
            throw new DAOException("CategoriaProdotti parameter is null");
        }
        try {
            PreparedStatement stm = CON.prepareStatement("INSERT INTO Prodotti_categorie (Nome, Descrizione, Logo, Nome_liste_cat) VALUES (?, ?, ?, ?);");
            stm.setString(1, entity.getNome());
            stm.setString(2, entity.getDescrizione());
            stm.setString(3, entity.getLogo());
            stm.setString(4, entity.getCategoriaLista().getNome());
            Integer rs = stm.executeUpdate();
            
            if(rs <= 0) {
                return null;
            }
            else {
                return entity;
            }
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public CategoriaProdotti update(CategoriaProdotti entity) throws DAOException{
        if (entity == null) {
            throw new DAOException("Parameter 'CategoriaProdotti' not valid for update",
                    new IllegalArgumentException("The passed CategoriaProdotti is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE Prodotti_categorie "
                + "SET Descrizione = ?, Logo = ?, Nome_liste_cat = ? WHERE Nome = ?")) {
            std.setString(1, entity.getDescrizione());
            std.setString(2, entity.getLogo());
            std.setString(3, entity.getCategoriaLista().getNome());
            std.setString(4, entity.getNome());
            if (std.executeUpdate() == 1) {
                return entity;
            } else {
                throw new DAOException("Impossible to update the CategoriaProdotti");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the CategoriaProdotti", ex);
        }
    }
}
