/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.entities.MessaggioChat;
import it.webproject2018.db.exceptions.DAOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.glassfish.gmbal.generic.Triple;

/**
 * DAO interface for MessaggioChat
 * 
 * @author Max
 */
public interface MessaggioChatDAO extends DAO<MessaggioChat, Triple<String, Integer, Timestamp>> {
	
	/**
	 * Retrieves the last messages in the chat of some list
	 * 
	 * @param id_list_chat the ID of the list
	 * @param lasttime the time considered
	 * @return a list
	 * @throws DAOException if an error occurred during the information
	 * retrieving
	 */
    public ArrayList<MessaggioChat> getChatLastMessages(Integer id_list_chat, Timestamp lasttime) throws DAOException;
}
