/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.entities;

import java.util.ArrayList;

/**
 *
 * @author davide
 */
public class Lista extends ArrayList<Prodotto> {
    private Boolean perm_edit; // permesso di modificare le caratteristiche della lista
    private Boolean perm_add_rem; // permesso di aggiungere o rimuovere prodotti dalla lista
    private Boolean perm_del; // permesso di eliminare la lista
    private Boolean accettato;
    
    private Integer id;
    private String nome;
    private String descrizione;
    private String immagine;
    private CategoriaListe categoria;
    private String owner;

    public Lista(Boolean perm_edit, Boolean perm_add_rem, Boolean perm_del, Boolean accettato, Integer id, String nome, String descrizione, String immagine, CategoriaListe categoria, String owner){
        this.perm_edit = perm_edit;
        this.perm_del = perm_del;
        this.perm_add_rem = perm_add_rem;
        this.accettato = accettato;
        
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione; 
        this.immagine = immagine; 
        this.categoria = categoria; 
        this.owner = owner;
    }
    
    /**
     * @return the perm_edit
     */
    public Boolean getPerm_edit() {
        return perm_edit;
    }

    /**
     * @param perm_edit the perm_edit to set
     */
    public void setPerm_edit(Boolean perm_edit) {
        this.perm_edit = perm_edit;
    }

    /**
     * @return the perm_add_rem
     */
    public Boolean getPerm_add_rem() {
        return perm_add_rem;
    }

    /**
     * @param perm_add_rem the perm_add_rem to set
     */
    public void setPerm_add_rem(Boolean perm_add_rem) {
        this.perm_add_rem = perm_add_rem;
    }

    /**
     * @return the perm_del
     */
    public Boolean getPerm_del() {
        return perm_del;
    }

    /**
     * @param perm_del the perm_del to set
     */
    public void setPerm_del(Boolean perm_del) {
        this.perm_del = perm_del;
    }

    /**
     * @return the accettato
     */
    public Boolean getAccettato() {
        return accettato;
    }

    /**
     * @param accettato the accettato to set
     */
    public void setAccettato(Boolean accettato) {
        this.accettato = accettato;
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
}
