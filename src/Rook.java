// Import List/ArrayList for keeping track of legal moves
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Rook extends Piece {
	// constant holding vector values for all 4 directions of Rook movement
	private static final int[] DIRECTIONAL_VECTORS  = {-8, 8, -1, 1};
    
    // Constructor for Rook, inherits from Piece super
    Rook(final int pieceLocation, final Side color) {
        super(pieceLocation, color, PieceType.ROOK);
    }
    
    @Override
    public Collection<Move> getLegalMoves(Board board) {
        // ArrayList to hold found legal moves among potential bishop moves
        final List<Move> legalMoves = new ArrayList<>();
        
        // go through each of the 4 vectors to evaluate legal moves 
        for (final int directionalVectorOffset : DIRECTIONAL_VECTORS) {
        	// set current move to pieces position 
        	int currentPotentialMove = this.locationOnBoard;
        	// while the current potential move is a Valid Board location increment by the vector
        	while (GameUtilities.isValidBoardLocation(currentPotentialMove)) {
				// Check for edge cases, break if needed
				if (isColumnHException(currentPotentialMove, directionalVectorOffset) ||
						isColumnAException(currentPotentialMove, directionalVectorOffset)) {
					break;
				}
        		currentPotentialMove += directionalVectorOffset;
        		// if the new position is a valid move continue
        		if (GameUtilities.isValidBoardLocation(currentPotentialMove)) {
        		     // get the boardsquare object of the current potential move
        			final BoardSquare potentialMoveSquare = board.getBoardSquare(currentPotentialMove);
        			
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
	                        legalMoves.add(new CaptureMove(board, this, currentPotentialMove, pieceOnSquare));
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
		return GameUtilities.COLUMN_A[currentLocation] && (currentOffset == -1);
	}

	// exception for Column H edge cases 
	private static boolean isColumnHException(final int currentLocation, final int currentOffset ) {
		// if rook is in Column H it cannot move to the right 
		return GameUtilities.COLUMN_H[currentLocation] && (currentOffset == 1);
	}
	
	@Override
	public Rook movePiece(Move move) {
		return new Rook(move.getMovingTo, move.getMovingPiece.getColor());
	}

	@Override
	public String toString() {
		return PieceType.ROOK.toString();
	}
}