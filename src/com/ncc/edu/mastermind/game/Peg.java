package com.ncc.edu.mastermind.game;

import android.view.View;
import android.widget.ImageView;
/**
 * The Peg Class - 
 * 	The peg class is a class designed to store the state of a Peg in the Mastermind Game.
 * A peg has a few different properties.
 * 	1)	State - the state of the peg (Empty, Red, Blue, etc)
 * 	2)	Button - the button of the Peg on the screen
 * 	3)	Position - the position of the button, if any
 * The Peg class contains some methods to help it along too..
 * @author Rich Tufano
 *
 */
public class Peg{
	/** this is the parcel code for the Peg **/
	public static final int PEG_PARCEL=31;
	
	private Choice pegState;
	private View pegButton;
	private int position;
	
	/**
	 * Peg - 
	 * this is the standard constructor.  It takes a Choice c, a View v, 
	 * and a position p, and binds them to the Peg object. 
	 * @param c - the choice of this peg
	 * @param v - the view of this peg
	 * @param p - the position of this peg in the row
	 */
	public Peg(Choice c, View v, int p){
		pegState = c;
		pegButton = v;
		position = p;
	}
	
	/**
	 * Peg - 
	 * this is the more specific constructor, creating an empty peg at View v
	 * and position p.  
	 * @param v - the View of this Peg
	 * @param p - the position of this peg
	 */
	public Peg(View v, int p){
		this(Choice.EMPTY, v, p);
	}
	/** 
	 * getChoice -
	 * @return the choice of this peg
	 */
	public Choice getChoice()		{ return pegState;  }
	/**
	 * setChoice -
	 * @param c the choice to set this peg
	 */
	public void setChoice(Choice c)	{ pegState = c;  	}
	/**
	 * getView - 
	 * @return the View of this Peg
	 */
	public View getView()			{ return pegButton;	}
	/**
	 * setView - 
	 * @param v thew View to set this peg
	 */
	public void setView(View v)		{ pegButton = v; 	}
	/**
	 * getPosition - 
	 * @return the position of this Peg
	 */
	public int getPosition()		{ return position;	}
	/**
	 * setPosition - 
	 * @param p the position that this peg should have
	 */
	public void setPosition(int p)	{ position = p;		}
	
	/**
	 * markPeg - 
	 * sets the Peg's choice to c, and draws the View's image resource to the 
	 * value stored in the Choice
	 * @param c - the choice to mark the peg with.
	 */
	public void markPeg(Choice c){
		pegState = c;
		try{
			(ImageView.class.cast(pegButton)).setImageResource(pegState.getId());
		}catch(ClassCastException e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * redrawView - 
	 * redraws the Peg after it's been rebound to a new view.
	 */
	public void redrawView(){
		this.markPeg(pegState);
	}
	
	/**
	 * clearPeg -
	 * sets the choice to empty by calling markPeg, which will clear the
	 * View's resource as well
	 */
	public void clearPeg(){
		this.markPeg(Choice.EMPTY);
	}
	
	/**
	 * lockPeg - 
	 * locks the peg from user input by setting it to disabled, and
	 * by making it unFocusable.
	 */
	public void lockPeg(){
		this.getView().setEnabled(false);
		this.getView().setFocusable(false);
	}
	
	/**
	 * unlockPeg - 
	 * unlocks this peg by setting it to Enabled, and by 
	 * making it focusable.
	 */
	public void unlockPeg(){
		this.getView().setEnabled(true);
		this.getView().setFocusable(true);
	}
	
	public String toString(){
		return "Peg["+pegState+"]/n";
	}
}
