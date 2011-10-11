package com.ncc.edu.mastermind;

import com.ncc.edu.project2.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

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