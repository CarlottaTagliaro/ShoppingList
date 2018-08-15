/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos;

import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.exceptions.DAOFactoryException;
import java.util.List;

/**
 * 
 * The basic DAO interface that all DAOs must implement.
 * Persistence Layer: DAO pattern
 * DAOs act as an intermediary between the application and the persistence layer.
 * 
 * @param <ENTITY_CLASS> the class of the entity to handle.
 * @param <PRIMARY_KEY_CLASS> the class of the primary key of the entity the DAO
 * handle.
 * 
 * @author Max
 */
public interface DAO<ENTITY_CLASS, PRIMARY_KEY_CLASS> {
	
	public Long getCount() throws DAOException;
}
