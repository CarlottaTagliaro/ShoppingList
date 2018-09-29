/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.entities;

/**
 *
 * @author davide
 */
public class CategoriaProdotti {
    private String nome;
    private String descrizione;
    private String logo;
    private CategoriaListe categoriaLista;
    
    public CategoriaProdotti(String nome){
        this.nome = nome;
    }
     
    public CategoriaProdotti(String nome, String descrizione, String logo, CategoriaListe categoriaLista){
        this.nome = nome;
        this.descrizione = descrizione;
        this.logo = logo;
        this.categoriaLista = categoriaLista;
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
     * @return the categoriaLista
     */
    public CategoriaListe getCategoriaLista() {
        return categoriaLista;
    }

    /**
     * @param categoriaLista the categoriaLista to set
     */
    public void setCategoriaLista(CategoriaListe categoriaLista) {
        this.categoriaLista = categoriaLista;
    }
}
