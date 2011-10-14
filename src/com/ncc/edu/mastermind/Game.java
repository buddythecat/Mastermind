package com.ncc.edu.mastermind;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.ncc.edu.mastermind.game.Choice;
import com.ncc.edu.mastermind.game.GuessRow;
import com.ncc.edu.mastermind.game.Peg;
import com.ncc.edu.mastermind.game.SolutionRow;
import com.ncc.edu.project2.R;

public class Game {
	public class GameLoseException extends Exception{
		private static final long serialVersionUID = 6944677277032824880L;
		public GameLoseException(){
			super("You've ran out of rows");
		}
	}
	public class GameWinException extends Exception{
		private static final long serialVersionUID = -7788145196599832393L;
		public GameWinException(){
			super("Congrats! You've won");
		}
	}
	
	public static final int NUM_GUESS_ROWS = 8;
	private GuessRow[] board;
	private GuessRow currentRow;
	private int rowNum;
	private SolutionRow solution;
	private Peg active;
	
	public Game(Activity parent){
		board = new GuessRow[Game.NUM_GUESS_ROWS];
		currentRow = null;
		solution = null;
		active = null;
		rowNum=0;
		this.buildGuessBoard(parent);
		this.buildSolutionRow(parent);
	}
	
	public GuessRow[] getBoard()				{	return board;		}
	public void setBoard(GuessRow[] newBoard)	{	board = newBoard;	}
	
	public GuessRow	getCurrentRow()				{	return currentRow;	}
	public void setCurrentRow(GuessRow next)	{	currentRow = next;	}
	public GuessRow getLastRow(){	
		if(rowNum>0)
			return board[rowNum-1];
		else
			return currentRow;	
	}
	public int getCurrentRowNum()				{	return rowNum;		}
	public void setCurrentRowNum(int row)		{	rowNum = row;		}
	public void incrCurrentRowNum()				{	rowNum++;			}
	
	public SolutionRow getSolution()			{	return solution;	}
	public void setSolution(SolutionRow sol)	{	solution = sol;		}
	
	public Peg getActivePeg()					{	return active;		}
	public void setActivePeg(Peg p)				{	active = p;			}
	
    private void buildGuessBoard(Activity parent){
    	ViewGroup table = (ViewGroup)parent.findViewById(R.id.game_board);
    	for(int i = 0; i<NUM_GUESS_ROWS; i++){
    		board[i] = new GuessRow((ViewGroup)table.getChildAt(i));
    		board[i].lockRow();
    	}
    	currentRow = board[rowNum];
    	currentRow.unlockRow();
    }
    
    private void buildSolutionRow(Activity parent){
    	ViewGroup t = ViewGroup.class.cast(parent.findViewById(R.id.game_solution));
    	solution = new SolutionRow(t);
    }
    
    public void catchSelectedPeg(View v){
    	active = currentRow.findPegByView(v);
    }
        
    public void nextRow() throws GameWinException, GameLoseException{
    	currentRow.lockRow();
    	currentRow.setFeedback(this.compareRowToSolution());
    	if(!solution.hasWon()){
    		rowNum++;
    		if(rowNum<Game.NUM_GUESS_ROWS){
    			currentRow = board[rowNum];
    			currentRow.unlockRow();
    			currentRow.matchPegs(solution.getPegs());
       		}
    		else
    			throw new GameLoseException();
    	}
    	else
    		throw new GameWinException();
    }
    
    public boolean isCurrentRowFull(){
    	return currentRow.isFull();
    }
    
    public void newGame(){
		for(int i = 0; i<Game.NUM_GUESS_ROWS; i++){
			board[i].clearRow();
			board[i].lockRow();
		}
		rowNum = 0;
		currentRow = board[rowNum];
		currentRow.unlockRow();
		solution.newSolution();
    }
    
    public void showSolution(){
    	solution.showSolution();
    }
	/**
	 * compareToRow - 
	 * This method is in charge of comparing the last guess to the solution(this).
	 * It returns a FeedbackRow which is a Row which contains only White/Black pegs.
	 * The WHITE pegs denote right peg, wrong position.  BLACK pegs denote right peg,
	 * right position. 
	 * @param target - the row to compare against the solution
	 * @returns - returns the choices for the feedback row to be assigned.
	 */
	public Choice[] compareRowToSolution(){
		Choice[] result = new Choice[4];
		Choice[] temp = solution.getSolution();
		for(int i = 0; i<4; i++){
			if(currentRow.findPegByChoice(temp[i])!=null)
				if(currentRow.getPegAtIndex(i).getChoice().equals(temp[i]))
					//This is the Right Peg at Right Spot
					result[i] = Choice.BLACK;
				else
					//this is just the right Peg
					result[i] = Choice.WHITE;
			else
				result[i] = Choice.EMPTY;
		}
		return result;
	}
}
