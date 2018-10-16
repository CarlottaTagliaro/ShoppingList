/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.entities;

import java.sql.Timestamp;

/**
 *
 * @author davide
 */
public class NotificaWeb {
    private String testo;
    private String tipo;
    private Timestamp data;
    private Integer idElemento;

    public NotificaWeb(String testo, String tipo, Timestamp data, Integer idElemento){
        this.testo = testo;
        this.tipo = tipo;
        this.data = data;
        this.idElemento = idElemento;
    }
    
    /**
     * @return the testo
     */
    public String getTesto() {
        return testo;
    }

    /**
     * @param testo the testo to set
     */
    public void setTesto(String testo) {
        this.testo = testo;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the data
     */
    public Timestamp getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Timestamp data) {
        this.data = data;
    }

    /**
     * @return the idElemento
     */
    public Integer getIdElemento() {
        return idElemento;
    }

    /**
     * @param idElemento the idElemento to set
     */
    public void setIdElemento(Integer idElemento) {
        this.idElemento = idElemento;
    }
}
