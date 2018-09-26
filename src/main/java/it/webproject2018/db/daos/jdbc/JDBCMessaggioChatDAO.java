/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import it.webproject2018.db.daos.MessaggioChatDAO;
import it.webproject2018.db.entities.MessaggioChat;
import it.webproject2018.db.exceptions.DAOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.gmbal.generic.Triple;
/**
 *
 * @author Max
 */
public class JDBCMessaggioChatDAO extends JDBCDAO<MessaggioChat, Triple<String, Integer, Date>> implements MessaggioChatDAO {
	
	public JDBCMessaggioChatDAO(Connection con) {
		super(con);
	}

	@Override
	public MessaggioChat getByPrimaryKey(Triple<String, Integer, Date> primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("message primary key is null");
        }
		
		try (PreparedStatement stm = CON.prepareStatement("select * from Chat "
				+ "where Email_sender = ? and ID_list = ? and Data = ?")) {
            stm.setString(1, primaryKey.first());
            stm.setInt(2, primaryKey.second());
            stm.setDate(3, primaryKey.third());
            try (ResultSet rs = stm.executeQuery()) {

                rs.next();

				String email_sender = rs.getString("Email_sender");
				Integer id_list = rs.getInt("ID_list");
				String message = rs.getString("Message");
				Date date = rs.getDate("Data");
				
				MessaggioChat mex = new MessaggioChat(email_sender, id_list, message, date);
                return mex;
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Error while getting Product by ID", ex);
        }
	}
	
	@Override
    public List<MessaggioChat> getAll() throws DAOException {
        ArrayList<MessaggioChat> chat = new ArrayList<>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Chat")) {
            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){                
                    String email_sender = rs.getString("Email_sender");
					Integer id_list = rs.getInt("ID_list");
					Date date = rs.getDate("Data");
                    
					Triple <String, Integer, Date>t = new Triple(email_sender, id_list, date);
					MessaggioChat mex = this.getByPrimaryKey(t);
					chat.add(mex);
                }
            }
        }
        catch (SQLException ex) {
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
	public MessaggioChat insert(MessaggioChat entity) throws DAOException{
        if (entity == null) {
            throw new DAOException("message parameter is null");
        }
        try {
            PreparedStatement stm = CON.prepareStatement("INSERT INTO Chat (Email_sender, ID_list, Message, Data)"
					+ "VALUES (?, ?, ?, ?);");
            stm.setString(1, entity.getEmail_sender());
            stm.setInt(2, entity.getId_list());
            stm.setString(3, entity.getMessage());
            stm.setDate(4, entity.getDate());
            Integer rs = stm.executeUpdate();
            
            if(rs <= 0) {
                return null;
            }
            else {
                return entity;
            }
        } catch (SQLException e) {
            return null;
        }
    }
	
	@Override
	public MessaggioChat update(MessaggioChat entity) throws DAOException{
        if (entity == null) {
            throw new DAOException("Parameter 'messaggio' not valid for update",
                    new IllegalArgumentException("The passed message is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE Chat "
                + "SET Message = ? WHERE Email_sender = ? and ID_list = ? and Data = ?")) {
            std.setString(1, entity.getMessage());
            std.setString(2, entity.getEmail_sender());
            std.setInt(3, entity.getId_list());
			std.setDate(4, entity.getDate());
            if (std.executeUpdate() == 1) {
                return entity;
            } else {
                throw new DAOException("Impossible to update the message");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the message", ex);
        }
    }

}
