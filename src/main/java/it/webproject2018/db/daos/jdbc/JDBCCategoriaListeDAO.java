/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import it.webproject2018.db.daos.CategoriaListeDAO;
import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.entities.CategoriaListe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

/**
 * The JDBC implementation of the {@link CategoriaListeDAO} interface.
 * 
 * @author davide
 */
public class JDBCCategoriaListeDAO extends JDBCDAO<CategoriaListe, String> implements CategoriaListeDAO {

    public JDBCCategoriaListeDAO(ServletContext sc) {
        super(sc);
    }

    public JDBCCategoriaListeDAO(Connection conn) {
        super(conn);
    }

	@Override
    public List<String> getAllNames() throws DAOException {
        List<String> names = new ArrayList<>();

        try (Statement stm = CON.createStatement()) {
            try (ResultSet rs = stm.executeQuery("select Nome from Liste_categorie order by Nome;")) {

                while (rs.next()) {
                    String nome = rs.getString("Nome");

                    names.add(nome);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of list category", ex);
        }

        return names;
    }

    @Override
    public CategoriaListe getByPrimaryKey(String primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Liste_categorie WHERE Nome = ?;")) {
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

    @Override
    public ArrayList<String> getListCategoryImages(String catNome) throws DAOException {
        if (catNome == null) {
            throw new DAOException("catNome is null");
        }

        ArrayList<String> immagini = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select * from Liste_categorie_immagini where Nome = ? ")) {
            stm.setString(1, catNome);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    String immagine = rs.getString("Immagine");

                    immagini.add(immagine);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting Category of the List", ex);
        }

        return immagini;
    }

    @Override
    public List<CategoriaListe> getAll() throws DAOException {
        List<CategoriaListe> shoppingLists = new ArrayList<>();

        try (Statement stm = CON.createStatement()) {
            try (ResultSet rs = stm.executeQuery("SELECT * FROM Liste_categorie ORDER BY Nome")) {

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

    @Override
    public CategoriaListe insert(CategoriaListe entity) throws DAOException {
        if (entity == null) {
            throw new DAOException("CategoriaListe parameter is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("INSERT INTO Liste_categorie (Nome, Descrizione) VALUES (?, ?);")) {
            stm.setString(1, entity.getNome());
            stm.setString(2, entity.getDescrizione());
            Integer rs = stm.executeUpdate();

            if (rs <= 0) {
                return getByPrimaryKey(entity.getNome());
            }

            return entity;
        } catch (Exception e) {
            return null;
        }
    }

	@Override
    public Boolean insertImage(CategoriaListe entity, String img) throws DAOException {
        if (entity == null || img == null) {
            throw new DAOException("product parameter is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("INSERT INTO Liste_categorie_immagini (Nome, Immagine) VALUES (?, ?)")) {
            stm.setString(1, entity.getNome());
            stm.setString(2, img);
            Integer rs = stm.executeUpdate();

            return (rs > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CategoriaListe update(CategoriaListe entity) throws DAOException {
        if (entity == null) {
            throw new DAOException("Parameter 'CategoriaListe' not valid for update",
                    new IllegalArgumentException("The passed CategoriaListe is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE Liste_categorie "
                + "SET Descrizione = ? WHERE Nome = ?")) {
            std.setString(1, entity.getDescrizione());
            std.setString(2, entity.getNome());
            if (std.executeUpdate() == 1) {
                return entity;
            } else {
                throw new DAOException("Impossible to update the CategoriaListe");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the CategoriaListe", ex);
        }
    }

    @Override
    public Boolean delete(String primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("Categoria liste is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("DELETE FROM Liste_categorie WHERE Nome = ? ")) {
            stm.setString(1, primaryKey);
            int res = stm.executeUpdate();
            if (res >= 1) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            return false;
        }
    }
}
