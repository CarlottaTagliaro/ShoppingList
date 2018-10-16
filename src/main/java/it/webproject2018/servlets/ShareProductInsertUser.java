/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import it.webproject2018.db.daos.jdbc.JDBCProdottoDAO;
import it.webproject2018.db.entities.ListaPermessi;
import it.webproject2018.db.entities.Utente;
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

    private JDBCProdottoDAO JdbcProdottoDao;

    @Override
    public void init() throws ServletException {
        JdbcProdottoDao = new JDBCProdottoDAO(super.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init();

        Utente user = (Utente) request.getSession().getAttribute("User");
        Integer idProdotto = Integer.parseInt(request.getParameter("idProdotto"));

        String[] share = request.getParameterValues("share");

        try {
            ArrayList<Utente> old_share = JdbcProdottoDao.getUserSharedProduct(idProdotto);
                        
            for (String s : share) {
                Utente[] user_array = old_share.toArray(new Utente[old_share.size()]);
                Optional<Utente> optional = Arrays.stream(user_array)
                        .filter(x -> x.getEmail().equals(s))
                        .findFirst();

                if (!optional.isPresent()) {
                    //insert
                    //TODO: send email notifications
                    JdbcProdottoDao.shareProduct(idProdotto, s);
                }
                else{
                    //remove from array
                    old_share.remove(optional.get());
                }
            }

            //delete
            for(Utente u : old_share){
                JdbcProdottoDao.deleteShareProduct(idProdotto, u.getEmail());
            }
                        
            //TODO: redirect to correct origin page
            response.sendRedirect(request.getContextPath().concat("/home"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JdbcProdottoDao.Close();
        //response.sendRedirect(request.getContextPath().concat("/myList"));
    }
}
