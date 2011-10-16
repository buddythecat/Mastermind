package com.ncc.edu.mastermind.game;

/**
 * PegNotFoundException - 
 * This is an exception to be thrown when a Peg isn't found within a Row.
 * @author Rich Tufano
 *
 */
public class PegNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4310246553477520657L;

	public PegNotFoundException(){
		super("Peg not found");
	}
}
