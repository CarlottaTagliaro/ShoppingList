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
public class NewList  extends SimpleTagSupport {
    private Lista lista;
    private static int ListNum = 0;
    
    public NewList(){
        
    }
    
    @Override
    public void doTag() throws JspException, IOException {
        
        String listaHtml = "";
        
        for(int i = 0; i < lista.size(); i ++){
            listaHtml += String.format("<li class=\"left clearfix\">"
                + "                         <span class=\"list-img pull-left\">\n"
                + "                             <img src=\"http://placehold.it/50/55C1E7/fff&text=%c\" alt=\"object\" class=\"img-circle\" />\n"
                + "                         </span>\n"
                + "                         <div class=\"list-body clearfix\">\n"
                + "                             <div class=\"header\">\n"
                + "                                 <strong class=\"primary-font\">%s</strong> \n"
                + "                             </div>\n"
                + "                         </div>\n"
                + "                     </li>\n", lista.get(i).getNome().charAt(0), lista.get(i).getNome());
        }
        
        String html = String.format("<div class=\"col-md-4 liste\">\n"
                + "                    <div class=\"row\">\n"
                + "                        <div class=\"img_wrapper\">\n"
                + "                            <img class=\"immagine_liste\" src=%s>\n"
                + "                            <div class=\"img_description\"> <p class=\"descrizione\"  > %s </p> </div>\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "                    <div class=\"row\">\n"
                + "                        <div class=\"panel panel-primary\">\n"
                + "                            <div class=\"panel-heading\" id=\"accordion\">\n"
                + "                                <span class=\"glyphicon glyphicon-shopping-cart\"></span> <b> %s </b> (%s)\n"
                + "                                <div class=\"btn-group pull-right\">\n"
                + "                                    <a type=\"button1 \" title=\"Share list\" class=\"btn btn-default btn-xs small\">\n"
                + "                                        <span class=\"glyphicon glyphicon-share-alt\"></span>\n"
                + "                                    </a>\n"
                + "                                    <a type=\"button1 \" title=\"Delete list\" class=\"btn btn-default btn-xs small\">\n"
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
                + "                </div>", lista.getImmagine(), lista.getDescrizione(), lista.getNome(), lista.getCategoria().getNome(), ListNum, ListNum, listaHtml);
        ListNum++;
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
