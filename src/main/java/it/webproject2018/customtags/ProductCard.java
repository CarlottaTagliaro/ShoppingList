/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.customtags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author davide
 */
public class ProductCard extends SimpleTagSupport {

    private String nome;
    private String descrizione;
    private String categoria;
    private String immagine;

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
                + "                                        <h3 class=\"modal-title\" id=\"exampleModalLabel\">Choose the list</h3>\n"
                + "                                    </div>\n"
                + "                                    <div class=\"modal-body\">\n"
                + "                                        <select class=\"form-control\" id=\"search-select\">\n"
                + "                                            <option value=\"Pet shop\">Pet shop</option>\n"
                + "                                            <option value=\"Super Market\">Super Market</option>\n"
                + "                                        </select>\n"
                + "                                    </div>\n"
                + "                                    <div class=\"modal-footer\">\n"
                + "                                        <button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">Close</button>\n"
                + "                                        <button type=\"button\" class=\"btn btn-primary\">Add</button>\n"
                + "                                    </div>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                        <div>\n"
                + "                            <input type=\"checkbox\" value=\"true\" disabled=\"true\"> Already in a list\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>", immagine, nome, categoria, descrizione
        );
        getJspContext().getOut().write(html);
    }

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
