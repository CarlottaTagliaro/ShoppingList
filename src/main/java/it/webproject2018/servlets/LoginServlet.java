package it.webproject2018.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.webproject2018.db.daos.jdbc.JDBCUtenteDAO;
import it.webproject2018.db.entities.Utente;
import javax.servlet.http.Cookie;

/**
 *
 * @author Stefano
 */
public class LoginServlet extends HttpServlet {

    private JDBCUtenteDAO JdbcUtenteDao;

    @Override
    public void init() throws ServletException {
        JdbcUtenteDao = new JDBCUtenteDAO(super.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init();

        PrintWriter w = response.getWriter();
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Utente user = JdbcUtenteDao.getUserAuthentication(username, password);
            if (user == null || !user.getEmail().equals(username)) {
                JdbcUtenteDao.Close();
                request.getSession().removeAttribute("User");

                response.sendRedirect(request.getContextPath() + "/login.jsp"); // No logged-in user found, so redirect to login page.
            } else {
                String rememberMe = request.getParameter("rememberMe");
                if (rememberMe != null && rememberMe.equals("true")) {
                    //enable remember me
                    String token = JdbcUtenteDao.createRememberMeID(username);
                    Cookie c = new Cookie("rememberMe", token);
                    c.setMaxAge(60 * 24 * 60 * 60); // two months
                    response.addCookie(c);
                }
                JdbcUtenteDao.Close();

                request.getSession().setAttribute("User", user);

                response.sendRedirect(request.getContextPath() + "/home");
                // Logged-in user found, so just continue request.
            }
        } catch (Exception e) {
            e.printStackTrace();
            w.println(e.getMessage());
        }
    }
}
