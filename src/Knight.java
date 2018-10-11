/* TO DO: B and G Column exceptions and Remove Row Exceptions */


// Import List/ArrayList for keeping track of legal moves
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Knight extends Piece{

    // Array constant containing values for potential knight moves in relation to a Knight's
    // current position on the board represented by ints between 0-63
    private final static int[] POTENTIAL_MOVES = { -6,  6, -10, 10, -15, 15, -17, 17};

    // Constructor for Knight, inherits from Piece super
    Knight(final int pieceLocation, final Side color) {
        super(pieceLocation, color);
    }

    @Override
    public List<Move> getLegalMoves(Board board) {
        // integer to keep track of the location we're currently examining
        int currentPotentialMove;

        // ArrayList to hold found legal moves among potential knight moves
        final List<Move> legalMoves = new ArrayList<>();

        // loop through each potential move to determine if it is a legal move
        for (int potentialMoveOffset : POTENTIAL_MOVES) {
            // current board position to check
            currentPotentialMove = this.locationOnBoard + potentialMoveOffset;

            // If the currentPotentialMove is within the valid range of the 0-63 board, and the offset does not fail
            // any of the Column Edge case exceptions
            if (GameUtilities.isValidBoardLocation(currentPotentialMove)       &&
               (!isFirstColumnException(locationOnBoard, potentialMoveOffset)) &&
               (!isLastColumnException(locationOnBoard, potentialMoveOffset))  &&
               (!isColumnBException(locationOnBoard, potentialMoveOffset))      &&
               (!isColumnGException(locationOnBoard, potentialMoveOffset))){
                // get the BoardSquare that is at the current potential move location
                final BoardSquare potentialMoveSquare = board.getSquare(currentPotentialMove);

                if (!potentialMoveSquare.isOccupied()) {
                    legalMoves.add(new Move()); // stubbed move object, expand later
                } else {
                    // if the potential square IS occupied, get the piece that is currently on that square and its
                    // color
                    final Piece pieceOnSquare = potentialMoveSquare.getPiece();
                    final Side pieceColor = pieceOnSquare.getColor();


                    // If the piece on the potential move square is not the same color as the knight, add this
                    // square as a potential legal move
                    if (this.color != pieceColor) {
                        legalMoves.add(new Move()); // stubbed move object, expand later
                    }
                }
            }

        }

        // Return an unmodifiable collection containing the legal moves list
        return Collections.unmodifiableList(legalMoves);
    }


    // Method to handle edge case for determining illegal moves when the knight is on the "a" (leftmost) column of the
    // board
    private static boolean isFirstColumnException(final int currentLocation, final int potentialMoveOffset) {
        // If the knight is in the first column, it cannot make any of the moves that move it to the left
        return GameUtilities.COLUMN_A[currentLocation] && ((potentialMoveOffset == -17) || (potentialMoveOffset == -10)
                || (potentialMoveOffset == 6) || (potentialMoveOffset == 15 ));
    }

    // Method to handle edge case for determining illegal moves when the knight is on the "h" (rightmost) column of the
    // board
    private static boolean isLastColumnException(final int currentLocation, final int potentialMoveOffset) {
        // If the knight is in the last column, it cannot make any of the moves that move it to the right
        return GameUtilities.COLUMN_H[currentLocation] && ((potentialMoveOffset == -15) || (potentialMoveOffset == -6)
                || (potentialMoveOffset == 10) || (potentialMoveOffset == 17));
    }

    // Method to handle edge case for determining illegal moves when the knight is on colun B (second from left) of the
    // board
    private static boolean isColumnBException(final int currentLocation, final int potentialMoveOffset) {
        // If the knight is in the second columns, it cannot make any of the moves that move two columns to the left
        return GameUtilities.COLUMN_B[currentLocation] && ((potentialMoveOffset == 6) || (potentialMoveOffset == -10)));
    }

    // Method to handle edge case for determining illegal moves when the knight is on column G (second from right) of the
    // board
    private static boolean isColumnGException(final int currentLocation, final int potentialMoveOffset) {
        // If the knight is in the Seventh columns, it cannot make any of the moves that move two columns to the right
        return GameUtilities.COLUMN_G[currentLocation] && ((potentialMoveOffset == -6) || (potentialMoveOffset == 10));
    }
}
