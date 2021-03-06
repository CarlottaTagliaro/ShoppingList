/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import de.scravy.pair.Pair;
import de.scravy.pair.Pairs;
import it.webproject2018.db.daos.ListaPermessiDAO;
import it.webproject2018.db.entities.ListaPermessi;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The JDBC implementation of the {@link ListaPermessiDAO} interface.
 *
 * @author davide
 */
public class JDBCListaPermessiDAO extends JDBCDAO<ListaPermessi, Pair<String, Integer>> implements ListaPermessiDAO {

    public JDBCListaPermessiDAO(Connection conn) {
        super(conn);
    }

    @Override
    public ListaPermessi insert(ListaPermessi entity) throws DAOException {
        if (entity == null) {
            throw new DAOException("list permission parameter is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("INSERT INTO Utenti_Liste (Email, ID, Perm_edit, Perm_add_rem, Perm_del, Accettato, Data_inserimento) VALUES (?, ?, ?, ?, ?, ?, NOW());")) {
            stm.setString(1, entity.getEmail());
            stm.setInt(2, entity.getId_lista());
            stm.setBoolean(3, entity.getPerm_edit());
            stm.setBoolean(4, entity.getPerm_add_rem());
            stm.setBoolean(5, entity.getPerm_del());
            stm.setBoolean(6, entity.getAccettato());
            Integer rs = stm.executeUpdate();

            if (rs > 0) {
                return entity;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while insert list permission " + e);
        }
    }

    @Override
    public ListaPermessi getByPrimaryKey(Pair<String, Integer> primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("lista ID is null");
        }

        try (PreparedStatement stm = CON.prepareStatement("select * from Utenti_Liste where Email = ? AND ID = ? ")) {
            stm.setString(1, primaryKey.getFirst());
            stm.setInt(2, primaryKey.getSecond());
            try (ResultSet rs = stm.executeQuery()) {

                if (rs.next()) {

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
        } catch (SQLException ex) {
            throw new DAOException("Error while getting Product by ID", ex);
        }

        return null;
    }

    @Override
    public List<ListaPermessi> getAll() throws DAOException {
        ArrayList<ListaPermessi> liste = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select * from Utenti_Liste")) {
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    Pair<String, Integer> primaryKey = Pairs.from(rs.getString("Email"), rs.getInt("ID"));

                    ListaPermessi lista_perm = getByPrimaryKey(primaryKey);

                    liste.add(lista_perm);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting all Products", ex);
        }

        return liste;
    }

    @Override
    public ArrayList<ListaPermessi> getAllByList(Integer idList, String emailUser) throws DAOException {
        ArrayList<ListaPermessi> liste = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select * from Utenti_Liste where ID = ? and Email != ?")) {
            stm.setInt(1, idList);
            stm.setString(2, emailUser);
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    Pair<String, Integer> primaryKey = Pairs.from(rs.getString("Email"), rs.getInt("ID"));

                    ListaPermessi lista_perm = getByPrimaryKey(primaryKey);

                    liste.add(lista_perm);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting all Products", ex);
        }

        return liste;
    }

    @Override
    public List<Pair<Utente, ListaPermessi>> getShareUserList(Utente user, String qry, Integer idLista) throws DAOException {
        ArrayList<Pair<Utente, ListaPermessi>> liste = new ArrayList<>();
        JDBCUtenteDAO UtenteDao = new JDBCUtenteDAO(CON);

        try (PreparedStatement stm = CON.prepareStatement("select Utenti.Email, Nome, Cognome, Perm_edit, Perm_add_rem, Perm_del, Accettato, ID from Utenti LEFT JOIN (select * from Utenti_Liste where ID = ?) as a on a.Email = Utenti.Email"
                + " WHERE (CONCAT(Nome, \" \", Cognome) LIKE ? OR Utenti.Email LIKE ?) AND Utenti.Email != ?")) {
            stm.setInt(1, idLista);
            stm.setString(2, "%" + qry + "%");
            stm.setString(3, "%" + qry + "%");
            stm.setString(4, user.getEmail());
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    String email = rs.getString("Email");
                    Pair<String, Integer> primaryKey = Pairs.from(email, rs.getInt("ID"));
                    ListaPermessi lista_perm;

                    if (rs.wasNull()) {
                        lista_perm = new ListaPermessi(email, idLista);
                    } else {
                        lista_perm = getByPrimaryKey(primaryKey);
                    }

                    Utente utente = UtenteDao.getByPrimaryKey(primaryKey.getFirst());

                    liste.add(Pairs.from(utente, lista_perm));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting getShareUserList", ex);
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
    public ListaPermessi update(ListaPermessi entity) throws DAOException {
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
            stm.setInt(1, primaryKey.getSecond());
            stm.setString(2, primaryKey.getFirst());
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
