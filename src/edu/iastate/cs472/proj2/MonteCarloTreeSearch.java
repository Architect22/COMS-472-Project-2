package edu.iastate.cs472.proj2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Benjamin Brown
 *
 */

/**
 * This class implements the Monte Carlo tree search method to find the best
 * move at the current state.
 */
public class MonteCarloTreeSearch extends AdversarialSearch {

	private int iterations = 0;
	private MCTree tree = null;
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
        // which is an 2D array of the following integers defined below:
    	// 
        // 0 - empty square,
        // 1 - red man
        // 2 - red king
        // 3 - black man
        // 4 - black king
        System.out.println(board);
        System.out.println();

        // TODO 
        
        // Return the move for the current state.
        // Here, we simply return the first legal move for demonstration.
        return legalMoves[0];
    }
    
    // TODO
    // 
    // Implement your helper methods here. They include at least the methods for selection,  
    // expansion, simulation, and back-propagation. 
    // 
    // For representation of the search tree, you are suggested (but limited) to use a 
    // child-sibling tree already implemented in the two classes CSTree and CSNode (which  
    // you may feel free to modify).  If you decide not to use the child-sibling tree, simply 
    // remove these two classes. 
    // 
    
	public CheckersMove makeDecision(MCNode state) {
		// tree <-- NODE(state)
		tree.addRoot(state);
		// while TIME-REMAINING() do
		while (iterations != 0) {
			// leaf <-- SELECT(tree)
			MCNode leaf = select(tree);
			// child <-- EXPAND(leaf)
			MCNode child = expand(leaf);
			// result <-- SIMULATE(child)
			// result = true if player of root node wins
			boolean result = simulate(child);
			// BACKPROPAGATE(result, child)
			backpropagate(result, child);
			// repeat the four steps for set number of iterations
			--iterations;
		}
		// return the move in ACTIONS(state) whose node has highest number of playouts
		return bestAction(tree.getRoot());
	}
    
	private CheckersMove bestAction(MCNode root) {
		MCNode bestChild = tree.getChildWithMaxPlayouts(root);
		for (CheckersMove a : game.getActions(root.getGameState())) {
			S result = game.getResult(root.getGameState(), a);
			if (result.equals(bestChild.getGameState())) return a;
		}
		return null;
	}
    
    /**
     * For the selection step that starts at the root of the search tree, use the upper confidence
	 * bound formula UCB(n) (i.e., UCB1(n) in the textbook on p. 163) and set the constant C
	 * used for balancing exploitation and exploration to its theoretically optimal value root 2
     */
	private MCNode select(MCTree gameTree) {
		MCNode node = gameTree.getRoot();
		while (!game.isTerminal(node.getGameState()) && isNodeFullyExpanded(node)) {
			node = gameTree.getChildWithMaxUCT(node);
		}
		return node;
	}
	
	private boolean isNodeFullyExpanded(MCNode node) {
		List<MCNode> visitedChildren = tree.getVisitedChildren(node);
		for (CheckersMove a : game.getActions(node.getGameState())) {
			S result = game.getResult(node.getGameState(), a);
			if (!visitedChildren.contains(result)) {
				return false;
			}
		}
		return true;
	}
	
	private MCNode expand(MCNode leaf) {
		if (game.isTerminal(leaf.getGameState())) return leaf;
		else {
			MCNode child = randomlySelectUnvisitedChild(leaf);
			return child;
		}
	}
	
    /**
     * During simulation, every state makes a uniformly random choice among all legal moves,
	 * whether for the agent or for its human opponent
     */
	private boolean simulate(MCNode node) {
		while (!game.isTerminal(node.getGameState())) {
			Random rand = new Random();
			CheckersMove a = game.getActions(node.getGameState()).get(rand.nextInt(game.getActions(node.getGameState()).size()));
			S result = game.getResult(node.getGameState(), a);
			NodeFactory nodeFactory = new NodeFactory();
			node = nodeFactory.createNode(result);
		}
		if (game.getUtility(node.getGameState(), game.getPlayer(tree.getRoot().getGameState())) > 0) return true;
		else return false;
	}
	
    /**
     * During back propagation, a draw from the playout causes the numerator of every node,
	 * whether black or white, along the upward path to the root to increase by 0.5.
     */
	private void backpropagate(boolean result, MCNode node) {
		tree.updateStats(result, node);
		if (tree.getParent(node) != null) backpropagate(result, tree.getParent(node));
	}
	
	private MCNode randomlySelectUnvisitedChild(MCNode node) {
		List<MCNode> unvisitedChildren = new ArrayList<>();
		List<MCNode> visitedChildren = tree.getVisitedChildren(node);
		for (CheckersMove a : game.getActions(node.getGameState())) {
			S result = game.getResult(node.getGameState(), a);
			if (!visitedChildren.contains(result)) unvisitedChildren.add(result);
		}
		Random rand = new Random();
		MCNode newChild = tree.addNode(node, unvisitedChildren.get(rand.nextInt(unvisitedChildren.size())));
		return newChild;
	}

}
