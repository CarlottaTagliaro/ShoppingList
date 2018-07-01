/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db;

/*import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
    public DBManager(String dbUrl, String dbName, String dbPsw) throws SQLException {
// JDBC: 1 – Load the driver
		try {
			
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException cnfe) {

			throw new RuntimeException(cnfe.getMessage(), cnfe.getCause());
		}
// JDBC: 2 – Connection
        CON = DriverManager.getConnection(dbUrl, dbName, dbPsw);
	}
	
	public static void shutdown() {
		try {
			
			DriverManager.getConnection("jdbc:mysql:;shutdown=true");
		
		} catch (SQLException sqle) {
		
			Logger.getLogger(DBManager.class.getName()).info(sqle.getMessage());
			
		}
	}
	// Da sistemare
	public Utente getUser(String userEmail) throws SQLException {
		if (userEmail == null) {
			throw new SQLException("userEmail is null");
		}
		
		try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Utenti WHERE Email = ? ")) {
			stm.setString(1, userEmail);
			try (ResultSet rs = stm.executeQuery()) {
				
				rs.next();
                Utente user = new Utente();
                user.setName(rs.getString("nome"));
                user.setSurname(rs.getString("cognome"));
                user.setEmail(rs.getString("email"));
                user.setPicture(rs.getString("immagine"));
                user.setPassword(rs.getString("password"));
                user.setIsAdmin(rs.getBoolean("isAdmin"));

				try (PreparedStatement slstm = CON.prepareStatement("SELECT * FROM Utenti WHERE Email = ? ")) {
					slstm.setString(1, user.getEmail());
                    try (ResultSet slrs = slstm.executeQuery()) {
                        slrs.next();
                        //user.setShoppingListsCount(slrs.getInt(1));
                    }
                }
                return user;
            }
        }
		//Utente user = new Utente();
		//return user;
    }
}