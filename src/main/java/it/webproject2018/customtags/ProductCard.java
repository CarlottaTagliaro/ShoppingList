/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.customtags;

import de.scravy.pair.Pair;
import it.webproject2018.db.daos.jdbc.JDBCProdottoDAO;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.entities.Utente;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.glassfish.gmbal.generic.Triple;

/**
 *
 * @author davide
 */
public class ProductCard extends SimpleTagSupport {

    private Prodotto product;

    public ProductCard() {

    }

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpSession session = request.getSession();
        ServletContext servletContext = session.getServletContext();
        JDBCProdottoDAO JdbcProdottoDao = new JDBCProdottoDAO(servletContext);
        Utente user = (Utente) request.getSession().getAttribute("User");

        Boolean isMine = false;

        String listeHtml = "";
        try {
            if (user != null) {
                isMine = product.getOwner().equals(user.getEmail());

                List<Triple<Integer, String, Integer>> lista = JdbcProdottoDao.getProductListAmount(product, user);

                for (int i = 0; i < lista.size(); i++) {
                    listeHtml += String.format("<option value=\"%d\" amount=\"%d\">%s</option>", lista.get(i).first(), lista.get(i).third(), lista.get(i).second());
                }
            } else {
                //utente non loggato

                ArrayList<Pair<Prodotto, Integer>> defaultList = (ArrayList<Pair<Prodotto, Integer>>) request.getSession().getAttribute("DefaultProductList");

                Lista l = (Lista) request.getSession().getAttribute("DefaultList");
                if (l != null && l.getCategoria().getNome().equals(product.getCategoria().getCategoriaLista().getNome())) {
                    Integer amount = 0;
                    for (Pair<Prodotto, Integer> p : defaultList) {
                        if (p.getFirst().getId().equals(product.getId())) {
                            amount = p.getSecond();
                            break;
                        }
                    }

                    listeHtml += String.format("<option value=\"%d\" amount=\"%d\">%s</option>", l.getId(), amount, l.getNome());
                }
            }
            JdbcProdottoDao.Close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String imgSrc = getProduct().Fotografie.size() > 0 ? getProduct().Fotografie.get(0) : "http://placehold.it/100/55C1E7/fff&text=" + getProduct().getNome().charAt(0);

        String html = (" \n"
                + "<div class=\"row card productRow\">\n"
                + "                <div class=\"col-xs-3\">\n"
                + "                    <img class=\"imageList img-responsive\" src=\"" + imgSrc + "\"/>\n"
                + "                </div>\n"
                + "                <div class=\"col-xs-6\">\n"
                + "                    <h4> <b> " + getProduct().getNome() + " </b></h4>\n"
                + "                    <h6> " + getProduct().getCategoria().getNome() + " </h6>\n"
                + "                    <p> " + getProduct().getNote() + " </p>\n"
                + "                </div>\n"
                + "\n"
                + "                <div class=\"col-xs-3\">\n"
                + "                    <div class=\"row\">\n"
                + "                         <div class=\"add-lista\">\n"
                + "                             <label class=\"aggiungi\"> Add: </label>\n"
                + "                            <button class=\"myButton\" text=\"+\" data-toggle=\"modal\" data-target=\"#add_modal_" + getProduct().getId() + "\"><span class=\"glyphicon glyphicon-plus\"></span></button>\n"
                + "                        </div>"
                + "                        <div class=\"modal fade\" id=\"add_modal_" + getProduct().getId() + "\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n"
                + "                            <div class=\"modal-dialog\" role=\"document\">\n"
                + "                                <div class=\"modal-content\">\n"
                + "                                    <div class=\"modal-header\">\n"
                + "                                        <h3 class=\"modal-title\" id=\"exampleModalLabel\"><b>Choose the list</b></h3>\n"
                + "                                    </div>\n"
                + "                                    <form action=\"AddProductToList\" method=\"POST\">\n"
                + "                                    <div class=\"modal-body\">\n"
                + "                                         <input type=\"text\" name=\"idProduct\" value=\"" + getProduct().getId() + "\" style=\"display: none;\">\n"
                + "                                         <div class=\"row\">\n"
                + "                                             <div class=\"col-xs-12 col-sm-4 scegliLista\">\n"
                + "                                                 <div class=\"row amount\">\n"
                + "                                                     <label> Choose: </label>\n"
                + "                                                 </div>\n"
                + "                                                 <div class=\"row\"> \n"
                + "                                                     <select id=\"selList_" + getProduct().getId() + "\" name=\"list\" class=\"form-control\">\n"
                + "                                                         " + listeHtml
                + "                                                     </select>\n"
                + "                                                 </div>"
                + "                                             </div>\n"
                + "                                             <div class=\"col-xs-6 col-sm-4\">\n"
                + "                                                 <div class=\"text-info\"><b> Already added: </b><input class=\"amount_added\" name=\"alreadyAdd\" type=\"text\" id=\"amount_" + getProduct().getId() + "\" readonly></div>\n"
                + "                                             </div>\n"
                + "                                             <div class=\"col-xs-6 col-sm-4\">\n"
                + "                                                 <div class=\"row\"> \n"
                + "                                                     <div class=\"amount\"><label>Amount:</label></div>\n"
                + "                                                 </div>\n"
                + "                                                 <div class=\"row\">\n"
                + "                                                     <input id=\"amount_value\" type=\"text\" value=\"0\" name=\"amount_value\">\n"
                + "                                                 </div>"
                + "                                                 <script>\n"
                + "                                                     $(\"input[name='amount_value']\").TouchSpin();\n"
                + "                                                     $( \"#selList_" + getProduct().getId() + "\" ).change(function() {\n"
                + "                                                         var value = $(this).val();"
                + "                                                         $(\"#amount_" + getProduct().getId() + "\").val($(this).find(\"option[value=\"+value+\"]\").attr(\"amount\"));"
                + "                                                     });"
                + "                                                     $( \"#selList_" + getProduct().getId() + "\" ).change();"
                + "                                                 </script>\n"
                + "                                             </div>\n"
                + "                                         </div>"
                + "                                    </div>\n"
                + "                                    <div class=\"modal-footer\">\n"
                + "                                         <button type=\"button\" class=\" btn btn-secondary\" data-dismiss=\"modal\"><b>Close</b></button>\n"
                + "                                         <button type=\"submit\" class=\"myButton3 btn btn-primary\"> <b>Add</b></button>"
                + "                                    </div>\n"
                + "                                    </form>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + (isMine ? "          <div class=\"row\">\n "
                        + "                         <div class=\"add-lista\">\n"
                        + "                             <label class=\"aggiungi\"> Share: </label>\n"
                        + "                             <button class=\"myButton\" text=\"S\" data-toggle=\"modal\" data-target=\"#share_modal_" + getProduct().getId() + "\"><span class=\"glyphicon glyphicon-share-alt\"></span></button>\n"
                        + "                         </div>"
                        + "                         <div class=\"modal fade\" id=\"share_modal_" + getProduct().getId() + "\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n"
                        + "                             <div class=\"modal-dialog\" role=\"document\">\n"
                        + "                                  <div class=\"modal-content\">\n"
                        + "                                        <div class=\"modal-header\">\n"
                        + "                                            <h3 class=\"modal-title\" id=\"exampleModalLabel\"><b>Choose the user</b></h3>\n"
                        + "                                        </div>\n"
                        + "                                            <form action='ShareProductInsertUser' method='POST'>"
                        + "                                         <div class=\"modal-body\">\n"
                        + "                                             <div class=\"row\">\n"
                        + "                                                 <div class=\"input-group\">\n"
                        + "                                                     <input type=\"text\" class=\"form-control form-control1\" id=\"modal_shareprod_" + product.getId() + "_search\" name=\"qry\" aria-label=\"...\" placeholder=\"Search user by email or name\" value=\"\">\n"
                        + "                                                     <div class=\"input-group-btn\">\n"
                        + "                                                     <button type=\"submit\" class=\"btn bottone-cerca btn-default\" onclick=\"shareProductGetUsers(" + product.getId() + ", $('#modal_shareprod_" + product.getId() + "_search').val())\">\n"
                        + "                                                         <span class=\"glyphicon glyphicon-search\"/>\n"
                        + "                                                     </button>\n"
                        + "                                                 </div>\n"
                        + "                                             </div>\n"
                        + "                                         </div>"
                        + "                                             <div class=\"row\">\n"
                        + "                                                 <table id=\"table_shareprod_" + product.getId() + "\" >\n"
                        + "                                                 </table>\n"
                        + "                                             </div>"
                        + "                                         </div>\n"
                        + "                                     </form>"
                        + "<script>"
                        + "$(document).ready(function(){$(\"#share_modal_" + product.getId() + "\").on('shown.bs.modal', function() {\n"
                        + "    shareProductGetUsers(" + product.getId() + ", '');\n"
                        + "})});"
                        + "</script>"
                        + "                                         <div class=\"modal-footer\">\n"
                        + "                                             <button type=\"button\" class=\" btn btn-secondary\" data-dismiss=\"modal\"><b>Close</b></button>\n"
                        + "                                             <button type=\"submit\" class=\"myButton3 btn btn-primary\"> <b>Share</b></button>"
                        + "                                         </div>\n"
                        + "                                     </form>"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </div>\n"
                        + "                    </div>\n " : "")
                + "                </div>\n"
                + "            </div>");
        getJspContext().getOut().write(html);
    }

    /**
     * @return the product
     */
    public Prodotto getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Prodotto product) {
        this.product = product;
    }
}
