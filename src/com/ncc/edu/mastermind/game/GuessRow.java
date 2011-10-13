package com.ncc.edu.mastermind.game;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class GuessRow extends Row {
	private static final int SIZE=4;
	public GuessRow(ViewGroup t){
		super(t, SIZE);
	}
	
	protected void makePegs() {
		for(int i = 1; i<pegs.length+1; i++){
			ImageButton temp = ImageButton.class.cast(pegSet.getChildAt(i));
			pegs[i-1] = new Peg(temp,i-1);
		}
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
			pegs[i].getView().setEnabled(false);
	}
	
	public void unlockRow(){
		for(int i=0; i<SIZE; i++)
			pegs[i].getView().setEnabled(true);
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
