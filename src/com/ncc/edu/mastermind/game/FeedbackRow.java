package com.ncc.edu.mastermind.game;

import android.view.View;
import android.view.ViewGroup;


public class FeedbackRow extends Row{
	public FeedbackRow(ViewGroup v){
		super(v, 4);
	}
	/**
	 * it is assumed that this makePegs will be handed the index of the last peg in the row, +1
	 * @param v
	 */
	protected void makePegs(){
		int k = 0;
		for(int i = 0; i<2; i++){
			for(int j=0; j<2; j++){
				pegs[k] = new Peg((View)((ViewGroup)pegSet.getChildAt(i)).getChildAt(j), k);
				k++;
			}
		}
	}
	
	public void clearRow(){
		for(int i = 0; i<4; i++){
			pegs[i].clearPeg();
		}
	}
	
	public void showFeedback(Choice[] feedbax){
		int pegNum = 0;
		for(int i = 0; i<4; i++){
			if(!feedbax[i].equals(Choice.EMPTY)){
				pegs[pegNum].markPeg(feedbax[i]);
				pegNum++;
			}
		}
	}
	
	public void redrawFeedbackView(ViewGroup v){
		pegSet = v;
		int k = 0;
		for(int i = 0; i<2; i++){
			for(int j=0; j<2; j++){
				pegs[k].setView(((View)((ViewGroup)pegSet.getChildAt(i)).getChildAt(j)));
				k++;
			}
		}
		this.showFeedback(this.getChoicesFromFeedback());
	}
	
	public Choice[] getChoicesFromFeedback(){
		Choice[] temp = new Choice[4];
		for(int i = 0; i<4; i++){
			temp[i] = pegs[i].getChoice();
		}
		return temp;
	}
	
	public int[] getChoicesForParcel(){
		int[] temp = new int[4];
		for(int i = 0; i<4; i++){
			temp[i] = pegs[i].getChoice().getKey();
		}
		return temp;
	}
	
	public void returnFromParcel(int[] parcelVals){
		Choice[] temp = new Choice[4];
		for(int i = 0; i<4; i++)
			if(parcelVals[i]!=-1)
				temp[i] = Choice.getChoiceFromKey(parcelVals[i]);
		showFeedback(temp);
	}
}