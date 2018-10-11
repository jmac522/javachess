public class OccupiedBoardSquare extends BoardSquare {
    // An occupied square also has a piece object in addition to a location
    // The piece on a square should be immutable. Can only be referenced
    // through getPiece() method
    private final Piece currentPiece;

    // Constructor. location and piece should be immutable
    protected OccupiedBoardSquare(final int loc, final Piece p) {
        // use constructor defined in BoardSquare to assign location
        super(loc);

        // use passed piece object to assign currentPiece
        this.currentPiece = p;
    }

    // Override isOccupied() method from BoardSquare
    @Override
    public boolean isOccupied() {
        // because this is Occupied, it will always return true
        return true;
    }

    // override getPiece() method from BoardSquare
    @Override
    public Piece getPiece() {
        // will return the piece contained on the square
        return this.currentPiece;
    }
}
