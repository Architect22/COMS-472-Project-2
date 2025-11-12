package edu.iastate.cs472.proj2;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): Figure 3.10, page
 * 79.<br>
 * 
 * Figure 3.10 Nodes are the data structures from which the search tree is
 * constructed. Each has a parent, a state, and various bookkeeping fields.
 * Arrows point from child to parent.<br>
 * <br>
 * Search algorithms require a data structure to keep track of the search tree
 * that is being constructed. For each node n of the tree, we have a structure
 * that contains four components:
 * <ul>
 * <li>n.STATE: the state in the state space to which the node corresponds;</li>
 * <li>n.PARENT: the node in the search tree that generated this node;</li>
 * <li>n.ACTION: the action that was applied to the parent to generate the node;
 * </li>
 * <li>n.PATH-COST: the cost, traditionally denoted by g(n), of the path from
 * the initial state to the node, as indicated by the parent pointers.</li>
 * </ul>
 *
 * @param <S> The type used to represent states
 * @param <A> The type of the actions to be used to navigate through the state space
 *
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 * @author Mike Stampone
 * @author Ruediger Lunde
 */


/**
 * Node type for the Monte Carlo search tree.
 */
public class MCNode<S, A>
{
	// n.STATE: the state in the state space to which the node corresponds;
	private final S gameState;

	// n.PARENT: the node in the search tree that generated this node;
	private final MCNode<S, A> parent;

	// n.ACTION: the action that was applied to the parent to generate the node;
	private final A action;

	// n.PATH-COST: the cost, traditionally denoted by g(n), of the path from
	// the initial state to the node, as indicated by the parent pointers.
	private final double pathCost;

	
	public MCNode(S state) {
		this(state, null, null, 0.0);
	}
	
	public MCNode(S gameState, MCNode<S, A> parent, A action, double pathCost) {
		this.gameState = gameState;
		this.parent = parent;
		this.action = action;
		this.pathCost = pathCost;
	}


	public S getGameState() {
		return gameState;
	}


	public MCNode<S, A> getParent() {
		return parent;
	}


	public A getAction() {
		return action;
	}


	public double getPathCost() {
		return pathCost;
	}
	

	@Override
	public String toString() {
		return "[parent=" + parent + ", action=" + action + ", state=" + getGameState() + ", pathCost=" + pathCost + "]";
	}
}

