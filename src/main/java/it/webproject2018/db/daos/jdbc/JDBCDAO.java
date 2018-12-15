/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import it.webproject2018.db.daos.DAO;
import it.webproject2018.db.exceptions.DAOFactoryException;
import java.sql.Connection;
import java.util.HashMap;

/**
 * This is the base DAO class all concrete DAO using JDBC technology must
 * extend.
 *
 * @author davide
 * @param <ENTITY_CLASS> the class of the entities the dao handle.
 * @param <PRIMARY_KEY_CLASS> the class of the primary key of the entity the dao
 * handle.
 */
public abstract class JDBCDAO<ENTITY_CLASS, PRIMARY_KEY_CLASS> implements DAO<ENTITY_CLASS, PRIMARY_KEY_CLASS> {

    /**
     * The JDBC {@link Connection} used to access the persistence system.
     */
    protected final Connection CON;

    /**
     * The list of other DAOs this DAO can interact with.
     */
    protected final HashMap<Class, DAO> FRIEND_DAOS;

    /**
     * This function establishes a connection with the mysql database
     *
     * @param sc the ServletContext
     * @return the connection
     */
    protected JDBCDAO(Connection conn) {
        super();

        this.CON = conn;
        FRIEND_DAOS = new HashMap<>();
    }

    @Override
    public void Close() {
        try {
            this.CON.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> daoClass) throws DAOFactoryException {
        return (DAO_CLASS) FRIEND_DAOS.get(daoClass);
    }
}
