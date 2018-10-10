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
	
	/**
	 * Returns the number of records of {@code ENTITY_CLASS} stored on the
	 * persistence system of the application.
	 *
	 * @return the number of records present into the storage system.
	 * @throws DAOException if an error occurred during the information
	 * retrieving.
	 */
	public Long getCount() throws DAOException;
	
	/**
	 * Returns the {@code ENTITY_CLASS} instance of the storage system record
	 * with the primary key equals to the one passed as parameter.
	 *
	 * @param primaryKey the primary key used to obtain the entity instance.
	 * @return the {@code ENTITY_CLASS} instance of the storage system record
	 * with the primary key equals to the one passed as parameter or
	 * {@code null} if no entities with that primary key is present into the
	 * storage system.
	 * @throws DAOException if an error occurred during the information
	 * retrieving.
	 */
	public ENTITY_CLASS getByPrimaryKey(PRIMARY_KEY_CLASS primaryKey) throws DAOException;
	
	/**
	 * Returns the list of all the valid entities of type {@code ENTITY_CLASS}
	 * stored by the storage system.
	 *
	 * @return the list of all the valid entities of type {@code ENTITY_CLASS}.
	 * @throws DAOException if an error occurred during the information
	 * retrieving.
	 */
	public List<ENTITY_CLASS> getAll() throws DAOException;
		
	/**
	 * Insert the entity of type {@code ENTITY_CLASS} in the persistence
	 * layer
	 *
	 * @param entity the entity to insert
	 * @return the status of the insertion
	 * @throws DAOException if an error occurred during the action.
	 */
	public Boolean insert(ENTITY_CLASS entity) throws DAOException;
	
	/**
	 * Updates the entity of type {@code ENTITY_CLASS} passed as parameter 
	 * and returns it.
	 * 
	 * @param entity the entity to update
	 * @return the updated entity.
	 * @throws DAOException if an error occurred during the action.
	 */
	public ENTITY_CLASS update(ENTITY_CLASS entity) throws DAOException;
	
	/**
	 * If this DAO can interact with it, then returns the DAO of class passed as
	 * parameter.
	 *
	 * @param <DAO_CLASS> the class name of the DAO that can interact with this
	 * DAO.
	 * @param daoClass the class of the DAO that can interact with this DAO.
	 * @return the instance of the DAO or null if no DAO of the type passed as
	 * parameter can interact with this DAO.
	 * @throws DAOFactoryException if an error occurred.
	 */
    public <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> daoClass) throws DAOFactoryException;
	
	/**
	 * Deletes the entity determined by the primaryKey passed
	 * from the persistence layer
	 *
	 * @param primaryKey the primary key of the entity to remove.
	 * @return the status of the deletion
	 * @throws DAOException if an error occurred during the action
	 */
	public Boolean delete(PRIMARY_KEY_CLASS primaryKey) throws DAOException;
}