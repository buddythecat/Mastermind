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
	
	public void showFeedback(Choice[] feedbax){
		for(int i = 0; i<4; i++){
			if(!feedbax[i].equals(Choice.EMPTY))
				pegs[i].markPeg(feedbax[i]);
		}
	}
	
	
}