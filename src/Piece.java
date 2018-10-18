/*
*  Piece is an abstract class that will extend to classes for specific piece types. The piece holds:
*   - The location of the piece on the board ( int between 0-63 )
*   - The side (white or black) the piece is on
*
*  Piece contains methods for:
*   = Calculating all legal moves for a given piece
*   =
*
*                                                                                                       */


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public abstract class Piece {
    // Properties containing the piece's position on the board and which side (W or B) it is on
    protected final int locationOnBoard;
    protected final Side color;
    // boolean to keep track of if piece has been moved yet ( especially important for pawns, rooks, and kings )
    protected boolean hasMoved;
    protected final PieceType pieceType;

    // Constructor
    Piece(final int pieceLocation, final Side color, final PieceType pieceType) {
        this.locationOnBoard = pieceLocation;
        this.color = color;
        this.hasMoved = false;
        this.pieceType = pieceType;
    }

    //getters
    public Side getColor() {
        return this.color;
    }
    public boolean hasMoved() { return this.hasMoved; }
    public int getLocationOnBoard() { return this.locationOnBoard; }
    public PieceType getPieceType() { return this.pieceType; }

    // Abstract method for returning a list of legal moves. Will be overridden for specific Piece subclasses
    public abstract Collection<Move> getLegalMoves(final Board board);

    // enum for piece types for use in toString Methods
    public enum PieceType {

        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N"){
            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B"){
            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK("R"){
            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q"){
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING("K"){
            @Override
            public boolean isKing() {
                return true;
            }
        };

        private String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public abstract boolean isKing();
    }
}
