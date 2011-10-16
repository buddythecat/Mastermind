package com.ncc.edu.mastermind.game;

/**
 * ChoiceNotFoundException - 
 * ChoiceNotFoundException is an Exception type that's thrown by the Choice class
 * if a request is made for a Choice that doesn't exist.
 * @author Rich Tufano
 *
 */
public class ChoiceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -4183696804159215202L;
	
	public ChoiceNotFoundException(){
		super("Choice was not found");
	}
}