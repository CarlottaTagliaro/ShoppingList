package it.webproject2018.servlets;

import de.scravy.pair.Pair;
import de.scravy.pair.Pairs;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.webproject2018.db.daos.jdbc.JDBCListaDAO;
import it.webproject2018.db.daos.jdbc.JDBCProdottoDAO;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.entities.Utente;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 *
 * @author Stefano
 */
public class AddProductToList extends HttpServlet {

    private JDBCListaDAO JDBCLista;
    private JDBCProdottoDAO JDBCProdotto;

    @Override
    public void init() throws ServletException {
        JDBCLista = new JDBCListaDAO(super.getServletContext());
        JDBCProdotto = new JDBCProdottoDAO(super.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init();
        PrintWriter w = response.getWriter();
        try {
            Boolean ok = true;
            
            Integer idProduct = Integer.parseInt(request.getParameter("idProduct"));
            Integer idList = Integer.parseInt(request.getParameter("list"));
            Integer alreadyAdd = Integer.parseInt(request.getParameter("alreadyAdd"));
            Integer amount = Integer.parseInt(request.getParameter("amount_value"));
            
            Boolean logged = (Utente) request.getSession().getAttribute("User") != null;
            
            if(!logged){   
                ArrayList<Pair<Prodotto, Integer>> defaultList = (ArrayList<Pair<Prodotto, Integer>>) request.getSession().getAttribute("DefaultProductList");;
                Lista l = (Lista) request.getSession().getAttribute("DefaultList");
                Prodotto p = JDBCProdotto.getByPrimaryKey(idProduct);
                Object[] stockArr = new Object[defaultList.size()];
                stockArr = defaultList.toArray(stockArr);
                
                Optional<Object> optional = Arrays.stream(stockArr)
                        .filter(x -> ((Pair<Prodotto, Integer>)x).getFirst().getId().equals(idProduct))
                        .findFirst();
                
                if(optional.isPresent()) {
                    //update
                    Pair<Prodotto, Integer> elem = (Pair<Prodotto, Integer>)optional.get();
                    int i = defaultList.indexOf(elem);
                    System.out.println(i);
                    System.out.println(defaultList.size());
                    defaultList.set(i, Pairs.from(elem.getFirst(), amount)); 
                    System.out.println(defaultList.size());                   
                }
                else{
                    //insert
                    defaultList.add(Pairs.from(p, amount));
                }
            }
            else{
                if(alreadyAdd==0) {
                    ok = JDBCLista.insertListProduct(idList, idProduct, amount);
                }
                else {
                    ok = JDBCLista.updateListProduct(idList, idProduct, amount);
                }
            }
            
            JDBCLista.Close();
            JDBCProdotto.Close();
            response.sendRedirect(request.getContextPath() + (!ok ? "/home" : "/home"));
        } catch (Exception e) {
            e.printStackTrace();
            w.println(e.getMessage());
        }
    }
}