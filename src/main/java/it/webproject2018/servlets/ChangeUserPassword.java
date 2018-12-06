package it.webproject2018.servlets;

import it.webproject2018.db.daos.UtenteDAO;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;

/**
 *
 * @author Stefano
 */
public class ChangeUserPassword extends HttpServlet {

    private UtenteDAO utenteDao;

    @Override
    public void init() throws ServletException {
		DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
		if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
		try {
			utenteDao = daoFactory.getDAO(UtenteDAO.class);
		} catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for user storage system", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        init();
        PrintWriter w = response.getWriter();
        try {
            Utente user = (Utente) request.getSession().getAttribute("User");
            String newP = request.getParameter("NewPassword");
            String confirmP = request.getParameter("ConfirmPassword");
            Boolean ok = false;

            if(newP.equals(confirmP)) {
                user = utenteDao.updatePassword(user, newP);
                ok = user != null;
            }
            
            response.sendRedirect(request.getContextPath() + (!ok ? "/profile" : "/profile"));
        } catch (DAOException e) {
            w.println(e.getMessage());
        }
    }
}