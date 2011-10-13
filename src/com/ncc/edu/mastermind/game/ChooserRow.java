package com.ncc.edu.mastermind.game;

import android.view.ViewGroup;
import android.widget.ImageButton;

public class ChooserRow extends Row {
	protected final static int SIZE = 6;
	public ChooserRow(ViewGroup v){
		super(v, ChooserRow.SIZE);
	}
	
	protected void makePegs(){
		for(int i = 0; i<pegs.length; i++){
			ImageButton temp = ImageButton.class.cast(pegSet.getChildAt(i));
			pegs[i] = new Peg(Choice.getChoiceFromName(String.class.cast(temp.getTag())),temp,i);
		}
	}
}
