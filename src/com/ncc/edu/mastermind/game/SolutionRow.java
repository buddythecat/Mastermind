package com.ncc.edu.mastermind.game;

import java.util.Random;

import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * SolutionRow 
 * The SolutionRow is a Row which contains the Solution to the puzzle.  
 * The Solution is generated randomly, and is the same size as the GuessRow.
 * @author Rich Tufano
 *
 */
public class SolutionRow extends GuessRow{
	private Choice[] solution = new Choice[GuessRow.SIZE];
	private Random rnd;
	
	/**
	 * SolutionRow - 
	 * Creates a SolutionRow whose tied to the ViewGroup v
	 * @param v the ViewGroup to tie to this SolutionRow
	 */
	public SolutionRow(ViewGroup v){
		super(v);
		rnd = new Random();
		this.buildSolution();
	}
	
	/**
	 * BuildSolution - 
	 * Creates a random solution for this Row by using a random number 
	 * generator to select choices at random, and then creates Pegs with 
	 * these Choices.
	 */
	private void buildSolution(){
		Choice[] choices = Choice.values();
		for(int i=0; i<GuessRow.SIZE; i++){
			solution[i] = choices[rnd.nextInt(choices.length-1)];
			pegs[i].getView().setFocusable(false);	
		}
	}
	
	/**
	 * getSolution - 
	 * @return the Choice Array for the solution
	 */
	public Choice[] getSolution(){
		return solution;
	}
	
	/**
	 * hasWon - 
	 * @return true if all the pegs in the solution are marked
	 */
	public boolean hasWon(){
		return(this.isFull());
	}
	
	/**
	 * newSolution - 
	 * creates a new solution by first clearing the row, and then calling the 
	 * buildSolution method.
	 */
	public void newSolution(){
		this.clearRow();
		this.buildSolution();
	}
	
	/**
	 * showSolution -
	 * shows the Solution for this row by marking the Pegs with the solution choices.
	 * This method is called when a game is won or lost.
	 */
	public void showSolution(){
		for(int i=0; i<GuessRow.SIZE; i++)
			pegs[i].markPeg(solution[i]);
	}
	
	/**
	 * getSolutionForParcel - 
	 * this method returns an array of ints containing the keys of the choices
	 * in the solution.  It used when the enclosing Game object is being parceled.
	 * @return an array of ints representing the solution's choices
	 */
	public int[] getSolutionForParcel(){
		int[] temp = new int[4];
		for(int i = 0; i<4; i++)
			temp[i] = solution[i].getKey();
		return temp;
	}
	
	/**
	 * recoverSolutionFromParcel
	 * rebuilds the solution from the values stored within the Parcel.  This method is called
	 * when a Game is recreated from a parcel
	 * @param recovered - the keys of the choices of the solution to be rebuilt.
	 */
	public void recoverSolutionFromParcel(int[] recovered){
		for(int i = 0; i<4; i++){
			try{
				solution[i] = Choice.getChoiceFromKey(i);
			}catch(ChoiceNotFoundException e){
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * registerNewRowView - 
	 * this method is in charge of rebinding Views to already existing Pegs in this row.
	 * This will often be called after an onStateChange or onPause... etc.
	 *  - It will first bind the ViewGroup to the supplied ViewGroup, it will then iterate
	 * through the pegs array, and rebind each peg to the corresponding View in the supplied
	 * ViewGroup's children.
	 *  - If this row has a feedback row, the feedbackRow will also be rebound.
	 * @param v The new ViewGroup to bind to this Row
	 */
	public void registerNewRowView(ViewGroup v){
		pegSet = v;
		for(int i = 0; i<pegs.length; i++){
			ImageButton temp = ImageButton.class.cast(pegSet.getChildAt(i));
			pegs[i].setView(temp);
			temp.setFocusable(false);
		}
	}
}