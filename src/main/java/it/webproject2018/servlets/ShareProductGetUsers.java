/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import com.google.gson.Gson;
import de.scravy.pair.Pair;
import it.webproject2018.db.daos.jdbc.JDBCProdottoDAO;
import it.webproject2018.db.entities.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author davide
 */
public class ShareProductGetUsers extends HttpServlet {

    private JDBCProdottoDAO JdbcProdottoDao;

    @Override
    public void init() throws ServletException {
        JdbcProdottoDao = new JDBCProdottoDAO(super.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init();

        Utente user = (Utente) request.getSession().getAttribute("User");
        String qry = request.getParameter("qry");
        Integer idProdotto = Integer.parseInt(request.getParameter("idProdotto"));

        if (qry == null) {
            qry = "";
        }
        
        try {
            ArrayList<Pair<Utente, Boolean>> lista = JdbcProdottoDao.getUserToShareWith(idProdotto, user, qry);
            
            ArrayList<UtenteShare> users = new ArrayList<>();
            
            for(Pair<Utente, Boolean> el : lista){
                String nome = el.getFirst().getName() + " " + el.getFirst().getSurname();
                
                users.add(new UtenteShare(el.getFirst().getEmail(), nome, el.getSecond()));
            }
            
            Gson gson = new Gson();
            String json = gson.toJson(users);

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    class UtenteShare{
        public String email;
        public String nome;
        public Boolean shared;
        
        public UtenteShare(String email, String nome, Boolean shared){
            this.email = email;
            this.nome = nome;
            this.shared = shared;
        }
    }
}
