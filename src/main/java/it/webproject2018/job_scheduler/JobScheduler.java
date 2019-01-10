/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.job_scheduler;

import java.util.concurrent.Executors;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;

/**
 * Web application lifecycle listener.
 *
 * @author alberto
 */
@WebListener
public class JobScheduler implements ServletContextListener {

    private java.util.concurrent.ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(new AdviceSender("shoppinglistprogweb@gmail.com", "vadoaprendereillatte", sce.getServletContext()), 0, 5, TimeUnit.MINUTES);
            // scheduler.scheduleAtFixedRate(new AdviceSender(), 0, 1, TimeUnit.SECONDS);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
    }
}
