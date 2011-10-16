package com.ncc.edu.mastermind;

import com.ncc.edu.project2.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
/**
 * AboutGameActivity
 * This is a simple Activity that displays an about box
 * with an OK button.
 * @author Rich Tufano
 */
public class AboutGameActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_game);
    }
    public void done(View v){
    	this.finish();
    }
}