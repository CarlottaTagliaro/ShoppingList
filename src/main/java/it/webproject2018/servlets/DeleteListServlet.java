/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.webproject2018.db.daos.jdbc.JDBCListaDAO;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;

/**
 *
 * @author Max
 */
public class DeleteListServlet extends HttpServlet {

    private JDBCListaDAO JDBCLista;

    @Override
    public void init() throws ServletException {
        JDBCLista = new JDBCListaDAO(super.getServletContext());
    }

    /**
     * Processes requests for HTTP GET methods for deleting lists
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init();

        Utente user = (Utente) request.getSession().getAttribute("User");
        if (user != null) {
            try {
                String ID = request.getParameter("List");
                Integer list = Integer.parseInt(ID);
                Boolean ok = JDBCLista.delete(list);

                JDBCLista.Close();
            } catch (DAOException e) {
                e.printStackTrace();
            }
        } else {
            request.getSession().setAttribute("DefaultProductList", null);
            request.getSession().removeAttribute("DefaultProductList");
            request.getSession().setAttribute("DefaultList", null);
            request.getSession().removeAttribute("DefaultList");
        }
        
        response.sendRedirect(request.getContextPath().concat("/myList"));
    }
}
