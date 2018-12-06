/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import de.scravy.pair.Pairs;
import it.webproject2018.db.daos.ListaPermessiDAO;
import it.webproject2018.db.entities.ListaPermessi;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author davide
 */
public class AcceptListShare extends HttpServlet {

    private ListaPermessiDAO listaPermessiDao;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
		if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
		try {
			listaPermessiDao = daoFactory.getDAO(ListaPermessiDAO.class);
		} catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for lista permessi storage system", ex);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init();

        Utente user = (Utente) request.getSession().getAttribute("User");
        Integer idLista = Integer.parseInt(request.getParameter("idLista"));

        try {
            ListaPermessi l = listaPermessiDao.getByPrimaryKey(Pairs.from(user.getEmail(), idLista));
            l.setAccettato(true);
            listaPermessiDao.update(l);
        }
        catch(DAOException e) {
            e.printStackTrace();
        }
        
        //redirect on login page
        response.sendRedirect(request.getHeader("referer"));
    }
}
