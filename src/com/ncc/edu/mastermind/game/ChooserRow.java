package com.ncc.edu.mastermind.game;

import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * ChooserRow - 
 * The ChooserRow object is a type of Row which is used for the Chooser Activity.  
 * It contains 6 different Pegs, one for each Choice type.  Other then showing these
 * basic Pegs, it requires nothing else.
 * @author Rich Tufano
 */
public class ChooserRow extends Row {
	/** This stores the of the size of the chooser row.  */
	protected final static int SIZE = 6;
	
	/**
	 * ChooserRow - 
	 * This is the standard constructor for the ChooserRow object.  It requires a ViewGroup to passed to it,
	 * which it will use to build it's pegs from.  
	 * @param v The ViewGroup object tied to this ChooserRow
	 */
	public ChooserRow(ViewGroup v){
		super(v, ChooserRow.SIZE);
	}
	
	/**
	 * MakePegs - 
	 * makePegs binds the pegs of this ChooserRow to the actual Views of these Peg objects.
	 */
	protected void makePegs(){
		for(int i = 0; i<pegs.length; i++){
			ImageButton temp = ImageButton.class.cast(pegSet.getChildAt(i));
			try{
				pegs[i] = new Peg(Choice.getChoiceFromName(String.class.cast(temp.getTag())),temp,i);
			}catch(ChoiceNotFoundException e){
				//if this runs into an untagged ImageButton, it will throw an error.  Catch this error, report it, and make a new 'empty' peg.
				System.out.print(e.getMessage());
				pegs[i] = new Peg(Choice.EMPTY, temp, i);
			}
		}
	}
}
