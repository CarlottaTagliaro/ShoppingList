package it.webproject2018.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.webproject2018.db.daos.jdbc.JDBCUtenteDAO;
import it.webproject2018.job_scheduler.MailSender;
import java.util.UUID;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Stefano
 */
public class RegisterServlet extends HttpServlet {
    private JDBCUtenteDAO JdbcUtenteDao;

    @Override
    public void init() throws ServletException {
        /* TODO: use DaoFactory */
        JdbcUtenteDao = new JDBCUtenteDAO(super.getServletContext());
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
            Boolean ok = JdbcUtenteDao.registerUser(name, surname, username, password, confirmString);
        
            if (!ok) {
                HttpSession session = request.getSession(false);
                String error = "Username already exists, choose another one.";
                JdbcUtenteDao.Close();
                
                session.setAttribute("error_message", error);
                response.sendRedirect(request.getContextPath() + "/register.jsp");
            }
            else {
                String link = request.getRequestURL().toString().replaceFirst("RegisterServlet","EmailConfirmationServlet?verify=" + confirmString);
                JdbcUtenteDao.Close();
                MailSender.sendDefault(username, "Conferma registrazione", 
                                       "link: " + link);
                    
                
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