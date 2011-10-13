package com.ncc.edu.mastermind;

import com.ncc.edu.mastermind.game.Choice;
import com.ncc.edu.mastermind.game.Peg;
import com.ncc.edu.mastermind.game.GuessRow;
import com.ncc.edu.mastermind.game.SolutionRow;
import com.ncc.edu.project2.R;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

public class PlayGameActivity extends Activity {
	private GuessRow[] board;
	private GuessRow currentRow;
	private int rowNum;
	private SolutionRow solution;
	private Peg active;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board);
        this.setUpBoard();
        currentRow.unlockRow();
    }
    
    
    public void setUpBoard(){
    	board = new GuessRow[8];
    	TableLayout table = (TableLayout)this.findViewById(R.id.game_board);
    	for(int i = 0; i<8; i++){
    		board[i] = new GuessRow((TableRow)table.getChildAt(i));
    		board[i].lockRow();
    	}
    	rowNum = 0;
    	currentRow = board[rowNum];
    	solution = new SolutionRow((TableRow)this.findViewById(R.id.game_solution));
    }
    
    public void launchChooser(View v){
    	Intent chooserIntent = new Intent(this, ChooserActivity.class);
    	//set the active peg to clicked view
    	active = currentRow.findPegByView(v);
    	//pull the used choices
    	chooserIntent.putExtra(Mastermind.IntentExtras.CHOICES_USED, currentRow.getSelectedChoiceKeys());
    	this.startActivityForResult(chooserIntent, Mastermind.Results.GET_CHOICE);
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	if(requestCode == Mastermind.Results.GET_CHOICE && resultCode == Mastermind.Results.SEND_CHOICE){
    		Bundle result = data.getExtras();
    		active.markPeg(Choice.getChoiceFromKey(result.getInt(Mastermind.IntentExtras.CHOICE_SELECTED)));
    		if(currentRow.isFull())
    			this.nextRow();
    	}
    }
    private void nextRow(){
    	//check last row against the solution
    	currentRow.lockRow();
    	solution.compareToRow(currentRow);
    	if(!solution.hasWon()){
    		if(rowNum<8){
    			rowNum++;
    			currentRow = board[rowNum];
    			currentRow.unlockRow();
    			currentRow.matchPegs(solution.getPegs());
    		}
    		else
    			this.lostGame();
    	}
    	else
    		this.wonGame();
    }
    
    private void lostGame(){
    	
    }
    
    private void wonGame(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Congratulations, You've Won\nWould you like to play again?")
    		.setCancelable(false)
    		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    			
				@Override
				public void onClick(DialogInterface dialog, int id) {
					PlayGameActivity.this.newGame();
					dialog.cancel();
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int id) {
					PlayGameActivity.this.finish();
				}
			});
    	AlertDialog alert = builder.create();
    	alert.show();
    }
    
    private void newGame(){
		for(int i = 0; i<8; i++){
			board[i].clearRow();
			board[i].lockRow();
		}
		rowNum = 0;
		currentRow = board[rowNum];
		currentRow.unlockRow();
		solution.newSolution();
    }
}
