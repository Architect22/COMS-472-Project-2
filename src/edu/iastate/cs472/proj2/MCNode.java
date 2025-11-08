package edu.iastate.cs472.proj2;

import java.util.ArrayList;
import java.util.List;

/**
 * Node type for the Monte Carlo search tree.
 */
public class MCNode<E>
{
	private MCNode parent = null; // a pointer to this node's parent
	private boolean isTerminal = true; // is this a terminal node
	private CheckersData gameState = null;
	private int playouts = 0; // the number of children on this node
	public List<MCNode<E>> children = new ArrayList<MCNode<E>>();
	public boolean visited = false;
	
	public MCNode(CheckersData gameState, MCNode parent, boolean isTerminal) {
		this.gameState = gameState;
		this.parent = parent;
		this.isTerminal = isTerminal;
	}
	public MCNode(CheckersData gameState) {
		this.gameState = gameState;
		this.parent = null;
		this.isTerminal = true;
	}
	public CheckersData getGameState() {
		return gameState;
	}
	public void setGameState(CheckersData gameState) {
		this.gameState = gameState;
	}
	public MCNode getParent() {
		return parent;
	}
	public void setParent(MCNode parent) {
		this.parent = parent;
	}
	public boolean isTerminal() {
		return isTerminal;
	}
	public void setTerminal(boolean isTerminal) {
		this.isTerminal = isTerminal;
	}
	public int getPlayouts() {
		return playouts;
	}
	public void setPlayouts(int count) {
		playouts = count;
	}
}

