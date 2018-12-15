/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.job_scheduler;

import it.webproject2018.db.daos.NotificaDAO;
import it.webproject2018.db.daos.UtenteDAO;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.entities.Utente;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import it.webproject2018.db.entities.Notifica;
import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 * @author alberto
 */
public class AdviceSender implements Runnable {

    private final String senderMail;
    private final String password;
    private final ServletContext servletContext;

    private UtenteDAO utenteDao;
    private NotificaDAO notificationDao;

    public AdviceSender(String senderMail, String password, ServletContext servletContext) throws ServletException {
        this.senderMail = senderMail;
        this.password = password;
        this.servletContext = servletContext;

        DAOFactory daoFactory = (DAOFactory) this.servletContext.getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            utenteDao = daoFactory.getDAO(UtenteDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for utente storage system", ex);
        }
        try {
            notificationDao = daoFactory.getDAO(NotificaDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for notifiche storage system", ex);
        }
    }

    @Override
    public void run() {
        /* qui c'e' solo codice esempio che ho usato per testare, va.*/
 /*String dest_mail = "";
        if (this.run) {
            mail = "web2018unitn@gmail.com";
            password = "shoppinglist18";
            ArrayList<Prodotto> products = new ArrayList<>();
            ArrayList<String> images = new ArrayList<>();
            products.add(new Prodotto(Integer.SIZE, "rastrello", "rastrella", 
                                      "a", images, new CategoriaProdotti("cose", "", "", null), "g.s@agg.it"));
            this.sendMailTo(new Utente("pippo","aaa", dest_mail, "lalala", false), products);
        }
        this.run = false;*/

        try {
            ArrayList<Notifica> notifications = null;
            Map<String, ArrayList<Prodotto>> userProductsMap = null;

            userProductsMap = new HashMap<>();
            notificationDao.generateNotificationsByProducts();
            notifications = notificationDao.getAllNotificationsNotSentByEmail();

            /* group the notifications by user email */
            for (Notifica notification : notifications) {
                String userEmail = notification.getLista().getOwner();

                if (userProductsMap.containsKey(userEmail)) {

                } else {
                    ArrayList<Prodotto> toAdd = new ArrayList<>();
                    toAdd.add(notification.getProdotto());
                    userProductsMap.put(userEmail, toAdd);
                    notification.setMail(true);
                    notificationDao.update(notification);
                }
            }

            for (Map.Entry<String, ArrayList<Prodotto>> entry : userProductsMap.entrySet()) {
                /* not efficient... */
                sendMailTo(utenteDao.getByPrimaryKey(entry.getKey()), entry.getValue());
            }

            /* setta flag mail */
        } catch (DAOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    void sendMailTo(Utente user, ArrayList<Prodotto> products) {
        String header = "SHOPPING LIST: ran off products";
        try {
            MailSender.send(this.senderMail, this.password, user.getEmail(), header,
                    this.generateMessage(user, products));
        } catch (MessagingException ex) {
            System.err.println("Fail sending email");
            Logger.getLogger(AdviceSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String generateMessage(Utente user, ArrayList<Prodotto> products) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(String.format("Hey: %s, you are running out of these products:\n",
                user.getSurname()));
        for (Prodotto p : products) {
            messageBuilder.append(String.format("PRODUCT %s, CATEGORY: %s.\n",
                    p.getNome(), p.getCategoria().getNome()));
        }

        messageBuilder.append("Have a nice day from SHOPPING LIST.");
        return messageBuilder.toString();
    }
}
