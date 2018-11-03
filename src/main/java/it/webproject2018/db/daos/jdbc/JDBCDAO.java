/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import it.webproject2018.db.daos.DAO;
import it.webproject2018.db.exceptions.DAOFactoryException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 * This is the base DAO class all concrete DAO using JDBC technology
 * must extend.
 * 
 * @author davide
 * @param <ENTITY_CLASS> the class of the entities the dao handle.
 * @param <PRIMARY_KEY_CLASS> the class of the primary key of the entity the
 * dao handle.
 */
public abstract class JDBCDAO<ENTITY_CLASS, PRIMARY_KEY_CLASS> implements DAO<ENTITY_CLASS, PRIMARY_KEY_CLASS> {

    /**
     * The JDBC {@link Connection} used to access the persistence system.
     */
    protected final Connection CON;
	protected final ServletContext SC;
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
    final private Connection CreateDbConn(ServletContext sc) {
        String dburl = sc.getInitParameter("dburl");
        String dbuser = sc.getInitParameter("dbuser");
        String dbpsw = sc.getInitParameter("dbpsw");

        try {

            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException cnfe) {
                throw new RuntimeException(cnfe.getMessage(), cnfe.getCause());
            }

            Connection conn = DriverManager.getConnection(dburl, dbuser, dbpsw);
            return conn;
        } catch (SQLException ex) {

            Logger.getLogger(getClass().getName()).severe(ex.toString());
            throw new RuntimeException(ex);

        }
    }

    protected JDBCDAO(Connection conn) {
        super();

        this.SC = null;
        this.CON = conn;
        FRIEND_DAOS = new HashMap<>();
    }

    protected JDBCDAO(ServletContext sc) {
        super();

        this.SC = sc;
        this.CON = CreateDbConn(sc);
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
