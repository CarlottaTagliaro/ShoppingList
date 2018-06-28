/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db;
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
	private String password;
	private Boolean isAdmin;

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
	public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getIsAdmin() {
		return isAdmin;
	}
    
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}
