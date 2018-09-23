/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import it.webproject2018.db.daos.UtenteDAO;
import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.entities.Utente;
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
public class JDBCUtenteDAO extends JDBCDAO<Utente, String> implements UtenteDAO {

    public JDBCUtenteDAO(Connection con) {
        super(con);
    }

    @Override
    public Utente getByPrimaryKey(String userEmail) throws DAOException {
        if (userEmail == null) {
            throw new DAOException("userEmail is null");
        }

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Utenti WHERE Email = ?")) {
            stm.setString(1, userEmail);
            try (ResultSet rs = stm.executeQuery()) {

                if (rs.next()) {
                    Utente user = new Utente();
                    user.setName(rs.getString("Nome"));
                    user.setSurname(rs.getString("Cognome"));
                    user.setEmail(rs.getString("Email"));
                    user.setPicture(rs.getString("Immagine"));;
                    user.setIsAdmin(rs.getBoolean("IsAdmin"));

                    JDBCListaDAO listaDao = new JDBCListaDAO(CON);
                    user.Liste = listaDao.getUserLists(user.getEmail());

                    return user;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting User by Email", ex);
        }

        return null;
    }

    @Override
    public Utente getUserAuthentication(String userEmail, String password) throws DAOException {
        if (userEmail == null || password == null) {
            throw new DAOException("userEmail or password is null");
        }

        Utente user = null;

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Utenti WHERE Email = ? AND Password = SHA2(?, 256)")) {
            stm.setString(1, userEmail);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                user = new Utente();

                user.setName(rs.getString("Nome"));
                user.setSurname(rs.getString("Cognome"));
                user.setEmail(rs.getString("Email"));
                user.setPicture(rs.getString("Immagine"));
                user.setIsAdmin(rs.getBoolean("IsAdmin"));

                JDBCListaDAO listaDao = new JDBCListaDAO(CON);
                user.Liste = listaDao.getUserLists(user.getEmail());
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while authenticating User", ex);
        }
        return user;
    }

    /**
     * Returns the number of {@link Utente utenti} stored on the persistence
     * system of the application.
     *
     * @return the number of records present into the storage system.
     * @throws DAOException if an error occurred during the information
     */
    @Override
    public Long getCount() throws DAOException {
        try (Statement stmt = CON.createStatement()) {
            ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM Utenti");
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count users", ex);
        }

        return 0L;
    }

    /**
     * Returns the list of all the valid {@link Utente utenti} stored by the
     * storage system.
     *
     * @return the list of all the valid {@code utenti}.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     */
    @Override
    public List<Utente> getAll() throws DAOException {
        List<Utente> utenti = new ArrayList<>();

        try (Statement stm = CON.createStatement()) {
            try (ResultSet rs = stm.executeQuery("SELECT * FROM Utenti ORDER BY Cognome, Nome")) {

                while (rs.next()) {
                    Utente user = new Utente();

                    user.setName(rs.getString("Nome"));
                    user.setSurname(rs.getString("Cognome"));
                    user.setEmail(rs.getString("Email"));
                    user.setPicture(rs.getString("Immagine"));
                    user.setIsAdmin(rs.getBoolean("IsAdmin"));

                    JDBCListaDAO listaDao = new JDBCListaDAO(CON);
                    user.Liste = listaDao.getUserLists(user.getEmail());

                    utenti.add(user);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
        return utenti;
    }

    /**
     * Update the user passed as parameter and returns it. Senza email perchè
     * primary key
     *
     * @param user the user used to update the persistence system.
     * @return the updated user.
     * @throws DAOException if an error occurred during the action.
     */
    
    public Utente update(Utente user) throws DAOException {
        if (user == null) {
            throw new DAOException("Parameter 'user' not valid for update",
                    new IllegalArgumentException("The passed user is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE Utenti "
                + "SET Nome = ?, Cognome = ?, Immagine = ?, IsAdmin = ? WHERE Email = ?")) {
            std.setString(1, user.getName());
            std.setString(2, user.getSurname());
            std.setString(3, user.getPicture());
            std.setBoolean(4, user.getIsAdmin());
            std.setString(5, user.getEmail());
            if (std.executeUpdate() == 1) {
                return user;
            } else {
                throw new DAOException("Impossible to update the user");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the user", ex);
        }
    }
    
	@Override
    public Boolean RegisterUser(String name, String surname, String userEmail, String password) throws DAOException {
        if (userEmail == null || password == null || name == null || surname == null) {
            throw new DAOException("userEmail or password or name or surname is null");
        }
        try {
            PreparedStatement stm = CON.prepareStatement("INSERT INTO Utenti (Nome, Cognome, Email, Immagine, Password, IsAdmin) VALUES (?, ?, ?, ?, SHA2(?, 256), ?);");
            stm.setString(1, name);
            stm.setString(2, surname);
            stm.setString(3, userEmail);
            stm.setString(4, "");
            stm.setString(5, password);
            stm.setInt(6, 0);
            Integer rs = stm.executeUpdate();
            
            if(rs <= 0) {
                return false;
            }
            else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
