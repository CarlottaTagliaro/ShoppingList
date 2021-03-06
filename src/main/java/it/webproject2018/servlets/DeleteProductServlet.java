/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import de.scravy.pair.Pair;
import it.webproject2018.db.daos.ProdottoDAO;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Max
 */
public class DeleteProductServlet extends HttpServlet {

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

    /**
     * Processes requests for HTTP GET methods for deleting products form a
     * certain list
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        init();

        Utente user = (Utente) request.getSession().getAttribute("User");
        String ID_product = request.getParameter("Product");
        String ID_list = request.getParameter("List");

        if (user != null) {
            try {

                Integer product = Integer.parseInt(ID_product);
                Integer list = Integer.parseInt(ID_list);

                Boolean ok = prodottoDao.deleteFromList(product, list);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        } else {
            ArrayList<Pair<Prodotto, Integer>> defaultList = (ArrayList<Pair<Prodotto, Integer>>) request.getSession().getAttribute("DefaultProductList");

            for (int i = 0; i < defaultList.size(); i++) {
                if (defaultList.get(i).getFirst().getId().equals(Integer.parseInt(ID_product))) {
                    defaultList.remove(i);
                    break;
                }
            }
            
            request.getSession().setAttribute("DefaultProductList", defaultList);
        }

        response.sendRedirect(request.getContextPath().concat("/myList"));
    }
}
