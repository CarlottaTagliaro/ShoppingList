/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.db.exceptions;

/**
 * The exception thrown when something goes wrong in data retrieving.
 * 
 * @author Max
 */
public class DAOException extends Exception {
	
	public DAOException() {
		super();
	}
	
	/**
	 * 
	 * @param message
	 */
	public DAOException(String message) {
        super(message);
    }
	
	/**
	 * 
	 * @param cause 
	 */
	public DAOException(Throwable cause) {
        super(cause);
    }
	
	/**
	 * 
	 * @param message
	 * @param cause 
	 */
	public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
