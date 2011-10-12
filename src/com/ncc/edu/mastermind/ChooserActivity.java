package com.ncc.edu.mastermind;

import com.ncc.edu.mastermind.game.*;

import com.ncc.edu.mastermind.game.Choice;
import com.ncc.edu.project2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChooserActivity extends Activity {
    private Bundle result;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	result = new Bundle();
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.game_choose);
    }
    
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
    	result.putInt("resID", selected.getId());
    	result.putInt("choiceIndex", selected.getKey());
    	Intent i = new Intent();
    	i.putExtras(result);
    	this.setResult(MastermindApplication.SEND_CHOICE, i);
    	this.finish();
    }
}