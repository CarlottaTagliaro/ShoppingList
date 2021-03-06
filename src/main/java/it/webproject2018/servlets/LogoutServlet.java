package it.webproject2018.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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

            //remove rememberMe cookie
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if (c.getName().equals("rememberMe")) {
                    c.setMaxAge(0);
                    c.setValue(null);
                    response.addCookie(c);
                }
            }

            response.sendRedirect(request.getContextPath() + "/home");
        } catch (Exception e) {
            w.println(e.getMessage());
        }
    }
}
