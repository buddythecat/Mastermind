package com.ncc.edu.mastermind.game;

import android.view.View;
import android.view.ViewGroup;

/**
 * FeedbackRow - 
 * The feedback row is the tiny set of pegs that we see next to a GuessRow.  The FeedbackRow shows
 * the feedback from guesses.  The potentials are:
 * 	White = Right Color, Wrong spot.
 * 	Black = Right Color, Wrong spot.
 * The feedback may not be in the same spot as the peg, because the feedback is marked in a 'first come first serve'
 * order. (AKA it doesn't skip incorrect pegs)
 * 
 * The FeedbackRow's size should be the same size as the GuessRow's, being that there should be as much feedback as
 * there is guesses.
 * 
 * The FeedbackRow is normally contained WITHIN a GuessRow, being that the two are tied together
 * 
 * @author Rich Tufano
 *
 */
public class FeedbackRow extends Row{
	/**
	 * FeedbackRow - 
	 * The standard constructor.  Creates a FeedbackRow bound to ViewGroup v.  
	 * Instantiated by calling Row's constructor and passing in the value of 
	 * GuessRow.SIZE
	 * @param v
	 */
	public FeedbackRow(ViewGroup v){
		super(v, GuessRow.SIZE);
	}
	/**
	 * MakePegs - 
	 * MakePegs is the (overriden) peg/view binder for the FeedbackRow.  This makePegs is different from
	 * all the other because the pegs in the FeedbackRow are split onto two different rows.  Therefor, 
	 * the iterator to step through these two rows is special.  
	 */
	protected void makePegs(){
		//k will store the position of the peg in the peg array
		int k = 0;
		//First loop - to iterate through the rows
		for(int i = 0; i<2; i++){
			//Second loop - to iterate through the columns
			for(int j=0; j<2; j++){
				//bind the peg using k for the position, and i and j to retrieve the peg at row[i] and col[j] in the tree
				pegs[k] = new Peg((View)((ViewGroup)pegSet.getChildAt(i)).getChildAt(j), k);
				//increment k
				k++;
			}
		}
	}
	
	/**
	 * clearRow - 
	 * clears this FeedbackRow.  This is mostly used when a game is reset, and is fired off by the
	 * Gues4sRow that contains this FeedbackRow.
	 */
	public void clearRow(){
		for(int i = 0; i<pegs.length; i++){
			pegs[i].clearPeg();
		}
	}
	
	/**
	 * showFeedback - 
	 * showFeedback draws the feedback in this FeedbackRow from an array of Choices.
	 * The array of Choices coming in is normally sent by the enclosing GuessRow.
	 *	- showFeedback should not leave spaces for empty choices in the array, and should 
	 *	therefor condense this supplied array.
	 * @param feedbax - the feedback, as sent from the GuessRow
	 */
	public void showFeedback(Choice[] feedbax){
		//the pegNum that's going to be marked.  This doesn't correspond to the index of feedbax.
		int pegNum = 0;
		//iterate through the pegs
		for(int i = 0; i<pegs.length; i++){
			//if this response isn't empty (it should only be White or Black)
			if(!feedbax[i].equals(Choice.EMPTY)){
				//mark this peg with the corresponding choice
				pegs[pegNum].markPeg(feedbax[i]);
				//increment the pegNum we wil be marking
				pegNum++;
			}
		}
	}
	
	/**
	 * redrawFeedbackView
	 * this method will re-bind this FeedbackRow with a new ViewGroup.  This is for re-drawing the
	 * Row after it's been unbound by a View Redraw.
	 * @param v - the new ViewGroup to bind to.
	 */
	public void redrawFeedbackView(ViewGroup v){
		//set the pegset to the new ViewGroup
		pegSet = v;
		//This is basically makePegs(), except we're not going to create new pegs, but just rebind them to the new pegs
		int k = 0;
		for(int i = 0; i<2; i++){
			for(int j=0; j<2; j++){
				//do not make new pegs, just rebind the Views
				pegs[k].setView(((View)((ViewGroup)pegSet.getChildAt(i)).getChildAt(j)));
				k++;
			}
		}
		//re-show the feedback
		this.showFeedback(this.getChoicesFromFeedback());
	}
	
	/**
	 * getChoicesFromFeedback - 
	 * this method returns the choices of the pegs in this FeedbackRow as an array of Choices.
	 * @return the array of choices corresponding to this FeedbackRow
	 */
	public Choice[] getChoicesFromFeedback(){
		Choice[] temp = new Choice[4];
		for(int i = 0; i<pegs.length; i++){
			temp[i] = pegs[i].getChoice();
		}
		return temp;
	}
	
	/**
	 * getChoicesForParcel -
	 * this method is build to get the keys of the choices for parceling this row.  All this method
	 * is really responsible for is returning an array of integers whose values are the keys of the
	 * choices in this row.  
	 * 	-This method is called when a Game is parceled.
	 * @return the keys of the choices in this row.
	 */
	public int[] getChoicesForParcel(){
		int[] temp = new int[4];
		for(int i = 0; i<4; i++){
			temp[i] = pegs[i].getChoice().getKey();
		}
		return temp;
	}
	
	/**
	 * returnFromParcel -
	 * this method is used to return from the parcelable.  It takes an array of integers, moves them 
	 * to an array of choices, and then passes this to showFeedback in order to redraw the feedback.
	 * @param parcelVals - the integers corresponding to the choices to draw in this feedbackRow
	 */
	public void returnFromParcel(int[] parcelVals){
		Choice[] temp = new Choice[4];
		for(int i = 0; i<4; i++)
			if(parcelVals[i]!=-1)
				try{
					temp[i] = Choice.getChoiceFromKey(parcelVals[i]);
				}catch(ChoiceNotFoundException e){
					System.out.println(e.getMessage());
				}
		showFeedback(temp);
	}
}