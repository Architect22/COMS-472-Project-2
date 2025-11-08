package edu.iastate.cs472.proj2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** 
 * This class requires no implementation.  You may use it to create a Monte Carlo search tree, or 
 * you may get the work done using the MCNode class. 
 * 
 * @author Benjamin Brown
 *
 * @param <E>
 */
public class MCTree<E> 
{
	MCNode<E> root;
	int size;
	
	public void addRoot(MCNode<E> root) {
		this.root = root;
		size++;
	}
	
	public MCNode<E> addNode(MCNode<E> node, MCNode<E> parent) {
		parent.children.add(node);
		node.setParent(parent);
		return node;
	}
	
	public MCNode<E> getRoot(){
		return root;
	}
	
	public MCNode<E> getChildWithMaxPlayouts(MCNode<E> root){
		// TODO: needs tree navigation. probably recursive
		int max = 0;
		MCNode<E> currentBest = null;
		for(int i = 0; i < root.children.size(); ++i) {
			if(root.children.get(i).getPlayouts() > max) {
				max = root.children.get(i).getPlayouts();
				currentBest = root.children.get(i);
				
			}
		}
		return currentBest;
	}
	
	public ArrayList<MCNode<E>> getVisitedChildren(MCNode<E> node){
		return null;
	}
	
	public MCNode<E> getParent(MCNode<E> node){
		return node.getParent();
	}
	
	public MCNode<E> getChildWithMaxUCT(MCNode<E> node) {
		List<MCNode<E>> best_children = new ArrayList<>();
		double max_uct = Double.NEGATIVE_INFINITY;
		
//		Wi stands for the number of wins for the node considered after the i-th move.
//		Ni stands for the number of simulations for the node considered after the i-th move.
//		HashMap<S, Double> Wi, Ni;
		
		for (MCNode<E> child : node.children) {
//			double uct = ((Wi.get(child.getState())) / (Ni.get(child.getState()))) + Math.sqrt((2 / Ni.get(child.getState())) * (Math.log(Ni.get(node.getState()))));
//			if (uct > max_uct) {
//				max_uct = uct;
//				best_children = new ArrayList<>();
//				best_children.add(child);
//			} else if (uct == max_uct) {
//				best_children.add(child);
//			}
		}
		
		Random rand = new Random();
		return best_children.get(rand.nextInt(best_children.size()));
	}
	public void updateStats(boolean result, MCNode node) {
//		Ni.put(node.getState(), Ni.get(node.getState()) + 1);
//		if (result) Wi.put(node.getState(), Wi.get(node.getState()) + 1);
	}
}
