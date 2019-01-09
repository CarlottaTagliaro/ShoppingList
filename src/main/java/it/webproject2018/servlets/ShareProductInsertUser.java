/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import it.webproject2018.db.daos.ProdottoDAO;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author davide
 */
public class ShareProductInsertUser extends HttpServlet {

    private ProdottoDAO prodottoDao;

    @Override
    public void init() throws ServletException {
		DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
		if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
		try {
			prodottoDao = daoFactory.getDAO(ProdottoDAO.class);
		} catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for prodotto storage system", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init();

        Utente user = (Utente) request.getSession().getAttribute("User");
        Integer idProdotto = Integer.parseInt(request.getParameter("idProdotto"));

        String[] share = request.getParameterValues("share");

        try {
            ArrayList<Utente> old_share = prodottoDao.getUserSharedProduct(idProdotto);
                        
            for (String s : share) {
                Utente[] user_array = old_share.toArray(new Utente[old_share.size()]);
                Optional<Utente> optional = Arrays.stream(user_array)
                        .filter(x -> x.getEmail().equals(s))
                        .findFirst();

                if (!optional.isPresent()) {
                    //insert
                    prodottoDao.shareProduct(idProdotto, s);
                }
                else{
                    //remove from array
                    old_share.remove(optional.get());
                }
            }

            //delete
            for(Utente u : old_share){
                prodottoDao.deleteShareProduct(idProdotto, u.getEmail());
            }
                        
            //redirect on login page
            response.sendRedirect(request.getHeader("referer"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
