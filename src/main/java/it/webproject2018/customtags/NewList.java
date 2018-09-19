/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.customtags;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author weatherly
 */
public class NewList  extends SimpleTagSupport {
    private String nome;
    private String categoria;
    private String immagine;
    private String descrizione;
    private ArrayList oggetti;
    private static int ListNum = 0;
    
    public NewList(){
        
    }
    
    @Override
    public void doTag() throws JspException, IOException {
        String col = "collapseTwo";
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
                + "                                        <li class=\"left clearfix\"><span class=\"list-img pull-left\">\n"
                + "                                                <img src=\"http://placehold.it/50/55C1E7/fff&text=T\" alt=\"object\" class=\"img-circle\" />\n"
                + "                                            </span>\n"
                + "                                            <div class=\"list-body clearfix\">\n"
                + "                                                <div class=\"header\">\n"
                + "                                                    <strong class=\"primary-font\" >Tosaerba</strong> \n"
                + "                                                </div>\n"
                + "                                            </div>\n"
                + "                                        </li>\n"
                + "                                    </ul>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "                </div>", immagine, descrizione, nome, categoria, ListNum, ListNum, oggetti);
        ListNum++;
        getJspContext().getOut().write(html);
    }
    
    //DA CAPIRE COME PASSARE UN ARRAY DI OGGETTI DA METTERE NELLA LISTA

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

     /**
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @param descrizione the descrizione to set
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
    /**
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the immagine
     */
    public String getImmagine() {
        return immagine;
    }

    /**
     * @param immagine the immagine to set
     */
    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
    
}
