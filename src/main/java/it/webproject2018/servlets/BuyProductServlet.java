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
public class BuyProductServlet extends HttpServlet {

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
            Integer idProduct = Integer.parseInt(request.getParameter("idProduct"));
            Integer idList = Integer.parseInt(request.getParameter("list"));
            Integer amount = Integer.parseInt(request.getParameter("amount_value"));
            Boolean ok = false;
            Boolean ok2 = false;
            
            Integer quantity = JDBCLista.getProductQuantity(idList, idProduct, amount);
            
            if(quantity != null && quantity != -1) {
                if(amount > quantity)
                    amount = quantity;
                ok = JDBCLista.updateListProductToBuy(idList, idProduct, amount, quantity);
            }
            
            if (ok) {
                ok2 = JDBCLista.buyProduct(idList, idProduct, amount);
            }
            
            response.sendRedirect(request.getContextPath() + (!ok2 ? "/myList" : "/myList"));
        } catch (Exception e) {
            w.println(e.getMessage());
        }
    }
}