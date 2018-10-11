/* TO DO: B and G Column exceptions and Remove Row Exceptions */


// Import List/ArrayList for keeping track of legal moves
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Bishop extends Piece{
	// constant holding vector values for all 4 directions of bishop movement
	private static final int[] DIRECTIONAL_VECTORS  = {-9, -7, 7, 9};
    
    // Constructor for Bishop, inherits from Piece super
    Bishop(final int pieceLocation, final Side color) {
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
	                        break;
        				} else {
        					// This is not a legal move and there are no further potential moves
        					// along this vector 
        					break;
        				}
        			}
        		}      		
        	}
        }
       // Return an unmodifiable collection containing the legal moves list
        return Collections.unmodifiableList(legalMoves);
    }
}

// Write general method for checking and adding moves in all four directions
// Something like private void getDirectionMoves(List legalMoves, int positionTracker, int incramentValue, char edgeColumn)

// exception for Column A edge cases
private static boolean isColumnAException(final int currentLocation, final int currentOffset ) {
	// if bishop is in column A it cannot move to the left
	return GameUtilities.COLUMN_A(currentLocation) && (currentOffset == 7 || currentOffset == -9);
}

// exception for Column B edge cases 
private static boolean isColumnHException(final int currentLocation, final int currentOffset ) {
	// if bishop is in Column B it cannot move to the right 
	return GameUtilities.COLUMN_H(currentLocation) && (currentOffset == -7 || currentOffset == 9);
}