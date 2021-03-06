/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets.pages;

import it.webproject2018.db.daos.UtenteDAO;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alberto
 */
@WebServlet(name = "EmailConfirmationServlet", urlPatterns = {"/EmailConfirmationServlet.jsp"})
public class EmailConfirmationServlet extends HttpServlet {
    
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
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DAOFactoryException {
        String hashStr = request.getParameter("verify");
                        
        if (this.handleVerifyString(hashStr)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
        else {
            /* TODO[Alberto]: add a proper error */
            System.out.println("unable to verify user");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DAOFactoryException ex) {
            Logger.getLogger(EmailConfirmationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DAOFactoryException ex) {
            Logger.getLogger(EmailConfirmationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    protected boolean handleVerifyString(String verifyString) throws ServletException {
		
        try {
            Utente user = userDao.getUserByConfString(verifyString);
            
            /* case where the user was not found */
            if (null == user) {
                return false;
            }
            
            /* TODO[alberto]: check if null is a proper value */
            user.setConfString(null);
            
            userDao.update(user);
            
            return true;
        }
        catch (DAOException ex) {
            throw new ServletException("Unable to check verify string, error in jdbcUtenteDao", ex);
        }
    }
}
