/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import com.google.gson.Gson;
import de.scravy.pair.Pair;
import it.webproject2018.db.daos.jdbc.JDBCListaPermessiDAO;
import it.webproject2018.db.entities.ListaPermessi;
import it.webproject2018.db.entities.Utente;
import java.io.IOException;
import java.io.PrintWriter;
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
public class ShareGetUsers extends HttpServlet {

    private JDBCListaPermessiDAO JdbcListaPermessiDao;

    @Override
    public void init() throws ServletException {
        JdbcListaPermessiDao = new JDBCListaPermessiDAO(super.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init();

        Utente user = (Utente) request.getSession().getAttribute("User");
        String qry = request.getParameter("qry");
        Integer idLista = Integer.parseInt(request.getParameter("idLista"));
        
        if (qry == null) {
            qry = "";
        }
        
        try {
            List<Pair<Utente, ListaPermessi>> liste = JdbcListaPermessiDao.getShareUserList(user, qry, idLista);
            JdbcListaPermessiDao.Close();
            ArrayList<UtentePermesso> resp_list = new ArrayList<>();
            
            for(Pair<Utente, ListaPermessi> el : liste){
                String nome = el.getFirst().getName() + " " + el.getFirst().getSurname();
                
                resp_list.add(new UtentePermesso(nome, el.getFirst().getEmail(), el.getSecond().getPerm_edit(), el.getSecond().getPerm_add_rem(), el.getSecond().getPerm_del()));
            }
            
            Gson gson = new Gson();
            String json = gson.toJson(resp_list);

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    class UtentePermesso{
        public String Nome;
        public String Email;
        public Boolean perm_edit;
        public Boolean perm_add_rem;
        public Boolean perm_del;
        
        public UtentePermesso(String Nome, String Email, Boolean perm_edit, Boolean perm_add_rem, Boolean perm_del){
            this.Nome = Nome;
            this.Email = Email;
            this.perm_edit = perm_edit;
            this.perm_add_rem = perm_add_rem;
            this.perm_del = perm_del;
        }
    }
}
