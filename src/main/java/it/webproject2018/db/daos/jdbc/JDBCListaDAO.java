/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import it.webproject2018.db.daos.ListaDAO;
import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.entities.CategoriaListe;
import it.webproject2018.entities.Lista;
import it.webproject2018.entities.Prodotto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author davide
 */
public class JDBCListaDAO extends JDBCDAO<Lista, Integer> implements ListaDAO {
    
    public JDBCListaDAO(Connection con) {
        super(con);
    }    
    
    @Override
    public ArrayList<Lista> getUserLists(String userEmail) throws DAOException{
        if (userEmail == null) {
            throw new DAOException("userEmail is null");
        }

        ArrayList<Lista> liste = null;
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Utenti_Liste join Liste on Utenti_Liste.ID = Liste.ID where Email = ? ")) {
            stm.setString(1, userEmail);
            try (ResultSet rs = stm.executeQuery()) {

                liste = new ArrayList<>();

                while(rs.next()){

                    Boolean perm_edit = rs.getBoolean("perm_edit");
                    Boolean perm_add_rem = rs.getBoolean("perm_add_rem");
                    Boolean perm_del = rs.getBoolean("perm_del");
                    Boolean accettato = rs.getBoolean("accettato");
                    Integer id = rs.getInt("ID");
                    String nome = rs.getString("Nome");
                    String descrizione = rs.getString("Descrizione");
                    String immagine = rs.getString("Immagine");
                    
                    JDBCCategoriaListeDAO categoriaListeDao = new JDBCCategoriaListeDAO(CON);
                    CategoriaListe categoria = categoriaListeDao.getByPrimaryKey(rs.getString("Categoria"));
                    String owner = rs.getString("Owner");

                    Lista lista = new Lista(perm_edit, perm_add_rem, perm_del, accettato, id, nome, descrizione, immagine, categoria, owner);

                    lista.addAll(getListProducts(id));

                    liste.add(lista);
                }
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Error while getting Lists of the User", ex);
        }
        
        
        return liste;
    }
    
    @Override
    public ArrayList<Prodotto> getListProducts(Integer listID) throws DAOException{
        if (listID == null) {
            throw new DAOException("listID is null");
        }

        ArrayList<Prodotto> prodotti = new ArrayList<>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Liste_Prodotti where ID_lista = ? ")) {
            stm.setInt(1, listID);
            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){
                
                    Integer id_prodotto = rs.getInt("ID_prodotto");
                    JDBCProdottoDAO prodottoDao = new JDBCProdottoDAO(CON);
                    Prodotto pro = prodottoDao.getByPrimaryKey(id_prodotto);
                    JDBCInformazioniAcquistoDAO infoDao = new JDBCInformazioniAcquistoDAO(CON);
                    pro.Acquisti.addAll(infoDao.getListProductsBuyInfo(listID, id_prodotto));
                    prodotti.add(pro);
                }
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Error while getting Products of the List", ex);
        }
        
        return prodotti;
    }
    
    @Override
    public Lista getByPrimaryKey(Integer primaryKey) throws DAOException{
        if (primaryKey == null) {
            throw new DAOException("lista ID is null");
        }
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Liste where ID = ? ")) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {

                if(rs.next()){

                    Boolean perm_edit = rs.getBoolean("perm_edit");
                    Boolean perm_add_rem = rs.getBoolean("perm_add_rem");
                    Boolean perm_del = rs.getBoolean("perm_del");
                    Boolean accettato = rs.getBoolean("accettato");
                    Integer id = rs.getInt("ID");
                    String nome = rs.getString("Nome");
                    String descrizione = rs.getString("Descrizione");
                    String immagine = rs.getString("Immagine");
                    
                    JDBCCategoriaListeDAO categoriaListeDao = new JDBCCategoriaListeDAO(CON);
                    CategoriaListe categoria = categoriaListeDao.getByPrimaryKey(rs.getString("Categoria"));
                    String owner = rs.getString("Owner");

                    Lista lista = new Lista(perm_edit, perm_add_rem, perm_del, accettato, id, nome, descrizione, immagine, categoria, owner);

                    lista.addAll(getListProducts(id));
                    
                    return lista;
                }
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Error while getting Product by ID", ex);
        }
        
        return null;
    }
    
    @Override
    public List<Lista> getAll() throws DAOException {
        ArrayList<Lista> liste = new ArrayList<>();
        
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti")) {
            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){                
                    Boolean perm_edit = rs.getBoolean("perm_edit");
                    Boolean perm_add_rem = rs.getBoolean("perm_add_rem");
                    Boolean perm_del = rs.getBoolean("perm_del");
                    Boolean accettato = rs.getBoolean("accettato");
                    Integer id = rs.getInt("ID");
                    String nome = rs.getString("Nome");
                    String descrizione = rs.getString("Descrizione");
                    String immagine = rs.getString("Immagine");
                    
                    JDBCCategoriaListeDAO categoriaListeDao = new JDBCCategoriaListeDAO(CON);
                    CategoriaListe categoria = categoriaListeDao.getByPrimaryKey(rs.getString("Categoria"));
                    String owner = rs.getString("Owner");

                    Lista lista = new Lista(perm_edit, perm_add_rem, perm_del, accettato, id, nome, descrizione, immagine, categoria, owner);

                    lista.addAll(getListProducts(id));

                    liste.add(lista);
                }
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Error while getting all Products", ex);
        }
        
        return liste;
    }
    
    @Override
    public Long getCount() throws DAOException {
        try (Statement stmt = CON.createStatement()) {
            ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM Liste");
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count lists", ex);
        }

        return 0L;
    }
}
