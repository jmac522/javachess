/*
*  Piece is an abstract class that will extend to classes for specific piece types. The piece holds:
*   - The location of the piece on the board ( int between 0-63 )
*   - The side (white or black) the piece is on
*	- Whether the piece has moved (useful for castling, en passant, and AI opening moves)
*   - An enum PieceType for checking the pieces type (ex. useful for finding a king)
* Piece contains methods for:
*   = Calculating all legal moves for a given piece
*   = Moving a piece based on a passed move object (Generates new piece. TODO: Consider building cache for
*   all possible piece locations )
*
*                                                                                                       */


import java.util.Collection;

public abstract class Piece {
    // Properties containing the piece's position on the board and which side (W or B) it is on
    protected final int locationOnBoard;
    protected final Side color;
    // boolean to keep track of if piece has been moved yet ( especially important for pawns, rooks, and kings )
    protected boolean firstMove;
    protected final PieceType pieceType;

    // Constructor
    Piece(final int pieceLocation, final Side color, final PieceType pieceType, boolean firstMove) {
        this.locationOnBoard = pieceLocation;
        this.color = color;
        this.pieceType = pieceType;
        this.firstMove = firstMove;

    }
    
    @Override public boolean equals(final Object other) {
    	if (this == other) {
    		return true;
    	}
    	
    	if (!(other instanceof Piece)) {
    		return false;
    	}
    	
    	final Piece otherPiece = (Piece) other;
    	return (this.locationOnBoard == otherPiece.getLocationOnBoard() &&
    		this.pieceType == otherPiece.getPieceType() &&
    		this.color == otherPiece.getColor() &&
    		this.firstMove == otherPiece.isFirstMove());
    }
    
    @Override public int hashCode() {
    	// TODO: Implament hash code
    	return 0;
    }

    //getters
    public Side getColor() {
        return this.color;
    }
    public boolean isFirstMove() { return this.firstMove; }
    public int getLocationOnBoard() { return this.locationOnBoard; }
    public PieceType getPieceType() { return this.pieceType; }

    // Abstract method for returning a list of legal moves. Will be overridden for specific Piece subclasses
    public abstract Collection<Move> getLegalMoves(final Board board);
	
	// Abstract method for moving a piece based on a passed move object. Generate a piece that
	// retains all properties of current Piece but with updated location on board
	public abstract Piece movePiece(Move move);
	
    // enum for piece types for use in toString Methods and checking piece type
    // when needed
    public enum PieceType {

        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("N"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("Q"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING("K"){
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        };
		
		// Memberfield to hold a string representation for a given piece type
        private String pieceName;
		
		// Constructor 
        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }
		
		// toString method for printing a string representation in a piece
		// used when printing out a board. 
        @Override
        public String toString() {
            return this.pieceName;
        }
		
		// Abstract method used in checking if a Piece is a king 
        public abstract boolean isKing();
        public abstract boolean isRook();
    }
}
