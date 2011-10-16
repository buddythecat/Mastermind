package com.ncc.edu.mastermind.game;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * GuessRow - 
 * GuessRow is a subclass of type Row that is used to store the data and view of a corresponding
 * Row on the GameBoard.  The GuessRow is incharge of marking it's corresponding Pegs, showing the
 * feedback after the row is complete, and managing it's pegs.
 * @author Rich Tufano
 *
 */
public class GuessRow extends Row {
	/** the amount of pegs in the row */
	protected static final int SIZE=4;
	/** the feedback tied to this row */
	private FeedbackRow feedback;
	/**
	 * GuessRow - 
	 * Standard constructor.  This creates a new GuessRow object tied to the
	 * supplied ViewGroup, by calling Row's constructor, passing along the
	 * size of the GuessRow (4)
	 * @param t - the ViewGroup that will correspond to this GuessRow
	 */
	public GuessRow(ViewGroup t){
		super(t, SIZE);
	}
	
	/**
	 * makePegs -
	 * MakePegs builds the actual pegs for this row.  It will iterate through the children
	 * of the Row's attached ViewGroup and will create a new Peg at each child.
	 */
	protected void makePegs() {
		for(int i = 0; i<pegs.length; i++){
			ImageButton temp = ImageButton.class.cast(pegSet.getChildAt(i));
			pegs[i] = new Peg(temp,i);
		}
	}
	
	/**
	 * getNextEmptyPeg() -
	 * getNextEmptyPeg will iterate through the pegs in this Row and will return the
	 * next peg that is empty.  If there are no empty pegs, it will return null.
	 * @return the next empty peg, or null if the row is full.
	 */
	public Peg getNextEmptyPeg(){
		for(int i=0; i<pegs.length; i++){
			if(pegs[i].getChoice().equals(Choice.EMPTY))
				return pegs[i];
		}
		return null;
	}
	
	/**
	 * setFeedback - 
	 * setFeedback will set the FeedbackRow to the values passed into it in the feedChoices.
	 * the method will then show the feedbackRow
	 * @param feedChoices - the choices to fill the FeedbackRow with
	 */
	public void setFeedback(Choice[] feedChoices){
		feedback = new FeedbackRow((ViewGroup)pegSet.getChildAt(SIZE));
		feedback.showFeedback(feedChoices);
	}
	
	/**
	 * getFeedback - 
	 * getFeedback gets the feedbackRow associated with this GuessRow
	 * @return the feedbackRow associated with this GuessRow
	 */
	public FeedbackRow getFeedback(){
		return feedback;
	}
	
	/**
	 * markPeg - 
	 * markPeg will mark the Peg whose View is v with Choice c.
	 * If the Peg isn't found, the method simply quietly dies.  
	 * @param v - the view of the Peg to mark
	 * @param c - the choice to mark the peg with
	 */
	public void markPeg(View v, Choice c){
		try{
			this.findPegByView(v).setChoice(c);
		}catch(PegNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * markPeg - 
	 * markPeg will mark the Peg whose position in this row is
	 * pos, and with Choice c.  If the peg isn't found, the method
	 * should quietly die.  (This really shouldn't happen however,
	 * since the position just shouldn't be out of bounds...)
	 * 
	 * @param pos - the position of the Peg in this row
	 * @param c - the choice to mark the peg with
	 */
	public void markPeg(int pos, Choice c){
		pegs[pos].setChoice(c);
	}
	
	/**
	 * getPegs -
	 * @return the array of pegs for this GuessRow
	 */
	public Peg[] getPegs(){
		return pegs;
	}
	
	/**
	 * clearPegs - 
	 * clearPegs will clear the peg with View v.
	 * if the Peg is not found, this method should die quietly.
	 * @param v the View of the Peg to clear
	 */
	public void clearPeg(View v){
		try{
			this.findPegByView(v).clearPeg();
		}catch(PegNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * lockRow -
	 * lockRow locks all the pegs in this row.  This method is called with a Row is done,
	 * or at the very start of a game.
	 */
	public void lockRow(){
		for(int i=0; i<SIZE; i++)
			pegs[i].lockPeg();
	}
	
	/**
	 * unlockRow -
	 * unlocks all the pegs in this row.  This method is called when it's this row's turn
	 * in the game.
	 */
	public void unlockRow(){
		for(int i=0; i<SIZE; i++)
			pegs[i].unlockPeg();
	}
	/**
	 * isFull - 
	 * returns true if this row is full (all peg's are not Empty), and false if not.
	 * @return true if this row is full.
	 */
	public boolean isFull(){
		for(int i = 0; i<SIZE; i++)
			if(pegs[i].getChoice().equals(Choice.EMPTY))
				return false;
		return true;
	}
	
	/**
	 * clearRow - 
	 * this method will clear all the pegs in this row,  
	 * This will not create any new pegs! it will only remove
	 * their associated Choice 
	 */
	public void clearRow(){
		for(int i = 0; i<SIZE; i++)
			pegs[i].clearPeg();
		if(feedback!=null)
			feedback.clearRow();
	}
	
	/**
	 * registerNewRowView - 
	 * this method is in charge of rebinding Views to already existing Pegs in this row.
	 * This will often be called after an onStateChange or onPause... etc.
	 *  - It will first bind the ViewGroup to the supplied ViewGroup, it will then iterate
	 * through the pegs array, and rebind each peg to the corresponding View in the supplied
	 * ViewGroup's children.
	 *  - If this row has a feedback row, the feedbackRow will also be rebound.
	 * @param v The new ViewGroup to bind to this Row
	 */
	public void registerNewRowView(ViewGroup v){
		pegSet = v;
		for(int i = 0; i<pegs.length; i++){
			ImageButton temp = ImageButton.class.cast(pegSet.getChildAt(i));
			pegs[i].setView(temp);
			pegs[i].redrawView();
		}
		if(feedback!=null)
			feedback.redrawFeedbackView((ViewGroup)pegSet.getChildAt(SIZE));
	}
}
