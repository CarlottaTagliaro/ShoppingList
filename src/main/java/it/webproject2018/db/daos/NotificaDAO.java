/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.entities.Notifica;
import it.webproject2018.db.exceptions.DAOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO interface for Notifica
 * 
 * @author alberto
 */
public interface NotificaDAO extends DAO<Notifica, Integer> {
    
	/**
	 * 
	 * @param userEmail
	 * @return
	 * @throws DAOException if an error occurred during the information
	 * retrieving
	 */
    public ArrayList<Notifica> getNotificationByUserEmail(String userEmail) throws DAOException;
    
	/**
	 * 
	 * @throws DAOException if an error occurred during the action
	 */
	public void generateNotificationsByProducts() throws DAOException;
    
	/**
	 * 
	 * @return
	 * @throws DAOException if an error occurred during the information
	 * retrieving
	 */
    public ArrayList<Notifica> getAllNotificationsNotSentByEmail() throws DAOException;
	
	/**
	 * 
	 * @param notifications
	 * @param fielName
	 * @param value
	 * @throws DAOException if an error occurred during the action
	 */
	public void setFieldTo(List<Notifica> notifications, String fielName, Object value) throws DAOException;
}

