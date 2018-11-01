/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import de.scravy.pair.Pair;
import de.scravy.pair.Pairs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import it.webproject2018.db.daos.ProdottoDAO;
import it.webproject2018.db.entities.CategoriaProdotti;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;
import org.glassfish.gmbal.generic.Triple;

/**
 *
 * @author davide
 */
public class JDBCProdottoDAO extends JDBCDAO<Prodotto, Integer> implements ProdottoDAO {

    public JDBCProdottoDAO(Connection con) {
        super(con);
    }

    public JDBCProdottoDAO(ServletContext sc) {
        super(sc);
    }

    @Override
    public Prodotto getByPrimaryKey(Integer productID) throws DAOException {
        if (productID == null) {
            throw new DAOException("productID is null");
        }

        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti where ID = ? ")) {
            stm.setInt(1, productID);
            try (ResultSet rs = stm.executeQuery()) {

                if (rs.next()) {

                    Integer id = rs.getInt("ID");
                    String nome = rs.getString("Nome");
                    String note = rs.getString("Note");
                    String logo = rs.getString("Logo");
                    String owner = rs.getString("Owner");
                    ArrayList<String> fotografie = getProductImages(productID);
                    JDBCCategoriaProdottiDAO categoriaProdottiDao = new JDBCCategoriaProdottiDAO(CON);
                    CategoriaProdotti categoria = categoriaProdottiDao.getByPrimaryKey(rs.getString("Categoria"));

                    Prodotto product = new Prodotto(id, nome, note, logo, fotografie, categoria, owner);

                    return product;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting Product by ID", ex);
        }

        return null;
    }

    @Override
    public ArrayList<String> getProductImages(Integer productID) throws DAOException {
        if (productID == null) {
            throw new DAOException("productID is null");
        }

        ArrayList<String> foto = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti_immagini where ID = ? ")) {
            stm.setInt(1, productID);
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {

                    String fotografia = rs.getString("Fotografia");
                    foto.add(fotografia);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting Product images", ex);
        }

        return foto;
    }

    @Override
    public ArrayList<Prodotto> getAllProducts(String orderBy) throws DAOException {
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti order by ?")) {
            stm.setString(1, orderBy);
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    Integer id_prodotto = rs.getInt("ID");
                    Prodotto pro = getByPrimaryKey(id_prodotto);
                    prodotti.add(pro);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting all Products", ex);
        }

        return prodotti;
    }

    public List<Triple<Integer, String, Integer>> getProductListAmount(Prodotto entity, Utente user) throws DAOException {
        ArrayList<Triple<Integer, String, Integer>> lista = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT DISTINCT * FROM (SELECT distinct Liste.ID, Liste.Nome FROM Liste, Utenti_Liste, Utenti"
                + "                WHERE Liste.ID = Utenti_Liste.ID"
                + "                AND Utenti.Email = Utenti_Liste.Email AND Utenti.Email = ?"
                + "                AND Utenti_Liste.Perm_add_rem = 1"
                + "                AND Liste.Categoria = "
                + "                     (SELECT Nome_liste_cat FROM Prodotti_categorie JOIN Prodotti ON Categoria = Prodotti_categorie.Nome WHERE ID = ?)) as a"
                + "                LEFT JOIN (SELECT * FROM Liste_Prodotti WHERE ID_prodotto = ?) as b ON a.ID = b.ID_lista;")) {
            stm.setString(1, user.getEmail());
            stm.setInt(2, entity.getId());
            stm.setInt(3, entity.getId());
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    Integer id_lista = rs.getInt("ID");
                    String nome = rs.getString("Nome");
                    Integer quantità = rs.getInt("Quantita");

                    lista.add(new Triple(id_lista, nome, quantità));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting all Product List Amount", ex);
        }

        return lista;
    }
    

    public Integer getProductOfListAmount(Prodotto product, Lista list) throws DAOException {
        Integer amount = 0;

        try (PreparedStatement stm = CON.prepareStatement("SELECT Quantita FROM Liste_Prodotti WHERE ID_prodotto = ? AND ID_lista = ?;")) {
            stm.setInt(1, product.getId());
            stm.setInt(2, list.getId());
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    amount = rs.getInt("Quantita");
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting Product of a List Amount", ex);
        }

        return amount;
    }

    @Override
    public ArrayList<Prodotto> getAllProducts(String filter, String orderBy) throws DAOException {
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti where Nome like ? order by ?")) {
            stm.setString(1, "%" + filter + "%");
            stm.setString(2, orderBy);
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    Integer id_prodotto = rs.getInt("ID");
                    Prodotto pro = getByPrimaryKey(id_prodotto);
                    prodotti.add(pro);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting all Products", ex);
        }

        return prodotti;
    }

    public ArrayList<Prodotto> getAllProductsByCategory(String catName, String qry) throws DAOException {
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti where Categoria = ? and Nome like ?")) {
            stm.setString(1, catName);
            stm.setString(2, "%" + qry + "%");
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    Integer id_prodotto = rs.getInt("ID");
                    Prodotto pro = getByPrimaryKey(id_prodotto);
                    prodotti.add(pro);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting all Products by Category", ex);
        }

        return prodotti;
    }

    @Override
    public ArrayList<Prodotto> getUserProducts(String userEmail) throws DAOException {
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti where Owner = ?")) {
            stm.setString(1, userEmail);
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    Integer id_prodotto = rs.getInt("ID");
                    Prodotto pro = getByPrimaryKey(id_prodotto);
                    prodotti.add(pro);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting all user Products", ex);
        }

        return prodotti;
    }

    @Override
    public List<Prodotto> getAll() throws DAOException {
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        //prendo solo prodotto creati da admin
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti JOIN Utenti ON Owner = Email WHERE IsAdmin = true")) {
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    Integer id_prodotto = rs.getInt("ID");
                    Prodotto pro = getByPrimaryKey(id_prodotto);
                    prodotti.add(pro);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting all Products", ex);
        }

        return prodotti;
    }

    @Override
    public ArrayList<Prodotto> getAllVisibleProducts(String srcQry, String orderBy, Integer count, Integer start) throws DAOException {
        if (srcQry == null) {
            srcQry = "";
        }

        if (orderBy == null || orderBy.equals("byName") || orderBy.equals("")) {
            orderBy = "Nome";
        } else if (orderBy.equals("byShop")) {
            orderBy = "Categoria";
        }

        ArrayList<Prodotto> prodotti = new ArrayList<>();

        //prendo solo prodotto creati da admin
        try (PreparedStatement stm = CON.prepareStatement("select * from Prodotti JOIN Utenti ON Owner = Email WHERE IsAdmin = true and Prodotti.Nome LIKE ? ORDER BY Prodotti." + orderBy + " LIMIT ?, ?")) {
            stm.setString(1, "%" + srcQry + "%");
            stm.setInt(2, start);
            stm.setInt(3, count);
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    Integer id_prodotto = rs.getInt("ID");
                    Prodotto pro = getByPrimaryKey(id_prodotto);
                    prodotti.add(pro);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting all Products", ex);
        }

        return prodotti;
    }

    @Override
    public ArrayList<Prodotto> getAllUserVisibleProducts(String userEmail, String srcQry, String orderBy, Integer count, Integer start) throws DAOException {
        if (srcQry == null) {
            srcQry = "";
        }

        if (orderBy == null || orderBy.equals("byName") || orderBy.equals("")) {
            orderBy = "Nome";
        } else if (orderBy.equals("byShop")) {
            orderBy = "Categoria";
        }

        ArrayList<Prodotto> prodotti = new ArrayList<>();

        //TODO: da sistemare orderby e like
        //prendo solo prodotti creati da admin
        try (PreparedStatement stm = CON.prepareStatement("Select * from ((select Prodotti.* from Prodotti JOIN Utenti ON Owner = Email WHERE IsAdmin = true or Owner = ?)"
                + "UNION"
                + "(SELECT Prodotti.* FROM Utenti_Prodotti JOIN Prodotti ON ID_prodotto = ID Where Email = ?)) as a WHERE Nome LIKE ? ORDER BY " + orderBy + " LIMIT ?, ?")) {
            stm.setString(1, userEmail);
            stm.setString(2, userEmail);
            stm.setString(3, "%" + srcQry + "%");
            stm.setInt(4, start);
            stm.setInt(5, count);
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    Integer id_prodotto = rs.getInt("ID");
                    Prodotto pro = getByPrimaryKey(id_prodotto);
                    prodotti.add(pro);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting All User Visible Products", ex);
        }

        return prodotti;
    }
    @Override
    public Long getCountVisibleProducts(String srcQry) throws DAOException{
        try (PreparedStatement stm = CON.prepareStatement("select COUNT(*) from Prodotti JOIN Utenti ON Owner = Email WHERE IsAdmin = true and Prodotti.Nome LIKE ? ")){
            stm.setString(1, "%" + srcQry + "%");
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count products", ex);
        }

        return 0L;
    }
    
    @Override
    public Long getCountUserVisibleProducts(String userEmail, String srcQry) throws DAOException{
        try (PreparedStatement stm = CON.prepareStatement("Select COUNT(*) from ((select Prodotti.* from Prodotti JOIN Utenti ON Owner = Email WHERE IsAdmin = true or Owner = ?)" +
                "UNION" +
                "(SELECT Prodotti.* FROM Utenti_Prodotti JOIN Prodotti ON ID_prodotto = ID Where Email = ?)) as a WHERE Nome LIKE ?")){
            stm.setString(1, userEmail);
            stm.setString(2, userEmail);
            stm.setString(3, "%" + srcQry + "%");
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count products", ex);
        }

        return 0L;
    }
    
    @Override
    public Long getCount() throws DAOException {
        try (Statement stmt = CON.createStatement()) {
            ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM Prodotti");
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count products", ex);
        }

        return 0L;
    }

    @Override
    public Prodotto update(Prodotto product) throws DAOException {
        if (product == null) {
            throw new DAOException("Parameter 'product' not valid for update",
                    new IllegalArgumentException("The passed product is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE Prodotti "
                + "SET Nome = ?, Note = ?, Logo = ?, Categoria = ?, Owner = ? WHERE ID = ?")) {
            std.setString(1, product.getNome());
            std.setString(2, product.getNote());
            std.setString(3, product.getLogo());
            std.setString(4, product.getCategoria().getNome());
            std.setString(5, product.getOwner());
            std.setInt(6, product.getId());
            if (std.executeUpdate() == 1) {
                return product;
            } else {
                throw new DAOException("Impossible to update the product");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the product", ex);
        }
    }

    @Override
    public Prodotto insert(Prodotto entity) throws DAOException {
        if (entity == null) {
            throw new DAOException("product parameter is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("INSERT INTO Prodotti (ID, Nome, Note, Logo, Categoria, Owner) VALUES (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, entity.getNome());
            stm.setString(2, entity.getNote());
            stm.setString(3, "");
            stm.setString(4, entity.getCategoria().getNome());
            stm.setString(5, entity.getOwner());
            Integer rs = stm.executeUpdate();

            ResultSet rsi = stm.getGeneratedKeys();
            if (rsi.next()) {
                return getByPrimaryKey(rsi.getInt(1));
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean insertImage(Prodotto entity, String img) throws DAOException {
        if (entity == null || img == null) {
            throw new DAOException("product parameter is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("INSERT INTO Prodotti_immagini (ID, Fotografia) VALUES (?, ?)")) {
            stm.setInt(1, entity.getId());
            stm.setString(2, img);
            Integer rs = stm.executeUpdate();

            return (rs > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean shareProduct(Integer idProduct, String email) throws DAOException {
        if (idProduct == null || email == null) {
            throw new DAOException("product parameter is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("INSERT INTO Utenti_Prodotti (ID_prodotto, Email, Data_inserimento) VALUES (?, ?, NOW())")) {
            stm.setInt(1, idProduct);
            stm.setString(2, email);
            Integer rs = stm.executeUpdate();

            return (rs > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean deleteShareProduct(Integer idProduct, String email) throws DAOException {
        if (idProduct == null || email == null) {
            throw new DAOException("product parameter is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("DELETE FROM Utenti_Prodotti WHERE ID_prodotto = ? AND Email = ?")) {
            stm.setInt(1, idProduct);
            stm.setString(2, email);
            Integer rs = stm.executeUpdate();

            return (rs > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Pair<Utente, Boolean>> getUserToShareWith(Integer idProdotto, Utente user, String qry) throws DAOException {
        ArrayList<Pair<Utente, Boolean>> lista = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT Utenti.Email, (ID_prodotto IS NOT NULL) as Condiviso FROM Utenti LEFT JOIN (SELECT * FROM Utenti_Prodotti WHERE ID_prodotto = ?) as a ON Utenti.Email = a.Email\n"
                + "WHERE Utenti.Email != ? AND (Utenti.Email LIKE ? OR CONCAT(Nome, \" \", Cognome) LIKE ?);")) {
            stm.setInt(1, idProdotto);
            stm.setString(2, user.getEmail());
            stm.setString(3, "%" + qry + "%");
            stm.setString(4, "%" + qry + "%");
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    String email = rs.getString("Email");
                    JDBCUtenteDAO JdbcUtenteDao = new JDBCUtenteDAO(CON);
                    Utente u = JdbcUtenteDao.getByPrimaryKey(email);
                    Boolean condiviso = rs.getBoolean("Condiviso");

                    lista.add(Pairs.from(u, condiviso));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting all Products", ex);
        }

        return lista;
    }

    public ArrayList<Utente> getUserSharedProduct(Integer idProdotto) throws DAOException {
        ArrayList<Utente> lista = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT Email FROM Utenti_Prodotti WHERE ID_prodotto = ?;")) {
            stm.setInt(1, idProdotto);
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    String email = rs.getString("Email");
                    JDBCUtenteDAO JdbcUtenteDao = new JDBCUtenteDAO(CON);
                    Utente u = JdbcUtenteDao.getByPrimaryKey(email);

                    lista.add(u);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting all Products", ex);
        }

        return lista;
    }

    @Override
    public Boolean delete(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("Prodotto is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("DELETE FROM Prodotti where ID = ? ")) {
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

    public Boolean deleteFromList(Integer ID_product, Integer ID_list) throws DAOException {
        if (ID_product == null || ID_list == null) {
            throw new DAOException("Something is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("DELETE FROM Liste_Prodotti "
                + "where ID_prodotto = ? and ID_lista = ? ")) {
            stm.setInt(1, ID_product);
            stm.setInt(2, ID_list);
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
