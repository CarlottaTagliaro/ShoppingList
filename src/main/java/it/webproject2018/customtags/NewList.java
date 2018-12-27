/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.customtags;

import de.scravy.pair.Pair;
import de.scravy.pair.Pairs;
import it.webproject2018.db.daos.ListaPermessiDAO;
import it.webproject2018.db.daos.ProdottoDAO;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.ListaPermessi;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author weatherly
 */
public class NewList extends SimpleTagSupport {

    private Lista lista;
    //only for not registered users
    private ArrayList<Pair<Prodotto, Integer>> listQuantities;

    private ListaPermessiDAO listaPermessiDao;
    private ProdottoDAO prodottoDao;

    private HttpServletRequest request;

    public NewList(){}
    
    private void init() throws ServletException {
        PageContext pageContext = (PageContext) getJspContext();
        request = (HttpServletRequest) pageContext.getRequest();
        HttpSession session = request.getSession();
        ServletContext servletContext = session.getServletContext();

        DAOFactory daoFactory = (DAOFactory) servletContext.getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            listaPermessiDao = daoFactory.getDAO(ListaPermessiDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for lista permessi storage system", ex);
        }
        try {
            prodottoDao = daoFactory.getDAO(ProdottoDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for prodotto storage system", ex);
        }
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            init();
        }
        catch(ServletException e){
            throw new JspException(e.getMessage());
        }
        
        Utente user = (Utente) request.getSession().getAttribute("User");
        ListaPermessi perm;

        Boolean canShare;
        Boolean canBuy;

        if (user != null) {
            canShare = lista.getOwner().equals(user.getEmail());
            Pair<String, Integer> primaryKey = Pairs.from(user.getEmail(), lista.getId());
            perm = new ListaPermessi(user.getEmail(), lista.getId());
            try {
                perm = listaPermessiDao.getByPrimaryKey(primaryKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
            canBuy = perm.getPerm_add_rem();
        } else {
            //utente non loggato
            perm = new ListaPermessi("", 1);
            perm.setPerm_del(true); //può eliminare la sua lista temporanea
            perm.setPerm_add_rem(true); //può eliminare i prodotti dalla sua lista temporanea
            canShare = false;
            canBuy = false;
        }

        String listaHtml = "";

        for (int i = 0; i < lista.size(); i++) {
            Integer amount = 0;
            try {
                if (user != null) {
                    amount = prodottoDao.getProductOfListAmount(lista.get(i), lista);
                } else {
                    for (int el = 0; el < listQuantities.size(); el++) {
                        if (listQuantities.get(el).getFirst().getId().equals(lista.get(i).getId())) {
                            amount = listQuantities.get(el).getSecond();
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            listaHtml += String.format("<li class=\"left clearfix\">"
                    + "                         <span class=\"list-img pull-left\">\n"
                    + "                             <img src=\"http://placehold.it/50/55C1E7/fff&text="
                    + lista.get(i).getNome().charAt(0) + "\" alt=\"object\" class=\"img-circle\" />\n"
                    + "                         </span>\n"
                    + "                         <div class=\"list-body clearfix\">\n"
                    + "                             <div class=\"header\">\n"
                    + "                                 <strong class=\"primary-font\">" + lista.get(i).getNome() + "</strong>" + " (" + amount + ")"
                    + (perm.getPerm_add_rem()
                    ? (canBuy ? "                 <button class=\"myButton3 addTo\" text=\"+\" data-toggle=\"modal\" data-target=\"#modal_prod_" + lista.getId() + "_" + i + "\"><b>+</b></button>\n" : "")
                    + "                                 <a href=\"DeleteProductServlet?Product=" + lista.get(i).getId() + "&List=" + lista.getId() + "\"><button class=\"myButton3 addTo\" title=\"Delete product\" class=\"btn btn-default btn-xs small\">\n"
                    + "                                     <span class=\"glyphicon glyphicon-trash\"></span>\n"
                    + "                                 </button></a>\n" : "")
                    + "                             </div>\n"
                    + "                             <div class=\"modal fade\" id=\"modal_prod_" + lista.getId() + "_" + i + "\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n"
                    + "                                 <div class=\"modal-dialog\" role=\"document\">\n"
                    + "                                     <div class=\"modal-content\">\n"
                    + "                                         <div class=\"modal-header\">\n"
                    + "                                             <h3 class=\"modal-title\" id=\"exampleModalLabel\"><b>Choose the quantity to buy:</b></h3>\n"
                    + "                                         </div>\n"
                    + "                                         <form action=\"BuyProductServlet\" method=\"POST\">\n"
                    + "                                         <input type=\"text\" name=\"list\" value=\"" + lista.getId() + "\" style=\"display: none;\">\n"
                    + "                                         <input type=\"text\" name=\"idProduct\" value=\"" + lista.get(i).getId() + "\" style=\"display: none;\">\n"
                    + "                                         <div class=\"modal-body\">\n"
                    + "                                             <div class=\"row\">\n"
                    + "                                                 <div class=\"col-xs-6 col-sm-4\">\n"
                    + "                                                     <div class=\"row\"> \n"
                    + "                                                         <div class=\"amount\"><label>Amount:</label></div>\n"
                    + "                                                     </div>\n"
                    + "                                                     <div class=\"row\">\n"
                    + "                                                         <input id=\"demo3\" type=\"text\" value=\"0\" name=\"amount_value\">\n"
                    + "                                                     </div>\n"
                    + "                                                     <script>\n"
                    + "                                                         $(\"input[name='amount_value']\").TouchSpin({max: " + amount + "});\n"
                    + "                                                     </script>\n"
                    + "                                                 </div>\n"
                    + "                                             </div>\n"
                    + "                                         </div>\n"
                    + "                                         <div class=\"modal-footer\">\n"
                    + "                                             <button type=\"button\" class=\" btn btn-secondary\" data-dismiss=\"modal\"><b>Close</b></button>\n"
                    + "                                             <button type=\"submit\" class=\"myButton3 btn btn-primary\"> <b>Buy</b></button>\n"
                    + "                                         </div>\n"
                    + "                                         </form>\n"
                    + "                                     </div>\n"
                    + "                                 </div>\n"
                    + "                             </div>\n" + "                         </div>\n"
                    + "                     </li>\n");
        }
        
        if(lista.isEmpty()) { //empty list
            listaHtml = "<b>No items</b>";
        }

        String html = String.format("<div class=\"col-xs-12  col-sm-6 col-md-4 liste liste\">\n"
                + "                    <div class=\"row row-lista\">\n"
                + "                        <div class=\"img_wrapper\">\n"
                + "                            <img class=\"immagine_liste\" src=\"" + lista.getImmagine() + "\">\n"
                + "                            <div class=\"img_description\"> <p class=\"descrizione\"  > "
                + lista.getDescrizione() + " </p> </div>\n" + "                        </div>\n"
                + "                    </div>\n" + "                    <div class=\"row row-lista\">\n"
                + "                        <div class=\"panel panel-primary\">\n"
                + "                            <div class=\"panel-heading\" id=\"accordion\">\n"
                + "                                <div class=\"col\">"
                + "                                    <span class=\"glyphicon glyphicon-shopping-cart\"></span> <b> "
                + lista.getNome() + " </b> (" + lista.getCategoria().getNome() + ")\n"
                + "                                </div>"
                + "                                <div class=\"btn-group panel-btn col\">\n"
                + (perm.getPerm_edit() ? "              <a type=\"button\" title=\"Modify list\" class=\"btn btn-default btn-xs small\" data-toggle=\"modal\" data-target=\"#modal_mod_" + lista.getId() + "\">\n"
                + "                                        <span class=\"glyphicon glyphicon-pencil\"></span>\n"
                + "                                    </a>\n"
                + "                                     <div class=\"modal fade\" id=\"modal_mod_" + lista.getId()
                + "\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n"
                + "                                         <div class=\"modal-dialog\" role=\"document\">\n"
                + "                                             <div class=\"modal-content\">\n"
                + "                                                 <div class=\"modal-header\">\n"
                + "                                                     <h3 class=\"modal-title\" id=\"exampleModalLabel\"><b> Modify list details:</b></h3>\n"
                + "                                                 </div>\n"
                + "                                                 <form action=\"ChangeListDetailsServlet\" id=\"upload_form\" enctype=\"multipart/form-data\" method=\"POST\">\n"
                + "                                                     <input type=\"hidden\" name=\"idList\" value=\"" + lista.getId() + "\">\n"
                + "                                                 <div class=\"modal-body\">\n"
                + "                                                     <div class=\"input-group\">\n"
                + "                                                         <div class=\"row cambia-dati\">\n"
                + "                                                             <label class=\"search\">Name:</label> \n"
                + "                                                             <input type=\"text\" value=\"" + lista.getNome() + "\" name=\"name\" class=\"form-control inserisci\" aria-label=\"...\">\n"
                + "                                                         </div>\n"
                + "                                                     </div>"
                + "                                                     <div class=\"row cambia-dati\">\n"
                + "                                                         <label class=\"search\">Description:</label> \n"
                + "                                                             <textarea id=\"description\" name=\"description\" cols=\"40\" rows=\"5\" class=\"form-control descrizione\">" + lista.getDescrizione() + "</textarea>\n"
                + "                                                     </div>\n"
                + "                                                     <div class=\"row cambia-dati\">\n"
                + "                                                         <div class=\"row\">\n"
                + "                                                         <label class=\"search\">Immagine:</label> \n"
                + "                                                         </div>\n"
                + "                                                             <input type=\"file\"  value=\"" + lista.getImmagine() + "\" name=\"file\" id=\"file_" + lista.getId() + "\" class=\"inputfile\">\n"
                + "                                                             <label for=\"file_" + lista.getId() + "\" class=\"button1 myButton3 btn\" ><span class=\"glyphicon glyphicon-open\"></span> Choose file </label>\n"
                + "                                                     </div>\n"
                + "                                                 </div>\n"
                + "                                                 <div class=\"modal-footer\">\n"
                + "                                                     <button type=\"button\" class=\" btn btn-secondary\" data-dismiss=\"modal\"><b>Close</b></button>\n"
                + "                                                     <button type=\"submit\" class=\"myButton3 btn btn-primary\"> <b>Save</b></button>\"\n"
                + "                                                 </div>\n"
                + "                                                 </form>\n"
                + "                                             </div>\n"
                + "                                         </div>\n"
                + "                                     </div>\n" : "")
                + (canShare ? "                                    <a type=\"button\" title=\"Share list\" class=\"btn btn-default btn-xs small\" data-toggle=\"modal\" data-target=\"#modal_share_" + lista.getId() + "\">\n"
                        + "                                        <span class=\"glyphicon glyphicon-share-alt\"></span>\n"
                        + "                                    </a>\n" + "<script>"
                        + "$(document).ready(function(){$(\"#modal_share_" + lista.getId()
                        + "\").on('shown.bs.modal', function() {\n" + "    shareGetUsers(" + lista.getId() + ", '');\n"
                        + "})});" + "</script>"
                        + "                                     <div class=\"modal fade\" id=\"modal_share_" + lista.getId()
                        + "\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n"
                        + "                                         <div class=\"modal-dialog\" role=\"document\">\n"
                        + "                                             <div class=\"modal-content\">\n"
                        + "                                                 <div class=\"modal-header\">\n"
                        + "                                                     <h3 class=\"modal-title\" id=\"exampleModalLabel\"><b>Share with:</b></h3>\n"
                        + "                                                 </div>\n"
                        + "                                                 <form action='ShareListInsertUser' method='POST'>"
                        + "                                                 <div class=\"modal-body\">\n"
                        + "                                                     <div class=\"row\">\n"
                        + "                                                         <div class=\"input-group\">\n"
                        + "                                                             <input type=\"text\" class=\"form-control form-control1\" id=\"modal_share_" + lista.getId() + "_search\" name=\"qry\" aria-label=\"...\" placeholder=\"Search user by email or name\" value=\"\">\n"
                        + "                                                             <div class=\"input-group-btn\">\n"
                        + "                                                                 <button type=\"button\" class=\"btn bottone-cerca btn-default\" onclick=\"shareGetUsers(" + lista.getId() + ", $('#modal_share_" + lista.getId() + "_search').val())\">\n"
                        + "                                                                     <span class=\"glyphicon glyphicon-search\"/>\n"
                        + "                                                                 </button>\n"
                        + "                                                             </div>\n"
                        + "                                                         </div>\n"
                        + "                                                     </div>"
                        + "                                                     <div class=\"row\">\n"
                        + "                                                             <table id=\"table_share_" + lista.getId() + "\">\n"
                        + "                                                                 <tr>\n"
                        + "                                                                     <th>Name Surname</th>\n"
                        + "                                                                 <th>Add/delete products</th>\n"
                        + "                                                                 <th>Modify list details</th>\n"
                        + "                                                                 <th>Delete list</th>\n"
                        + "                                                             </tr>\n"
                        + "                                                         </table>"
                        + "                                                     </div>"
                        + "                                                 </div>\n"
                        + "                                                 <div class=\"modal-footer\">\n"
                        + "                                                     <button type=\"button\" class=\" btn btn-secondary\" data-dismiss=\"modal\"><b>Close</b></button>\n"
                        + "                                                     <button type=\"submit\" class=\"myButton3 btn btn-primary\"> <b>Share</b></button>\"\n"
                        + "                                                 </div>\n"
                        + "                                             </form>\n"
                        + "                                             </div>\n"
                        + "                                         </div>\n"
                        + "                                     </div>\n" : "")
                + (perm.getPerm_del() ? "              <a type=\"button\" title=\"Delete list\" class=\"btn btn-default btn-xs small\" href=\"DeleteListServlet?List=" + lista.getId() + "\">\n"
                + "                                        <span class=\"glyphicon glyphicon-trash\"></span>\n"
                + "                                    </a>\n" : "")
                + "                                    <button type=\"button\" title=\"Show list\" id=\"prova\" class=\"btn btn-default btn-xs small\" data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#collapse" + lista.getId() + "\" onclick=\"scendi(this)\">\n"
                + "                                        <span class=\"scendi glyphicon glyphicon-chevron-down\"></span>\n"
                + "                                    </button>\n" + "                                </div>\n"
                + "                            </div>\n" + "                            \n"
                + "                            <!-- controllare se devo aggiungere anche la categoria della lista, in più vedere se necessaria la foto dell'oggetto e mettere la spunta per dire che è stato comprato-->\n"
                + "                            <div class=\"collapse\" id=\"collapse" + lista.getId() + "\">\n"
                + "                                <div class=\"panel-body\">\n"
                + "                                    <ul class=\"lista\">\n" + listaHtml
                + "                                    </ul>\n" + "                                </div>\n"
                + "                            </div>\n" + "                        </div>\n"
                + "                    </div>\n" + "                </div>");

        getJspContext().getOut().write(html);
    }

    /**
     * @return the lista
     */
    public Lista getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(Lista lista) {
        this.lista = lista;
    }

    /**
     * @return the listQuantities
     */
    public ArrayList<Pair<Prodotto, Integer>> getListQuantities() {
        return listQuantities;
    }

    /**
     * @param listQuantities the listQuantities to set
     */
    public void setListQuantities(ArrayList<Pair<Prodotto, Integer>> listQuantities) {
        this.listQuantities = listQuantities;
    }
}
