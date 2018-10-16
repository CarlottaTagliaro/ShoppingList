/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.entities;

import java.util.ArrayList;

/**
 *
 * @author davide
 */
public class Lista extends ArrayList<Prodotto> {
    private ListaPermessi listPermission;
    
    private Integer id;
    private String nome;
    private String descrizione;
    private String immagine;
    private CategoriaListe categoria;
    private String owner;

    public Lista(Integer id, String nome, String descrizione, String immagine, CategoriaListe categoria, String owner){
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione; 
        this.immagine = immagine; 
        this.categoria = categoria; 
        this.owner = owner;
    }
    
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
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

    /**
     * @return the categoria
     */
    public CategoriaListe getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(CategoriaListe categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @return the listPermission
     */
    public ListaPermessi getListPermission() {
        return listPermission;
    }

    /**
     * @param listPermission the listPermission to set
     */
    public void setListPermission(ListaPermessi listPermission) {
        this.listPermission = listPermission;
    }
}
