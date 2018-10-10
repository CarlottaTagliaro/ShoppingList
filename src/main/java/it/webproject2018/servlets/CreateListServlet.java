/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import it.webproject2018.db.daos.jdbc.JDBCListaDAO;
import it.webproject2018.db.entities.CategoriaListe;
import it.webproject2018.db.daos.jdbc.JDBCCategoriaListeDAO;
import it.webproject2018.db.daos.jdbc.JDBCListaPermessiDAO;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.ListaPermessi;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefano
 */
public class CreateListServlet extends HttpServlet {

    private JDBCListaDAO JDBCLista;
    private JDBCListaPermessiDAO JDBCListaPermessi;
    private JDBCCategoriaListeDAO JDBCCategoriaListe;

    @Override
    public void init() throws ServletException {
        JDBCLista = new JDBCListaDAO(super.getServletContext());
        JDBCCategoriaListe = new JDBCCategoriaListeDAO(super.getServletContext());
        JDBCListaPermessi = new JDBCListaPermessiDAO(super.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter w = response.getWriter();
        try {
            Utente user = (Utente) request.getSession().getAttribute("User");

            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String category = request.getParameter("category");
            String owner = user.getEmail();
            //String picture = request.getParameter("file");

            CategoriaListe cat = JDBCCategoriaListe.getByPrimaryKey(category);

            Lista list = new Lista(null, name, description, "", cat, owner);

            list = JDBCLista.insert(list);
            Boolean ok = list != null;
            
            if(ok){
                ListaPermessi permessi = new ListaPermessi(true, true, true, true, user.getEmail(), list.getId());
                JDBCListaPermessi.insert(permessi);
            }
            response.sendRedirect(request.getContextPath() + (!ok ? "/newList" : "/myList"));
        } catch (DAOException e) {
            w.println(e.getMessage());
        }
    }
}
