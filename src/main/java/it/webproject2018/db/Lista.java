/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db;

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

    public Lista(Boolean perm_edit, Boolean perm_add_rem, Boolean perm_del, Boolean accettato){
        this.perm_edit = perm_edit;
        this.perm_del = perm_del;
        this.perm_add_rem = perm_add_rem;
        this.accettato = accettato;
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
}
