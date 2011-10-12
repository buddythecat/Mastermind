package com.ncc.edu.mastermind.game;


import java.io.Serializable;

import com.ncc.edu.project2.R;

public enum Choice implements Serializable{
	BLACK(R.drawable.black, "Black", 0),
	BLUE(R.drawable.blue, "Blue", 1),
	GREEN(R.drawable.green, "Green", 2),
	RED(R.drawable.red, "Red", 3),
	WHITE(R.drawable.white, "White", 4),
	YELLOW(R.drawable.yellow, "Yellow", 5),
	EMPTY(R.drawable.empty, "Unchosen", 6);
	
	private int resId;
	private int key;
	private String name;
	
	private Choice(int id, String choiceName, int k){
		resId = id;
		name = choiceName;
		key = k;
	}
	
	public int getId(){return resId;}
	public String getName(){return name;}
	public int getKey(){return key;}
	
	public static Choice getChoiceFromKey(int i){
		Choice c;
		switch(i){
		case 0:
			c = Choice.BLACK;
			break;
		case 1:
			c = Choice.BLUE;
			break;
		case 2:
			c = Choice.GREEN;
			break;
		case 3:
			c = Choice.RED;
			break;
		case 4:
			c = Choice.WHITE;
			break;
		case 5:
			c = Choice.YELLOW;
			break;
		default:
			c = Choice.EMPTY;
			break;
		}
		return c;
	}
}
