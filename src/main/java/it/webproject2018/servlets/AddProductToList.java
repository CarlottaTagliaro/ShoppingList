package it.webproject2018.servlets;

import de.scravy.pair.Pair;
import de.scravy.pair.Pairs;
import it.webproject2018.db.daos.ListaDAO;
import it.webproject2018.db.daos.ProdottoDAO;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 *
 * @author Stefano
 */
public class AddProductToList extends HttpServlet {

    private ListaDAO listaDao;
    private ProdottoDAO prodottoDao;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
		if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
		try {
			listaDao = daoFactory.getDAO(ListaDAO.class);
			prodottoDao = daoFactory.getDAO(ProdottoDAO.class);
		} catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for lista and prodotto storage system", ex);
        }
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
            
            amount += alreadyAdd;
            
            Boolean logged = (Utente) request.getSession().getAttribute("User") != null;
            
            if(!logged){   
                ArrayList<Pair<Prodotto, Integer>> defaultList = (ArrayList<Pair<Prodotto, Integer>>) request.getSession().getAttribute("DefaultProductList");;
                Lista l = (Lista) request.getSession().getAttribute("DefaultList");
                Prodotto p = prodottoDao.getByPrimaryKey(idProduct);
                Object[] stockArr = new Object[defaultList.size()];
                stockArr = defaultList.toArray(stockArr);
                
                Optional<Object> optional = Arrays.stream(stockArr)
                        .filter(x -> ((Pair<Prodotto, Integer>)x).getFirst().getId().equals(idProduct))
                        .findFirst();
                
                if(optional.isPresent()) {
                    //update
                    Pair<Prodotto, Integer> elem = (Pair<Prodotto, Integer>)optional.get();
                    int i = defaultList.indexOf(elem);
                    defaultList.set(i, Pairs.from(elem.getFirst(), amount)); 
                }
                else{
                    //insert
                    defaultList.add(Pairs.from(p, amount));
                }
            }
            else{
                if(alreadyAdd==0) {
                    ok = listaDao.insertListProduct(idList, idProduct, amount);
                }
                else {
                    ok = listaDao.updateListProduct(idList, idProduct, amount);
                }
            }
            
            response.sendRedirect(request.getContextPath() + (!ok ? "/home" : "/home"));
        } catch (Exception e) {
            e.printStackTrace();
            w.println(e.getMessage());
        }
    }
}