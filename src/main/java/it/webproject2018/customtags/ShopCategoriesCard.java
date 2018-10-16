/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.customtags;

import it.webproject2018.db.entities.CategoriaProdotti;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author weatherly
 */
public class ShopCategoriesCard  extends SimpleTagSupport {
    private CategoriaProdotti category;
    
    public ShopCategoriesCard(){
        
    }
    
    @Override
    public void doTag() throws JspException, IOException {
        String enc_name = URLEncoder.encode(category.getNome());
        
        String html = String.format("<div class=\"col-md-4 liste\">\n" +
"                    <div class=\"row\">\n" +
"                        <div class=\"img_wrapper\">\n" +
"                            <img class=\"immagine_liste\" src=\"http://placehold.it/770x300&text=%s\">\n" +
"                            <div class=\"img_description\"> <p class=\"descrizione\" > %s </p> </div>\n" +
"                        </div>\n" +
"                    </div>\n" +
"                    <div class=\"row\">\n" +
"                        <div class=\"panel panel-primary\">\n" +
"                            <div class=\"panel-heading\" id=\"accordion\">\n" +
"                                <span class=\"glyphicon glyphicon-shopping-cart\"></span> <a class=\"scegli_categoria\" href=\"prodotti?catName=%s\"> <b> %s</b></a>\n" +
"                            </div>\n" +
"                        </div>\n" +
"                    </div>\n" +
"                </div>", enc_name, category.getDescrizione(), enc_name, category.getNome());
        getJspContext().getOut().write(html);
    }

    /**
     * @return the category
     */
    public CategoriaProdotti getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(CategoriaProdotti category) {
        this.category = category;
    }

    
}
