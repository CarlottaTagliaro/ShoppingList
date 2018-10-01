/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import it.webproject2018.db.daos.MessaggioChatDAO;
import it.webproject2018.db.entities.MessaggioChat;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.gmbal.generic.Triple;

/**
 *
 * @author Max
 */
public class JDBCMessaggioChatDAO extends JDBCDAO<MessaggioChat, Triple<String, Integer, Timestamp>> implements MessaggioChatDAO {

    public JDBCMessaggioChatDAO(Connection con) {
        super(con);
    }

    @Override
    public MessaggioChat getByPrimaryKey(Triple<String, Integer, Timestamp> primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("message primary key is null");
        }

        try (PreparedStatement stm = CON.prepareStatement("select * from Chat "
                + "where Email_sender = ? and ID_list = ? and Data = ? LIMIT 15")) {
            stm.setString(1, primaryKey.first());
            stm.setInt(2, primaryKey.second());
            stm.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(primaryKey.third()));
            
            try (ResultSet rs = stm.executeQuery()) {

                if(rs.next()){
                    String email_sender = rs.getString("Email_sender");
                    Integer id_list = rs.getInt("ID_list");
                    String message = rs.getString("Message");
                    Timestamp date = rs.getTimestamp("Data");

                    JDBCUtenteDAO JdbcUtenteDao = new JDBCUtenteDAO(CON);
                    Utente user = JdbcUtenteDao.getByPrimaryKey(email_sender);

                    MessaggioChat mex = new MessaggioChat(user, id_list, message, date);
                    return mex;
                }
                else 
                    return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting Product by ID", ex);
        }
    }

    @Override
    public List<MessaggioChat> getAll() throws DAOException {
        ArrayList<MessaggioChat> chat = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select * from Chat")) {
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    String email_sender = rs.getString("Email_sender");
                    Integer id_list = rs.getInt("ID_list");
                    Timestamp date = rs.getTimestamp("Data");

                    Triple<String, Integer, Timestamp> t = new Triple(email_sender, id_list, date);
                    MessaggioChat mex = this.getByPrimaryKey(t);
                    chat.add(mex);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting all messages", ex);
        }

        return chat;
    }

    @Override
    public Long getCount() throws DAOException {
        try (Statement stmt = CON.createStatement()) {
            ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM Chat");
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count messages", ex);
        }

        return 0L;
    }

    @Override
    public Boolean insert(MessaggioChat entity) throws DAOException {
        if (entity == null) {
            throw new DAOException("message parameter is null");
        }
        try {
            PreparedStatement stm = CON.prepareStatement("INSERT INTO Chat (Email_sender, ID_list, Message, Data)"
                    + "VALUES (?, ?, ?, ?);");
            stm.setString(1, entity.getSender().getEmail());
            stm.setInt(2, entity.getId_list());
            stm.setString(3, entity.getMessage());
            stm.setTimestamp(4, entity.getDate());
            Integer rs = stm.executeUpdate();

            return (rs > 0);
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public MessaggioChat update(MessaggioChat entity) throws DAOException {
        if (entity == null) {
            throw new DAOException("Parameter 'messaggio' not valid for update",
                    new IllegalArgumentException("The passed message is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE Chat "
                + "SET Message = ? WHERE Email_sender = ? and ID_list = ? and Data = ?")) {
            std.setString(1, entity.getMessage());
            std.setString(2, entity.getSender().getEmail());
            std.setInt(3, entity.getId_list());
            std.setTimestamp(4, entity.getDate());
            if (std.executeUpdate() == 1) {
                return entity;
            } else {
                throw new DAOException("Impossible to update the message");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the message", ex);
        }
    }

    @Override
    public ArrayList<MessaggioChat> getChatLastMessages(Integer id_list_chat) throws DAOException{
        if (id_list_chat == null) {
            throw new DAOException("id_list of the chat is null");
        }
        
        ArrayList<MessaggioChat> messages = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select * from Chat "
                + "where ID_list = ? LIMIT 15")) {
            stm.setInt(1, id_list_chat);
            try (ResultSet rs = stm.executeQuery()) {

                rs.next();

                String email_sender = rs.getString("Email_sender");
                Integer id_list = rs.getInt("ID_list");
                Timestamp date = rs.getTimestamp("Data");

                Triple<String, Integer, Timestamp> primaryKey = new Triple<>(email_sender, id_list, date);
                
                MessaggioChat mex = getByPrimaryKey(primaryKey);
                messages.add(mex);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting Product by ID", ex);
        }
        
        return messages;
    }
}
