/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import it.webproject2018.db.daos.ListaPermessiDAO;
import it.webproject2018.db.entities.ListaPermessi;
import it.webproject2018.db.exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import javax.servlet.ServletContext;

/**
 *
 * @author davide
 */
public class JDBCListaPermessiDAO extends JDBCDAO<ListaPermessi, Pair<String, Integer>> implements ListaPermessiDAO {
    
    public JDBCListaPermessiDAO(Connection conn) {
        super(conn);
    }    
    
    public JDBCListaPermessiDAO(ServletContext sc) {
        super(sc);
    }    
    
    @Override
    public ListaPermessi insert(ListaPermessi entity) throws DAOException{
        if (entity == null) {
            throw new DAOException("list permission parameter is null");
        }
        try {
            PreparedStatement stm = CON.prepareStatement("INSERT INTO Utenti_Liste (Email, ID, Perm_edit, Perm_add_rem, Perm_del, Accettato) VALUES (?, ?, ?, ?, ?, ?);");
            stm.setString(1, entity.getEmail());
            stm.setInt(2, entity.getId_lista());
            stm.setBoolean(3, entity.getPerm_edit());
            stm.setBoolean(4, entity.getPerm_add_rem());
            stm.setBoolean(5, entity.getPerm_del());
            stm.setBoolean(6, entity.getAccettato());
            Integer rs = stm.executeUpdate();
            
            if (rs > 0)
                return entity;
            
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    
    @Override
    public ListaPermessi getByPrimaryKey(Pair<String, Integer> primaryKey) throws DAOException{
        if (primaryKey == null) {
            throw new DAOException("lista ID is null");
        }
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Utenti_Liste where Email = ? AND ID = ? ")) {
            stm.setString(1, primaryKey.getKey());
            stm.setInt(2, primaryKey.getValue());
            try (ResultSet rs = stm.executeQuery()) {

                if(rs.next()){

                    Boolean perm_edit = rs.getBoolean("perm_edit");
                    Boolean perm_add_rem = rs.getBoolean("perm_add_rem");
                    Boolean perm_del = rs.getBoolean("perm_del");
                    Boolean accettato = rs.getBoolean("accettato");
                    Integer id = rs.getInt("ID");
                    String email = rs.getString("Email");
                    
                    ListaPermessi lista_perm = new ListaPermessi(perm_edit, perm_add_rem, perm_del, accettato, email, id);
                    
                    return lista_perm;
                }
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Error while getting Product by ID", ex);
        }
        
        return null;
    }
    
    @Override
    public List<ListaPermessi> getAll() throws DAOException {
        ArrayList<ListaPermessi> liste = new ArrayList<>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Utenti_Liste")) {
            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){    
                    Pair<String, Integer> primaryKey = new Pair<>(rs.getString("Email"), rs.getInt("ID"));
                    
                    ListaPermessi lista_perm = getByPrimaryKey(primaryKey);

                    liste.add(lista_perm);
                }
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Error while getting all Products", ex);
        }
        
        return liste;
    }
    
    @Override
    public Long getCount() throws DAOException {
        try (Statement stmt = CON.createStatement()) {
            ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM Utenti_Liste");
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count lists", ex);
        }

        return 0L;
    }
    
    @Override
    public ListaPermessi update(ListaPermessi entity) throws DAOException{
        if (entity == null) {
            throw new DAOException("Parameter 'list' not valid for update",
                    new IllegalArgumentException("The passed list is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE Utenti_Liste "
                + "SET Perm_edit = ?, Perm_add_rem = ?, Perm_del = ?, Accettato = ? "
                + "WHERE Email = ? AND ID = ?")) {
            std.setBoolean(1, entity.getPerm_edit());
            std.setBoolean(2, entity.getPerm_add_rem());
            std.setBoolean(3, entity.getPerm_del());
            std.setBoolean(4, entity.getAccettato());
            std.setString(5, entity.getEmail());
            std.setInt(6, entity.getId_lista());
            if (std.executeUpdate() == 1) {
                return entity;
            } else {
                throw new DAOException("Impossible to update the list");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the list", ex);
        }
    }

    @Override
    public Boolean delete(Pair<String, Integer> primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("Prodotto is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("DELETE FROM Utenti_Liste where ID = ? AND Email = ?")) {
            stm.setInt(1, primaryKey.getValue());
            stm.setString(2, primaryKey.getKey());
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
