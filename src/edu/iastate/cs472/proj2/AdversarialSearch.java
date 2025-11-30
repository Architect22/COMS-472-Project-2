package edu.iastate.cs472.proj2;

/**
 * 
 * @author Benjamin Brown
 *
 */

/**
 * This class is to be extended by the classes AlphaBetaSearch and MonteCarloTreeSearch.
 */
public abstract class AdversarialSearch{
    protected CheckersData board;

    // An instance of this class will be created in the Checkers.Board
    // It would be better to keep the default constructor.

    protected void setCheckersData(CheckersData board) {
        this.board = board;
    }
    
    /** 
     * takes a state as input and returns a list of legal moves at the state. The load of
	 * implementation can be shifted to the method getLegalMoves() within CheckersData,)
	 * 
     * @return an array of valid moves
     */
    protected CheckersMove[] legalMoves(CheckersData state, int player) {
    	return state.getLegalMoves(player); 
    }
    
    protected int calculateHeuristic(CheckersData state, int player) {
    	return 0;
    }
    protected Metrics getMetrics() {
    	return null;
    }
	
    /**
     * Return a move returned from either the alpha-beta search or the Monte Carlo tree search.
     * 
     * @param legalMoves
     * @return CheckersMove 
     */
    public abstract CheckersMove makeMove(CheckersMove[] legalMoves);
}
