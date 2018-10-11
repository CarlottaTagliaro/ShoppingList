/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import it.webproject2018.db.daos.jdbc.JDBCProdottoDAO;
import it.webproject2018.db.exceptions.DAOException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Max
 */
public class DeleteProductServlet extends HttpServlet {

    private JDBCProdottoDAO JDBCProdotto;

    @Override
    public void init() throws ServletException {
        JDBCProdotto = new JDBCProdottoDAO(super.getServletContext());
    }

    /**
     * Processes requests for HTTP GET methods for deleting products form a
     * certain list
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter w = response.getWriter();
        try {
            String ID_product = request.getParameter("Product");
            String ID_list = request.getParameter("List");

            Integer product = Integer.parseInt(ID_product);
            Integer list = Integer.parseInt(ID_list);

            Boolean ok = JDBCProdotto.deleteFromList(product, list);
            response.sendRedirect(request.getContextPath().concat("/myList"));
        } catch (DAOException e) {
            w.println(e.getMessage());
        }
    }
}
