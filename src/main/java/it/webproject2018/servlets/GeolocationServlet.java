/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import it.webproject2018.db.daos.jdbc.JDBCListaDAO;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.maps.SearchPlaces;
import java.io.IOException;
import static java.lang.Integer.min;
import java.util.ArrayList;
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

        if (latitudine == null || longitudine == null) {
            response.setStatus(400); // bad request
        }
        Float lat = Float.parseFloat(latitudine);
        Float lon = Float.parseFloat(longitudine);

        try {
            JDBCListaDAO listaDao = new JDBCListaDAO(super.getServletContext());
            ArrayList<Lista> liste = listaDao.getUserLists(user.getEmail());

            for (Lista l : liste) {
                SearchPlaces p = SearchPlaces.GetPlaces(lat, lon, 1000, l.getCategoria().getNome());

                for (int i = 0; i < min(p.size(), 1); i++) {
                    System.out.println("Negozio vicino: " + p.get(i).Name + " - " + l.getCategoria().getNome() + " a " + p.get(i).Distance + " m");
                    System.out.println("-------");

                    //send notification
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
