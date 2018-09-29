package it.webproject2018.servlets;

import it.webproject2018.db.daos.jdbc.JDBCProdottoDAO;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.entities.CategoriaProdotti;
import it.webproject2018.db.entities.Prodotto;
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
public class CreateProductServlet extends HttpServlet {
    private JDBCProdottoDAO JDBCProdottoDAO;

    @Override
    public void init() throws ServletException {
        Connection conn = (Connection) super.getServletContext().getAttribute("connection");
        JDBCProdottoDAO = new JDBCProdottoDAO(conn);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter w = response.getWriter();
        try {
            Utente user = (Utente)request.getSession().getAttribute("User");
            String name = request.getParameter("name");
            String category = request.getParameter("selectCategory");
            String description = request.getParameter("description");
            
            Prodotto prod = new Prodotto(null, name, description, null, null, new CategoriaProdotti(category));
            prod.setOwner(user);
            Boolean ok = JDBCProdottoDAO.insert(prod);
            response.sendRedirect(request.getContextPath() + (!ok ? "/newProduct.jsp" : "/myProducts.jsp"));
        } catch (Exception e) {
            w.println(e.getMessage());
        }
    }
}