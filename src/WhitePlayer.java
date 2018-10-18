import java.util.Collection;

public class WhitePlayer extends Player {
    public WhitePlayer(Board board, Collection<Move> whiteInitialLegalMoves, Collection<Move> blackInitialLegalMoves) {
        super(board, whiteInitialLegalMoves, blackInitialLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }
}
