/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db;

/**
 *
 * @author davide
 */
public class Prodotto {
    private Integer id;
    private String nome;
    private String note;
    private String logo;
    private String fotografia;
    private CategoriaProdotti categoria;
    
    public Prodotto(){}
    
    public Prodotto(Integer id, String nome, String note, String logo, String fotografia, CategoriaProdotti categoria){
        this.id = id;
        this.nome = nome;
        this.note = note;
        this.logo = logo;
        this.fotografia = fotografia;
        this.categoria = categoria;
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
     * @return the fotografia
     */
    public String getFotografia() {
        return fotografia;
    }

    /**
     * @param fotografia the fotografia to set
     */
    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
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
}
