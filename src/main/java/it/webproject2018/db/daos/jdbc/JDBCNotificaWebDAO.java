/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.daos.jdbc;

import it.webproject2018.db.daos.NotificaWebDAO;
import it.webproject2018.db.entities.NotificaWeb;
import it.webproject2018.db.exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

/**
 * The JDBC implementation of the {@link NotificaWebDAO} interface.
 * 
 * @author davide
 */
public class JDBCNotificaWebDAO extends JDBCDAO<NotificaWeb, String> implements NotificaWebDAO {

    public JDBCNotificaWebDAO(Connection conn) {
        super(conn);
    }

    public JDBCNotificaWebDAO(ServletContext sc) {
        super(sc);
    }

	@Override
    public List<NotificaWeb> getAllUserNotifications(String email) throws DAOException {
        ArrayList<NotificaWeb> lista = new ArrayList<>();
        if (email == null) {
            throw new DAOException("email is null");
        }

        try (PreparedStatement stm = CON.prepareStatement("CALL getNotifications(?)")) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    String testo = rs.getString("testo");
                    String tipo = rs.getString("tipo");
                    Timestamp data = rs.getTimestamp("data");
                    Integer id_elem = rs.getInt("id_elem");
                    Boolean isNew = rs.getBoolean("new");

                    NotificaWeb notification = new NotificaWeb(testo, tipo, data, id_elem, isNew);
                    lista.add(notification);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting Product by ID", ex);
        }

        return lista;
    }

    @Override
    public NotificaWeb getByPrimaryKey(String email) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NotificaWeb> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotificaWeb insert(NotificaWeb entity) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotificaWeb update(NotificaWeb entity) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean delete(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
