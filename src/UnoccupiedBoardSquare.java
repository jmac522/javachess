/*
*
*
*
*
*
*                                                                              */
public class UnoccupiedBoardSquare extends BoardSquare {

    // Constructor. Location should be immutable
    protected UnoccupiedBoardSquare( final int loc) {
        // use constructor defined in BoardSquare
        super(loc);
    }

    // Override isOccupied() method from BoardSquare
    @Override
    public boolean isOccupied() {
        // because this is UnnocupiedBoardSquare, it will always return false
        return false;
    }

    // override getPiece() method from BoardSquare
    @Override
    public Piece getPiece() {
        // will return null as it is UnnocupiedBoardSquare
        return null;
    }
}
