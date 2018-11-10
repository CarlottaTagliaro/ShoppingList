/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.entities;

import java.util.ArrayList;

/**
 *
 * @author Max
 *
 * dimensioni di array da prefissare? nel db sono fisse, e qua sono private,
 * quindi forse posso fissarle direttamente qui evitando overflow
 */
public class Utente {

    private String nome;
    private String cognome;
    private String email;
    private String immagine;
    private Boolean isAdmin;
    private String confString;
    
    public Utente(){}
    
    public Utente(String nome, String cognome, String email, String immagine, Boolean isAdmin, String confString){
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.immagine = immagine;
        this.isAdmin = isAdmin;
        this.confString = confString;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof ListaPermessi)) {
            return false;
        }
        
        Utente u = (Utente) other;
        
        return u.getEmail().equals(getEmail());
    }

    public String getName() {
        return nome;
    }

    public void setName(String nome) {
        this.nome = nome;
    }

    public String getSurname() {
        return cognome;
    }

    public void setSurname(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return immagine;
    }

    public void setPicture(String immagine) {
        this.immagine = immagine;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getConfString() {
        return confString;
    }
    
    public void setConfString(String confString) {
        this.confString = confString;
    }
}
