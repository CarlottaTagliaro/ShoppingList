/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import de.scravy.pair.Pairs;
import it.webproject2018.db.daos.jdbc.JDBCListaPermessiDAO;
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
public class ShareListInsertUser extends HttpServlet {

    private JDBCListaPermessiDAO JdbcListaPermessiDao;

    @Override
    public void init() throws ServletException {
        JdbcListaPermessiDao = new JDBCListaPermessiDAO(super.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init();

        Utente user = (Utente) request.getSession().getAttribute("User");
        Integer idLista = Integer.parseInt(request.getParameter("idLista"));

        ArrayList<ListaPermessi> new_perm = getNewPerm(request, user.getEmail(), idLista);
        ListaPermessi[] new_perm_array = new_perm.toArray(new ListaPermessi[new_perm.size()]);
        
        try {
            ArrayList<ListaPermessi> old_perm = JdbcListaPermessiDao.getAllByList(idLista, user.getEmail());
            ListaPermessi[] old_perm_array = old_perm.toArray(new ListaPermessi[old_perm.size()]);

            for (ListaPermessi l : new_perm_array) {
                Optional<ListaPermessi> optional = Arrays.stream(old_perm_array)
                        .filter(x -> x.getEmail().equals(l.getEmail()))
                        .findFirst();
                
                if(optional.isPresent()){
                    ListaPermessi p = optional.get();
                    //check if update
                    if(!l.getPerm_add_rem().equals(p.getPerm_add_rem()) ||
                            !l.getPerm_edit().equals(p.getPerm_edit()) ||
                            !l.getPerm_del().equals(p.getPerm_del())){
                        //update
                        l.setAccettato(p.getAccettato());
                        JdbcListaPermessiDao.update(l);
                    }
                    
                    old_perm.remove(p);
                }
                else{
                    //insert
                    //TODO: send email
                    JdbcListaPermessiDao.insert(l);
                }
                
                new_perm.remove(l);
            }
            
            for(ListaPermessi l : old_perm){
                //delete
                JdbcListaPermessiDao.delete(Pairs.from(l.getEmail(), l.getId_lista()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
                
        response.sendRedirect(request.getContextPath().concat("/myList"));
    }

    protected ArrayList<ListaPermessi> getNewPerm(HttpServletRequest request, String email, Integer idLista) {
        String[] perm_add_rem = request.getParameterValues("perm_add_rem");
        String[] perm_edit = request.getParameterValues("perm_edit");
        String[] perm_del = request.getParameterValues("perm_del");

        ArrayList<ListaPermessi> new_perm = new ArrayList<>();

        for (String el : perm_add_rem) {
            ListaPermessi[] perm_array = new_perm.toArray(new ListaPermessi[new_perm.size()]);
            Optional<ListaPermessi> optional = Arrays.stream(perm_array)
                    .filter(x -> x.getEmail().equals(el))
                    .findFirst();

            if (optional.isPresent()) {
                optional.get().setPerm_add_rem(true);
            } else {
                ListaPermessi perm = new ListaPermessi(el, idLista);
                perm.setPerm_add_rem(true);
                new_perm.add(perm);
            }
        }

        for (String el : perm_edit) {
            ListaPermessi[] perm_array = new_perm.toArray(new ListaPermessi[new_perm.size()]);
            Optional<ListaPermessi> optional = Arrays.stream(perm_array)
                    .filter(x -> x.getEmail().equals(el))
                    .findFirst();

            if (optional.isPresent()) {
                optional.get().setPerm_edit(true);
            } else {
                ListaPermessi perm = new ListaPermessi(el, idLista);
                perm.setPerm_edit(true);
                new_perm.add(perm);
            }
        }

        for (String el : perm_del) {
            ListaPermessi[] perm_array = new_perm.toArray(new ListaPermessi[new_perm.size()]);
            Optional<ListaPermessi> optional = Arrays.stream(perm_array)
                    .filter(x -> x.getEmail().equals(el))
                    .findFirst();

            if (optional.isPresent()) {
                optional.get().setPerm_del(true);
            } else {
                ListaPermessi perm = new ListaPermessi(el, idLista);
                perm.setPerm_del(true);
                new_perm.add(perm);
            }
        }

        return new_perm;
    }
}
