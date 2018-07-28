/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package job_scheduler;

import it.webproject2018.db.CategoriaProdotti;
import it.webproject2018.db.Prodotto;
import it.webproject2018.db.Utente;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import job_scheduler.MailSender;
/**
 *
 * @author caramellaio
 */
public class AdviceSender implements Runnable {
    
    private String mail;
    private String password;
    private boolean run = true;
    @Override
    public void run() {
        /* qui c'e' solo codice esempio che ho usato per testare, va.*/
        String dest_mail = "";
        if (this.run) {
            System.out.println("porco dio!");
            mail = "web2018unitn@gmail.com";
            password = "shoppinglist18";
            ArrayList<Prodotto> products = new ArrayList<>();
            products.add(new Prodotto(Integer.SIZE, "rastrello", "rastrella", 
                                      "a", "a", new CategoriaProdotti("cose", "", "", null) ));
            this.sendMailTo(new Utente("pippo","aaa", dest_mail, "lalala", false), products);
        }
        this.run = false;
    }
    
    void sendMailTo(Utente user, ArrayList<Prodotto> products) {
        String email = user.getEmail();
        String header = "SHOPPING LIST: prodotti in scadenza";
        try {
            MailSender.Send(mail, password, user.getEmail(), header,
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
    
    public void setMailSender(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }
}
