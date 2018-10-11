/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import it.webproject2018.db.entities.Utente;
import it.webproject2018.maps.SearchPlaces;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author davide
 */
public class GeolocationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente user = (Utente) request.getSession().getAttribute("User");
        
        String latitudine = request.getParameter("lat");
        String longitudine = request.getParameter("long");
        
        if(latitudine == null || longitudine == null)
            response.setStatus(400); // bad request
        
        Float lat = Float.parseFloat(latitudine);
        Float lon = Float.parseFloat(longitudine);
        
        SearchPlaces p = SearchPlaces.GetPlaces(lat, lon, 900, "farmacia");
        
        for(int i = 0; i < p.size(); i++){
            System.out.println(p.get(i).Name);
            System.out.println(p.get(i).Distance);
            System.out.println("-------");
        }
        
        
    }
}
