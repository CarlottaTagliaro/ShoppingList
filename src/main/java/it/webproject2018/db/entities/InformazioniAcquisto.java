/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.entities;

import java.sql.Date;

/**
 *
 * @author davide
 */
public class InformazioniAcquisto {
    private Date data;
    private Integer quantità;
    private Integer id_lista;
    private Integer id_prodotto;
    
    public InformazioniAcquisto(Date data, Integer quantità, Integer id_lista, Integer id_prodotto){
        this.data = data;
        this.quantità = quantità;
        this.id_lista = id_lista;
        this.id_prodotto = id_prodotto;
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

    /**
     * @return the id_lista
     */
    public Integer getId_lista() {
        return id_lista;
    }

    /**
     * @param id_lista the id_lista to set
     */
    public void setId_lista(Integer id_lista) {
        this.id_lista = id_lista;
    }

    /**
     * @return the id_prodotto
     */
    public Integer getId_prodotto() {
        return id_prodotto;
    }

    /**
     * @param id_prodotto the id_prodotto to set
     */
    public void setId_prodotto(Integer id_prodotto) {
        this.id_prodotto = id_prodotto;
    }
}
