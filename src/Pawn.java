import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class Pawn extends Piece {

    // constant holding vector values for pawn movement
    private static final int[] DIRECTIONAL_VECTORS  = {8};

    // boolean to keep track of if pawn has been moved yet/is eligble for double square move
    private boolean hasMoved = false;

    Pawn(final int pieceLocation, final Side color) {
        super(pieceLocation, color);
    }

    @Override
    public Collection<Move> getLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int directionalVectorOffset : DIRECTIONAL_VECTORS) {
            // Get current potential move based on movement vector and movement direction determined by Side of piece
            // Black will be positive vector White will be negative
            int currentPotentialMove = this.locationOnBoard + (this.getColor().getMovementDirection() * directionalVectorOffset);

            if (!GameUtilities.isValidBoardLocation(currentPotentialMove)) {
                continue;
            }

            // if you're moving forward and the tile is not occupied
            if (directionalVectorOffset == 8 && !board.getSquare(currentPotentialMove).isOccupied()) {
                // TODO: create pawn move class
                legalMoves.add(new PassiveMove(board, this, currentPotentialMove));
            }
        }

        return legalMoves;
    }
}

