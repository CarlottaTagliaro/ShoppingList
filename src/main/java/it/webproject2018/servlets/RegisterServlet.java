package it.webproject2018.servlets;

import it.webproject2018.db.daos.UtenteDAO;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;
import it.webproject2018.job_scheduler.MailSender;
import java.util.UUID;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Stefano
 */
public class RegisterServlet extends HttpServlet {
    
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        registerUser(request, response);
    }
    
    protected void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter w = response.getWriter();
        try {
            String name = request.getParameter("nome");
            String surname = request.getParameter("surname");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String confirmString = generateConfirmString();
            Boolean ok = utenteDao.registerUser(name, surname, username, password, confirmString);
        
            if (!ok) {
                HttpSession session = request.getSession(false);
                String error = "Username already exists, choose another one.";
                
                session.setAttribute("error_message", error);
                response.sendRedirect(request.getContextPath() + "/register.jsp");
            }
            else {
                String link = request.getRequestURL().toString().replaceFirst("RegisterServlet","EmailConfirmationServlet?verify=" + confirmString);
                MailSender.sendDefault(username, "Registration Confirm", 
                                       "Click on the following link: " + link);
                    
                
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        } catch (Exception e) {
            w.println(e.getMessage());
        }
    }
    
    private String generateConfirmString() {
        String confString;
        confString = UUID.randomUUID().toString().replace("-", "");
        
        return confString;
    }
}