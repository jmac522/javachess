public class PawnMove extends Move {
    protected PawnMove(Board board, Piece movingPiece, int movingTo) {
        super(board, movingPiece, movingTo);
    }

    // Method to execute a passive move (non capturing) by a Knight, Rook, or Bishop
    @Override
    public String toString() {
        return movingPiece.pieceType.moveNotation() + GameUtilities.moveNameLookup.get(movingPiece.locationOnBoard) +
                " " + GameUtilities.moveNameLookup.get(movingTo);
    }
}
