package com.ncc.edu.mastermind;

import android.app.Application;

public class MastermindApplication extends Application {
	public static final int GET_CHOICE = 10;
	public static final int SEND_CHOICE = 11;
	public static enum Choice {RED, GREEN, YELLOW, BLUE, WHITE, BLACK}
}
