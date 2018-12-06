package it.webproject2018.servlets;

import it.webproject2018.db.daos.ListaDAO;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;

/**
 *
 * @author Stefano
 */
public class BuyProductServlet extends HttpServlet {

    private ListaDAO listaDao;

    @Override
    public void init() throws ServletException {
		DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
		if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
		try {
			listaDao = daoFactory.getDAO(ListaDAO.class);
		} catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for lista storage system", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        init();
        PrintWriter w = response.getWriter();
        try {
            Integer idProduct = Integer.parseInt(request.getParameter("idProduct"));
            Integer idList = Integer.parseInt(request.getParameter("list"));
            Integer amount = Integer.parseInt(request.getParameter("amount_value"));
            Boolean ok = false;
            Boolean ok2 = false;
            
            Integer quantity = listaDao.getProductQuantity(idList, idProduct, amount);
            
            if(quantity != null && quantity != -1) {
                if(amount > quantity)
                    amount = quantity;
                ok = listaDao.updateListProductToBuy(idList, idProduct, amount, quantity);
            }
            
            if (ok) {
                ok2 = listaDao.buyProduct(idList, idProduct, amount);
            }
            
            response.sendRedirect(request.getContextPath() + (!ok2 ? "/myList" : "/myList"));
        } catch (Exception e) {
            w.println(e.getMessage());
        }
    }
}