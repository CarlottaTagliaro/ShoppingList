/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.entities;

import java.sql.Date;

/**
 *
 * @author Max
 */
public class MessaggioChat {
	private String email_sender;
	private Integer id_list;
	private String message;
	private Date date;
	
	public MessaggioChat(String email_sender, Integer id_lista, String message, Date data) {
		this.email_sender = email_sender;
		this.id_list = id_lista;
		this.message = message;
		this.date = data;
	}

	/**
	 * @return the email_sender
	 */
	public String getEmail_sender() {
		return email_sender;
	}

	/**
	 * @param email_sender the email_sender to set
	 */
	public void setEmail_sender(String email_sender) {
		this.email_sender = email_sender;
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
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}	
}
