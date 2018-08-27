/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.entities;

import java.util.Date;

/**
 *
 * @author davide
 */
public class InformazioniAcquisto {
    private Date data;
    private Integer quantità;
    
    public InformazioniAcquisto(Date data, Integer quantità){
        this.data = data;
        this.quantità = quantità;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @return the quantità
     */
    public Integer getQuantità() {
        return quantità;
    }

    /**
     * @param quantità the quantità to set
     */
    public void setQuantità(Integer quantità) {
        this.quantità = quantità;
    }
}
