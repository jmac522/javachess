abstract class CastleMove extends Move {

    protected final Rook castleRook;
    protected final int castleRookStart;
    protected final int castleRookEnd;

    protected CastleMove(Board board, Piece movingPiece, int movingTo, final Rook castleRook,
                         final int castleRookStart, final int castleRookEnd) {
        super(board, movingPiece, movingTo);
        this.castleRook = castleRook;
        this.castleRookStart = castleRookStart;
        this.castleRookEnd = castleRookEnd;
    }

    public Rook getCastleRook() {
        return this.castleRook;
    }

    @Override
    public boolean isCastlingMove() {
        return true;
    }

    @Override
    public Board execute() {

        final Board.Builder builder = new Board.Builder();

        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if(!this.movingPiece.equals(piece) && !this.castleRook.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for(final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        builder.setPiece(this.movingPiece.movePiece(this));
        builder.setPiece(new Rook(this.castleRookEnd, this.castleRook.getColor(), false));
        builder.setSideToMove(this.board.getCurrentPlayer().getOpponent().getSide());

        return builder.build();
    }

    public static final class KingSideCastleMove extends CastleMove {

        protected KingSideCastleMove(Board board, Piece movingPiece, int movingTo, final Rook castleRook,
                                     final int castleRookStart, final int castleRookEnd) {
            super(board, movingPiece, movingTo, castleRook, castleRookStart, castleRookEnd);
        }

        @Override
        public String toString() {
            return "O-O";
        }
    }

    public static final class QueenSideCastleMove extends CastleMove {
        protected QueenSideCastleMove(Board board, Piece movingPiece, int movingTo, final Rook castleRook,
                                      final int castleRookStart, final int castleRookEnd) {
            super(board, movingPiece, movingTo, castleRook, castleRookStart, castleRookEnd);
        }

        @Override
        public String toString() {
            return "O-O-O";
        }
    }
}
