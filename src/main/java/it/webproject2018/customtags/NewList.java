/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.customtags;

import it.webproject2018.db.entities.Lista;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author weatherly
 */
public class NewList extends SimpleTagSupport {

    private Lista lista;

    public NewList() {

    }

    @Override
    public void doTag() throws JspException, IOException {

        String listaHtml = "";

        for (int i = 0; i < lista.size(); i++) {
            listaHtml += String.format("<li class=\"left clearfix\">"
                    + "                         <span class=\"list-img pull-left\">\n"
                    + "                             <img src=\"http://placehold.it/50/55C1E7/fff&text=%c\" alt=\"object\" class=\"img-circle\" />\n"
                    + "                         </span>\n"
                    + "                         <div class=\"list-body clearfix\">\n"
                    + "                             <div class=\"header\">\n"
                    + "                                 <strong class=\"primary-font\">%s</strong> \n"
                    + "                                 <button class=\"myButton3 addTo\" text=\"+\" data-toggle=\"modal\" data-target=\"#exampleModal\"><b>+</b></button>\n"
                    + "                                 <button class=\"myButton3 addTo\" title=\"Delete product\" class=\"btn btn-default btn-xs small\">\n"
                    + "                                     <span class=\"glyphicon glyphicon-trash\"></span>\n"
                    + "                                 </button>\n"
                    + "                             </div>\n"
                    + "                             <div class=\"modal fade\" id=\"exampleModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n"
                    + "                                 <div class=\"modal-dialog\" role=\"document\">\n"
                    + "                                     <div class=\"modal-content\">\n"
                    + "                                         <div class=\"modal-header\">\n"
                    + "                                             <h3 class=\"modal-title\" id=\"exampleModalLabel\"><b>Choose the quantity:</b></h3>\n"
                    + "                                         </div>\n"
                    + "                                         <div class=\"modal-body\">\n"
                    + "                                             <div class=\"row\">\n"
                    + "                                                 <div class=\"col-xs-6 col-sm-4\">\n"
                    + "                                                     <div class=\"row\"> \n"
                    + "                                                         <div class=\"amount\"><label>Amount:</label></div>\n"
                    + "                                                     </div>\n"
                    + "                                                     <div class=\"row\">\n"
                    + "                                                         <input id=\"demo3\" type=\"text\" value=\"0\" name=\"demo3\">\n"
                    + "                                                     </div>\n"
                    + "                                                     <script>\n"
                    + "                                                         $(\"input[name='demo3']\").TouchSpin();\n"
                    + "                                                     </script>\n"
                    + "                                                 </div>\n"
                    + "                                             </div>\n"
                    + "                                         </div>\n"
                    + "                                         <div class=\"modal-footer\">\n"
                    + "                                             <button type=\"button\" class=\" btn btn-secondary\" data-dismiss=\"modal\"><b>Close</b></button>\n"
                    + "                                             <button type=\"button\" class=\"myButton3 btn btn-primary\"> <b>Add</b></button>\"\n"
                    + "                                         </div>\n"
                    + "                                     </div>\n"
                    + "                                 </div>\n"
                    + "                             </div>\n"
                    + "                         </div>\n"
                    + "                     </li>\n", lista.get(i).getNome().charAt(0), lista.get(i).getNome());
        }

        String html = String.format("<div class=\"col-xs-12  col-sm-6 col-md-4 liste liste\">\n"
                + "                    <div class=\"row row-lista\">\n"
                + "                        <div class=\"img_wrapper\">\n"
                + "                            <img class=\"immagine_liste\" src=%s>\n"
                + "                            <div class=\"img_description\"> <p class=\"descrizione\"  > %s </p> </div>\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "                    <div class=\"row row-lista\">\n"
                + "                        <div class=\"panel panel-primary\">\n"
                + "                            <div class=\"panel-heading\" id=\"accordion\">\n"
                + "                                <div class=\"col\">"
                + "                                    <span class=\"glyphicon glyphicon-shopping-cart\"></span> <b> %s </b> (%s)\n"
                + "                                </div>"
                + "                                <div class=\"btn-group panel-btn col\">\n"
                + "                                    <a type=\"button\" title=\"Share list\" class=\"btn btn-default btn-xs small\">\n"
                + "                                        <span class=\"glyphicon glyphicon-share-alt\"></span>\n"
                + "                                    </a>\n"
                + "                                    <a type=\"button\" title=\"Delete list\" class=\"btn btn-default btn-xs small\">\n"
                + "                                        <span class=\"glyphicon glyphicon-trash\"></span>\n"
                + "                                    </a>\n"
                + "                                    <button type=\"button\" title=\"Show list\" id=\"prova\" class=\"btn btn-default btn-xs small\" data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#collapse%s\" onclick=\"scendi(this)\">\n"
                + "                                        <span class=\"scendi glyphicon glyphicon-chevron-down\"></span>\n"
                + "                                    </button>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                            \n"
                + "                            <!-- controllare se devo aggiungere anche la categoria della lista, in più vedere se necessaria la foto dell'oggetto e mettere la spunta per dire che è stato comprato-->\n"
                + "                            <div class=\"collapse\" id=\"collapse%s\">\n"
                + "                                <div class=\"panel-body\">\n"
                + "                                    <ul class=\"lista\">\n"
                + "                                        %s\n"
                + "                                    </ul>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "                </div>", lista.getImmagine(), lista.getDescrizione(), lista.getNome(), lista.getCategoria().getNome(), lista.getId(), lista.getId(), listaHtml);
        
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
}