/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import it.webproject2018.db.daos.NotificaDAO;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.Notifica;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

/**
 *
 * @author alberto
 */
public class JDBCNotificaDAO extends JDBCDAO<Notifica, Integer> implements NotificaDAO {

    public JDBCNotificaDAO(Connection conn) {
        super(conn);
    }

    public JDBCNotificaDAO(ServletContext sc) {
        super(sc);
    }

    @Override
    public Long getCount() throws DAOException {
        try (Statement stmt = CON.createStatement()) {
            ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM Notifiche");
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count products", ex);
        }

        return 0L;
    }

    @Override
    public Notifica getByPrimaryKey(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("notificationID is null");
        }

        try (PreparedStatement stm = CON.prepareStatement("select * from Notifiche where ID = ? ")) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {

                if (rs.next()) {

                    Integer id = rs.getInt("ID");
                    Integer idList = rs.getInt("ID_list");
                    JDBCListaDAO listaDao = new JDBCListaDAO(CON);
                    Lista list = listaDao.getByPrimaryKey(idList);
                    Integer idProdotto = rs.getInt("ID_prodotto");
                    JDBCProdottoDAO prodottoDAO = new JDBCProdottoDAO(CON);
                    Prodotto product = prodottoDAO.getByPrimaryKey(idProdotto);
                    Integer giorniMancanti = rs.getInt("Giorni_mancanti");
                    Integer quantitaMancante = rs.getInt("Quantita_mancanti");
                    boolean mail = rs.getBoolean("Mail");
                    Timestamp creazione = rs.getTimestamp("Creazione");
                    Notifica notification = new Notifica(id, list, product, giorniMancanti, quantitaMancante, mail, creazione);
                    return notification;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting Notification by ID", ex);
        }

        return null;
    }

    @Override
    public List<Notifica> getAll() throws DAOException {
        List<Notifica> notificationList = new ArrayList<>();
        try (PreparedStatement stm = CON.prepareStatement("select * from Notifiche")) {
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    Integer id_notifica = rs.getInt("ID");
                    Notifica notification = getByPrimaryKey(id_notifica);
                    notificationList.add(notification);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting all notifications", ex);
        }

        return notificationList;
    }

    @Override
    public Notifica insert(Notifica entity) throws DAOException {
        if (entity == null) {
            throw new DAOException("notication parameter is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("INSERT INTO Notifiche (ID, ID_list, ID_prodotto, GiorniMancanti, QuantitaMancanti, Mail, Creazione) VALUES (null, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
            stm.setInt(1, entity.getId());
            stm.setInt(2, entity.getLista().getId());
            stm.setInt(3, entity.getProdotto().getId());
            stm.setInt(4, entity.getGiorniMancanti());
            stm.setInt(5, entity.getQuantitaMancante());
            stm.setBoolean(6, entity.getMail());
            stm.setTimestamp(7, entity.getCreazione());

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

    @Override
    public Notifica update(Notifica entity) throws DAOException {
        if (entity == null) {
            throw new DAOException("Parameter 'product' not valid for update",
                    new IllegalArgumentException("The passed product is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE Notifiche "
                + "SET ID = ?, ID_list = ?, ID_prodotto = ?, GiorniMancanti = ?, "
                + "QuantitaMancanti = ? Mail = ? Creazione = ? WHERE ID = ?")) {
            std.setInt(1, entity.getId());
            std.setInt(2, entity.getLista().getId());
            std.setInt(3, entity.getProdotto().getId());
            std.setInt(4, entity.getGiorniMancanti());
            std.setInt(5, entity.getQuantitaMancante());
            std.setBoolean(6, entity.getMail());
            std.setTimestamp(7, entity.getCreazione());
            if (std.executeUpdate() == 1) {
                return entity;
            } else {
                throw new DAOException("Impossible to update the notification");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the notification", ex);
        }
    }

    @Override
    public ArrayList<Notifica> getNotificationByUserEmail(String userEmail) throws DAOException {
        ArrayList<Notifica> notificationList = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select Notifiche.ID, LISTE.Owner "
                + "from Notifiche join Liste on Liste.ID = Notifiche.ID_list"
                + "where Liste.Owner = ?")) {
            stm.setString(1, userEmail);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Integer id_notifica = rs.getInt("ID");
                    Notifica notification = getByPrimaryKey(id_notifica);
                    notificationList.add(notification);
                }

            }

        } catch (SQLException ex) {
            throw new DAOException("Unable to get notifications by userEmail", ex);
        }
        return notificationList;
    }

    @Override
    public Boolean delete(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("Notifica is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("DELETE FROM Notifiche where ID = ? ")) {
            stm.setInt(1, primaryKey);
            int res = stm.executeUpdate();
            return res >= 1;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public void generateNotificationsByProducts() throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("call add_notifications()")) {
            stm.execute();
        } catch (SQLException ex) {
            throw new DAOException("Unable to add new notifications", ex);
        }
    }

    @Override
    public ArrayList<Notifica> getAllNotificationsNotSentByEmail() throws DAOException {
        ArrayList<Notifica> retval = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select * from Notifiche where Mail=false")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Integer id_notifica = rs.getInt("ID");
                    Notifica notification = getByPrimaryKey(id_notifica);
                    retval.add(notification);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Unable to get notification with Mail = false", ex);
        }
        return retval;
    }

    public void setFieldTo(List<Notifica> notifications, String fielName, Object value) throws DAOException {
        for (Notifica notification : notifications) {
            try (PreparedStatement std = CON.prepareStatement("UPDATE notifiche SET ? = ? where ID = ?")) {
                std.setString(1, fielName);
                std.setObject(2, value);
                std.setInt(3, notification.getId());
            } catch (SQLException ex) {
                throw new DAOException("unable to update a field of certain notifications", ex);
            }
        }
    }
}
