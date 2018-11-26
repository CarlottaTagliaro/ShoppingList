/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import it.webproject2018.db.daos.UtenteDAO;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;
import it.webproject2018.job_scheduler.MailSender;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alberto
 */
public class ResetPasswordServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAOFactory daoFactory;
        UtenteDAO userDAO;
        String username = request.getParameter("username");
        
        if (null == username) {
            throw new ServletException("parameter username is null, ResetPasswordServlet aborted");
        }
        
        daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (null == daoFactory) {
            throw new ServletException("dao factory is null");
        }
        
        try {
            userDAO = daoFactory.getDAO(UtenteDAO.class);
        } catch(DAOFactoryException e) {
            throw new ServletException("unable to get userDAO from DAOFactory");
        }
        
        try {
            Utente user = userDAO.getByPrimaryKey(username);
            
            String newPassword = generateRandomPassword();
            
            if (user == null) {
                /* TODO: trigger a user message error */
                response.sendRedirect(request.getContextPath() + "/resetPassword.jsp");
            }
            
            /* TODO: this is not robust in case of faults... */
            MailSender.sendDefault(username, "Reset password", "Your new password is: "+newPassword);
            userDAO.updatePassword(user, newPassword);
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            
        } catch(DAOException e) {
            throw new ServletException("userDAO failed getting user, ResetPasswordServlet aborted");
        } catch (MessagingException ex) {
            throw new ServletException("Failed sending new password");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Send ";
    }// </editor-fold>

    private String generateRandomPassword() {        
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
