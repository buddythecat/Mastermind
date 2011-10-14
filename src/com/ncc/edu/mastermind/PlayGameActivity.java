package com.ncc.edu.mastermind;

import com.ncc.edu.mastermind.game.Choice;
import com.ncc.edu.project2.R;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PlayGameActivity extends Activity {
	private Game thisGame;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board);
        thisGame = new Game(this);
    }
    
    protected Game getGame()			{	return thisGame;	}
    
    
    public void launchChooser(View v){
    	Intent chooserIntent = new Intent(this, ChooserActivity.class);
    	//set the active peg to clicked view
    	
    	thisGame.catchSelectedPeg(v);
    	//pull the used choices
    	this.startActivityForResult(
    			chooserIntent, 
    			Mastermind.Results.GET_CHOICE);
    }
    
    public void onSaveInstanceState(Bundle savedInstanceState){
    	savedInstanceState.putParcelable("Game", thisGame);
    	super.onSaveInstanceState(savedInstanceState);
    }
    
    public void onRestoreInstanceState(Bundle savedInstanceState){
    	super.onRestoreInstanceState(savedInstanceState);
    	
    	thisGame = Game.class.cast(savedInstanceState.getParcelable("Game"));
    	//thisGame.rebuildGameFromParcel(this);
    	thisGame.rebuildGuessBoard(this);
    }
    
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
}
