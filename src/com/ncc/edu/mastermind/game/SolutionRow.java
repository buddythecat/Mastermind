package com.ncc.edu.mastermind.game;

import java.util.Random;

import android.view.ViewGroup;

public class SolutionRow extends GuessRow{
	private Choice[] solution = new Choice[GuessRow.SIZE];
	private Random rnd;
	
	public SolutionRow(ViewGroup v){
		super(v);
		rnd = new Random();
		this.buildSolution();
	}
	
	private void buildSolution(){
		Choice[] choices = Choice.values();
		for(int i=0; i<GuessRow.SIZE; i++){
			solution[i] = choices[rnd.nextInt(choices.length-1)];
			pegs[i].getView().setFocusable(false);	
		}
	}
	
	public Choice[] getSolution(){
		return solution;
	}

	
	public boolean hasWon(){
		return(this.isFull());
	}
	
	public void newSolution(){
		this.clearRow();
		this.buildSolution();
	}
	
	public void showSolution(){
		for(int i=0; i<GuessRow.SIZE; i++)
			pegs[i].markPeg(solution[i]);
	}
}