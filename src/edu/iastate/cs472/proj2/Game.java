package edu.iastate.cs472.proj2;


import java.util.List;


public interface Game {

    int getPlayer(CheckersData state);

    List<CheckersMove> getActions(CheckersData state);

    CheckersData getResult(CheckersData state, CheckersMove action);

    boolean isTerminal(CheckersData state);

    double getUtility(CheckersData state, int player);
}

