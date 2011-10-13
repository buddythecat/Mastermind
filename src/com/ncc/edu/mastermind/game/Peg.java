package com.ncc.edu.mastermind.game;

import android.view.View;
import android.widget.ImageButton;

public class Peg{
	private Choice pegState;
	private View pegButton;
	private int position;
	
	public Peg(Choice c, View v, int p){
		pegState = c;
		pegButton = v;
		position = p;
	}
	
	public Peg(View v, int p){
		pegState = Choice.EMPTY;
		pegButton = v;
		position = p;
	}
	
	public Choice getChoice()		{ return pegState;  }
	public void setChoice(Choice c)	{ pegState = c;  	}
	public View getView()			{ return pegButton;	}
	public void setView(View v)		{ pegButton = v; 	}
	public int getPosition()		{ return position;	}
	public void setPosition(int p)	{ position = p;		}
	
	public void markPeg(Choice c){
		pegState = c;
		try{
			(ImageButton.class.cast(pegButton)).setImageResource(pegState.getId());
		}catch(ClassCastException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void clearPeg(){
		this.markPeg(Choice.EMPTY);
	}
	
	public void lockPeg(){
		this.getView().setEnabled(false);
	}
	
}
