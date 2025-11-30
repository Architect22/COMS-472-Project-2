package edu.iastate.cs472.proj2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * 
 * @author Benjamin Brown
 *
 */
public class CheckersGame implements Game{

	@Override
	public int getPlayer(GameState state) {
		return state.getCurrentPlayer();
	}

	@Override
	public List<CheckersMove> getActions(GameState state) {
		int player = state.getCurrentPlayer();
		CheckersMove[] actions = state.getCheckersData().getLegalMoves(player);
		return actions == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(actions));
	}

	@Override
	public GameState getResult(GameState state, CheckersMove action) {
		CheckersData simulatedBoard = new CheckersData(state.getCheckersData());
		simulatedBoard.makeMove(action);
		int nextPlayer = state.getCurrentPlayer() == CheckersData.RED ? CheckersData.BLACK : CheckersData.RED;
		return new GameState(simulatedBoard, nextPlayer);
	}

	@Override
	public boolean isTerminal(GameState state) {
		int player = state.getCurrentPlayer();
		CheckersMove[] moves = state.getCheckersData().getLegalMoves(player);
		return (moves == null || moves.length == 0);
	}

	@Override
	public double getUtility(GameState state, int player) {
    	int redPieceCount = 0;
    	int blackPieceCount = 0;
    	for(int row = 0; row < 8; ++row) {
    		for(int col = 0; col < 8; ++col) {
    			if (state.getCheckersData().existsRedPlayerPiece(row, col)) {
    				redPieceCount++;
    			}
    			else if (state.getCheckersData().existsBlackPlayerPiece(row, col)) {
    				blackPieceCount++;
    			}
    		}
    	}
    	
    	int myPieceCount = (player == CheckersData.RED) ? redPieceCount : blackPieceCount;
    	int otherPieceCount = (player == CheckersData.BLACK) ? redPieceCount : blackPieceCount;
    	return Integer.compare(myPieceCount, otherPieceCount);
    }

}
