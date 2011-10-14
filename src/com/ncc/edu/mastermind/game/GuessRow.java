package com.ncc.edu.mastermind.game;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class GuessRow extends Row {
	protected static final int SIZE=4;
	private FeedbackRow feedback;
	
	public GuessRow(ViewGroup t){
		super(t, SIZE);
	}
	
	public GuessRow(){
		super(SIZE);
	}
	
	protected void makePegs() {
		for(int i = 0; i<pegs.length; i++){
			ImageButton temp = ImageButton.class.cast(pegSet.getChildAt(i));
			pegs[i] = new Peg(temp,i);
		}
	}
	
	public Peg getNextEmptyPeg(){
		for(int i=0; i<pegs.length; i++){
			if(pegs[i].getChoice().equals(Choice.EMPTY))
				return pegs[i];
		}
		return null;
	}
	
	public void setFeedback(Choice[] feedChoices){
		feedback = new FeedbackRow((ViewGroup)pegSet.getChildAt(SIZE));
		feedback.showFeedback(feedChoices);
	}
	
	public void markPeg(View v, Choice c){
		this.findPegByView(v).setChoice(c);
	}
	
	public void markPeg(int pos, Choice c){
		pegs[pos].setChoice(c);
	}
	
	public Peg[] getPegs(){
		return pegs;
	}
	
	public void matchPegs(Peg[] toMatch){
		for(int i = 0; i < SIZE; i++){
			if(!toMatch[i].getChoice().equals(Choice.EMPTY)){
				pegs[i].markPeg(toMatch[i].getChoice());
				pegs[i].lockPeg();
			}
		}
	}
	public void clearPeg(View v){
		this.findPegByView(v).clearPeg();
	}
	
	public void lockRow(){
		for(int i=0; i<SIZE; i++)
			pegs[i].lockPeg();
	}
	
	public void unlockRow(){
		for(int i=0; i<SIZE; i++)
			pegs[i].unlockPeg();
	}
	
	public void finishRow(){
		for(int i=0; i<SIZE; i++)
			pegs[i].getView().setClickable(false);
	}
	
	public boolean isFull(){
		boolean isFull = true;
		for(int i = 0; i<SIZE; i++)
			if(pegs[i].getChoice().equals(Choice.EMPTY))
				isFull = false;
		return isFull;
	}
	
	public void clearRow(){
		for(int i = 0; i<SIZE; i++)
			pegs[i].clearPeg();
	}
}
