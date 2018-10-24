/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import de.scravy.pair.Pairs;
import it.webproject2018.db.daos.jdbc.JDBCListaPermessiDAO;
import it.webproject2018.db.entities.ListaPermessi;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author davide
 */
public class AcceptListShare extends HttpServlet {

    private JDBCListaPermessiDAO JdbcListaPermessiDao;

    @Override
    public void init() throws ServletException {
        JdbcListaPermessiDao = new JDBCListaPermessiDAO(super.getServletContext());
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init();

        Utente user = (Utente) request.getSession().getAttribute("User");
        Integer idLista = Integer.parseInt(request.getParameter("idLista"));

        try {
            ListaPermessi l = JdbcListaPermessiDao.getByPrimaryKey(Pairs.from(user.getEmail(), idLista));
            l.setAccettato(true);
            JdbcListaPermessiDao.update(l);
        }
        catch(DAOException e) {
            e.printStackTrace();
        }
        
        JdbcListaPermessiDao.Close();
        
        //redirect on login page
        response.sendRedirect(request.getHeader("referer"));
    }
}
