package it.webproject2018.servlets;

import it.webproject2018.db.daos.UtenteDAO;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Stefano
 */
public class LoginServlet extends HttpServlet {

    private UtenteDAO userDao;

    @Override
    public void init() throws ServletException {
		DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
		if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
		try {
			userDao = daoFactory.getDAO(UtenteDAO.class);
		} catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for user storage system", ex);
        }
	}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init();
		
        PrintWriter w = response.getWriter();
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Utente user = userDao.getUserAuthentication(username, password);
            
			if (user == null || !user.getEmail().equals(username) || null != user.getConfString()) {
                HttpSession session = request.getSession(false);
                String error = null;
                request.getSession().removeAttribute("User");

                if (null == user || ! user.getEmail().equals(username)) {
                    error = "Error: username or password are wrong";
                } else {
                    error = "Error: user has not been confirmed. Confirm your email.";
                }
                session.setAttribute("error_message", error);
                response.sendRedirect(request.getContextPath() + "/login.jsp"); // No logged-in user found, so redirect to login page.
            } else {
                String rememberMe = request.getParameter("rememberMe");
                if (rememberMe != null && rememberMe.equals("true")) {
                    //enable remember me
                    String token = userDao.createRememberMeID(username);
                    Cookie c = new Cookie("rememberMe", token);
                    c.setMaxAge(60 * 24 * 60 * 60); // two months
                    response.addCookie(c);
                }

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
