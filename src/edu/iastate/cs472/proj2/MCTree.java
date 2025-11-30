package edu.iastate.cs472.proj2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/** 
 *	Basic implementation of Game Tree for the Monte Carlo Tree Search
 *
 * 	Wi stands for the number of wins for the node considered after the i-th move.
 * 	Ni stands for the number of simulations for the node considered after the i-th move.
 *
 * @author Suyash Jain
 */

public class MCTree<S> 
{
	HashMap<MCNode<S>, List<MCNode<S>>> gameTree;
	HashMap<S, Double> Wi, Ni;
	MCNode<S> root;
	
	
	public MCTree() {
		this.gameTree = new HashMap<>();
		Wi = new HashMap<>();
		Ni = new HashMap<>();
	}
	
	
	public void addRoot(S root) {
		MCNode<S> rootNode = new MCNode<>(root);
		this.root = rootNode;
		gameTree.put(rootNode, new ArrayList<>());
		Wi.put(root, 0.0);
		Ni.put(root, 0.0);
	}
	
	public MCNode<S> getRoot(){
		return root;
	}
	
	public List<S> getVisitedChildren(MCNode<S> parent) {
		List<S> visitedChildren = new ArrayList<>();
		if (gameTree.containsKey(parent)) {
			for (MCNode<S> child : gameTree.get(parent)) {
				visitedChildren.add(child.getGameState());
			}
		}
		return visitedChildren;
	}

	public MCNode<S> addChild(MCNode<S> parent, S child) {
		MCNode<S> newChild = new MCNode<>(child);
		List<MCNode<S>> children = successors(parent);
		children.add(newChild);
		gameTree.put(parent, children);
		Wi.put(child, 0.0);
		Ni.put(child, 0.0);
		return newChild;
	}
	
	public MCNode<S> getParent(MCNode<S> node) {
		MCNode<S> parent = null;
		for (MCNode<S> key : gameTree.keySet()) {
			List<MCNode<S>> children = successors(key);
			for (MCNode<S> child : children) {
				if (child.getGameState() == node.getGameState()) {
					parent = key;
					break;
				}
			}
			if (parent != null) break;
		}
		return parent;
	}
	
	
	public List<MCNode<S>> successors(MCNode<S> node) {
		if (gameTree.containsKey(node)) return gameTree.get(node);
		else return new ArrayList<>();
	}

	public void updateStats(boolean result, MCNode<S> node) {
		Ni.put(node.getGameState(), Ni.get(node.getGameState()) + 1);
		if (result) Wi.put(node.getGameState(), Wi.get(node.getGameState()) + 1);
	}
	
	public MCNode<S> getChildWithMaxUCT(MCNode<S> node) {
		List<MCNode<S>> best_children = new ArrayList<>();
		double max_uct = Double.NEGATIVE_INFINITY;
		for (MCNode<S> child : successors(node)) {
			double uct = ((Wi.get(child.getGameState())) / (Ni.get(child.getGameState()))) + Math.sqrt((2 / Ni.get(child.getGameState())) * (Math.log(Ni.get(node.getGameState()))));
			if (uct > max_uct) {
				max_uct = uct;
				best_children = new ArrayList<>();
				best_children.add(child);
			} else if (uct == max_uct) {
				best_children.add(child);
			}
		}
		
//		System.out.println("Max uct: "+ max_uct);
		Random rand = new Random();
		return best_children.get(rand.nextInt(best_children.size()));
	}
	
	public MCNode<S> getChildWithMaxPlayouts(MCNode<S> node) {
		List<MCNode<S>> best_children = new ArrayList<>();
		double max_playouts = Double.NEGATIVE_INFINITY;
		for (MCNode<S> child : successors(node)) {
			double playouts = (Ni.get(child.getGameState()));
			if (playouts > max_playouts) {
				max_playouts = playouts;
				best_children = new ArrayList<>();
				best_children.add(child);
			} else if (playouts == max_playouts) {
				best_children.add(child);
			}
		}
		Random rand = new Random();
		return best_children.get(rand.nextInt(best_children.size()));
	}
	
}
