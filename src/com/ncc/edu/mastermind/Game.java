package com.ncc.edu.mastermind;

import android.app.Activity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

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
	
	public int getCurrentRowNum()				{	return rowNum;		}
	public void setCurrentRowNum(int row)		{	rowNum = row;		}
	public void incrCurrentRowNum()				{	rowNum++;			}
	
	public SolutionRow getSolution()			{	return solution;	}
	public void setSolution(SolutionRow sol)	{	solution = sol;		}
	
	public Peg getActivePeg()					{	return active;		}
	public void setActivePeg(Peg p)				{	active = p;			}
	
    private void buildGuessBoard(Activity parent){
    	TableLayout table = (TableLayout)parent.findViewById(R.id.game_board);
    	for(int i = 0; i<NUM_GUESS_ROWS; i++){
    		board[i] = new GuessRow((TableRow)table.getChildAt(i));
    		board[i].lockRow();
    	}
    	currentRow = board[rowNum];
    	currentRow.unlockRow();
    }
    
    private void buildSolutionRow(Activity parent){
    	solution = new SolutionRow((TableRow)parent.findViewById(R.id.game_solution));
    }
    
    public void catchSelectedPeg(View v){
    	active = currentRow.findPegByView(v);
    }
    
    
    public void nextRow() throws GameWinException, GameLoseException{
    	currentRow.lockRow();
    	solution.compareToRow(currentRow);
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
}
