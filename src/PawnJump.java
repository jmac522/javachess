public class PawnJump extends Move {
    protected PawnJump(Board board, Piece movingPiece, int movingTo) {
        super(board, movingPiece, movingTo);
    }

    @Override
    public Board execute() {
        final Board.Builder builder = new Board.Builder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if(!this.movingPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for(final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        final Pawn movedPawn = (Pawn) this.movingPiece.movePiece(this);
        builder.setPiece(movedPawn);
        builder.setEnPassant(movedPawn);
        builder.setSideToMove(this.board.getCurrentPlayer().getOpponent().getSide());
        return builder.build();
    }
}
