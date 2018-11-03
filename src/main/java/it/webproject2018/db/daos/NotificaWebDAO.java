/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.entities.NotificaWeb;
import it.webproject2018.db.exceptions.DAOException;
import java.util.List;

/**
 * DAO interface for NotificaWeb
 * 
 * @author davide
 */
public interface NotificaWebDAO extends DAO<NotificaWeb, String> {
	
	/**
	 * 
	 * @param email
	 * @return
	 * @throws DAOException if an error occurred during the information
	 * retrieving
	 */
	public List<NotificaWeb> getAllUserNotifications(String email) throws DAOException;
	
}
