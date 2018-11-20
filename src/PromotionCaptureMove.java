public class PromotionCaptureMove extends CaptureMove {
    private Piece.PieceType promotionChoice;
    PromotionCaptureMove(final Board board, final Piece movingPiece, final int movingTo,
                final Piece threatenedPiece, final Piece.PieceType promotionChoice) {
        super(board, movingPiece, movingTo, threatenedPiece);
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
            if (piece != threatenedPiece) {
                builder.setPiece(piece);
            }
        }
        // Move the piece based on promotion choice
        builder.setPiece(((Pawn)this.movingPiece).promotePawn(this));
        builder.setSideToMove(this.board.getCurrentPlayer().getOpponent().getSide());

        return builder.build();
    }
}
