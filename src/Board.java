import java.util.*;

public class Board {

    private final List<BoardSquare> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;

    // constructor takes builder object in order to create a board instance
    private Board(Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = findActivePieces(this.gameBoard, Side.WHITE);
        this.blackPieces = findActivePieces(this.gameBoard, Side.BLACK);

        final Collection<Move> whiteInitialLegalMoves = findLegalMoves(this.whitePieces);
        final Collection<Move> blackInitialLegalMoves = findLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteInitialLegalMoves, blackInitialLegalMoves);
        this.blackPlayer = new BlackPlayer(this, whiteInitialLegalMoves, blackInitialLegalMoves);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < GameUtilities.TOTAL_TILES; i ++) {
            final String squareText = this.gameBoard.get(i).toString();
            builder.append(String.format("%3s", squareText));
            if((i + 1) % GameUtilities.TILES_PER_ROW == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public Collection<Piece> getBlackPieces() {
        return blackPieces;
    }

    public Collection<Piece> getWhitePieces() {
        return whitePieces;
    }

    private Collection<Move> findLegalMoves(final Collection<Piece> pieceList) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final Piece piece : pieceList) {
            legalMoves.addAll(piece.getLegalMoves(this));
        }

        return Collections.unmodifiableList(legalMoves);
    }

    private static Collection<Piece> findActivePieces(List<BoardSquare> gameBoard, Side side) {

        final List<Piece> activePieces = new ArrayList<>();

        for (final BoardSquare square : gameBoard) {
            if (square.isOccupied()) {
                final Piece piece = square.getPiece();
                if(piece.getColor() == side) {
                    activePieces.add(piece);
                }
            }
        }
        return Collections.unmodifiableList(activePieces);
    }


    public BoardSquare getBoardSquare(final int location) {
        return this.gameBoard.get(location);
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

    public static Board createInitialPosition() {
        final Builder builder = new Builder();

        // Set up the black pieces
        builder.setPiece(new Rook(0, Side.BLACK)); // A8
        builder.setPiece(new Knight(1, Side.BLACK)); // B8
        builder.setPiece(new Bishop(2, Side.BLACK)); // C8
        builder.setPiece(new Queen(3, Side.BLACK)); // D8
        builder.setPiece(new King(4, Side.BLACK)); // E8
        builder.setPiece(new Bishop(5, Side.BLACK)); // F8
        builder.setPiece(new Knight(6, Side.BLACK)); // G8
        builder.setPiece(new Rook(7, Side.BLACK)); // H8
        builder.setPiece(new Pawn(8, Side.BLACK)); // A7
        builder.setPiece(new Pawn(9, Side.BLACK)); // B7
        builder.setPiece(new Pawn(10, Side.BLACK)); // C7
        builder.setPiece(new Pawn(11, Side.BLACK)); // D7
        builder.setPiece(new Pawn(12, Side.BLACK)); // E7
        builder.setPiece(new Pawn(13, Side.BLACK)); // F7
        builder.setPiece(new Pawn(14, Side.BLACK)); // G7
        builder.setPiece(new Pawn(15, Side.BLACK)); // H7


        // Set up White Pieces
        builder.setPiece(new Pawn(48, Side.WHITE));
        builder.setPiece(new Pawn(49, Side.WHITE));
        builder.setPiece(new Pawn(50, Side.WHITE));
        builder.setPiece(new Pawn(51, Side.WHITE));
        builder.setPiece(new Pawn(52, Side.WHITE));
        builder.setPiece(new Pawn(53, Side.WHITE));
        builder.setPiece(new Pawn(54, Side.WHITE));
        builder.setPiece(new Pawn(55, Side.WHITE));
        builder.setPiece(new Rook(56, Side.WHITE));
        builder.setPiece(new Knight(57, Side.WHITE));
        builder.setPiece(new Bishop(58, Side.WHITE));
        builder.setPiece(new Queen(59, Side.WHITE));
        builder.setPiece(new King(60, Side.WHITE));
        builder.setPiece(new Bishop(61, Side.WHITE));
        builder.setPiece(new Knight(62, Side.WHITE));
        builder.setPiece(new Rook(63, Side.WHITE));

        //white moves first
        builder.setSideToMove(Side.WHITE);

        return builder.build();
    }

    // Builder class to build an immutable instance of a board
    public static class Builder {

        Map<Integer, Piece> boardLayout;
        Side sideToMove;

        public Builder() {
            this.boardLayout = new HashMap<>();
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
