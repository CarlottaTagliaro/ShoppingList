/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.entities;

/**
 *
 * @author alberto
 */
public class Notifica {
    private Integer id;
    private Lista list;
    private Prodotto product;
    private Integer giorniMancanti;
    private Integer quantitaMancante;
    private boolean mail;
    private boolean sito;
    
    public Notifica() {}
    
    public Notifica(Integer id, Lista list, Prodotto product, Integer giorniMancanti, 
                     Integer quantitaMancante, boolean mail, boolean sito) {
        this.id = id;
        this.list = list;
        this.product = product;
        this.giorniMancanti = giorniMancanti;
        this.quantitaMancante = quantitaMancante;
        this.mail = mail;
        this.sito = sito;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setLista(Lista list) {
        this.list = list;
    }
    
    public Lista getLista() {
        return this.list;
    }
    
    public void setProdotto(Prodotto product) {
        this.product = product;
    }
    
    public Prodotto getProdotto() {
        return this.product;
    }
    
    public void setGiorniMancanti(Integer giorniMancanti) {
        this.giorniMancanti = giorniMancanti;
    }
    
    public Integer getGiorniMancanti() {
        return this.giorniMancanti;
    }
    
    public void setQuantitaMancante(Integer quantitaMancante) {
        this.quantitaMancante = quantitaMancante;
    }
    
    public Integer getQuantitaMancante() {
        return this.quantitaMancante;
    }
    
   public void setMail(boolean mail) {
       this.mail = mail;
   }
   
   public boolean getMail() {
       return this.mail;
   }
   
   public void setSito(boolean sito) {
       this.sito = sito;
   }
   
   public boolean getSito() {
       return this.sito;
   }
}
