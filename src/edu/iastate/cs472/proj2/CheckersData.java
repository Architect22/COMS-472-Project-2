package edu.iastate.cs472.proj2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

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
    
    CheckersData(CheckersData copy) {
        board = new int[8][8];
        for(int i=0; i<copy.board.length;i++)
        {
            for(int j=0;j<8;j++)
            {
                board[i][j]=copy.board[i][j];
            }
        }
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
    
    
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) {
    		return true;
    	}
    	if (obj instanceof CheckersData c) {
    		return Arrays.deepEquals(this.board, c.board);
    	}
        return false;
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
    	// Update the board for the given move. You need to take care of the following situations:
        // 1. move the piece from (fromRow,fromCol) to (toRow,toCol)
    	int playerPiece = board[fromRow][fromCol];
    	board[toRow][toCol] = playerPiece;
    	board[fromRow][fromCol] = EMPTY;
    	
        // 2. if this move is a jump, remove the captured piece
    	if (isJump(fromRow, toRow)) {
    		int jumpedRow = (fromRow > toRow ? toRow + 1 : fromRow + 1);
    		int jumpedColumn = (fromCol > toCol ? toCol + 1 : fromCol + 1);
    		board[jumpedRow][jumpedColumn] = EMPTY;
    	}
    	
        // 3. if the piece moves into the kings row on the opponent's side of the board, crowned it as a king
    	if ((playerPiece == BLACK) && (toRow == 7)) {
    		board[toRow][toCol] = BLACK_KING;
    	}
    	else if ((playerPiece == RED) && (toRow == 0)) {
    		board[toRow][toCol] = RED_KING;
    	}
 	
    }

    boolean isJump(int fromRow, int toRow) {
        // In a jump, the piece moves two rows.  (In a regular move, it only moves one row.)
        return Math.abs(fromRow - toRow) == 2;
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
    			CheckersMove move = addPossibleMoveSE(player, row, column);
    			if (move != null) {
    				moves.add(move);
    			}
    			
    			move = addPossibleMoveSW(player, row, column);
    			if (move != null) {
    				moves.add(move);
    			}
    			
    			move = addPossibleMoveNE(player, row, column);
    			if (move != null) {
    				moves.add(move);
    			}
    			
    			move = addPossibleMoveNW(player, row, column);
    			if (move != null) {
    				moves.add(move);
    			}  

    			// Look to see if there is a jump starting from here
       			CheckersMove[] jumps = getLegalJumpsFrom(player, row, column);
       			if ((jumps != null) && (jumps.length > 0)) {
       				moves.addAll(Arrays.asList(jumps));
       			}
    			
    		}
    	}
    	
    	// If there are any jump moves, remove the single space moves since jumps are required
    	if (moves.stream().anyMatch(x -> x.isJump())) {
    	   	moves = moves.stream().filter(x -> x.isJump()).toList();    		
    	}
    	
    	if (moves.size() == 0) {
    		return null;
    	}
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

		List<CheckersMove> moves = new ArrayList<CheckersMove>();
		
		// Get list of possible single jumps
		Stack<CheckersMove> queue = getLegalSingleJumpsFrom(player, row, col);
		
		while (!queue.isEmpty()) {
			CheckersMove jump = queue.pop();
			int jumpRow = jump.rows.getLast();
			int jumpCol = jump.cols.getLast();
			
			// Check to see if there are more jumps that can be made
			CheckersMove multijumpMove = addPossibleJumpSE(player, jumpRow, jumpCol, jump);
			if (multijumpMove == null) {
				// No more jumps available
				moves.add(jump);
			} else {
				// See if more jumps can be added
				queue.add(multijumpMove);
			}
			
			multijumpMove = addPossibleJumpSW(player, jumpRow, jumpCol, jump);
			if (multijumpMove == null) {
				moves.add(jump);
			} else {
				queue.add(multijumpMove);
			}
			
			multijumpMove = addPossibleJumpNE(player, jumpRow, jumpCol, jump);
			if (multijumpMove == null) {
				moves.add(jump);
			} else {
				queue.add(multijumpMove);
			}
			
			multijumpMove = addPossibleJumpNW(player, jumpRow, jumpCol, jump);
			if (multijumpMove == null) {
				moves.add(jump);
			} else {
				queue.add(multijumpMove);
			}
		}
				
    	return moves.toArray(new CheckersMove[0]);
    }
	
	private Stack<CheckersMove> getLegalSingleJumpsFrom(int player, int row, int col) {
		Stack<CheckersMove> queue = new Stack<CheckersMove>();
		
		CheckersMove move = addPossibleJumpSE(player, row, col, null);
		if (move != null) {
			queue.add(move);
		}
		
		move = addPossibleJumpSW(player, row, col, null);
		if (move != null) {
			queue.add(move);
		}
		
		move = addPossibleJumpNE(player, row, col, null);
		if (move != null) {
			queue.add(move);
		}
		
		move = addPossibleJumpNW(player, row, col, null);
		if (move != null) {
			queue.add(move);
		}
		
		return queue;
	}
	
	private CheckersMove addPossibleMoveSW(int player, int row, int column) {
		int moveToRow = row+1;
		int moveToColumn = column-1;
		
		return addPossibleMoveSouth(player, row, column, moveToRow, moveToColumn);
	}
	
	private CheckersMove addPossibleMoveSE(int player, int row, int column) {
		int moveToRow = row+1;
		int moveToColumn = column+1;
		
		return addPossibleMoveSouth(player, row, column, moveToRow, moveToColumn);
	}
	
	private CheckersMove addPossibleMoveNW(int player, int row, int column) {
		int moveToRow = row-1;
		int moveToColumn = column-1;
		
		return addPossibleMoveNorth(player, row, column, moveToRow, moveToColumn);
	}

	private CheckersMove addPossibleMoveNE(int player, int row, int column) {
		int moveToRow = row-1;
		int moveToColumn = column+1;
		
		return addPossibleMoveNorth(player, row, column, moveToRow, moveToColumn);
	}
	
	private CheckersMove addPossibleMoveSouth(int player, int row, int column, int moveToRow, int moveToColumn) {
		if (!isWithinGameBounds(moveToRow, moveToColumn) || board[moveToRow][moveToColumn] != EMPTY) {
			return null;
		}
		
		boolean legalBlackMove = (player == BLACK) && (board[row][column] == BLACK || board[row][column] == BLACK_KING);
		boolean legalRedMove = (player == RED) && (board[row][column] == RED_KING);	
	
		if (legalRedMove || legalBlackMove) {	
			return new CheckersMove(row,  column,  moveToRow,  moveToColumn);
		}
		
		return null;
	}	
	
	private CheckersMove addPossibleMoveNorth(int player, int row, int column, int moveToRow, int moveToColumn) {
		if (!isWithinGameBounds(moveToRow, moveToColumn) || board[moveToRow][moveToColumn] != EMPTY) {
			return null;
		}
		
		boolean legalRedMove = (player == RED) && (board[row][column] == RED || board[row][column] == RED_KING);
		boolean legalBlackMove = (player == BLACK) && (board[row][column] == BLACK_KING);	
	
		if (legalRedMove || legalBlackMove) {	
			return new CheckersMove(row,  column,  moveToRow,  moveToColumn);
		}
		
		return null;
	}
	
	private CheckersMove addPossibleJumpSW(int player, int row, int column, CheckersMove prevMove) {
		int jumpToRow = row+2;
		int jumpToColumn = column-2;
		int playerToJumpRow = row+1;
		int playerToJumpColumn = column-1;
		
		return addPossibleJumpSouth(player, row, column, jumpToRow, jumpToColumn, playerToJumpRow, playerToJumpColumn, prevMove);
	}
	
	private CheckersMove addPossibleJumpSE(int player, int row, int column, CheckersMove prevMove) {
		int jumpToRow = row+2;
		int jumpToColumn = column+2;
		int playerToJumpRow = row+1;
		int playerToJumpColumn = column+1;
		
		return addPossibleJumpSouth(player, row, column, jumpToRow, jumpToColumn, playerToJumpRow, playerToJumpColumn, prevMove);
	}
	
	private CheckersMove addPossibleJumpNW(int player, int row, int column, CheckersMove prevMove) {
		int jumpToRow = row-2;
		int jumpToColumn = column-2;
		int playerToJumpRow = row-1;
		int playerToJumpColumn = column-1;
		
		return addPossibleJumpNorth(player, row, column, jumpToRow, jumpToColumn, playerToJumpRow, playerToJumpColumn, prevMove);
	}

	private CheckersMove addPossibleJumpNE(int player, int row, int column, CheckersMove prevMove) {
		int jumpToRow = row-2;
		int jumpToColumn = column+2;
		int playerToJumpRow = row-1;
		int playerToJumpColumn = column+1;
		
		return addPossibleJumpNorth(player, row, column, jumpToRow, jumpToColumn, playerToJumpRow, playerToJumpColumn, prevMove);
	}
	
	private CheckersMove addPossibleJumpSouth(int player, int row, int column, int jumpToRow,
			int jumpToColumn, int playerToJumpRow, int playerToJumpColumn, CheckersMove prevMove) {
		
		if (!isWithinGameBounds(jumpToRow, jumpToColumn) || board[jumpToRow][jumpToColumn] != EMPTY) {
			return null;
		}
		
		int pieceThatStartedJump = prevMove == null
				? board[row][column]
				: board[prevMove.rows.getFirst()][prevMove.cols.getFirst()];
		
		boolean legalBlackMove = (player == BLACK) &&
			(pieceThatStartedJump == BLACK || pieceThatStartedJump == BLACK_KING) &&
			existsRedPlayerPiece(playerToJumpRow, playerToJumpColumn);
		
		boolean legalRedMove = (player == RED) &&
			(pieceThatStartedJump == RED_KING) &&
			existsBlackPlayerPiece(playerToJumpRow, playerToJumpColumn);
		
		if (legalRedMove || legalBlackMove) {
			if (prevMove == null) {
				return new CheckersMove(row,  column,  jumpToRow,  jumpToColumn);
			} else if (prevMove.canJumpBeAddedToMove(jumpToRow, jumpToColumn)) {
				CheckersMove clone = prevMove.clone();
				clone.addMove(jumpToRow, jumpToColumn);
				return clone;
			}
		}
		
		return null;
	}

	private CheckersMove addPossibleJumpNorth(int player, int row, int column, int jumpToRow,
			int jumpToColumn, int playerToJumpRow, int playerToJumpColumn, CheckersMove prevMove) {
		
		if (!isWithinGameBounds(jumpToRow, jumpToColumn) || board[jumpToRow][jumpToColumn] != EMPTY) {
			return null;
		}
	
		int pieceThatStartedJump = prevMove == null
				? board[row][column]
				: board[prevMove.rows.getFirst()][prevMove.cols.getFirst()];
		
		// Broken: The existing row/column is empty on a jump, so we need to not check
		// the piece at a location, but the piece the started the jump
		boolean legalRedMove = (player == RED) &&
			(pieceThatStartedJump == RED || pieceThatStartedJump == RED_KING) &&
			existsBlackPlayerPiece(playerToJumpRow, playerToJumpColumn);
		
		boolean legalBlackMove = (player == BLACK) &&
			(pieceThatStartedJump == BLACK_KING) &&
			existsBlackPlayerPiece(playerToJumpRow, playerToJumpColumn);
		
		if (legalRedMove || legalBlackMove) {
			if (prevMove == null) {
				return new CheckersMove(row,  column,  jumpToRow,  jumpToColumn);
			} else if (prevMove.canJumpBeAddedToMove(jumpToRow, jumpToColumn)) {
				CheckersMove clone = prevMove.clone();
				clone.addMove(jumpToRow, jumpToColumn);
				return clone;
			}
		}
		
		return null;
	}
	
	private boolean isWithinGameBounds(int row, int column) {
		return ((row >= 0) && (row < 8) && (column >= 0) && (column < 8));
	}
	
	public boolean existsRedPlayerPiece(int row, int column) {
		return ((board[row][column] == RED) || (board[row][column] == RED_KING));	
	}

	public boolean existsBlackPlayerPiece(int row, int column) {
		return ((board[row][column] == BLACK) || (board[row][column] == BLACK_KING));	
	}
}
