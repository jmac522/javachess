import java.util.*;

public class Board {
	// Member fields holding
	// - The current game board
	// - All of the white pieces in play 
	// - All of the black pieces in play
	// - The player playing white
	// - the player playing black
    private final List<BoardSquare> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

    private final Pawn enPassantPawn;

    // constructor takes builder object in order to create a board instance
    private Board(Builder builder) {
    	// Create a gameboard using the builder
        this.gameBoard = createGameBoard(builder);
        // Get all active white and black pieces
        this.whitePieces = findActivePieces(this.gameBoard, Side.WHITE);
        this.blackPieces = findActivePieces(this.gameBoard, Side.BLACK);

        // Set enPassant pawn
        this.enPassantPawn = builder.enPassantPawn;

		// Get all the legal moves for both white and black
        final Collection<Move> whiteInitialLegalMoves = findLegalMoves(this.whitePieces);
        final Collection<Move> blackInitialLegalMoves = findLegalMoves(this.blackPieces);
		
		// Assign the Players who are playing each respective side
        if (GameDriver.isVsComputer) {
            this.whitePlayer = new WhitePlayer(this, whiteInitialLegalMoves, blackInitialLegalMoves, Player.PlayerType.HUMAN);
            this.blackPlayer = new BlackPlayer(this, whiteInitialLegalMoves, blackInitialLegalMoves, Player.PlayerType.COMPUTER);
        } else {
            this.whitePlayer = new WhitePlayer(this, whiteInitialLegalMoves, blackInitialLegalMoves, Player.PlayerType.HUMAN);
            this.blackPlayer = new BlackPlayer(this, whiteInitialLegalMoves, blackInitialLegalMoves, Player.PlayerType.HUMAN);
        }


        this.currentPlayer = builder.sideToMove.choosePlayer(this.whitePlayer, this.blackPlayer);
    }
	
	// Board's toString method to print a visualization of the board to the console
	// for testing 
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
	
	// Getters
    public Player whitePlayer() {
        return this.whitePlayer;
    }

    public Player blackPlayer() {
        return this.blackPlayer;
    }

    public Collection<Piece> getBlackPieces() {
        return blackPieces;
    }

    public Collection<Piece> getWhitePieces() {
        return whitePieces;
    }
    
    public Player getCurrentPlayer() {
    	return this.currentPlayer;
    }

    public Pawn getEnPassantPawn() {
        return enPassantPawn;
    }

	// Method for returning all of a given sides legal moves by calling each
	// of its individual active pieces' legalMoves method
    private Collection<Move> findLegalMoves(final Collection<Piece> pieceList) {
        final List<Move> legalMoves = new ArrayList<>();
		
		// Get the legal moves from each individual piece
        for (final Piece piece : pieceList) {
            legalMoves.addAll(piece.getLegalMoves(this));
        }

        return legalMoves;
    }
	
	// Method to find all of the active pieces of a given side
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

    // Method for getting all active pieces on the board for piece heuristic calculations
    public Collection<Piece> getAllActivePieces() {
        final List<Piece> activePieces = new ArrayList<>();

        for (final BoardSquare square : this.gameBoard) {
            if (square.isOccupied()) {
                final Piece piece = square.getPiece();
                activePieces.add(piece);
            }
        }
        return Collections.unmodifiableList(activePieces);
    }

	// Gets a boardsquare at a given location on the gameboard
    public BoardSquare getBoardSquare(final int location) {
        return this.gameBoard.get(location);
    }

    // Method used to create a game board using a Builder object
    private static List<BoardSquare> createGameBoard(final Builder builder) {
        final List<BoardSquare> squares = new ArrayList<>();


        for(int i = 0; i < GameUtilities.TOTAL_TILES; i++) {
            // loops through and adds the 64 board squares, if there is a piece it will be added
            // if piece is null, the appropriate UnoccupiedBoardSquare will be added from the cache
            BoardSquare.SquareColor squareShade = GameUtilities.calculateSquareColor(i);
            squares.add(BoardSquare.createBoardSquare(i, builder.boardLayout.get(i), squareShade));
        }

        return Collections.unmodifiableList(squares);
    }

	
	// Method for creating the initial state of a board for a new game
    public static Board createInitialPosition(boolean vsComputer) {
        final Builder builder = new Builder();

        // Set up the black pieces
        builder.setPiece(new Rook(0, Side.BLACK, true)) // A8
               .setPiece(new Knight(1, Side.BLACK, true)) // B8
               .setPiece(new Bishop(2, Side.BLACK, true)) // C8
               .setPiece(new Queen(3, Side.BLACK, true)) // D8
               .setPiece(new King(4, Side.BLACK, true)) // E8
               .setPiece(new Bishop(5, Side.BLACK, true)) // F8
               .setPiece(new Knight(6, Side.BLACK, true)) // G8
               .setPiece(new Rook(7, Side.BLACK, true)) // H8
               .setPiece(new Pawn(8, Side.BLACK, true)) // A7
               .setPiece(new Pawn(9, Side.BLACK, true)) // B7
               .setPiece(new Pawn(10, Side.BLACK, true)) // C7
               .setPiece(new Pawn(11, Side.BLACK, true)) // D7
               .setPiece(new Pawn(12, Side.BLACK, true)) // E7
               .setPiece(new Pawn(13, Side.BLACK, true)) // F7
               .setPiece(new Pawn(14, Side.BLACK, true)) // G7
               .setPiece(new Pawn(15, Side.BLACK, true)); // H7


        // Set up White Pieces
        builder.setPiece(new Pawn(48, Side.WHITE, true))
               .setPiece(new Pawn(49, Side.WHITE, true))
               .setPiece(new Pawn(50, Side.WHITE, true))
               .setPiece(new Pawn(51, Side.WHITE, true))
               .setPiece(new Pawn(52, Side.WHITE, true))
               .setPiece(new Pawn(53, Side.WHITE, true))
               .setPiece(new Pawn(54, Side.WHITE, true))
               .setPiece(new Pawn(55, Side.WHITE, true))
               .setPiece(new Rook(56, Side.WHITE, true))
               .setPiece(new Knight(57, Side.WHITE, true))
               .setPiece(new Bishop(58, Side.WHITE, true))
               .setPiece(new Queen(59, Side.WHITE, true))
               .setPiece(new King(60, Side.WHITE, true))
               .setPiece(new Bishop(61, Side.WHITE, true))
               .setPiece(new Knight(62, Side.WHITE, true))
               .setPiece(new Rook(63, Side.WHITE, true));

        //white moves first
        builder.setSideToMove(Side.WHITE);

        return builder.build();
    }

    public List<Move> getAllLegalMoves() {
        List legalMoves = new ArrayList();
        legalMoves.addAll(this.whitePlayer.getLegalMoves());
        legalMoves.addAll(this.blackPlayer.getLegalMoves());
        return Collections.unmodifiableList(legalMoves);
    }

    // Builder class to build an immutable instance of a board
    public static class Builder {

        Map<Integer, Piece> boardLayout;
        Side sideToMove;

        private Pawn enPassantPawn;
		// Initializes a hashmap to hold the boardLayout representation
        public Builder() {
            this.boardLayout = new HashMap<>();
        }
		
		// Adds a given piece to the board layout based on that piece's
		// location on the board
        public Builder setPiece(final Piece piece) {
            this.boardLayout.put(piece.getLocationOnBoard(), piece);
            return this;
        }
		
		// Set which player's turn it is
        public Builder setSideToMove(final Side side) {
            this.sideToMove = side;
            return this;
        }
		
		// Creates the actually instance of the board object 
        public Board build() {
            return new Board(this);
        }

        public void setEnPassant(Pawn movedPawn) {
            this.enPassantPawn = movedPawn;
        }
    }
}
