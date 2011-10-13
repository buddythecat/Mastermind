package com.ncc.edu.mastermind;

import android.app.Application;

public class Mastermind extends Application {
	public static final class IntentExtras{
		public static final String CHOICES_USED = "choicesUsed";
		public static final String CHOICE_SELECTED = "choiceSelected";
	}
	public static final class Results{
		public static final int GET_CHOICE = 10;
		public static final int SEND_CHOICE = 11;
	}
	
	
}
