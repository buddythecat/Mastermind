package com.ncc.edu.mastermind;

import android.app.Application;

/**
 * The Mastermind Class
 * This class is used to store final/static constants used in the application.
 * @author Rich Tufano
 *
 */
public class Mastermind extends Application {
	/**
	 * Constants for Intents
	 * @author Rich Tufano
	 */
	public static final class IntentExtras{
		/** For the request for the choice */
		public static final String CHOICES_USED = "choicesUsed";
		/** For the result of the result*/
		public static final String CHOICE_SELECTED = "choiceSelected";
	}
	/**
	 * Constants for Results
	 * @author Rich Tufano
	 */
	public static final class Results{
		/** For the start */
		public static final int GET_CHOICE = 10;
		/** For the finish */
		public static final int SEND_CHOICE = 11;
	}
	
	
}
