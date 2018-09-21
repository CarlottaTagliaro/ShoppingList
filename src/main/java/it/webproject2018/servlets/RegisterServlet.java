package it.webproject2018.servlets;

import it.webproject2018.db.daos.jdbc.JDBCUtenteDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefano
 */
public class RegisterServlet extends HttpServlet {
    private JDBCUtenteDAO JdbcUtenteDao;

    @Override
    public void init() throws ServletException {
        Connection conn = (Connection) super.getServletContext().getAttribute("connection");
        JdbcUtenteDao = new JDBCUtenteDAO(conn);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter w = response.getWriter();
        try {
            String name = request.getParameter("nome");
            String surname = request.getParameter("surname");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            Boolean ok = JdbcUtenteDao.RegisterUser(name, surname, username, password);
            if (!ok) {
                response.sendRedirect(request.getContextPath() + "/register.jsp");
            }
            else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        } catch (Exception e) {
            w.println(e.getMessage());
        }
    }
}