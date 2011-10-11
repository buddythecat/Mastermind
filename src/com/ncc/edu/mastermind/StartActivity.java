package com.ncc.edu.mastermind;

import com.ncc.edu.project2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void startGame(View v){
    	this.startActivity(new Intent(this, PlayGameActivity.class));
    }
    
    public void aboutGame(View v){
    	this.startActivity(new Intent(this, AboutGameActivity.class));
    }
    
    public void exitGame(View v){
    	this.finish();
    }
}