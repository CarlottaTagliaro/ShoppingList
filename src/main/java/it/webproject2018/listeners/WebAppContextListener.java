/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.listeners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Max
 * 
 * ancora da testare
 */
public class WebAppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        String dburl = sce.getServletContext().getInitParameter("dburl");
        String dbname = sce.getServletContext().getInitParameter("dbname");
        String dbpsw = sce.getServletContext().getInitParameter("dbpsw");

        try {

            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException cnfe) {
                throw new RuntimeException(cnfe.getMessage(), cnfe.getCause());
            }

            Connection conn = DriverManager.getConnection(dburl, dbname, dbpsw);
            
            sce.getServletContext().setAttribute("connection", conn);           

        } catch (SQLException ex) {

            Logger.getLogger(getClass().getName()).severe(ex.toString());
            throw new RuntimeException(ex);

        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //DBManager.shutdown();
    }
}
