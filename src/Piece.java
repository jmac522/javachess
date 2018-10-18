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
    private final int locationOnBoard;
    private final Side color;
    // boolean to keep track of if piece has been moved yet ( especially important for pawns, rooks, and kings )
    private boolean hasMoved;

    // Constructor
    Piece(final int pieceLocation, final Side color) {
        this.locationOnBoard = pieceLocation;
        this.color = color;
        this.hasMoved = false;
    }

    //getters
    public Side getColor() {
        return this.color;
    }
    public boolean hasMoved() { return this.hasMoved; }
    public int getLocationOnBoard() { return this.locationOnBoard; }

    // Abstract method for returning a list of legal moves. Will be overridden for specific Piece subclasses
    public abstract Collection<Move> getLegalMoves(final Board board);


    // Method to determine if a passed location is a valid location on board from 0-63
    // consider moving out to utility class as static method in the future

}
