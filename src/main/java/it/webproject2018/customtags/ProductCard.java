/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.customtags;

import it.webproject2018.db.entities.Prodotto;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

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
        String html = String.format(" \n"
                + "<div class=\"row card\">\n"
                + "                <div class=\"col-xs-3\">\n"
                + "                    <img class=\"imageList img-responsive\" src=\"%s\"/>\n"
                + "                </div>\n"
                + "                <div class=\"col-xs-6\">\n"
                + "                    <h4> <b> %s </b></h4>\n"
                + "                    <h6> %s </h6>\n"
                + "                    <p> %s </p>\n"
                + "                </div>\n"
                + "\n"
                + "                <div class=\"col-xs-3 myColumn\">\n"
                + "                    <div>\n"
                + "                         <div class=\"add-lista\">\n"
                + "                             <label class=\"aggiungi\"> Add: </label>\n"
                + "                            <button class=\"myButton\" text=\"+\" data-toggle=\"modal\" data-target=\"#exampleModal\"><b>+</b></button>\n"
                + "                        </div>"
                + "                        <div class=\"modal fade\" id=\"exampleModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n"
                + "                            <div class=\"modal-dialog\" role=\"document\">\n"
                + "                                <div class=\"modal-content\">\n"
                + "                                    <div class=\"modal-header\">\n"
                + "                                        <h3 class=\"modal-title\" id=\"exampleModalLabel\"><b>Choose the list</b></h3>\n"
                + "                                    </div>\n"
                + "                                    <div class=\"modal-body\">\n"
                + "                                         <div class=\"row\">\n"
                + "                                             <div class=\"col-xs-12 col-sm-4 scegliLista\">\n"
                + "                                                 <div class=\"row amount\">\n"
                + "                                                     <label> Choose: </label>\n"
                + "                                                 </div>\n"
                + "                                                 <div class=\"row\"> \n"
                + "                                                     <select class=\"form-control\" id=\"search-select1\">\n"
                + "                                                         <option value=\"Pet shop\">Pet shop</option>\n"
                + "                                                         <option value=\"Super Market\">Super Market</option>\n"
                + "                                                     </select>\n"
                + "                                                 </div>"
                + "                                             </div>\n"
                + "                                             <div class=\"col-xs-6 col-sm-4\">\n"
                + "                                                 <div class=\"text-info\"><b> Already added: </b></div>\n"
                + "                                             </div>\n"
                + "                                             <div class=\"col-xs-6 col-sm-4\">\n"
                + "                                                 <div class=\"row\"> \n"
                + "                                                     <div class=\"amount\"><label>Amount:</label></div>\n"
                + "                                                 </div>\n"
                + "                                                 <div class=\"row\">\n"
                + "                                                     <input id=\"demo3\" type=\"text\" value=\"\" name=\"demo3\">\n"
                + "                                                 </div>"
                + "                                                 <script>\n"
                + "                                                     $(\"input[name='demo3']\").TouchSpin();\n"
                + "                                                 </script>\n"
                + "                                             </div>\n"
                + "                                         </div>"
                + "                                    </div>\n"
                + "                                    <div class=\"modal-footer\">\n"
                + "                                         <button type=\"button\" class=\" btn btn-secondary\" data-dismiss=\"modal\"><b>Close</b></button>\n"
                + "                                         <button type=\"button\" class=\"myButton3 btn btn-primary\"> <b>Add</b></button>"
                + "                                    </div>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>", getProduct().Fotografie.size() > 0 ? getProduct().Fotografie.get(0) : "http://placehold.it/50/55C1E7/fff&text=" + getProduct().getNome().charAt(0),
                getProduct().getNome(), getProduct().getCategoria().getNome(), getProduct().getNote()
        );
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
