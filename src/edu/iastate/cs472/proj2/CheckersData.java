package edu.iastate.cs472.proj2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An object of this class holds data about a game of checkers.
 * It knows what kind of piece is on each square of the checkerboard.
 * Note that RED moves "up" the board (i.e. row number decreases)
 * while BLACK moves "down" the board (i.e. row number increases).
 * Methods are provided to return lists of available legal moves.
 */
public class CheckersData {

  /*  The following constants represent the possible contents of a square
      on the board.  The constants RED and BLACK also represent players
      in the game. */

    static final int
            EMPTY = 0,
            RED = 1,
            RED_KING = 2,
            BLACK = 3,
            BLACK_KING = 4;


    int[][] board;  // board[r][c] is the contents of row r, column c.


    /**
     * Constructor.  Create the board and set it up for a new game.
     */
    CheckersData() {
        board = new int[8][8];
        setUpGame();
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < board.length; i++) {
            int[] row = board[i];
            sb.append(8 - i).append(" ");
            for (int n : row) {
                if (n == 0) {
                    sb.append(" ");
                } else if (n == 1) {
                    sb.append(ANSI_RED + "R" + ANSI_RESET);
                } else if (n == 2) {
                    sb.append(ANSI_RED + "K" + ANSI_RESET);
                } else if (n == 3) {
                    sb.append(ANSI_YELLOW + "B" + ANSI_RESET);
                } else if (n == 4) {
                    sb.append(ANSI_YELLOW + "K" + ANSI_RESET);
                }
                sb.append(" ");
            }
            sb.append(System.lineSeparator());
        }
        sb.append("  a b c d e f g h");

        return sb.toString();
    }

    /**
     * Set up the board with checkers in position for the beginning
     * of a game.  Note that checkers can only be found in squares
     * that satisfy  row % 2 == col % 2.  At the start of the game,
     * all such squares in the first three rows contain black squares
     * and all such squares in the last three rows contain red squares.
     */
    void setUpGame() {
    	// Set up the board with pieces BLACK, RED, and EMPTY
    	for (int row=0; row<8; ++row) {
    		for (int column=0; column<8; ++column) {
    			if (row % 2 == column % 2) {
    				if (row <= 2) {
    					board[row][column] = BLACK;
    				} else if (row >= 5) {
    					board[row][column] = RED;   					
    				} else {
    					board[row][column] = EMPTY;
    				}    					
    			} else {
					board[row][column] = EMPTY;   				
    			}
    		}
    	}
    }


    /**
     * Return the contents of the square in the specified row and column.
     */
    int pieceAt(int row, int col) {
        return board[row][col];
    }


    /**
     * Make the specified move.  It is assumed that move
     * is non-null and that the move it represents is legal.
     *
     * Make a single move or a sequence of jumps
     * recorded in rows and cols.
     *
     */
    void makeMove(CheckersMove move) {
        int l = move.rows.size();
        for(int i = 0; i < l-1; i++)
            makeMove(move.rows.get(i), move.cols.get(i), move.rows.get(i+1), move.cols.get(i+1));
    }


    /**
     * Make the move from (fromRow,fromCol) to (toRow,toCol).  It is
     * assumed that this move is legal.  If the move is a jump, the
     * jumped piece is removed from the board.  If a piece moves to
     * the last row on the opponent's side of the board, the
     * piece becomes a king.
     *
     * @param fromRow row index of the from square
     * @param fromCol column index of the from square
     * @param toRow   row index of the to square
     * @param toCol   column index of the to square
     */
    void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        // TODO
    	// Update the board for the given move. You need to take care of the following situations:
        // 1. move the piece from (fromRow,fromCol) to (toRow,toCol)
        // 2. if this move is a jump, remove the captured piece
        // 3. if the piece moves into the kings row on the opponent's side of the board, crowned it as a king
    }

    /**
     * Return an array containing all the legal CheckersMoves
     * for the specified player on the current board.  If the player
     * has no legal moves, null is returned.  The value of player
     * should be one of the constants RED or BLACK; if not, null
     * is returned.  If the returned value is non-null, it consists
     * entirely of jump moves or entirely of regular moves, since
     * if the player can jump, only jumps are legal moves.
     *
     * @param player color of the player, RED or BLACK
     */
	CheckersMove[] getLegalMoves(int player) {   	
    	if ((player != RED) && (player != BLACK)) {
    		return null;
    	}
    	
       	List<CheckersMove> moves = new ArrayList<CheckersMove>();
       	
    	for (int row=0; row<8; ++row) {
    		for (int column=0; column<8; ++column) {
				// Look to see if there are moves into empty spaces
    			addPossibleMoveSE(player, row, column, moves);
       			addPossibleMoveSW(player, row, column, moves);
    			addPossibleMoveNE(player, row, column, moves);
       			addPossibleMoveNW(player, row, column, moves);
       		    			
				// Look to see if there is a jump starting from here

    		}
    	}
    	
       	moves.add(new CheckersMove(5, 1, 4, 0));
    	return moves.toArray(new CheckersMove[0]);
    }


    /**
     * Return a list of the legal jumps that the specified player can
     * make starting from the specified row and column.  If no such
     * jumps are possible, null is returned.  The logic is similar
     * to the logic of the getLegalMoves() method.
     *
     * Note that each CheckerMove may contain multiple jumps. 
     * Each move returned in the array represents a sequence of jumps 
     * until no further jump is allowed.
     *
     * @param player The player of the current jump, either RED or BLACK.
     * @param row    row index of the start square.
     * @param col    col index of the start square.
     */
	CheckersMove[] getLegalJumpsFrom(int player, int row, int col) {
        // TODO 
        return null;
    }
	
	private void addPossibleMoveSE(int player, int row, int column, List<CheckersMove> moves) {
		if (!isWithinGameBounds(row+1, column-1) || board[row+1][column-1] != EMPTY) {
			return;
		}
		
		boolean legalBlackMove = (player == BLACK) && (board[row][column] == BLACK || board[row][column] == BLACK_KING);
		boolean legalRedMove = (player == RED) && (board[row][column] == RED_KING);	
	
		if (legalRedMove || legalBlackMove) {	
			moves.add(new CheckersMove(row,  column,  row+1,  column-1));
		}
	}
	
	private void addPossibleMoveSW(int player, int row, int column, List<CheckersMove> moves) {
		if (!isWithinGameBounds(row+1, column+1) || board[row+1][column+1] != EMPTY) {
			return;
		}
		
		boolean legalBlackMove = (player == BLACK) && (board[row][column] == BLACK || board[row][column] == BLACK_KING);
		boolean legalRedMove = (player == RED) && (board[row][column] == RED_KING);	
	
		if (legalRedMove || legalBlackMove) {	
			moves.add(new CheckersMove(row,  column,  row+1,  column+1));
		}
	}
	
	private void addPossibleMoveNE(int player, int row, int column, List<CheckersMove> moves) {
		if (!isWithinGameBounds(row-1, column-1) || board[row-1][column-1] != EMPTY) {
			return;
		}
		
		boolean legalRedMove = (player == RED) && (board[row][column] == RED || board[row][column] == RED_KING);
		boolean legalBlackMove = (player == BLACK) && (board[row][column] == BLACK_KING);

			
		if (legalRedMove || legalBlackMove) {	
			moves.add(new CheckersMove(row,  column,  row-1,  column-1));
		}
	}

	private void addPossibleMoveNW(int player, int row, int column, List<CheckersMove> moves) {
		if (!isWithinGameBounds(row-1, column+1) || board[row-1][column+1] != EMPTY) {
			return;
		}
		
		boolean legalRedMove = (player == RED) && (board[row][column] == RED || board[row][column] == RED_KING);
		boolean legalBlackMove = (player == BLACK) && (board[row][column] == BLACK_KING);

			
		if (legalRedMove || legalBlackMove) {	
			moves.add(new CheckersMove(row,  column,  row-1,  column+1));
		}
	}
	
	private boolean isWithinGameBounds(int row, int column) {
		return ((row >= 0) && (row < 8) && (column >= 0) && (column < 8));
	}
}
