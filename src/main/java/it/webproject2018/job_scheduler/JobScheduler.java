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
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import it.webproject2018.job_scheduler.AdviceSender;
import java.util.concurrent.ScheduledFuture;
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
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new AdviceSender("", "", sce.getServletContext()), 0, 10000, TimeUnit.DAYS);
        // scheduler.scheduleAtFixedRate(new AdviceSender(), 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
    }
}
