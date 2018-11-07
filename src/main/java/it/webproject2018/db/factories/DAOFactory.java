/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.factories;

import it.webproject2018.db.daos.DAO;
import it.webproject2018.db.exceptions.DAOFactoryException;

/**
 * This interface must be implemented by all the concrete {@code DAOFactor(y)}
 * ies.
 *
 * @author Max
 */
public interface DAOFactory {

    /**
     * Shutdowns the connection to the storage system.
     */
    public void shutdown();

    /**
     * Returns the concrete {@link DAO dao} which type is the class passed as
     * parameter.
     *
     * @param <DAO_CLASS> the class name of the {@code dao} to get.
     * @param daoInterface the class instance of the {@code dao} to get.
     * @return the concrete {@code dao} which type is the class passed as
     * parameter.
     * @throws DAOFactoryException if an error occurred during the operation.
     */
    public <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> daoInterface) throws DAOFactoryException;
}
