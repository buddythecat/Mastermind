package com.ncc.edu.mastermind;
import com.ncc.edu.mastermind.game.Choice;
import com.ncc.edu.mastermind.game.ChooserRow;
import com.ncc.edu.mastermind.game.Row;

import com.ncc.edu.project2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class ChooserActivity extends Activity {
    private Bundle result;
    private Row cRow;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	result = new Bundle();
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.game_choose);
        cRow = new ChooserRow(ViewGroup.class.cast(this.findViewById(R.id.group_choose)));
        this.lockUsedPegs(this.getIntent().getIntArrayExtra(Mastermind.IntentExtras.CHOICES_USED));
    }
    
    private void lockUsedPegs(int[] used){
    	for(int i = 0; i<used.length; i++){
    		if(used[i]!=-1){
    			cRow.findPegByChoice(Choice.getChoiceFromKey(used[i])).getView().setEnabled(false);
    		}
    	}
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
    	result.putInt(Mastermind.IntentExtras.CHOICE_SELECTED, selected.getKey());
    	
    	Intent i = new Intent();
    	i.putExtras(result);
    	
    	this.setResult(Mastermind.Results.SEND_CHOICE, i);
    	
    	this.finish();
    }
}