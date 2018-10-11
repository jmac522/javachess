/* TO DO: B and G Column exceptions and Remove Row Exceptions */


// Import List/ArrayList for keeping track of legal moves
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Bishop extends Piece{


    // Constructor for Bishop, inherits from Piece super
    Bishop(final int pieceLocation, final Side color) {
        super(pieceLocation, color);
    }

    @Override
    public Collection<Move> getLegalMoves(Board board) {
    	// Declare a list to hold all POTENTIAL moves a bishop can make on the board
    	// based on its current position 
        final List<int> potentialMoves = new ArrayList<>();
        
        // ArrayList to hold found legal moves among potential bishop moves
        final List<Move> legalMoves = new ArrayList<>();
        
        
        // REFACTOR THESE INTO METHODS
        // CONSIDER directly adding to legal moves
        //Start by examining up-right movement by incramenting by 7 until reaching 
        // column H 
        int upRightTracker = this.locationOnBoard;
        boolean hitPiece = false;
        // if the current position is not on column H and we havent hit an obstructing piece
        // add the upper right square from current position to 
        // potential moves list 
        if (!GameUtilities.COLUMN_H(upRightTracker) && !hitPiece) {
        	upRightTracker -= 7; // set tracker to square to upper right
        	 // get the BoardSquare that is at the current potential move location
            final BoardSquare potentialMoveSquare = board.getSquare(upRightTracker);
            // if the square is not occupied we can add the square as a potential location
            if (!potentialMoveSquare.isOccupied()) {
            	// if the square is not occupied
                potentialMoves.add(upRightTracker); // stubbed move object, expand later
            } else {
            	// if the square IS occupied check the side of the piece
                final Piece pieceOnSquare = potentialMoveSquare.getPiece();
                final Side pieceColor = pieceOnSquare.getColor();
                if (this.color != pieceColor) {
                	// if it is an opponents piece, add to list and mark hit piece true
                	potentialMoves.add(upRightTracker);
                	hitPiece = true;
                } else {
                	hitPiece = true;
                }
            }
        }
        
        // Examining bottom right movement, using same process
        int bottomRightTracker = this.locationOnBoard;
        hitPiece = false;
        if (!GameUtilities.COLUMN_H(bottomRightTracker) && !hitPiece) {
        	bottomRightTracker += 9;
        	final BoardSquare potentialMoveSquare = board.getSquare(bottomRightTracker);
            // if the square is not occupied we can add the square as a potential location
            if (!potentialMoveSquare.isOccupied()) {
            	// if the square is not occupied
                potentialMoves.add(bottomRightTracker); // stubbed move object, expand later
            } else {
            	// if the square IS occupied check the side of the piece
                final Piece pieceOnSquare = potentialMoveSquare.getPiece();
                final Side pieceColor = pieceOnSquare.getColor();
                if (this.color != pieceColor) {
                	// if it is an opponents piece, add to list and mark hit piece true
                	potentialMoves.add(bottomRightTracker);
                	hitPiece = true;
                } else {
                	hitPiece = true;
                }
            }
        }
       
       
        // Examining bottom left movement, using same process
        int bottomLeftTracker = this.locationOnBoard;
        hitPiece = false;
        if (!GameUtilities.COLUMN_A(bottomLeftTracker) && !hitPiece) {
        	bottomRightTracker += 7;
        	final BoardSquare potentialMoveSquare = board.getSquare(bottomLeftTracker);
            // if the square is not occupied we can add the square as a potential location
            if (!potentialMoveSquare.isOccupied()) {
            	// if the square is not occupied
                potentialMoves.add(bottomLeftTracker); // stubbed move object, expand later
            } else {
            	// if the square IS occupied check the side of the piece
                final Piece pieceOnSquare = potentialMoveSquare.getPiece();
                final Side pieceColor = pieceOnSquare.getColor();
                if (this.color != pieceColor) {
                	// if it is an opponents piece, add to list and mark hit piece true
                	potentialMoves.add(bottomLeftTracker);
                	hitPiece = true;
                } else {
                	hitPiece = true;
                }
            }
        }
        
        int upLeftTracker = this.locationOnBoard;
        hitPiece = false;
        // check up left moves
        if (!GameUtilities.COLUMN_A(upLeftTracker) && !hitPiece) {
        	upLeftTracker -= 9; // set tracker to square to upper right
        	 // get the BoardSquare that is at the current potential move location
            final BoardSquare potentialMoveSquare = board.getSquare(upLeftTracker);
            // if the square is not occupied we can add the square as a potential location
            if (!potentialMoveSquare.isOccupied()) {
            	// if the square is not occupied
                potentialMoves.add(upLeftTracker);
                // add to legal moves as passive move 
                // legalMoves.add(new Move());
            } else {
            	// if the square IS occupied check the side of the piece
                final Piece pieceOnSquare = potentialMoveSquare.getPiece();
                final Side pieceColor = pieceOnSquare.getColor();
                if (this.color != pieceColor) {
                	// if it is an opponents piece, add to list and mark hit piece true
                	potentialMoves.add(upLeftTracker);
                	// add to legal moves as capture move
                	// legalMoves.add(new Move())
                	hitPiece = true;
                } else {
                	hitPiece = true;
                }
            }
        }

        // Return an unmodifiable collection containing the legal moves list
        return Collections.unmodifiableList(legalMoves);
    }
}

// Write general method for checking and adding moves in all four directions
// Something like private void getDirectionMoves(List legalMoves, int positionTracker, int incramentValue, char edgeColumn)
