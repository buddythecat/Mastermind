package com.ncc.edu.mastermind;

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
	private ImageButton tempButton;
	private int tempSelectedPos;
	private int guessRow;
	private Choice[][] board = new Choice[8][4];
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board);
        this.setUpBoard();
        guessRow = 0;
    }
    
    
    public void setUpBoard(){
    	TableLayout table = (TableLayout)this.findViewById(R.id.game_board);
    	for(int i = 1; i<8; i++){
    		TableRow tempRow = (TableRow)table.getChildAt(i);
    		for(int j = 1; j<5; j++){
    			tempRow.getChildAt(j).setEnabled(false);
    		}
    	}
    	this.buildBoard();
    }
    
    public void buildBoard(){
    	for(int i = 0; i<8; i++)
    		for(int j=0; j<4; j++)
    			board[i][j] = Choice.EMPTY;
    }
    
    public TableRow getRow(int num){
    	TableLayout table = (TableLayout)this.findViewById(R.id.game_board);
    	return((TableRow)table.getChildAt(num++));
    }
    
    public void lockRow(int num){
    	TableRow tempRow = this.getRow(num);
    	for(int j = 1; j<5; j++)
    		tempRow.getChildAt(j).setClickable(false);
    }
    public void unlockRow(int num){
    	TableRow tempRow = this.getRow(num);
    	for(int j = 1; j<5; j++)
    		tempRow.getChildAt(j).setEnabled(true);
    }
    
    public void launchChooser(View v){
    	tempButton = (ImageButton)v;
    	Intent chooserIntent = new Intent(this, ChooserActivity.class);
    	this.startActivityForResult(chooserIntent, MastermindApplication.GET_CHOICE);
    	
    	TableRow temp = this.getRow(guessRow);
    	int i = 1;
    	while( !((ImageButton)temp.getChildAt(i) ).equals(tempButton) )
    		i++;
    	tempSelectedPos = --i;
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	if(requestCode == MastermindApplication.GET_CHOICE && resultCode == MastermindApplication.SEND_CHOICE){
    		Bundle result = data.getExtras();
    		Choice selected = Choice.getChoiceFromKey(result.getInt("choiceIndex"));
    		if(this.checkRowForDuplicate(guessRow, selected))
    			Toast.makeText(this, "Duplicate Selection, try again", Toast.LENGTH_SHORT).show();
    		else{
    			tempButton.setImageResource(result.getInt("resID"));
    			board[guessRow][tempSelectedPos] = selected;
    		}
    		if(this.rowFull(guessRow)){
    			this.lockRow(guessRow);
    			guessRow++;
    			this.unlockRow(guessRow);
    		}
    			
    	}
    }
    
    private boolean checkRowForDuplicate(int row, Choice c){
    	boolean duplicate = false;
    	for(int i=0; i<4; i++){
    		if(board[guessRow][i].equals(c))
    			duplicate = true;
    	}
    	return duplicate;
    }
    
    private boolean rowFull(int row){
    	for(int i=0; i<4; i++){
    		if(board[guessRow][i].equals(Choice.EMPTY))
    			return false;
    	}
    	return true;
	}
}
