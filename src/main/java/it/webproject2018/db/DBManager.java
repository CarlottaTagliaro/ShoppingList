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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Max Gestore per la connessione al database
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
                user.setPicture(rs.getString("immagine"));;
                user.setIsAdmin(rs.getBoolean("isAdmin"));

                
                user.Liste = getUserLists(user.getEmail());
                
                return user;
            }
        }
        //Utente user = new Utente();
        //return user;
    }
    
    public ArrayList<Lista> getUserLists(String userEmail) throws SQLException {
        if (userEmail == null) {
            throw new SQLException("userEmail is null");
        }

        ArrayList<Lista> liste = new ArrayList<Lista>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Utenti_Liste join Liste on Utenti_Liste.ID = Liste.ID where Email = ? ")) {
            stm.setString(1, userEmail);
            try (ResultSet rs = stm.executeQuery()) {

                rs.next();

                Boolean perm_edit = rs.getBoolean("perm_edit");
                Boolean perm_add_rem = rs.getBoolean("perm_add_rem");
                Boolean perm_del = rs.getBoolean("perm_del");
                Boolean accettato = rs.getBoolean("accettato");
                
                Integer id = rs.getInt("ID");
                String nome = rs.getString("Nome");
                String descrizione = rs.getString("Descrizione");
                String immagine = rs.getString("Immagine");
                CategoriaListe categoria = getListCategory(rs.getString("Categoria"));
                String owner = rs.getString("Owner");

                Lista lista = new Lista(perm_edit, perm_add_rem, perm_del, accettato, id, nome, descrizione, immagine, categoria, owner);
                
                lista.addAll(getListProducts(id));
                
                liste.add(lista);
            }
        }
        
        return liste;
    }
    
    public ArrayList<Prodotto> getListProducts(Integer listID) throws SQLException {
        if (listID == null) {
            throw new SQLException("listID is null");
        }

        ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Liste join Liste_Prodotti on Liste.ID = Liste_Prodotti.ID_lista where Liste.ID = ? ")) {
            stm.setInt(1, listID);
            try (ResultSet rs = stm.executeQuery()) {

                rs.next();
                
                Integer id_prodotto = rs.getInt("ID_prodotto");
                prodotti.add(getProduct(id_prodotto));
            }
        }
        
        return prodotti;
    }
    
    public Prodotto getProduct(Integer productID) throws SQLException {
        if (productID == null) {
            throw new SQLException("productID is null");
        }
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti where ID = ? ")) {
            stm.setInt(1, productID);
            try (ResultSet rs = stm.executeQuery()) {

                rs.next();

                Integer id = rs.getInt("ID");
                String nome = rs.getString("Nome");
                String note = rs.getString("Note");;
                String logo = rs.getString("Logo");
                String fotografia = rs.getString("Fotografia");
                CategoriaProdotti categoria = getProductCategory(rs.getString("Categoria"));
                
                Prodotto product = new Prodotto(id, nome, note, logo, fotografia, categoria);
                               
                
                return product;
            }
        }
    }
    
    public CategoriaProdotti getProductCategory(String catNome) throws SQLException {
        if (catNome == null) {
            throw new SQLException("catNome is null");
        }
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti_categorie where Nome = ? ")) {
            stm.setString(1, catNome);
            try (ResultSet rs = stm.executeQuery()) {

                rs.next();

                String nome = rs.getString("Nome");
                String descrizione = rs.getString("Descrizione");;
                String logo = rs.getString("Logo");
                CategoriaListe categoria = getListCategory(rs.getString("Nome_liste_cat"));
                
                CategoriaProdotti cat = new CategoriaProdotti(nome, descrizione, logo, categoria);              
                
                return cat;
            }
        }
    }
    
    public CategoriaListe getListCategory(String catNome) throws SQLException {
        if (catNome == null) {
            throw new SQLException("catNome is null");
        }
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Liste_categorie where Nome = ? ")) {
            stm.setString(1, catNome);
            try (ResultSet rs = stm.executeQuery()) {

                rs.next();

                String nome = rs.getString("Nome");
                String descrizione = rs.getString("Descrizione");;
                String immagine = rs.getString("Immagine");
                
                CategoriaListe cat = new CategoriaListe(nome, descrizione, immagine);              
                
                return cat;
            }
        }
    }
}
