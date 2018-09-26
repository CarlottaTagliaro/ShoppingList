/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.entities.MessaggioChat;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;
import java.sql.Date;
import java.util.ArrayList;
import org.glassfish.gmbal.generic.Triple;

/**
 *
 * @author Max
 */
public interface MessaggioChatDAO extends DAO<MessaggioChat, Triple<String, Integer, Date>> {
    public ArrayList<MessaggioChat> getChatLastMessages(Integer id_list_chat) throws DAOException;
}