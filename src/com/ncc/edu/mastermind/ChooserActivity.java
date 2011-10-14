package com.ncc.edu.mastermind;

import com.ncc.edu.mastermind.game.Choice;

import com.ncc.edu.project2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * ChooserActivity - 
 * This subclass of Activity is used to poll the user for the
 * peg to place at the selected position.
 * @author Rich "Dances With Caterpillars" TUfano
 *
 */
public class ChooserActivity extends Activity {
	
    private Bundle result;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	result = new Bundle();
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.game_choose);
    }
    
    /**
     * pickChoice - 
     * pickChoice is bound to the row buttons in the XML for the chooser layout.
     * It handles receiving the choice from the user, adding it to a bundle,
     * and passing that back to the parent activity.  
     * @param v - the View which has been clicked
     */
    public void pickChoice(View v){
    	Choice selected = Choice.EMPTY;
    	switch(v.getId()){
    	case R.id.button_choose_white:
    		selected = Choice.WHITE;
    		break;
    	case R.id.button_choose_black:
    		selected = Choice.BLACK;
    		break;
    	case R.id.button_choose_blue:
    		selected = Choice.BLUE;
    		break;
    	case R.id.button_choose_green:
    		selected = Choice.GREEN;
    		break;
    	case R.id.button_choose_yellow:
    		selected = Choice.YELLOW;
    		break;
    	case R.id.button_choose_red:
    		selected = Choice.RED;
    		break;
    	}
    	
    	result.putInt(Mastermind.IntentExtras.CHOICE_SELECTED, selected.getKey());
    	
    	Intent i = new Intent();
    	i.putExtras(result);
    	
    	this.setResult(Mastermind.Results.SEND_CHOICE, i);
    	
    	this.finish();
    }
}