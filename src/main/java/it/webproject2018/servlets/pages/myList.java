/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets.pages;

import de.scravy.pair.Pair;
import it.webproject2018.db.daos.jdbc.JDBCListaDAO;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.Prodotto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author davide
 */
public class myList extends HttpServlet {

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
            Boolean showNewList = true;
            JDBCListaDAO JdbcListaDao = new JDBCListaDAO(super.getServletContext());
            Utente user = (Utente) request.getSession().getAttribute("User");
            List<Lista> userLists;
            if (user != null) {
                userLists = JdbcListaDao.getUserLists(user.getEmail());
            } else {
                userLists = new ArrayList<>();
                
                ArrayList<Pair<Prodotto, Integer>> defaultList = (ArrayList<Pair<Prodotto, Integer>>) request.getSession().getAttribute("DefaultProductList");

                Lista l = (Lista) request.getSession().getAttribute("DefaultList");
                
                if(l == null){
                    //utente non ha già una lista -> può crearne una nuova
                    showNewList = true;
                }
                else{
                    showNewList = false;
                    //creo lista da visualizzare
                    for(Pair<Prodotto, Integer> p : defaultList){
                        l.add(p.getFirst());
                    }
                    userLists.add(l);
                }
            }
            
            request.setAttribute("userLists", userLists);
            request.setAttribute("showNewList", showNewList);
            
            JdbcListaDao.Close();
            getServletContext().getRequestDispatcher("/myList.jsp").forward(request, response);
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
