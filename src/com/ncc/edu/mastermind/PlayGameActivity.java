package com.ncc.edu.mastermind;

import com.ncc.edu.mastermind.game.Choice;
import com.ncc.edu.mastermind.game.ChoiceNotFoundException;
import com.ncc.edu.mastermind.game.GuessRow;
import com.ncc.edu.mastermind.game.Peg;
import com.ncc.edu.mastermind.game.SolutionRow;
import com.ncc.edu.project2.R;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * PlayGameActivity Class
 * This Class is the Activity which is shows the GameBoard and
 * drives the Game itself.  It's inner class, the Game object
 * stores all of the different Rows and Buttons, and has all
 * the logic for the Mastermind Game.
 * @author Rich Tufano
 *
 */
public class PlayGameActivity extends Activity {
	private Game thisGame;
	
	/**
	 * onCreate -
	 * creates the gameBoard from a savedInstanceState (if any),
	 */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board);
        thisGame = new Game();
    }
    
    /**
     * getGame
     * @return the Game object for this Activity
     */
    public Game getGame(){
    	return thisGame;
    }
    
    /**
     * launchChooser
     * the launchChooser method is tied to each button in the GuessRow's.
     * When these buttons are clicked, this method is called.
     * The launchChooser method creates a new intent, stores the peg
     * which has been clicked, and starts the chooserActivity
     * and waits for the result.  
     * @param v the peg which has been clicked
     */
    public void launchChooser(View v){
    	Intent chooserIntent = new Intent(this, ChooserActivity.class);
    	//set the active peg to clicked view
    	
    	thisGame.catchSelectedPeg(v);
    	//pull the used choices
    	this.startActivityForResult(
    			chooserIntent, 
    			Mastermind.Results.GET_CHOICE);
    }
    
    /**
     * onSaveInstanceState - 
     * parcels the game and packs it into the bundle.
     */
    public void onSaveInstanceState(Bundle savedInstanceState){
    	savedInstanceState.putParcelable("Game", thisGame);
    	super.onSaveInstanceState(savedInstanceState);
    }
    
    /**
     * onRestoreInstanceState - 
     * rebuilds the Activity based on the bundle passed to it
     */
    public void onRestoreInstanceState(Bundle savedInstanceState){
    	super.onRestoreInstanceState(savedInstanceState);
    	
    	thisGame = Game.class.cast(savedInstanceState.getParcelable("Game"));
    	//thisGame.rebuildGameFromParcel(this);
    	thisGame.rebuildGuessBoard(this);
    }
    
    /**
     * onActivityResult - 
     * this overridden method is what handles the result from the ChooserActivity.
     * It takes the resulting Intent, unpacks the bundle, and removes the choice from 
     * the bundle.  The choice is encoded as an integer key, which is then turned into 
     * it's corresponding choice.  The active peg is then marked with this choice.
     * 
     * This method then checks if the row is full and, if it is, tells them game to check the 
     * row for a win, and move on.
     * 
     * If the game has been won or lost, the corresponding Exception is thrown, and this method
     * handles them by calling the endGame() method with the result.
     * 
     * Otherwise, it returns back to the Activity.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	if(requestCode == Mastermind.Results.GET_CHOICE && resultCode == Mastermind.Results.SEND_CHOICE){
    		Bundle result = data.getExtras();
    		thisGame.getActivePeg().markPeg(Choice.getChoiceFromKey(result.getInt(Mastermind.IntentExtras.CHOICE_SELECTED)));
    		
    		try{
    			if(thisGame.isCurrentRowFull()){
    				thisGame.nextRow();
    				thisGame.getCurrentRow().getNextEmptyPeg().getView().requestFocus();
    				
    			}
    			else
    				thisGame.getCurrentRow().getNextEmptyPeg().getView().requestFocus();
    		}catch(Game.GameLoseException e){
    			thisGame.showSolution();
    			this.endGame("Game Over");
    		}catch(Game.GameWinException e){
    			thisGame.showSolution();
    			this.endGame("Contrats! You've won!");
    		}
    	}
    }
    
    /**
     * endGame - 
     * this method shows the AlertDialog that tells the player
     * whether or not they've won the game, and prompts them if they'd
     * like to play another game.  This method then either finishes the Activity
     * or starts a new game.
     * @param s the result of the game: either "Game Over" or "You've Won"
     */
    private void endGame(String s){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(s+"\nWould you like to play again?")
    		.setCancelable(false)
    		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
 
				public void onClick(DialogInterface dialog, int id) {
					PlayGameActivity.this.getGame().newGame();
					dialog.cancel();
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int id) {
					PlayGameActivity.this.finish();
				}
			});
    	AlertDialog alert = builder.create();
    	alert.show();
    }
    

    /**
     * Game Class
     * This Class controls the flow of the Game itself.
     * @author Rich Tufano
     *
     */
    class Game implements Parcelable{
    	/**
    	 * GameLoseException -
    	 * This Exception is thrown when the Game is lost (all rows are used and none match the solution)
    	 * @author Rich Tufano
    	 *
    	 */
    	public class GameLoseException extends Exception{
    		private static final long serialVersionUID = 6944677277032824880L;
    		public GameLoseException(){
    			super("You've ran out of rows");
    		}
    	}
    	/**
    	 * GameWinException -
    	 * This Exception is thrown when the Game is won (Solution has been matched)
    	 * @author Rich Tufano
    	 *
    	 */
    	public class GameWinException extends Exception{
    		private static final long serialVersionUID = -7788145196599832393L;
    		public GameWinException(){
    			super("Congrats! You've won");
    		}
    	}
    	
    	public static final int GAME_PARCEL = 40;
    	/**	Number of Rows in this game **/
    	public static final int NUM_GUESS_ROWS = 8;
    	/** an integer for storing after coming back from a Parcel **/
    	public int[] storage;
    	/** Row array that stores the guesses **/
    	private GuessRow[] board;
    	/** the current row **/
    	private GuessRow currentRow;
    	/** the current row number **/
    	private int rowNum;
    	/** the solution for this game **/
    	private SolutionRow solution;
    	/** the peg currently active **/
    	private Peg active;
    	
    	/**
    	 * Game - 
    	 * Creates a game object tied to the activity which called it.  
    	 * @param parent The activity that created the Game
    	 */
    	public Game(){
    		board = new GuessRow[Game.NUM_GUESS_ROWS];
    		currentRow = null;
    		solution = null;
    		active = null;
    		rowNum=0;
    		this.buildGuessBoard(PlayGameActivity.this);
    		this.buildSolutionRow(PlayGameActivity.this);
    		Toast.makeText(PlayGameActivity.this.getApplicationContext(), "Guess #"+(rowNum+1), Toast.LENGTH_SHORT).show();
    	}
    	
    	/**
    	 * Game - 
    	 * recreates a game from a parcel
    	 * @param in the parcel to create from
    	 */
    	public Game(Parcel in){
    		board = new GuessRow[Game.NUM_GUESS_ROWS];
    		currentRow = null;
    		solution = null;
    		active = null;
    		rowNum = 0;
    		readFromParcel(in);
    		rebuildGameFromParcel(PlayGameActivity.this);

    	}
    	
    	/**
    	 * readFromParcel - 
    	 * this recreates the Game from the parcel by
    	 * rebinding all of that data that's been stored when
    	 * it was parceled.  This data is then stored in the
    	 * storage int[]
    	 * @param in - The parcel to recreate from
    	 */
    	public void readFromParcel(Parcel in){
    		storage = new int[in.dataSize()];
    		for(int i = 0; i<storage.length; i++)
    			storage[i] = in.readInt();
    	}
    	/**
    	 * rebuildGameFromParcel - 
    	 * this method actually rebind all of the Views 
    	 * when the new Game is created from the Parcel, 
    	 * and then re-populates all the data that was stored
    	 * from the parcel to all the rows.  Requires the actvity
    	 * which has called it.
    	 * @param parent the activity which called it.
    	 */
    	public void rebuildGameFromParcel(Activity parent){
    		this.buildGuessBoard(parent);
    		this.buildSolutionRow(parent);
    		int dataPos = 0;
    		//get the current row
    		rowNum = storage[dataPos];
    		dataPos++;
    		//make a int array to store the row vals
    		int[] temp = new int[4];
    		//iterate through the rows
    		for(int i = 0; i<NUM_GUESS_ROWS; i++){
    			//build the guesses
    			for(int j = 0; j<4; j++){
    				try{
    					board[i].getPegAtIndex(j).markPeg(Choice.getChoiceFromKey(storage[dataPos]));
    				}catch(ChoiceNotFoundException e){
    					System.out.println(e.getMessage());
    				}
    				dataPos++;
    			}
    			//build the feedback
    			for(int j = 0; j<4; j++){
    				temp[j]=storage[dataPos];
    				dataPos++;
    			}
    			board[i].getFeedback().returnFromParcel(temp);
    		}
    		//find the active(if any) peg
    		int[] activePos = new int[2];
    		activePos[0] = storage[dataPos];
    		dataPos++;
    		activePos[1] = storage[dataPos];
    		dataPos++;
    		if(activePos[0] == -1 && activePos[1] == -1)
    			active = null;
    		else
    			active = board[activePos[1]].getPegAtIndex(activePos[0]);
    		
    		//build the solution
    		for(int i = 0; i<4; i++){
    			temp[i] = storage[dataPos];
    			dataPos++;
    		}
    		solution.recoverSolutionFromParcel(temp);
    		//destroy the storage parcel
    	}
    	
    	/**
    	 * getBoard - 
    	 * @return the array of GuessRows
    	 */
    	public GuessRow[] getBoard()				{	return board;		}
    	/**
    	 * setBoard -
    	 * @param newBoard the new array of GuessRows
    	 */
    	public void setBoard(GuessRow[] newBoard)	{	board = newBoard;	}
    	/**
    	 * getCurrentRow
    	 * @return the current GuessRow
    	 */
    	public GuessRow	getCurrentRow()				{	return currentRow;	}
    	/**
    	 * setCurrentRow
    	 * @param next the next GuessRow.
    	 */
    	public void setCurrentRow(GuessRow next)	{	currentRow = next;	}
    	/**
    	 * getLastRow
    	 * @return the last row that was active, unless the current row is 0, in which case it returns the currentRow
    	 */
    	public GuessRow getLastRow(){	
    		if(rowNum>0)
    			return board[rowNum-1];
    		else
    			return currentRow;	
    	}
    	/**
    	 * getCurrentRowNum
    	 * @return the number of the current row
    	 */
    	public int getCurrentRowNum()				{	return rowNum;		}
    	/**
    	 * setCurrentRowNum
    	 * @param row the new number of the current row
    	 */
    	public void setCurrentRowNum(int row)		{	rowNum = row;		}
    	/**
    	 * incrCurrentRowNum
    	 * 	increment the current row number
    	 */
    	public void incrCurrentRowNum()				{	rowNum++;			}
    	/**
    	 * getSolution
    	 * @return the SolutionRow foaccess outer class from inner classr this game
    	 */
    	public SolutionRow getSolution()			{	return solution;	}
    	/**
    	 * setSolution
    	 * @param sol the solution for this game
    	 */
    	public void setSolution(SolutionRow sol)	{	solution = sol;		}
    	/**
    	 * getActivePeg
    	 * @return the active peg
    	 */
    	public Peg getActivePeg()					{	return active;		}
    	/**
    	 * setActivePeg
    	 * @param p the new peg to be active
    	 */
    	public void setActivePeg(Peg p)				{	active = p;			}
    	/**
    	 * buildGuessBoard-
    	 * Builds the guess board for this game by binding finding the ViewGroup that corresponds to the 
    	 * gameboard, iterating through it's children to bind them to the GuessRows, and then binding the 
    	 * SolutionRow to that ViewGroup.  
    	 * This method also sets the current row, locks the inactive rows.
    	 * @param parent - the activity from which this Game was called
    	 */
        private void buildGuessBoard(Activity parent){
        	ViewGroup table = (ViewGroup)parent.findViewById(R.id.game_board);
        	for(int i = 0; i<NUM_GUESS_ROWS; i++){
        		board[i] = new GuessRow((ViewGroup)table.getChildAt(i));
        		board[i].lockRow();
        	}
        	currentRow = board[rowNum];
        	currentRow.unlockRow();
        }
        
        /**
         * rebuildGuessBoard
         * rebuilds the Guess board.  Just like the BuildGuessBoard method, except
         * no new object are created.  The Views are just rebound to their objects.
         * @param parent - the Activity that contains this Game
         */
        public void rebuildGuessBoard(Activity parent){
        	ViewGroup table = (ViewGroup)parent.findViewById(R.id.game_board);
        	for(int i = 0; i<NUM_GUESS_ROWS; i++){
        		board[i].registerNewRowView((ViewGroup)table.getChildAt(i));
        		board[i].lockRow();
        	}
        	currentRow = board[rowNum];
        	currentRow.unlockRow();
        	//don't need to reset active
        	solution.registerNewRowView((ViewGroup)parent.findViewById(R.id.game_solution));
        	
        }
        
        /**
         * buildSolution - 
         * builds the solution for this Game, and binds it to the ViewGroup in the main View.
         * @param parent the Activity which contains this Game
         */
        private void buildSolutionRow(Activity parent){
        	ViewGroup t = ViewGroup.class.cast(parent.findViewById(R.id.game_solution));
        	solution = new SolutionRow(t);
        }
        
        /**
         * catchSelectedPeg - 
         * stores the peg that's being currently selected.
         * @param v - the view of the peg being selected
         */
        public void catchSelectedPeg(View v){
        	active = currentRow.findPegByView(v);
        }
        
        /**
         * nextRow - 
         * This method is called once a GuessRow is full.  
         * 	It 	locks the currentRow,
         * 		makes the feedback for the currentRow
         * 		check is the game was won
         * 			if it has been won, throw a GameWinException
         * 		increments the row number
         * 		checks if there are more rows (if the game is not over)
         * 			if it is over, throw a GameOverException
         * 		sets the currentRow to the next row
         * 		unlocks the new currentRow
         * 
         * @throws GameWinException - thrown if the game is won
         * @throws GameLoseException - thrown if the game is lost
         */
        public void nextRow() throws GameWinException, GameLoseException{
        	currentRow.lockRow();
        	currentRow.setFeedback(this.compareRowToSolution());
        	if(!solution.hasWon()){
        		rowNum++;
        		if(rowNum<Game.NUM_GUESS_ROWS){
        			currentRow = board[rowNum];
        			currentRow.unlockRow();
        			Toast.makeText(PlayGameActivity.this.getApplicationContext(), "Guess #"+(rowNum+1), Toast.LENGTH_SHORT).show();
        			//currentRow.matchPegs(solution.getPegs());
           		}
        		else
        			throw new GameLoseException();
        	}
        	else
        		throw new GameWinException();
        }
        
        /**
         * isCurrentRowFull()
         * @return true if the current row is full
         */
        public boolean isCurrentRowFull(){
        	return currentRow.isFull();
        }
        
        /**access outer class from inner class
         * newGame - 
         * clears all the GuessRows and locks them, resets the rowNum, sets the 
         * currentRow to the first row, unlocks the currentRow, and builds a 
         * new solution
         */
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
        
        /**
         * showSolution - 
         * calls the SolutionRow's showSolution method.  This method is called
         * once a game is over.
         */
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
    				if(currentRow.getPegAtIndex(i).getChoice().equals(temp[i])){
    					//This is the Right Peg at Right Spot
    					result[i] = Choice.BLACK;
    					solution.markPeg(i, temp[i]);
    				}
    				else
    					//this is just the right Peg
    					result[i] = Choice.WHITE;
    			else
    				result[i] = Choice.EMPTY;
    		}
    		return result;
    	}
    	
    	/**
    	 * writeToParcel - 
    	 * this method writes all the data for the game (as ints) to the passed Parcel.
    	 * Very messy.  Ints must be read back out in order
    	 */
    	public void writeToParcel(Parcel out, int flags){
    		int activePos[] = new int[2];
    		activePos[0] = -1;
    		activePos[1] = -1;
    		out.writeInt(rowNum);
    		for(int i = 0; i<NUM_GUESS_ROWS; i++){
    		
    			if(active != null && board[i].findPegIndex(active)!=-1){
    				activePos[0] = board[i].findPegIndex(active);
    				activePos[1] = i;
    			}
    		
    			out.writeIntArray(board[i].getSelectedChoiceKeys());
    			if(board[i].getFeedback()!=null)
    				out.writeIntArray(board[i].getFeedback().getChoicesForParcel());
    			else
    				for(int j=0; j<4; j++)
    					out.writeInt(-1);
    			
        	}
    		out.writeIntArray(activePos);
    		out.writeIntArray(solution.getSolutionForParcel());
    	}
    	/*
    	 * 
    	 * (non-Javadoc)
    	 * @see android.os.Parcelable#describeContents()
    	 */
    	@Override
    	public int describeContents() {
    		return 0;
    	}
    	public final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
    		public Game createFromParcel(Parcel in){
    			return new Game(in);
    		}
    		public Game[] newArray(int size){
    			return new Game[size];
    		}
    	};
    }

}
