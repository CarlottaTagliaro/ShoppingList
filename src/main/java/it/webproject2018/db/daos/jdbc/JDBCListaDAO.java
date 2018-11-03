/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import de.scravy.pair.Pairs;
import it.webproject2018.db.daos.ListaDAO;
import it.webproject2018.db.entities.CategoriaListe;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.exceptions.DAOException;

/**
 * The JDBC implementation of the {@link ListaDAO} interface.
 * 
 * @author davide
 */
public class JDBCListaDAO extends JDBCDAO<Lista, Integer> implements ListaDAO {

    public JDBCListaDAO(Connection conn) {
        super(conn);
    }

    public JDBCListaDAO(ServletContext sc) {
        super(sc);
    }

    @Override
    public ArrayList<Lista> getUserLists(String userEmail) throws DAOException {
        if (userEmail == null) {
            throw new DAOException("userEmail is null");
        }

        ArrayList<Lista> liste = null;

        try (PreparedStatement stm = CON.prepareStatement("select * from Utenti_Liste join Liste on Utenti_Liste.ID = Liste.ID where Email = ?")) {
            stm.setString(1, userEmail);
            try (ResultSet rs = stm.executeQuery()) {

                liste = new ArrayList<>();

                while (rs.next()) {
                    Integer id = rs.getInt("ID");
                    String nome = rs.getString("Nome");
                    String descrizione = rs.getString("Descrizione");
                    String immagine = rs.getString("Immagine");

                    JDBCCategoriaListeDAO categoriaListeDao = new JDBCCategoriaListeDAO(CON);
                    CategoriaListe categoria = categoriaListeDao.getByPrimaryKey(rs.getString("Categoria"));
                    String owner = rs.getString("Owner");

                    Lista lista = new Lista(id, nome, descrizione, immagine, categoria, owner);

                    JDBCListaPermessiDAO listaPermessiDao = new JDBCListaPermessiDAO(CON);
                    lista.setListPermission(listaPermessiDao.getByPrimaryKey(Pairs.from(userEmail, id)));

                    lista.addAll(getListProducts(id));

                    liste.add(lista);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting Lists of the User", ex);
        }

        return liste;
    }

    @Override
    public ArrayList<Prodotto> getListProducts(Integer listID) throws DAOException {
        if (listID == null) {
            throw new DAOException("listID is null");
        }

        ArrayList<Prodotto> prodotti = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select * from Liste_Prodotti where ID_lista = ? ")) {
            stm.setInt(1, listID);
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {

                    Integer id_prodotto = rs.getInt("ID_prodotto");
                    JDBCProdottoDAO prodottoDao = new JDBCProdottoDAO(CON);
                    Prodotto pro = prodottoDao.getByPrimaryKey(id_prodotto);
                    JDBCInformazioniAcquistoDAO infoDao = new JDBCInformazioniAcquistoDAO(CON);
                    pro.Acquisti.addAll(infoDao.getListProductsBuyInfo(listID, id_prodotto));
                    prodotti.add(pro);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting Products of the List", ex);
        }

        return prodotti;
    }

	@Override
    public Boolean insertListProduct(Integer listID, Integer prodID, Integer amount) throws DAOException {
        if (listID == null) {
            throw new DAOException("listID is null");
        }

        try (PreparedStatement stm = CON.prepareStatement("INSERT INTO Liste_Prodotti (ID_lista, ID_prodotto, Quantita) VALUES (?, ?, ?);")) {
            stm.setInt(1, listID);
            stm.setInt(2, prodID);
            stm.setInt(3, amount);
            Integer rs = stm.executeUpdate();

            return (rs > 0);
        } catch (Exception e) {
            return null;
        }
    }

	@Override
    public Integer getProductQuantity(Integer listID, Integer prodID, Integer amount) throws DAOException {
        if (listID == null) {
            throw new DAOException("listID is null");
        }

        try (PreparedStatement stm = CON.prepareStatement("select Quantita from Liste_Prodotti where ID_lista = ? AND ID_prodotto = ?")) {
            stm.setInt(1, listID);
            stm.setInt(2, prodID);
            Integer quantity = -1;

            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    quantity = rs.getInt("Quantita");
                }
            }

            return quantity;
        } catch (Exception e) {
            throw new DAOException("Error while getting product quantity " + e);
            //return null;
        }
    }

	@Override
    public Boolean buyProduct(Integer listID, Integer prodID, Integer amount) throws DAOException {
        if (listID == null) {
            throw new DAOException("listID is null");
        }

        try (PreparedStatement stm = CON.prepareStatement("INSERT INTO Liste_Prodotti_Acquistati (ID_lista, ID_prodotto, Data_acquisto, Quantita) VALUES (?, ?, NOW(), ?);")) {
            stm.setInt(1, listID);
            stm.setInt(2, prodID);
            stm.setInt(3, amount);
            Integer rs = stm.executeUpdate();

            return (rs > 0);
        } catch (Exception e) {
            throw new DAOException("Error while buy product " + e);
            //return null;
        }
    }

	@Override
    public Boolean updateListProductToBuy(Integer listID, Integer prodID, Integer amount, Integer quantity) throws DAOException {
        if (listID == null) {
            throw new DAOException("listID is null");
        }

        Boolean update = quantity.equals(amount);
        String sql = "DELETE FROM Liste_Prodotti WHERE (ID_lista = ? AND ID_prodotto = ?)";
        if (update) {
            sql = "UPDATE Liste_Prodotti SET Quantita = ? WHERE (ID_lista = ? AND ID_prodotto = ?)";
        }

        try (PreparedStatement stm = CON.prepareStatement(sql)) {
            if (!update) {//delete
                stm.setInt(1, listID);
                stm.setInt(2, prodID);
            } else {//update
                stm.setInt(1, amount);
                stm.setInt(2, listID);
                stm.setInt(3, prodID);
            }

            Integer rs = stm.executeUpdate();

            return (rs > 0);
        } catch (Exception e) {
            throw new DAOException("Error while update product to buy " + e);
            //return null;
        }
    }

	@Override
    public Boolean updateListProduct(Integer listID, Integer prodID, Integer amount) throws DAOException {
        if (listID == null) {
            throw new DAOException("listID is null");
        }

        try (PreparedStatement stm = CON.prepareStatement("UPDATE Liste_Prodotti SET Quantita = ? WHERE (ID_lista = ? AND ID_prodotto = ?)")) {
            stm.setInt(1, amount);
            stm.setInt(2, listID);
            stm.setInt(3, prodID);
            Integer rs = stm.executeUpdate();

            return (rs > 0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Lista getByPrimaryKey(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("lista ID is null");
        }

        try (PreparedStatement stm = CON.prepareStatement("select * from Liste where ID = ? ")) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {

                if (rs.next()) {
                    Integer id = rs.getInt("ID");
                    String nome = rs.getString("Nome");
                    String descrizione = rs.getString("Descrizione");
                    String immagine = rs.getString("Immagine");

                    JDBCCategoriaListeDAO categoriaListeDao = new JDBCCategoriaListeDAO(CON);
                    CategoriaListe categoria = categoriaListeDao.getByPrimaryKey(rs.getString("Categoria"));
                    String owner = rs.getString("Owner");

                    Lista lista = new Lista(id, nome, descrizione, immagine, categoria, owner);

                    lista.addAll(getListProducts(id));

                    return lista;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting Product by ID", ex);
        }

        return null;
    }

    @Override
    public List<Lista> getAll() throws DAOException {
        ArrayList<Lista> liste = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select * from Liste")) {
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    Integer id = rs.getInt("ID");
                    String nome = rs.getString("Nome");
                    String descrizione = rs.getString("Descrizione");
                    String immagine = rs.getString("Immagine");

                    JDBCCategoriaListeDAO categoriaListeDao = new JDBCCategoriaListeDAO(CON);
                    CategoriaListe categoria = categoriaListeDao.getByPrimaryKey(rs.getString("Categoria"));
                    String owner = rs.getString("Owner");

                    Lista lista = new Lista(id, nome, descrizione, immagine, categoria, owner);

                    lista.addAll(getListProducts(id));

                    liste.add(lista);
                }
            }
        } catch (SQLException ex) {
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

    @Override
    public Lista insert(Lista entity) throws DAOException {
        if (entity == null) {
            throw new DAOException("list parameter is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("INSERT INTO Liste (ID, Nome, Descrizione, Immagine, Categoria, Owner) VALUES (null, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, entity.getNome());
            stm.setString(2, entity.getDescrizione());
            stm.setString(3, entity.getImmagine());
            stm.setString(4, entity.getCategoria().getNome());
            stm.setString(5, entity.getOwner());
            Integer rs = stm.executeUpdate();

            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long x = (generatedKeys.getLong(1));
                    Integer i = Integer.valueOf(x.intValue());
                    Lista l = new Lista(i, entity.getNome(), entity.getDescrizione(), entity.getImmagine(), null, entity.getOwner());
                    return l;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Lista update(Lista entity) throws DAOException {
        if (entity == null) {
            throw new DAOException("Parameter 'list' not valid for update",
                    new IllegalArgumentException("The passed list is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE Liste "
                + "SET Nome = ?, Descrizione = ?, Immagine = ?, Categoria = ?, Owner = ? WHERE ID = ?")) {
            std.setString(1, entity.getNome());
            std.setString(2, entity.getDescrizione());
            std.setString(3, entity.getImmagine());
            std.setString(4, entity.getCategoria().getNome());
            std.setString(5, entity.getOwner());
            std.setInt(6, entity.getId());
            if (std.executeUpdate() == 1) {
                return entity;
            } else {
                throw new DAOException("Impossible to update the list");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the list", ex);
        }
    }

    @Override
    public Boolean delete(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("Lista is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("DELETE FROM Liste WHERE ID = ? ")) {
            stm.setInt(1, primaryKey);
            int res = stm.executeUpdate();
            if (res >= 1) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            return false;
        }
    }
}
