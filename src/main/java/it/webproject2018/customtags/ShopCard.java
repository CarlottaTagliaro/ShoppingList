/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.customtags;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import it.webproject2018.db.entities.CategoriaListe;

/**
 *
 * @author weatherly
 */
public class ShopCard  extends SimpleTagSupport {
    private CategoriaListe shop;
    
    public ShopCard(){
        
    }
    
    @Override
    public void doTag() throws JspException, IOException {
        
        String imgHtml = "";
        
        for(int i = 0; i < getShop().getImmagini().size(); i++){
            imgHtml += String.format("<div class=\"%s item\" data-slide-number=\"%d\">\n" +
"                                    <img class=\"img-responsive center-block\" src=\"%s\">\n" +
"                                </div>\n", (i == 0 ? "active" : ""), i, getShop().getImmagini().get(i));
        }
               
        int hash_name = shop.getNome().hashCode();
        String enc_name = URLEncoder.encode(shop.getNome());
        
        String html = String.format("<div class=\"col-md-4 liste\">\n" +
"                    <div class=\"row center shop-header\">\n" +
"                        <div class=\"carousel slide newLista\" id=\"car_"+ hash_name + "\">\n" +
"                            <!-- Carousel items -->\n" +
"                            <div class=\"carousel-inner\">\n" +
"                               %s" +
"                            </div><!-- Carousel nav -->\n" +
"                            <a class=\"left carousel-control\" href=\"#car_"+ hash_name +"\" data-slide=\"prev\">\n" +
"                                <span class=\"glyphicon glyphicon-chevron-left\"></span>  \n" +
"                                <span class=\"sr-only\">Previous</span>\n" +
"                            </a>\n" +
"                            <a class=\"right carousel-control\" href=\"#car_" + hash_name + "\" data-slide=\"next\">\n" +
"                                <span class=\"glyphicon glyphicon-chevron-right\"></span>\n" +
"                                <span class=\"sr-only\">Next</span>\n" +
"                            </a>                                 \n" +
"                        </div>\n" +
"                    </div>\n" +
"                    <div class=\"row center\">\n" +
"                        <div class=\"panel panel-primary\">\n" +
"                            <div class=\"panel-heading\" id=\"accordion\">\n" +
"                                <span class=\"glyphicon glyphicon-usd\"></span> <a class=\"scegli_negozio\" href=\"shopCategories?catName=%s\"><b>%s</b></a>\n" +
"                            </div>\n" +
"                        </div>\n" +
"                    </div>\n" +
"                </div>", imgHtml, enc_name, shop.getNome());
        getJspContext().getOut().write(html);
    }

    /**
     * @return the shop
     */
    public CategoriaListe getShop() {
        return shop;
    }

    /**
     * @param shop the shop to set
     */
    public void setShop(CategoriaListe shop) {
        this.shop = shop;
    }
    
}
