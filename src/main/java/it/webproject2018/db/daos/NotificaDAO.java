/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.entities.Notifica;
import it.webproject2018.db.exceptions.DAOException;
import java.util.ArrayList;

/**
 *
 * @author alberto
 */
public interface NotificaDAO extends DAO<Notifica, Integer> {
    
    public ArrayList<Notifica> getNotificationByUserEmail(String userEmail) throws DAOException;
    
    public void generateNotificationsByProducts() throws DAOException;
    
    public ArrayList<Notifica> getAllNotificationsNotSentByEmail() throws DAOException;
}

