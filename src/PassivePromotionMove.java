public class PassivePromotionMove extends Move {

    private Piece.PieceType promotionChoice;
    protected PassivePromotionMove(Board board, Piece movingPiece, int movingTo, Piece.PieceType promotionChoice) {
        super(board, movingPiece, movingTo);
        this.promotionChoice = promotionChoice;
    }

    public Piece.PieceType getPromotionChoice() {
        return promotionChoice;
    }

    @Override
    public Board execute() {

        final Board.Builder builder = new Board.Builder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            // Loop through all of the pieces of the player making the move, if a piece
            // is not the one being moved, it can be added to the board in its current
            // position
            if(!this.movingPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }

        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        // Move the piece based on promotion choice
        builder.setPiece(((Pawn)this.movingPiece).promotePawn(this));
        builder.setSideToMove(this.board.getCurrentPlayer().getOpponent().getSide());

        return builder.build();
    }

    // Method to execute a passive move (non capturing) by a Knight, Rook, or Bishop
    @Override
    public String toString() {
        return movingPiece.pieceType.moveNotation() + GameUtilities.moveNameLookup.get(movingPiece.locationOnBoard) +
                " " + GameUtilities.moveNameLookup.get(movingTo);
    }
}