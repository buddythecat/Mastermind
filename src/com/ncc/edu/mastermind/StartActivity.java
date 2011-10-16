package com.ncc.edu.mastermind;

import com.ncc.edu.project2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * StartActivity
 * This is the Activity that will show when the App starts.
 * Very simple screen, 3 buttons.  This will fire off either the PlayGameActivity
 * or the AboutGameActivity, or will finish the application.
 * @author Rich Tufano
 *
 */
public class StartActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    /**
     * startGame - 
     * Fire the PlayGameActivity
     * @param v the button clicked
     */
    public void startGame(View v){
    	this.startActivity(new Intent(this, PlayGameActivity.class));
    }
    
    /**
     * aboutGame - 
     * Shoot off the AboutGameActivity
     * @param v the button clicked
     */
    public void aboutGame(View v){
    	this.startActivity(new Intent(this, AboutGameActivity.class));
    }
    
    /**
     * exitGame - 
     * finish the application
     * @param v the button clicked
     */
    public void exitGame(View v){
    	this.finish();
    }
}