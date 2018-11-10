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

	// Method for returning a pieces heuristic value based on its type and position for the minimax algorithm
    public double getPieceHeuristic() {
        return this.pieceType.getPieceAdjustedHeuristic(this.color, this.locationOnBoard);
    }


    // enum for piece types for use in toString Methods and checking piece type
    // when needed
    public enum PieceType {

        PAWN("pawn", 10) {
            @Override
            public String moveNotation() {
                return "P";
            }

            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public double getPieceAdjustedHeuristic(Side side, int location) {
                if (side == Side.BLACK) {
                    //return GameUtilities.BLACK_PAWN_PIECE_SQUARE[location] + this.getBaseHeuristic();
                    return this.getBaseHeuristic();
                } else {
                    //return GameUtilities.WHITE_PAWN_PIECE_SQUARE[location] + this.getBaseHeuristic();
                    return this.getBaseHeuristic();
                }
            }
        },
        KNIGHT("knight", 30){
            @Override
            public String moveNotation() {
                return "N";
            }

            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public double getPieceAdjustedHeuristic(Side side, int location) {
                if (side == Side.BLACK) {
                    //return GameUtilities.BLACK_KNIGHT_PIECE_SQUARE[location] + this.getBaseHeuristic();
                    return this.getBaseHeuristic();
                } else {
                    //return GameUtilities.WHITE_KNIGHT_PIECE_SQUARE[location] + this.getBaseHeuristic();
                    return this.getBaseHeuristic();
                }
            }
        },
        BISHOP("bishop", 30){
            @Override
            public String moveNotation() {
                return "B";
            }

            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public double getPieceAdjustedHeuristic(Side side, int location) {
                if (side == Side.BLACK) {
                    //return GameUtilities.BLACK_BISHOP_PIECE_SQUARE[location] + this.getBaseHeuristic();
                    return this.getBaseHeuristic();
                } else {
                    //return GameUtilities.WHITE_BISHOP_PIECE_SQUARE[location] + this.getBaseHeuristic();
                    return this.getBaseHeuristic();
                }
            }
        },
        ROOK("rook", 50){
            @Override
            public String moveNotation() {
                return "R";
            }

            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }

            @Override
            public double getPieceAdjustedHeuristic(Side side, int location) {
                if (side == Side.BLACK) {
                    //return GameUtilities.BLACK_ROOK_PIECE_SQUARE[location] + this.getBaseHeuristic();
                    return this.getBaseHeuristic();
                } else {
                    //return GameUtilities.WHITE_ROOK_PIECE_SQUARE[location] + this.getBaseHeuristic();
                    return this.getBaseHeuristic();
                }
            }
        },
        QUEEN("queen", 90){
            @Override
            public String moveNotation() {
                return "Q";
            }

            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public double getPieceAdjustedHeuristic(Side side, int location) {
                if (side == Side.BLACK) {
                    //return GameUtilities.BLACK_QUEEN_PIECE_SQUARE[location] + this.getBaseHeuristic();
                    return this.getBaseHeuristic();
                } else {
                    //return GameUtilities.WHITE_QUEEN_PIECE_SQUARE[location] + this.getBaseHeuristic();
                    return this.getBaseHeuristic();
                }
            }
        },
        KING("king", 900){
            @Override
            public String moveNotation() {
                return "K";
            }

            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public double getPieceAdjustedHeuristic(Side side, int location) {
                if (side == Side.BLACK) {
                    //return GameUtilities.BLACK_KING_PIECE_SQUARE[location] + this.getBaseHeuristic();
                    return this.getBaseHeuristic();
                } else {
                    //return GameUtilities.WHITE_KING_PIECE_SQUARE[location] + this.getBaseHeuristic();
                    return this.getBaseHeuristic();
                }
            }
        };
		
		// Memberfield to hold a string representation for a given piece type
        private String pieceName;
		private double baseHeuristic;
		// Constructor 
        PieceType(final String pieceName, double baseHeuristic) {
            this.pieceName = pieceName;
            this.baseHeuristic = baseHeuristic;
        }
		
		// toString method for printing a string representation in a piece
		// used when printing out a board. 
        @Override
        public String toString() {
            return this.pieceName;
        }

        // Method to get a PieceTypes base heuristic
        public double getBaseHeuristic() {
            return this.baseHeuristic;
        }

        public abstract String moveNotation();
		// Abstract method used in checking if a Piece is a king 
        public abstract boolean isKing();
        public abstract boolean isRook();
        public abstract double getPieceAdjustedHeuristic(Side side, int location);
    }
}
