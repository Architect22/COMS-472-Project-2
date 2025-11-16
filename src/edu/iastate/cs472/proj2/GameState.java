package edu.iastate.cs472.proj2;


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
        return "Current Player: " + (currentPlayer == CheckersData.RED ? "RED" : "BLACK")
        		+ "\n" +
        		checkersData.toString() + "\n";
   }
}
