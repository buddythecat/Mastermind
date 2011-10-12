package com.ncc.edu.mastermind.game;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TableRow;

public class Row {
	private Peg[] pegs;
	private TableRow pegSet;
	
	public Row(TableRow t){
		pegs = new Peg[4];
		pegSet = t;
		//we're now going to cycle through this row and bind the buttons to their Peg objects.
		this.makePegs();
	}
	
	private void makePegs(){
		for(int i = 1; i<5; i++){
			View temp;			
			temp = pegSet.getChildAt(i);
			pegs[i-1] = new Peg(
					ImageButton.class.cast(temp),
					i-1);
		}
	}
	
	public Peg findPegByView(View v){
		Peg result = null;
		for(int i=0; i<4; i++){
			if(pegs[i].getView().equals(v))
				result = pegs[i];
		}
		return result;
	}
	
	public void markPeg(View v, Choice c){
		this.findPegByView(v).setChoice(c);
	}
	
	public void clearPeg(View v){
		this.findPegByView(v).clearPeg();
	}
	
	public Choice[] getAvailableChoices(){
		Choice[] available = Choice.values();
		int count = available.length-1;
		for(int i = 0; i<available.length-1; i++){
			boolean found = false;
				for(int j = 0; j<4; j++){
					if(pegs[j].equals(available[i]))
						found = true;
				}
			if(found){
				count--;
				available[i]=null;
			}
		}
		Choice[] result = new Choice[count];
		count = 0;
		for(int i = 0; i<available.length; i++){
			if(available[i]!=null){
				result[count]=available[i];
				count++;
			}
		}
		return result;
	}
	
	public void lockRow(){
		for(int i=0; i<4; i++)
			pegs[i].getView().setEnabled(false);
	}
	
	public void unlockRow(){
		for(int i=0; i<4; i++)
			pegs[i].getView().setEnabled(true);
	}
	
	public void finishRow(){
		for(int i=0; i<4; i++)
			pegs[i].getView().setClickable(false);
	}
	
	public boolean isFull(){
		boolean isFull = true;
		for(int i = 0; i<4; i++)
			if(pegs[i].getChoice().equals(Choice.EMPTY))
				isFull = false;
		return isFull;
	}
	
}
