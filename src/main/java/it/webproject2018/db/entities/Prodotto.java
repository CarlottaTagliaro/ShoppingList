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
public class Prodotto {

    private Integer id;
    private String nome;
    private String note;
    private String logo;
    private CategoriaProdotti categoria;
    public ArrayList<String> Fotografie;
    public ArrayList<InformazioniAcquisto> Acquisti;
    private String owner;

    public Prodotto() {
    }

    public Prodotto(Integer id, String nome, String note, String logo, ArrayList<String> fotografie, CategoriaProdotti categoria, String owner) {
        this.id = id;
        this.nome = nome;
        this.note = note;
        this.logo = logo;
        this.Fotografie = fotografie;
        this.categoria = categoria;
        this.Acquisti = new ArrayList<>();
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
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * @param logo the logo to set
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * @return the categoria
     */
    public CategoriaProdotti getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(CategoriaProdotti categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner of the product
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }
}
