/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.job_scheduler;

import it.webproject2018.db.entities.CategoriaProdotti;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.entities.Utente;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import it.webproject2018.job_scheduler.MailSender;
import it.webproject2018.db.daos.jdbc.JDBCNotificaDAO;
import it.webproject2018.db.entities.Notifica;
import it.webproject2018.db.exceptions.DAOException;
import java.sql.Connection;
import java.util.List;
/**
 *
 * @author alberto
 */
public class AdviceSender implements Runnable {
    
    private String senderMail;
    private String password;
    private Connection conn;
    
    public AdviceSender(String senderMail, String password, Connection conn) {
        this.senderMail = senderMail;
        this.password = password;
        this.conn = conn;
    }

    @Override
    public void run() {
        /* qui c'e' solo codice esempio che ho usato per testare, va.*/
        /*String dest_mail = "";
        if (this.run) {
            System.out.println("porco dio!");
            mail = "web2018unitn@gmail.com";
            password = "shoppinglist18";
            ArrayList<Prodotto> products = new ArrayList<>();
            ArrayList<String> images = new ArrayList<>();
            products.add(new Prodotto(Integer.SIZE, "rastrello", "rastrella", 
                                      "a", images, new CategoriaProdotti("cose", "", "", null), "g.s@agg.it"));
            this.sendMailTo(new Utente("pippo","aaa", dest_mail, "lalala", false), products);
        }
        this.run = false;*/
        JDBCNotificaDAO notificationDao = new JDBCNotificaDAO(conn);
        try {
            notificationDao.generateNotificationsByProducts();
        } catch (DAOException ex) {
            System.err.println(ex.getMessage());
        }
        
        try {
            List<Notifica> notifications = notificationDao.getAllNotificationNotSentByEmail();
            /* TODO: continua qui */
        } catch (DAOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    void sendMailTo(Utente user, ArrayList<Prodotto> products) {
        String email = user.getEmail();
        String header = "SHOPPING LIST: prodotti in scadenza";
        try {
            MailSender.Send(this.senderMail, this.password, user.getEmail(), header,
                    this.generateMessage(user, products));
        } catch (MessagingException ex) {
            System.err.println("Fail sending email");
            Logger.getLogger(AdviceSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    String generateMessage(Utente user, ArrayList<Prodotto> products) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(String.format("Ciao: %s, hai dei prodotti in scadenza:\n",
                                            user.getSurname()));
        for (Prodotto p : products) {
            messageBuilder.append(String.format("PRODOTTO %s, CATEGORIA: %s.\n",
                                                p.getNome(), p.getCategoria().getNome()));
        }
        
        messageBuilder.append("Buona Giornata da SHOPPING LIST.");
        return messageBuilder.toString();
    }
}
