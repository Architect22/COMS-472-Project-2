package edu.iastate.cs472.proj2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameState {
	private CheckersData checkersData;
	private int currentPlayer;
	
	public GameState(CheckersData checkersData, int currentPlayer) {
		this.checkersData = checkersData;
		this.currentPlayer = currentPlayer;
	}
	
	public CheckersData getCheckersData() {
		return checkersData;
	}
	
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) {
    		return true;
    	}
    	if (obj instanceof GameState gs) {
    		return currentPlayer == gs.currentPlayer &&
    				checkersData.equals(gs.checkersData);
    	}
        return false;
    }
    
    @Override
    public String toString() {
    	Pattern pattern = Pattern.compile("\\u001B\\[[0-9;]*m");
    	Matcher matcher = pattern.matcher(checkersData.toString());
    	String checkersDataText = matcher.replaceAll("");        
    	        
        return "\nCurrent Player: " + (currentPlayer == CheckersData.RED ? "RED" : "BLACK")
        		+ "\n" + checkersDataText + "\n";
   }
}
