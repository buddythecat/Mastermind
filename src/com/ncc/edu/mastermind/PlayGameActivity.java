package com.ncc.edu.mastermind;

import com.ncc.edu.mastermind.game.Choice;
import com.ncc.edu.mastermind.game.Peg;
import com.ncc.edu.mastermind.game.Row;
import com.ncc.edu.project2.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class PlayGameActivity extends Activity {
	private Row[] board;
	private int guessRow;
	private Peg active;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board);
        this.setUpBoard();
        board[guessRow].unlockRow();
    }
    
    
    public void setUpBoard(){
    	board	 = new Row[8];
    	TableLayout table = (TableLayout)this.findViewById(R.id.game_board);
    	for(int i = 0; i<8; i++){
    		board[i] = new Row((TableRow)table.getChildAt(i));
    		board[i].lockRow();
    	}
    }
    
    public void launchChooser(View v){
    	Intent chooserIntent = new Intent(this, ChooserActivity.class);
    	active = board[guessRow].findPegByView(v);	
    	this.startActivityForResult(chooserIntent, MastermindApplication.GET_CHOICE);
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	if(requestCode == MastermindApplication.GET_CHOICE && resultCode == MastermindApplication.SEND_CHOICE){
    		Bundle result = data.getExtras();
    		active.markPeg(Choice.getChoiceFromKey(result.getInt("choiceIndex")));
    	}
    }
}
