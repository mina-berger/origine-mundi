/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.util;

/**
 *
 * @author mina
 */
public class CoreException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//static final long serialVersionUID = 6287977888167466900L;
	public CoreException(){super();}
	public CoreException(String message){super(message);}
	public CoreException(Exception e){super(e);} 
	public CoreException(String message, Exception e){super(message, e);} 
}
