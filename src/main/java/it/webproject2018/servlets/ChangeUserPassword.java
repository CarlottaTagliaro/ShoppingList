package it.webproject2018.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.webproject2018.db.daos.jdbc.JDBCUtenteDAO;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;

/**
 *
 * @author Stefano
 */
public class ChangeUserPassword extends HttpServlet {

    private JDBCUtenteDAO JDBCUtente;

    @Override
    public void init() throws ServletException {
        JDBCUtente = new JDBCUtenteDAO(super.getServletContext());
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
                user = JDBCUtente.updatePassword(user, newP);
                ok = user != null;
            }
            
            JDBCUtente.Close();
            response.sendRedirect(request.getContextPath() + (!ok ? "/profile" : "/profile"));
        } catch (DAOException e) {
            w.println(e.getMessage());
        }
    }
}