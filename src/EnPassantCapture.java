public class EnPassantCapture extends PawnCaptureMove {
    EnPassantCapture(Board board, Piece movingPiece, int movingTo, Piece threatenedPiece) {
        super(board, movingPiece, movingTo, threatenedPiece);
    }
}
