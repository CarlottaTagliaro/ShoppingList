package it.webproject2018.servlets;

import it.webproject2018.db.daos.jdbc.JDBCUtenteDAO;
import it.webproject2018.db.entities.DBManager;
import it.webproject2018.db.entities.Utente;
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
public class LoginServlet extends HttpServlet {
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
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            Utente user = JdbcUtenteDao.getUserAuthentication(username, password);
            if (user == null || !user.getEmail().equals(username)) {
                request.getSession().removeAttribute("User");
                response.sendRedirect(request.getContextPath() + "/login.jsp"); // No logged-in user found, so redirect to login page.
            }
            else {
                request.getSession().setAttribute("User", user);
                response.sendRedirect(request.getContextPath() + "/home.jsp");
                // Logged-in user found, so just continue request.
            }
        } catch (Exception e) {
            w.println(e.getMessage());
        }
    }
}
