/*
 *  King Class
 *
 *
 *
 *
 *
 *
 *
 *                                                                                                       */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class King extends Piece{

    private static final int[] POSSIBLE_MOVES  = {-8, 8, -1, 1, -9, 9, -7, 7};

    King(final int pieceLocation, final Side color) {
        super(pieceLocation, color);
    }

    @Override
    public Collection<Move> getLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int possibleMoveOffset : POSSIBLE_MOVES) {
            // use the offset from current location to examine a potential move
            final int currentPotentialMove = this.locationOnBoard + possibleMoveOffset;

            if(GameUtilities.isValidBoardLocation(currentPotentialMove)) {
                if (isFirstColumnException(this.locationOnBoard, possibleMoveOffset) ||
                    isLastColumnException(this.locationOnBoard, possibleMoveOffset)) {
                    // if the current potential move fails an edge case, move on to the next one
                    continue;
                }

                // get the BoardSquare object for the potential move
                final BoardSquare potentialMoveSquare = board.getSquare(currentPotentialMove);

                if(!potentialMoveSquare.isOccupied()) {
                    // if the square is not occupied, add to legal moves
                    legalMoves.add(new PassiveMove(board,
                            this, currentPotentialMove));
                } else {
                    // if the potential square IS occupied, get the piece that is currently on that square and its
                    // color
                    final Piece pieceOnSquare = potentialMoveSquare.getPiece();
                    final Side pieceColor = pieceOnSquare.getColor();
                    if (pieceColor != this.color) {
                        //if the piece occupying the square is an opposing piece it can be captured
                        legalMoves.add(new CaptureMove(board, this, currentPotentialMove, pieceOnSquare));
                    }
                }
            }
        }

        // Return an unmodifiable collection containing the legal moves list
        return Collections.unmodifiableList(legalMoves);
    }

    // Method to handle edge case for determining illegal moves when the king is on the "a" (leftmost) column of the
    // board
    private static boolean isFirstColumnException(final int currentLocation, final int potentialMoveOffset) {
        // If the king is in the first column, it cannot make any of the moves that move it to the left
        return GameUtilities.COLUMN_A[currentLocation] && ((potentialMoveOffset == -1) || (potentialMoveOffset == -9)
                || (potentialMoveOffset == 7));
    }

    // Method to handle edge case for determining illegal moves when the king is on the "h" (rightmost) column of the
    // board
    private static boolean isLastColumnException(final int currentLocation, final int potentialMoveOffset) {
        // If the king is in the last column, it cannot make any of the moves that move it to the right
        return GameUtilities.COLUMN_H[currentLocation] && ((potentialMoveOffset == -7) || (potentialMoveOffset == 1)
                || (potentialMoveOffset == 9));
    }
}
