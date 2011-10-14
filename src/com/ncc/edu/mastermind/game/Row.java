package com.ncc.edu.mastermind.game;

import android.view.View;
import android.view.ViewGroup;

public abstract class Row {
	protected Peg[] pegs;
	protected ViewGroup pegSet;

	public Row(ViewGroup t, int len){
		pegs = new Peg[len];
		pegSet = t;
		//we're now going to cycle through this row and bind the buttons to their Peg objects.
		this.makePegs();
	}
	
	public Row(int len){
		this(null, len);
	}
	
	protected abstract void makePegs();

	public Peg findPegByView(View v) {
		for(int i=0; i<pegs.length; i++){
			if(pegs[i].getView().equals(v))
				return pegs[i];
		}
		return null;
	}

	public Peg findPegByChoice(Choice c) {
		for(int i = 0; i<pegs.length; i++){
			if(pegs[i].getChoice().equals(c))
				return pegs[i];
		}
		return null;
	}

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
	
	public Peg getPegAtIndex(int i){
		return pegs[i];
	}
	
	public int findPegIndex(Peg p){
		int i = 0;
		while(!pegs[i].equals(p))
			i++;
		return i;
	}

}