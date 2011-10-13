package com.ncc.edu.mastermind.game;

import java.util.Random;

import android.view.ViewGroup;


public class SolutionRow extends GuessRow{
	private Choice[] solution = new Choice[4];
	private Random rnd;
	
	public SolutionRow(ViewGroup v){
		super(v);
		rnd = new Random();
		this.buildSolution();
	}
	
	private void buildSolution(){
		Choice[] available = Choice.values();
		int temp;
		for(int i=0; i<4; i++){
			temp = rnd.nextInt(available.length);
			while(available[temp].equals(Choice.EMPTY))
				temp = rnd.nextInt(available.length);	
			solution[i] = available[temp];
			available[temp] = Choice.EMPTY;
		}
	}
	
	public void compareToRow(GuessRow target){
		for(int i = 0; i<4;i++){
			if(target.getPegAtIndex(i).getChoice().equals(solution[i]))
				this.getPegAtIndex(i).markPeg(solution[i]);
		}
	}
	
	public boolean hasWon(){
		return(this.isFull());
	}
	
	public void newSolution(){
		this.clearRow();
		this.buildSolution();
	}
}