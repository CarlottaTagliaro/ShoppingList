package it.webproject2018.servlets;

import it.webproject2018.db.DBManager;
import it.webproject2018.db.Utente;
import java.io.IOException;
import java.io.PrintWriter;
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
    private DBManager dbManager;

    @Override
    public void init() throws ServletException {
        // If you have any <init-param> in web.xml, then you could get them
        // here by config.getInitParameter("name") and assign it as field.
        dbManager = (DBManager) super.getServletContext().getAttribute("dbmanager");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter w = response.getWriter();
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Utente user = dbManager.getUserAuthentication(username, password);
            if (user == null || !user.getEmail().equals(username)) {
                response.sendRedirect(request.getContextPath() + "/login.jsp"); // No logged-in user found, so redirect to login page.
            }
            else {
                request.getSession().setAttribute("IsAdmin", user.getIsAdmin());
                response.sendRedirect(request.getContextPath() + "/home.jsp");
                // Logged-in user found, so just continue request.
            }
        } catch (Exception e) {
            w.println(e.getMessage());
        }
    }
}
