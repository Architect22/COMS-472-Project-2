package edu.iastate.cs472.proj2;

import java.util.ArrayList;

/**
 * A CheckersMove object represents a move in the game of Checkers.
 * It holds the row and column of the piece that is to be moved
 * and the row and column of the square to which it is to be moved.
 * (This class makes no guarantee that the move is legal.)
 *
 * It represents an action in the game of Checkers.
 * There may be a single move or multiple jumps in an action.
 * It holds a sequence of the rows and columns of the piece
 * that is to be moved, for example:
 * a single move: (2, 0) -> (3, 1)
 * a sequence of jumps: (2, 0) -> (4, 2) -> (6, 0)
 *
 * 
 * @author Benjamin Brown
 *
 *
 */
public class CheckersMove {
    
    ArrayList<Integer> rows = new ArrayList<Integer>();
    ArrayList<Integer> cols = new ArrayList<Integer>();
    
    CheckersMove(int r1, int c1, int r2, int c2) {
        // Constructor, a single move from
        //(r1, c1) to (r2, c2)
        
        // move's start
        rows.add(r1);
        cols.add(c1);
        
        // move's destination
        rows.add(r2);
        cols.add(c2);
    }
    
    CheckersMove() {
        // Constructor, create an empty move
    }
    
    boolean isJump() {
        // Test whether this move is a jump.  It is assumed that
        // the move is legal.  In a jump, the piece moves two
        // rows.  (In a regular move, it only moves one row.)
        return (rows.get(0) - rows.get(1) == 2 || rows.get(0) - rows.get(1) == -2);
    }
    
    
    void addMove(int r, int c){
        // add another move (continuous jump), which goes from
        // (last ele in rows, last ele in cols) to (r, c)
        rows.add(r);
        cols.add(c);
    }
    
    //get a copy of this move
    @Override
    public CheckersMove clone() {
        CheckersMove move = new CheckersMove();
        
        move.rows.addAll(this.rows);
        move.cols.addAll(this.cols);
        
        return move;
        
    }
    
    public boolean canJumpBeAddedToMove(int row, int column) {
    	int numMoves = rows.size();
    	if (numMoves < 2) {
    		return false;
    	}
    	
		// Jump cannot be added if we are going back to the previous move location
    	if ((rows.get(numMoves-2) == row) && (cols.get(numMoves-2) == column)) {
    		return false;
    	}
		
		// It also cannot be added if adding this would result in 2 sets of duplicate row/column combinations,
		// as it is possible to get back to a previous jump location once (as in jumping in a circle), but you
		// cannot get back to two locations
    	int numDuplicates = 0;
    	
    	// Check current location for duplicate first
    	if (doesMoveExist(row, column, 0)) {
    		numDuplicates++;
    	}
    	
    	// Check for a duplicate in existing moves
       	for (int i=0; i<rows.size(); ++i) {
       		if (doesMoveExist(rows.get(i).intValue(), cols.get(i).intValue(), i+1)) {
       			numDuplicates++;
       		}
    	}
       	
       	return numDuplicates < 2;
    }
    
    private boolean doesMoveExist(int row, int column, int startRow) {
    	for (int i=startRow; i<rows.size(); ++i) {
    		if ((rows.get(i) == row) && (cols.get(i) == column)) {
    			return true;
    		}
    	}
    	return false;    	
    }
    
}  // end class CheckersMove.
