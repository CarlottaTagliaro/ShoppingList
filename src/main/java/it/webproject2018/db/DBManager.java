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
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author Max
 * 
 * @description Gestore per la connessione al database
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

    public Utente getUser(String userEmail) throws SQLException {
        if (userEmail == null) {
            throw new SQLException("userEmail is null");
        }

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Utenti WHERE Email = ?")) {
            stm.setString(1, userEmail);
            try (ResultSet rs = stm.executeQuery()) {

                if(rs.next()){
                    Utente user = new Utente();
                    user.setName(rs.getString("Nome"));
                    user.setSurname(rs.getString("Cognome"));
                    user.setEmail(rs.getString("Email"));
                    user.setPicture(rs.getString("Immagine"));;
                    user.setIsAdmin(rs.getBoolean("IsAdmin"));


                    user.Liste = getUserLists(user.getEmail());

                    return user;
                }
            }
        }
        //Utente user = new Utente();
        //return user;
        return null;
    }

    public Utente getUserAuthentication(String userEmail, String password) throws SQLException {
        if (userEmail == null || password == null) {
            throw new SQLException("userEmail or password is null");
        }
        
        PreparedStatement stm = CON.prepareStatement("SELECT * FROM Utenti WHERE Email = ? AND Password = ? ");
        stm.setString(1, userEmail);
        stm.setString(2, password);
        ResultSet rs = stm.executeQuery();
        
        Utente user = null;

        if(rs.next())
        {
            user = new Utente();
            
            user.setName(rs.getString("Nome"));
            user.setSurname(rs.getString("Cognome"));
            user.setEmail(rs.getString("Email"));
            user.setPicture(rs.getString("Immagine"));
            user.setIsAdmin(rs.getBoolean("IsAdmin"));
            /*user.setName(rs.getString(1));
            user.setSurname(rs.getString(2));
            user.setEmail(rs.getString(3));
            user.setPicture(rs.getString(4));
            user.setIsAdmin(rs.getBoolean(5));*/
            user.Liste = getUserLists(user.getEmail());
        }
        
        return user;
    }
    
    public ArrayList<Lista> getUserLists(String userEmail) throws SQLException {
        if (userEmail == null) {
            throw new SQLException("userEmail is null");
        }

        ArrayList<Lista> liste = null;
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Utenti_Liste join Liste on Utenti_Liste.ID = Liste.ID where Email = ? ")) {
            stm.setString(1, userEmail);
            try (ResultSet rs = stm.executeQuery()) {

                liste = new ArrayList<Lista>();

                while(rs.next()){

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
        }
        
        return liste;
    }
    
    public ArrayList<Prodotto> getListProducts(Integer listID) throws SQLException {
        if (listID == null) {
            throw new SQLException("listID is null");
        }

        ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Liste_Prodotti where ID_lista = ? ")) {
            stm.setInt(1, listID);
            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){
                
                    Integer id_prodotto = rs.getInt("ID_prodotto");
                    Prodotto pro = getProduct(id_prodotto);
                    pro.Acquisti.addAll(getListProductsBuyInfo(listID, id_prodotto));
                    prodotti.add(pro);
                }
            }
        }
        
        return prodotti;
    }
    
    
    public ArrayList<InformazioniAcquisto> getListProductsBuyInfo(Integer listID, Integer productID) throws SQLException {
        if (listID == null) {
            throw new SQLException("listID is null");
        }
        if (productID == null) {
            throw new SQLException("productID is null");
        }

        ArrayList<InformazioniAcquisto> info = new ArrayList<>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Liste_Prodotti_Acquistati where ID_lista = ? and ID_prodotto = ? ")) {
            stm.setInt(1, listID);
            stm.setInt(2, productID);
            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){
                
                    Date data_acq = rs.getDate("Data_acquisto");
                    Integer quantità = rs.getInt("Quantita");
                    info.add(new InformazioniAcquisto(data_acq, quantità));
                }
            }
        }
        
        return info;
    }
    
    public Prodotto getProduct(Integer productID) throws SQLException {
        if (productID == null) {
            throw new SQLException("productID is null");
        }
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti where ID = ? ")) {
            stm.setInt(1, productID);
            try (ResultSet rs = stm.executeQuery()) {

                if(rs.next()){

                    Integer id = rs.getInt("ID");
                    String nome = rs.getString("Nome");
                    String note = rs.getString("Note");
                    String logo = rs.getString("Logo");
                    ArrayList<String> fotografie = getProductImages(productID);
                    CategoriaProdotti categoria = getProductCategory(rs.getString("Categoria"));

                    Prodotto product = new Prodotto(id, nome, note, logo, fotografie, categoria);
                    
                    return product;
                }
            }
        }
        
        return null;
    }
    
    
    public ArrayList<String> getProductImages(Integer productID) throws SQLException {
        if (productID == null) {
            throw new SQLException("productID is null");
        }
        
        ArrayList<String> foto = new ArrayList<>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti_immagini where ID = ? ")) {
            stm.setInt(1, productID);
            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){

                    String fotografia = rs.getString("Fotografia");
                    foto.add(fotografia);
                }
            }
        }
        
        return foto;
    }
    
    public CategoriaProdotti getProductCategory(String catNome) throws SQLException {
        if (catNome == null) {
            throw new SQLException("catNome is null");
        }
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti_categorie where Nome = ? ")) {
            stm.setString(1, catNome);
            try (ResultSet rs = stm.executeQuery()) {

                if(rs.next()){

                    String nome = rs.getString("Nome");
                    String descrizione = rs.getString("Descrizione");;
                    String logo = rs.getString("Logo");
                    CategoriaListe categoria = getListCategory(rs.getString("Nome_liste_cat"));

                    CategoriaProdotti cat = new CategoriaProdotti(nome, descrizione, logo, categoria);              

                    return cat;
                }
            }
        }
        return null;
    }
    
    public CategoriaListe getListCategory(String catNome) throws SQLException {
        if (catNome == null) {
            throw new SQLException("catNome is null");
        }
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Liste_categorie where Nome = ? ")) {
            stm.setString(1, catNome);
            try (ResultSet rs = stm.executeQuery()) {

                if(rs.next()){
                    String nome = rs.getString("Nome");
                    String descrizione = rs.getString("Descrizione");
                    ArrayList<String> immagini = getListCategoryImages(catNome);

                    CategoriaListe cat = new CategoriaListe(nome, descrizione, immagini);              

                    return cat;
                }
            }
        }
        
        return null;
    }
    
    public ArrayList<String> getListCategoryImages(String catNome) throws SQLException {
        if (catNome == null) {
            throw new SQLException("catNome is null");
        }
        
        ArrayList<String> immagini = new ArrayList<>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Liste_categorie_immagini where Nome = ? ")) {
            stm.setString(1, catNome);
            try (ResultSet rs = stm.executeQuery()) {
                while(rs.next()){
                    String immagine = rs.getString("Immagine");
                
                    immagini.add(immagine);
                }
            }
        }
        
        return immagini;
    }
}
