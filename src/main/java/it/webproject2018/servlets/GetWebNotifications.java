/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import com.google.gson.Gson;
import it.webproject2018.db.daos.NotificaWebDAO;
import it.webproject2018.db.daos.UtenteDAO;
import it.webproject2018.db.entities.NotificaWeb;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;
import java.io.IOException;
import java.io.PrintWriter;
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

    private NotificaWebDAO notificaWebDao;
    private UtenteDAO utenteDao;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            notificaWebDao = daoFactory.getDAO(NotificaWebDAO.class);
            utenteDao = daoFactory.getDAO(UtenteDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for notifica and utente storage system", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init();

        Utente user = (Utente) request.getSession().getAttribute("User");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (user != null) {
            try {
                List<NotificaWeb> notifiche = notificaWebDao.getAllUserNotifications(user.getEmail());

                String onlyNews = request.getParameter("onlyNews");

                if (onlyNews == null) {
                    Gson gson = new Gson();
                    String json = gson.toJson(notifiche);
                    out.print(json);

                    //aggiornare user ultima visualizzazione
                    utenteDao.updateUserLastAccess(user);
                } else if (onlyNews.equals("true")) {
                    String output = notifiche.stream().anyMatch(x -> x.getIsNew()) ? "true" : "false";
                    out.print(output);
                }

                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            out.print("[]");
        }
    }

}
