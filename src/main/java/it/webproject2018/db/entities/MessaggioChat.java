/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.entities;

import java.sql.Timestamp;

/**
 *
 * @author Max
 */
public class MessaggioChat {
	private Utente sender;
	private Integer id_list;
	private String message;
	private Timestamp date;
	
	public MessaggioChat(Utente sender, Integer id_lista, String message, Timestamp data) {
		this.sender = sender;
		this.id_list = id_lista;
		this.message = message;
		this.date = data;
	}


	/**
	 * @return the id_list
	 */
	public Integer getId_list() {
		return id_list;
	}

	/**
	 * @param id_list the id_list to set
	 */
	public void setId_list(Integer id_list) {
		this.id_list = id_list;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}	

    /**
     * @return the sender
     */
    public Utente getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(Utente sender) {
        this.sender = sender;
    }
}
