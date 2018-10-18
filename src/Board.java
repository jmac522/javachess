import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Board {

    private final List<BoardSquare> gameBoard;


    // constructor takes builder object in order to create a board instance
    private Board(Builder builder) {
        this.gameBoard = createGameBoard(builder);
    }


    public BoardSquare getBoardSquare(final int location) {
        return null;
    }

    // Method used to create a game board using a Builder object
    private static List<BoardSquare> createGameBoard(final Builder builder) {
        final List<BoardSquare> squares = new ArrayList<>();


        for(int i = 0; i < GameUtilities.TOTAL_TILES; i++) {
            // loops through and adds the 64 board squares, if there is a piece it will be added
            // if piece is null, the appropriate UnoccupiedBoardSquare will be added from the cache 
            squares.add(BoardSquare.createBoardSquare(i, builder.boardLayout.get(i)));
        }

        return Collections.unmodifiableList(squares);
    }

    // Builder class to build an immutable instance of a board
    public static class Builder {

        Map<Integer, Piece> boardLayout;
        Side sideToMove;

        public Builder() {

        }

        public Builder setPiece(final Piece piece) {
            this.boardLayout.put(piece.getLocationOnBoard(), piece);
            return this;
        }

        public Builder setSideToMove(final Side side) {
            this.sideToMove = side;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }
}
