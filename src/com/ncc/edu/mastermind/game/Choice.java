package com.ncc.edu.mastermind.game;

import com.ncc.edu.project2.R;

/**
 * Choice -
 * Choice is an enumeration that is built to store the state of a peg.  
 * In Mastermind, the possibilities for a Peg's state are:
 * 	Black, Blue, Green, Red, White, Yellow, and Empty.
 * The Choice enumeration includes 
 * @author Rich Tufano
 *
 */
public enum Choice{
	BLACK(R.drawable.black, "Black", 0),
	BLUE(R.drawable.blue, "Blue", 1),
	GREEN(R.drawable.green, "Green", 2),
	RED(R.drawable.red, "Red", 3),
	WHITE(R.drawable.white, "White", 4),
	YELLOW(R.drawable.yellow, "Yellow", 5),
	EMPTY(R.drawable.empty, "Empty", 6);
	
	private int resId;	//The ResourceID of this choice
	private int key;	//The Key of this choice
	private String name;	//The name of this choice, in a String
	
	/**
	 * Choice - Enumerated Constructor
	 * Creates a new Choice Enumeration with the specified ID, name and key
	 * @param id - the id of the drawable that is tied with this choice
	 * @param choiceName - the name of this choice 
	 * @param k - the key of this choice in the array of Choice.values
	 */
	private Choice(int id, String choiceName, int k){
		resId = id;
		name = choiceName;
		key = k;
	}
	/**
	 * getId -
	 * returns the drawable ID of this Choice
	 * @return the ID of this Choice's drawable
	 */
	public int getId(){return resId;}
	/**
	 * getName - 
	 * returns the name of this Choice
	 * @return the string of the name of this choice
	 */
	public String getName(){return name;}
	/**
	 * getKey - 
	 * returns this Choice's key in the array of values.
	 * @return the choice's key in the array of values.
	 */
	public int getKey(){return key;}
	
	/**
	 * getChoiceeFromName
	 * Returns the specified Choice from a string that's been passed in.  This is used, mostly
	 * for binding choices based on the tag of the imageView's it's being bound to.
	 * If s does not correspond to a Choice, this method throws a ChoiceNotFoundException.
	 * @param s - the name of the Choice to return, in a String
	 * @return the Choice with name s
	 */
	public static Choice getChoiceFromName(String s) throws ChoiceNotFoundException{
		Choice[] values = Choice.values();
		for(int i=0; i<values.length; i++){
			if(values[i].getName().equals(s))
				return values[i];
		}
		throw new ChoiceNotFoundException();
	}
	
	/**
	 * getChoiceFromKey
	 * Returns the Choice found at the key at index i. 
	 * @param i the index of the Choice to retrieve
	 * @return the choice at spot i in the array of Choice.values
	 */
	public static Choice getChoiceFromKey(int i) throws ChoiceNotFoundException{
		
		if(i<6)
			return Choice.values()[i];
		
		throw new ChoiceNotFoundException();
	}
}
