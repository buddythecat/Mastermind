package com.ncc.edu.mastermind.game;

import android.view.View;
import android.view.ViewGroup;

/**
 * The Row Class - 
 * The Row is an abstract class designed to set the basics for the children Row's (GuessRow, ChooserRow, FeedbackRow, SolutionRow).  
 * It has most of the findPeg* methods, as well as the data for the peg arrays, and the ViewGroup that holds their Views
 * @author snake
 *
 */
public abstract class Row{
	public static final int ROW_PARCEL = 32;
	
	protected Peg[] pegs;
	protected ViewGroup pegSet;

	/**
	 * Row -
	 * Creates a (child of)Row with ViewGroup v and length of len
	 * @param t - the ViewGroup of the Row
	 * @param len - the Length of the Row
	 */
	public Row(ViewGroup t, int len){
		pegs = new Peg[len];
		pegSet = t;
		//we're now going to cycle through this row and bind the buttons to their Peg objects.
		this.makePegs();
	}
	
	/**
	 * makePegs - 
	 * this is a placeholder for the child classes' makePegs method.  
	 */
	protected abstract void makePegs();

	/**
	 * findPegByView -
	 * This will find the Peg in this row whose assigned the View v.  If no Peg 
	 * is found, the method will throw a PegNotFoundException 
	 * @param v - the View of the peg to find
	 * @throws PegNotFoundException if no peg is found
	 * @return the peg with the view, v
	 */
	public Peg findPegByView(View v) {
		for(int i=0; i<pegs.length; i++){
			if(pegs[i].getView().equals(v))
				return pegs[i];
		}
		//if not found
		throw new PegNotFoundException();
	}

	/**
	 * findPegByChoice - 
	 * This will find the (first)Peg in the Row with Choice c, and return it.
	 * If there is not Peg with that value in the row, it'll NOT throw a PegNotFoundException,
	 * it wil instead return null
	 * @param c - the choice of the Peg to find
	 * @return - the Peg with that choice, or null if not found
	 */
	public Peg findPegByChoice(Choice c) {
		for(int i = 0; i<pegs.length; i++){
			if(pegs[i].getChoice().equals(c))
				return pegs[i];
		}
		return null;
	}
	
	/**
	 * getSelectedChoiceKeys -
	 * returns the Keys of the Choices of the Pegs in this row.  This 
	 * method is used for when the enclosing Game object is parceled. 
	 * If a choice is empty, it will fill the key with a -1
	 * @return the keys of the choices of the pegs of this object.  
	 */
	public int[] getSelectedChoiceKeys() {
		int[] result = new int[pegs.length];
		for(int i=0; i<pegs.length; i++){
			if(!pegs[i].getChoice().equals(Choice.EMPTY))
				result[i]=pegs[i].getChoice().getKey();
			else
				result[i]=-1;
		}
		return result;
	}
	
	/**
	 * getPegAtIndex -
	 * this method gets the peg at index i.  If no peg is found, this method
	 * will throw a PegNotFoundException.    
	 * @param i the index of the Peg to find
	 * @return the Peg at that index
	 */
	public Peg getPegAtIndex(int i){
		if(i>=pegs.length)
			throw new PegNotFoundException();
		return pegs[i];
	}
	
	/**
	 * findPegIndex - 
	 * finds the index of the Peg p.  Will throw a PegNotFoundException if 
	 * the peg isn't in this row
	 * @param p - the Peg whose index we want
	 * @return the index of the peg
	 */
	public int findPegIndex(Peg p){
		int i = 0;
		while(i<4 && !pegs[i].equals(p))
			i++;
		//check the end case, that i=3 but pegs[i] != p
		if((i<3) || (i==3 && pegs[i].equals(p)))
			return i;
		else
			return -1;
	}

}