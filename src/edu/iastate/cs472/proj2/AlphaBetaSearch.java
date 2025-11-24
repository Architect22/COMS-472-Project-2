package edu.iastate.cs472.proj2;

/**
 * 
 * @author Benjamin Brown
 *
 */


/**
 * This class implements the Alpha-Beta pruning algorithm to find the best 
 * move at current state.
*/
public class AlphaBetaSearch extends AdversarialSearch {
	private Game game;
	 private Metrics metrics = new Metrics();
	 public final static String METRICS_NODES_EXPANDED = "nodesExpanded";
    /**
     * Creates a new search object for a given game.
     */
    public static AlphaBetaSearch createFor(Game game) {
        return new AlphaBetaSearch(game);
    }

    public AlphaBetaSearch(Game game) {
        this.game = game;
    }
    /**
     * The input parameter legalMoves contains all the possible moves.
     * It contains four integers:  fromRow, fromCol, toRow, toCol
     * which represents a move from (fromRow, fromCol) to (toRow, toCol).
     * It also provides a utility method `isJump` to see whether this
     * move is a jump or a simple move.
     * 
     * Each legalMove in the input now contains a single move
     * or a sequence of jumps: (rows[0], cols[0]) -> (rows[1], cols[1]) ->
     * (rows[2], cols[2]).
     *
     * @param legalMoves All the legal moves for the agent at current step.
     */
    public CheckersMove makeMove(CheckersMove[] legalMoves) {
        // The checker board state can be obtained from this.board,
        // which is a int 2D array. The numbers in the `board` are
        // defined as
        // 0 - empty square,
        // 1 - red man
        // 2 - red king
        // 3 - black man
        // 4 - black king
        System.out.println(board);
        System.out.println();
        
        CheckersData boardCopy = new CheckersData(board);
        return makeDecision(new GameState(boardCopy, CheckersData.BLACK),CheckersData.BLACK);
    }

    public CheckersMove makeDecision(GameState state, int player) {
        metrics = new Metrics();
        CheckersMove result = null;
        double resultValue = Double.NEGATIVE_INFINITY;
        for (CheckersMove action : game.getActions(state)) {
            double value = minValue(game.getResult(state, action), player, resultValue, Double.POSITIVE_INFINITY);
            if (value > resultValue) {
                result = action;
                resultValue = value;
            }
        }
        return result;
    }

    public double maxValue(GameState state, int player, double alpha, double beta) {
        metrics.incrementInt(METRICS_NODES_EXPANDED);
        if (game.isTerminal(state))
            return game.getUtility(state, player);
        double value = Double.NEGATIVE_INFINITY;
        for (CheckersMove action : game.getActions(state)) {
            value = Math.max(value, minValue(game.getResult(state, action), player, alpha, beta));
            if (value >= beta)
                return value;
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    public double minValue(GameState state, int player, double alpha, double beta) {
        metrics.incrementInt(METRICS_NODES_EXPANDED);
        if (game.isTerminal(state))
            return game.getUtility(state, player);
        double value = Double.POSITIVE_INFINITY;
        for (CheckersMove action : game.getActions(state)) {
            value = Math.min(value, maxValue(game.getResult(state, action), player, alpha, beta));
            if (value <= alpha)
                return value;
            beta = Math.min(beta, value);
        }
        return value;
    }

    @Override
    public Metrics getMetrics() {
        return metrics;
    }

    @Override
    protected int calculateHeuristic(CheckersData state, int player) {
    	int pieceCount = 0;
    	for(int row = 0; row < 8; ++row) {
    		for(int col = 0; col < 8; ++col) {
    			if(player == CheckersData.RED && state.existsRedPlayerPiece(row, col)) {
    				pieceCount++;
    			}
    			else if(player == CheckersData.BLACK && state.existsBlackPlayerPiece(row, col)) {
    				pieceCount++;
    			}
    		}
    	}
    	return pieceCount;
    }
    
//    In addition, the class AlphaBetaSearch needs to implement an evaluation function that takes a
//    state of the game as input and returns a value. The utility function for terminal states has the
//    following values:
//    • 1 if a win by the agent,
//    • −1 if a loss by the agent,
//    • 0 if a draw.
//    The score on a leaf that is not a terminal node is generated according to a heuristic of yours. 
    private int evaluate(CheckersData state) {
    	
    	return 0;
    }
    
    

}
