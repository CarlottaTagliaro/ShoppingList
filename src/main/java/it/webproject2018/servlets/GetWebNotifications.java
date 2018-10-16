/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import com.google.gson.Gson;
import it.webproject2018.db.daos.jdbc.JDBCNotificaWebDAO;
import it.webproject2018.db.entities.NotificaWeb;
import it.webproject2018.db.entities.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author davide
 */
public class GetWebNotifications extends HttpServlet {
    private JDBCNotificaWebDAO JdbcNotificaWebDao;
        
    @Override
    public void init() throws ServletException {
        JdbcNotificaWebDao = new JDBCNotificaWebDAO(super.getServletContext());
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init();

        Utente user = (Utente) request.getSession().getAttribute("User");
        
        try {
            List<NotificaWeb> notifiche = JdbcNotificaWebDao.getAllUserNotifications(user.getEmail());
            
            Gson gson = new Gson();
            String json = gson.toJson(notifiche);
            
            //TODO: aggiornare user ultima visualizzazione

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        JdbcNotificaWebDao.Close();
    }

}
