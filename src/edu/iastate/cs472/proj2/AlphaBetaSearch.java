package edu.iastate.cs472.proj2;

import java.util.ArrayList;

/**
 * 
 * @author Benjamin Brown
 *
 */


/**
 * This class implements the Alpha-Beta pruning algorithm to find the best 
 * move at current state.
*/
public class AlphaBetaSearch extends AdversarialSearch{
	private Game game;
	private Metrics metrics = new Metrics();
	public final static String METRICS_NODES_EXPANDED = "nodesExpanded";
	
	private ArrayList<CheckersMove> blackPreviousMove = new ArrayList<>();
	private ArrayList<CheckersMove> redPreviousMove = new ArrayList<>();;
	private int blackMoveRepeatCount = 0;
	private int redMoveRepeatCount = 0;
	private boolean draw = false;

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
        return makeDecision(new GameState(boardCopy, CheckersData.BLACK));
    }

    public CheckersMove makeDecision(GameState state) {
        metrics = new Metrics();
        CheckersMove result = null;
        double resultValue = Double.NEGATIVE_INFINITY;
        int player = state.getCurrentPlayer();
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
        if (game.isTerminal(state) || draw)
            return game.getUtility(state, player);
        double value = Double.NEGATIVE_INFINITY;
        for (CheckersMove action : game.getActions(state)) {
        	GameState result = game.getResult(state, action);
        	calculateDraw(action, player);
            value = Math.max(value, minValue(result, result.getCurrentPlayer(), alpha, beta));
            if (value >= beta)
                return value;
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    public double minValue(GameState state, int player, double alpha, double beta) {
        metrics.incrementInt(METRICS_NODES_EXPANDED);
        if (game.isTerminal(state) || draw)
            return game.getUtility(state, player);
        double value = Double.POSITIVE_INFINITY;
        for (CheckersMove action : game.getActions(state)) {
        	GameState result = game.getResult(state, action);
        	calculateDraw(action, player);
            value = Math.min(value, maxValue(result, result.getCurrentPlayer(), alpha, beta));
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
    
    private boolean calculateDraw(CheckersMove result, int player) {
        // if you haven't seen this move yet, add it to a list
        if(player == CheckersData.BLACK && !blackPreviousMove.contains(result)) {
        	blackPreviousMove.add(result);
        	if(blackPreviousMove.size() > 3) {
        		// if you list is bigger than 2 moves, remove a move
        		blackPreviousMove.remove(0);
        	}
        }
        else if(player == CheckersData.BLACK && blackPreviousMove.contains(result)) {
        	// if you are cycling the same moves, increase the repeat count variable
        	blackMoveRepeatCount++;
        }
        if(player == CheckersData.RED && !redPreviousMove.contains(result)) {
        	redPreviousMove.add(result);
        	if(redPreviousMove.size() > 3) {
        		redPreviousMove.remove(0);
        	}
        }
        else if(player == CheckersData.RED && redPreviousMove.contains(result)) {
        	redMoveRepeatCount++;
        }
        
        if(redMoveRepeatCount >= 3 || blackMoveRepeatCount >= 3) {
        	draw = true;
        }
        return draw;
    }
}
