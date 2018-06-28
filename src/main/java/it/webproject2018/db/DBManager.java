/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db;

/*import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;*/
import it.webproject2018.listeners.WebAppContextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
/**
 *
 * @author Max
 * Gestore per la connessione al database
 */
public class DBManager {
// transient: uso di serializzazione, quando convertiamo un oggetto in uno 
// stream, in modo da poterne gestire la persistenza o la remotizzazione. La 
// var CON non deve essere serializzata
	private final transient Connection CON;
	
	/**
	 * URL format is jdbc:sub-protocol:sub-name
	 * sub-protocol is used by DriverManager to locate the driver
	 * sub-name		is used by the driver to identify which database to connect to
	 * @param dbUrl
	 * @throws SQLException 
	 */
    public DBManager(String dbUrl) throws SQLException {
// JDBC: 1 – Load the driver
		try {
			
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException cnfe) {

			throw new RuntimeException(cnfe.getMessage(), cnfe.getCause());
		}
// JDBC: 2 – Connection
        CON = DriverManager.getConnection(dbUrl, "sql2241988", "uZ9%zJ5%");
	}
	
	public static void shutdown() {
		try {
			DriverManager.getConnection("jdbc:mysql:;shutdown=true");
		} catch (SQLException sqle) {
			Logger.getLogger(DBManager.class.getName()).info(sqle.getMessage());
		}
	}
	
	public static void main(String[] args) {
		System.out.println("mic check1");
		System.out.println("mic check2");
	}
}