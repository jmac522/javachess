import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class Pawn extends Piece {

    // constant holding vector values for pawn movement
    private static final int[] DIRECTIONAL_VECTORS  = {8, 16, 7, 9};



    Pawn(final int pieceLocation, final Side color) {
        super(pieceLocation, color, PieceType.PAWN);
    }

    @Override
    public Collection<Move> getLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int directionalVectorOffset : DIRECTIONAL_VECTORS) {
            // Get current potential move based on movement vector and movement direction determined by Side of piece
            // Black will be positive vector White will be negative
            final int currentPotentialMove = this.locationOnBoard + (this.getColor().getMovementDirection() * directionalVectorOffset);

            if (!GameUtilities.isValidBoardLocation(currentPotentialMove)) {
                continue;
            }


            if (directionalVectorOffset == 8 && !board.getBoardSquare(currentPotentialMove).isOccupied()) {
                // if you're moving forward and the tile is not occupied add legal move
                // TODO: create pawn move class
                legalMoves.add(new PassiveMove(board, this, currentPotentialMove));
            } else if (directionalVectorOffset == 16 && !this.hasMoved() &&
                      (GameUtilities.ROW_TWO[currentPotentialMove] && this.getColor().isWhite()) ||
                      (GameUtilities.ROW_SEVEN[currentPotentialMove] && this.getColor().isBlack())) {
                // if pawn has not moved yet, is on the appropriate rank for its side, and
                // square directly in front is unoccupied, double move is possible

                // get square directly in front of pawn
                final int forwardSquare = this.locationOnBoard + (this.color.getMovementDirection() * 8);
                if (!board.getBoardSquare(forwardSquare).isOccupied() && !board.getBoardSquare(currentPotentialMove).isOccupied()) {
                    // if the square directly in front of the pawn and two spaces ahead are unoccupied, double move is
                    // possible
                    legalMoves.add(new PassiveMove(board, this, currentPotentialMove));
                }
            } else if (directionalVectorOffset == 7 &&
                      !((GameUtilities.COLUMN_H[this.locationOnBoard] && this.color.isWhite()) ||
                      (GameUtilities.COLUMN_A[this.locationOnBoard] && this.color.isBlack()))) {
                // if this is a diagonal vector of 7, pawn can only capture if square is occupied by piece of opposite color
                // vector does not work for white pawns on H file and Black pawns on A file
                final BoardSquare potentialMoveSquare = board.getBoardSquare(currentPotentialMove);
                if(potentialMoveSquare.isOccupied()) {
                    // if the square is occupied, get the piece and its Side
                    final Piece pieceOnSquare = potentialMoveSquare.getPiece();
                    final Side pieceColor = pieceOnSquare.getColor();
                    if (this.color != pieceColor) {
                        // if it is an opponent's piece, it can be captured
                        legalMoves.add(new CaptureMove(board, this, currentPotentialMove, pieceOnSquare));
                    }
                }
            } else if (directionalVectorOffset == 9 &&
                     !((GameUtilities.COLUMN_H[this.locationOnBoard] && this.color.isBlack()) ||
                      (GameUtilities.COLUMN_A[this.locationOnBoard] && this.color.isWhite()))) {
                // if this is a diagonal vector of 9, pawn can only capture if square is occupied by piece of opposite color
                // vector does not work for Black pawns on H file and White pawns on A file
                final BoardSquare potentialMoveSquare = board.getBoardSquare(currentPotentialMove);
                if(potentialMoveSquare.isOccupied()) {
                    // if the square is occupied, get the piece and its Side
                    final Piece pieceOnSquare = potentialMoveSquare.getPiece();
                    final Side pieceColor = pieceOnSquare.getColor();
                    if (this.color != pieceColor) {
                        // if it is an opponent's piece, it can be captured
                        legalMoves.add(new CaptureMove(board, this, currentPotentialMove, pieceOnSquare));
                    }
                }
            }
        }

        // return unmodifiable list of pawn's legal moves 
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}

