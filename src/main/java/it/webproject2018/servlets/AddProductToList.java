package it.webproject2018.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.webproject2018.db.daos.jdbc.JDBCListaDAO;

/**
 *
 * @author Stefano
 */
public class AddProductToList extends HttpServlet {

    private JDBCListaDAO JDBCLista;

    @Override
    public void init() throws ServletException {
        JDBCLista = new JDBCListaDAO(super.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter w = response.getWriter();
        try {
            Boolean ok = false;
            
            Integer idProduct = Integer.parseInt(request.getParameter("idProduct"));
            Integer idList = Integer.parseInt(request.getParameter("list"));
            Integer alreadyAdd = Integer.parseInt(request.getParameter("alreadyAdd"));
            Integer amount = Integer.parseInt(request.getParameter("amount_value"));
            
            if(alreadyAdd==0) {
                ok = JDBCLista.insertListProduct(idList, idProduct, amount);
            }
            else {
                ok = JDBCLista.updateListProduct(idList, idProduct, amount);
            }
            
            response.sendRedirect(request.getContextPath() + (!ok ? "/home" : "/home"));
        } catch (Exception e) {
            w.println(e.getMessage());
        }
    }
}