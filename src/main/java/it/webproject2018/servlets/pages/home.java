/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets.pages;

import it.webproject2018.db.daos.ProdottoDAO;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author davide
 */
public class home extends HttpServlet {
    private Integer numElem = 10;
        
    private ProdottoDAO prodottoDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for prodotto storage system");
        }
        try {
            prodottoDAO = daoFactory.getDAO(ProdottoDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for prodotto storage system", ex);
        }
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Utente user = (Utente) request.getSession().getAttribute("User");
            List<Prodotto> productList;

            String srcText = request.getParameter("qry");
            if(srcText == null)
                srcText = "";
            String orderBy = request.getParameter("orderBy");
            if(orderBy == null)
                orderBy = "";
            String page = request.getParameter("page");
            Integer pageN = 0;
            if(page != null)
                pageN = Integer.parseInt(page);

            Integer start = numElem * pageN;
            Long count = 0L;
            
            if (user != null) {
                productList = prodottoDAO.getAllUserVisibleProducts(user.getEmail(), srcText, orderBy, numElem, start);
                count = prodottoDAO.getCountUserVisibleProducts(user.getEmail(), srcText);
            } else {                
                productList = prodottoDAO.getAllVisibleProducts(srcText, orderBy, numElem, start);
                count = prodottoDAO.getCountVisibleProducts(srcText);
            }
            
            count = (long)Math.ceil((double)count / numElem);
            
            request.setAttribute("productList", productList);
            request.setAttribute("qry", srcText);
            request.setAttribute("orderBy", orderBy);
            request.setAttribute("page", pageN);
            request.setAttribute("count", count);
            
            getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
