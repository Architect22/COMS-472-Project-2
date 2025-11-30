package edu.iastate.cs472.proj2;


import java.util.List;
/**
 * 
 * @author Benjamin Brown
 *
 */

public interface Game {

    int getPlayer(GameState state);

    List<CheckersMove> getActions(GameState state);

    GameState getResult(GameState state, CheckersMove action);

    boolean isTerminal(GameState state);

	double getUtility(GameState state, int player);
}

