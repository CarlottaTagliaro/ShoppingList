/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.filters;

import it.webproject2018.db.daos.jdbc.JDBCUtenteDAO;
import it.webproject2018.db.entities.Utente;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author davide
 */
public class AutoLoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            if (req.getSession().getAttribute("User") == null) {
                Cookie[] cookies = req.getCookies();
                for (int i = 0; i < cookies.length; i++) {
                    Cookie c = cookies[i];
                    if (c.getName().equals("rememberMe")) {
                        //do automatic login
                        System.out.println("user automatic logged in");
                        String token = c.getValue();
                        
                        JDBCUtenteDAO JdbcUtenteDao = new JDBCUtenteDAO(request.getServletContext());
                        
                        Utente user = JdbcUtenteDao.getUserByRememberMeID(token);
                        req.getSession().setAttribute("User", user);
                        
                        JdbcUtenteDao.Close();
                    }
                }
            }

            //continue navigating
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    @Override
    public void init(FilterConfig filterConfig) {
    }
}
