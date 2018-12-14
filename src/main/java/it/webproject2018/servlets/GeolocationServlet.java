/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import com.google.gson.Gson;
import it.webproject2018.db.daos.ListaDAO;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;
import it.webproject2018.maps.SearchPlaces;
import java.io.IOException;
import java.io.PrintWriter;
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

	private ListaDAO listaDao;
	
	@Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
		if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
		try {
			listaDao = daoFactory.getDAO(ListaDAO.class);
		} catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for lista storage system", ex);
        }
    }
	
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
        
        ArrayList<GeoResponse> resp = new ArrayList<>();

        try {
            ArrayList<Lista> liste = listaDao.getUserLists(user.getEmail());

            listaDao.Close();
            for (Lista l : liste) {
                SearchPlaces p = SearchPlaces.GetPlaces(lat, lon, 1000, l.getCategoria().getNome());

                for (int i = 0; i < min(p.size(), 1); i++) {
                    resp.add(new GeoResponse(l.getNome(), l.getCategoria().getNome(), p.get(i).Name, p.get(i).Distance));
                }
            }
            
            Gson gson = new Gson();
            String json = gson.toJson(resp);

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    class GeoResponse{
        public String nomeLista;
        public String tipoNegozio;
        public String nomeNegozio;
        public Integer distanza;
        
        public GeoResponse(String nomeLista, String tipoNegozio, String nomeNegozio, Integer distanza){
            this.nomeLista = nomeLista;
            this.tipoNegozio = tipoNegozio;
            this.nomeNegozio = nomeNegozio;
            this.distanza = distanza;
        }
    }
}
