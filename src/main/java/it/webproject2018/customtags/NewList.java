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
 * @author weatherly
 */
public class NewList  extends SimpleTagSupport {
    private String nome;
    private String categoria;
    private String immagine;
    
    public NewList(){
        
    }
    
    @Override
    public void doTag() throws JspException, IOException {
        String html = String.format("<div class=\"carousel slide newLista\" id=\"myCarousel\">\n"
                + "                <!-- Carousel items -->\n"
                + "                <div class=\"carousel-inner\">\n"
                + "                    <div class=\"active item\" data-slide-number=\"0\">\n"
                + "                        <img src=%s>\n"
                + "                        <div class=\"carousel-caption\">\n"
                + "                            <h3>%s</h3>\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "                </div><!-- Carousel nav -->\n"
                + "                <a class=\"left carousel-control\" href=\"#myCarousel\" data-slide=\"prev\">\n"
                + "                    <span class=\"glyphicon glyphicon-chevron-left\"></span>  \n"
                + "                    <span class=\"sr-only\">Previous</span>\n"
                + "                </a>\n"
                + "                <a class=\"right carousel-control\" href=\"#myCarousel\" data-slide=\"next\">\n"
                + "                    <span class=\"glyphicon glyphicon-chevron-right\"></span>\n"
                + "                    <span class=\"sr-only\">Next</span>\n"
                + "                </a>                                 \n"
                + "            </div>", immagine, nome, categoria);
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
