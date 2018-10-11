// Import List/ArrayList for keeping track of legal moves
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Rook extends Piece {
	// constant holding vector values for all 4 directions of Rook movement
	private static final int[] DIRECTIONAL_VECTORS  = {-8, 8, -1, 1};
    
    // Constructor for Rook, inherits from Piece super
    Rook(final int pieceLocation, final Side color) {
        super(pieceLocation, color);
    }
    
    @Override
    public Collection<Move> getLegalMoves(Board board) {
 
        
        // ArrayList to hold found legal moves among potential bishop moves
        final List<Move> legalMoves = new ArrayList<>();
        
        // go through each of the 4 vectors to evaluate legal moves 
        for (int directionalVectorOffset : DIRECTIONAL_VECTORS) {
        	// set current move to pieces position 
        	int currentPotentialMove = this.locationOnBoard;
        	// while the current potential move is a Valid Board location increment by the vector
        	while (GameUtilities.isValidBoardLocation(currentPotentialMove)) {
        		currentPotentialMove += directionalVectorOffset;
        		// if the new position is a valid move and does not fail any edge case exceptions
        		// add appropriate move to legal moves list 
        		if (GameUtilities.isValidBoardLocation(currentPotentialMove) &&
        		       !isColumnHException(currentPotentialMove, directionalVectorOffset) &&
        		       !isColumnAException(currentPotetnailMove, directionalVectorOffset)) {
        		     // get the boardsquare object of the current potential move
        			final BoardSquare potentialMoveSquare = board.getSquare(currentPotentialMove);
        			
        			if (!potentialMoveSquare.isOccupied()) {
        				// if the square is not occupied, add it to the legal moves
        				legalMoves.add(new PassiveMove(board, this, currentPotentialMove));
        			} else {
        				// if the square is occupied get the piece occupying the square and its side
        				final Piece pieceOnSquare = potentialMoveSquare.getPiece();
        				final Side pieceColor = pieceOnSquare.getColor();
        				if (this.color != pieceColor) {
        					// if it is an opponents piece, it can be captured
        					// but there will be no further potential moves down this vector 
	                        legalMoves.add(new CaptureMove(board, this, currentPotentialMove, pieceOnSqaure));
	                        break; // break from while loop
        				} else {
        					// This is not a legal move and there are no further potential moves
        					// along this vector, break from while loop
        					break;
        				}
        			}
        		}      		
        	}
        }
       // Return an unmodifiable collection containing the legal moves list
        return Collections.unmodifiableList(legalMoves);
    }
    
    
    // Edge case checking 
    private static boolean isColumnAException(final int currentLocation, final int currentOffset ) {
		// if rook is in column A it cannot move to the left
		return GameUtilities.COLUMN_A(currentLocation) && (currentOffset == -1);
	}

	// exception for Column H edge cases 
	private static boolean isColumnHException(final int currentLocation, final int currentOffset ) {
		// if rook is in Column H it cannot move to the right 
		return GameUtilities.COLUMN_H(currentLocation) && (currentOffset == 1);
	}
}