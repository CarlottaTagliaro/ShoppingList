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
import javax.servlet.ServletContext;

/**
 *
 * @author davide
 */
public class JDBCUtenteDAO extends JDBCDAO<Utente, String> implements UtenteDAO {

    public JDBCUtenteDAO(Connection con) {
        super(con);
    }

    public JDBCUtenteDAO(ServletContext sc) {
        super(sc);
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

                    return user;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting User by Email", ex);
        }

        return null;
    }

    @Override
    public String createRememberMeID(String userEmail) throws DAOException {
        if (userEmail == null) {
            throw new DAOException("userEmail is null");
        }

        try (PreparedStatement stm = CON.prepareStatement("SELECT SHA2(CONCAT(Email, Password), 256) as id FROM Utenti WHERE Email = ?")) {
            stm.setString(1, userEmail);
            try (ResultSet rs = stm.executeQuery()) {

                if (rs.next()) {
                    return rs.getString("id");
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while creating Remember Me ID", ex);
        }

        return null;
    }

    @Override
    public Utente getUserByRememberMeID(String id) throws DAOException {
        if (id == null) {
            throw new DAOException("Remember me id is null");
        }

        try (PreparedStatement stm = CON.prepareStatement("SELECT Email FROM Utenti WHERE SHA2(CONCAT(Email, Password), 256) = ?")) {
            stm.setString(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return getByPrimaryKey(rs.getString("Email"));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting user by Remember Me ID", ex);
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

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    user = new Utente();

                    user.setName(rs.getString("Nome"));
                    user.setSurname(rs.getString("Cognome"));
                    user.setEmail(rs.getString("Email"));
                    user.setPicture(rs.getString("Immagine"));
                    user.setIsAdmin(rs.getBoolean("IsAdmin"));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while authenticating User", ex);
        }
        return user;
    }

    @Override
    public Long getCount() throws DAOException {
        try (Statement stmt = CON.createStatement()) {
            try (ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM Utenti")) {
                if (counter.next()) {
                    return counter.getLong(1);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to count users", ex);
        }

        return 0L;
    }

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

                    utenti.add(user);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
        return utenti;
    }

    @Override
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

    public Utente updatePassword(Utente user, String password) throws DAOException {
        if (user == null) {
            throw new DAOException("Parameter 'user' not valid for update",
                    new IllegalArgumentException("The passed user is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE Utenti SET Password = SHA2(?, 256) WHERE Email = ?")) {
            std.setString(1, password);
            std.setString(2, user.getEmail());
            if (std.executeUpdate() == 1) {
                return user;
            } else {
                throw new DAOException("Impossible to update the user");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the user", ex);
        }
    }

    public Boolean updateUserLastAccess(Utente user) throws DAOException {
        if (user == null) {
            throw new DAOException("Parameter 'user' not valid for update",
                    new IllegalArgumentException("The passed user is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE Utenti "
                + "SET Ultima_visualizzazione = NOW() WHERE Email = ?")) {
            std.setString(1, user.getEmail());
            if (std.executeUpdate() == 1) {
                return true;
            } else {
                throw new DAOException("Impossible to update the  the user");
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
        try (PreparedStatement stm = CON.prepareStatement("INSERT INTO Utenti (Nome, Cognome, Email, Immagine, Password, IsAdmin, Ultima_visualizzazione) VALUES (?, ?, ?, ?, SHA2(?, 256), ?, NOW());")) {
            stm.setString(1, name);
            stm.setString(2, surname);
            stm.setString(3, userEmail);
            stm.setString(4, "");
            stm.setString(5, password);
            stm.setInt(6, 0);
            Integer rs = stm.executeUpdate();

            if (rs <= 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Utente insert(Utente entity) throws DAOException {
        //TODO: pensare come sistemarlo
        return null;
    }

    @Override
    public Boolean delete(String primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("Utente is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("DELETE FROM Utenti where Email = ? ")) {
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
