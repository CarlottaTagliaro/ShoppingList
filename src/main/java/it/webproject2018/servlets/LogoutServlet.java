package it.webproject2018.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefano
 */
public class LogoutServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter w = response.getWriter();
        try {
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/home.jsp");
        } catch (Exception e) {
            w.println(e.getMessage());
        }
    }
}