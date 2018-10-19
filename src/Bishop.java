/* TO DO: B and G Column exceptions and Remove Row Exceptions */


// Import List/ArrayList for keeping track of legal moves
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Bishop extends Piece{
	// constant holding vector values for all 4 directions of bishop movement
	private static final int[] DIRECTIONAL_VECTORS  = {-9, -7, 7, 9};
    
    // Constructor for Bishop, inherits from Piece super
    Bishop(final int pieceLocation, final Side color) {
		super(pieceLocation, color, PieceType.BISHOP);
	}

    @Override
    public Collection<Move> getLegalMoves(Board board) {
 
        
        // ArrayList to hold found legal moves among potential bishop moves
        final List<Move> legalMoves = new ArrayList<>();
        
        // go through each of the 4 vectors to evaluate legal moves 
        for (int directionalVectorOffset : DIRECTIONAL_VECTORS) {
        	// set current move to pieces position 
        	int currentPotentialMove = this.getLocationOnBoard();
        	// while the current potential move is a Valid Board location increment by the vector
        	while (GameUtilities.isValidBoardLocation(currentPotentialMove)) {
        		// Check for edge cases, break if needed
        		if (isColumnHException(currentPotentialMove, directionalVectorOffset) ||
					isColumnAException(currentPotentialMove, directionalVectorOffset)) {
        			break;
				}
				// increment current move by vector offset
        		currentPotentialMove += directionalVectorOffset;
        		// if the new position is a valid position continue
        		if (GameUtilities.isValidBoardLocation(currentPotentialMove)) {
        		     // get the board square object of the current potential move
        			final BoardSquare potentialMoveSquare = board.getBoardSquare(currentPotentialMove);
        			
        			if (!potentialMoveSquare.isOccupied()) {
        				// if the square is not occupied, add it to the legal moves
        				legalMoves.add(new PassiveMove(board, this, currentPotentialMove));
        			} else {
        				// if the square is occupied get the piece occupying the square and its side
        				final Piece pieceOnSquare = potentialMoveSquare.getPiece();
        				final Side pieceColor = pieceOnSquare.getColor();
        				if (this.getColor() != pieceColor) {
        					// if it is an opponents piece, it can be captured
        					// but there will be no further potential moves down this vector 
	                        legalMoves.add(new CaptureMove(board, this, currentPotentialMove, pieceOnSquare));
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
    
    // exception for Column A edge cases
	private static boolean isColumnAException(final int currentLocation, final int currentOffset ) {
		// if bishop is in column A it cannot move to the left
		return GameUtilities.COLUMN_A[currentLocation] && (currentOffset == 7 || currentOffset == -9);
	}
	
	// exception for Column B edge cases 
	private static boolean isColumnHException(final int currentLocation, final int currentOffset ) {
		// if bishop is in Column H it cannot move to the right
		return GameUtilities.COLUMN_H[currentLocation] && (currentOffset == -7 || currentOffset == 9);
	}
	
	@Override
	public Bishop movePiece(Move move) {
		return new Bishop(move.getMovingTo(), move.getMovingPiece().getColor());
	}

	@Override
	public String toString() {
    	return PieceType.BISHOP.toString();
	}
}



