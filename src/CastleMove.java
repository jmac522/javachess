abstract class CastleMove extends Move {
    protected CastleMove(Board board, Piece movingPiece, int movingTo) {
        super(board, movingPiece, movingTo);
    }

    public static final class KingSideCastleMove extends CastleMove {

        protected KingSideCastleMove(Board board, Piece movingPiece, int movingTo) {
            super(board, movingPiece, movingTo);
        }
    }

    public static final class QueenSideCastleMove extends CastleMove {
        protected QueenSideCastleMove(Board board, Piece movingPiece, int movingTo) {
            super(board, movingPiece, movingTo);
        }
    }
}
