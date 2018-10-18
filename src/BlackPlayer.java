import java.util.Collection;

public class BlackPlayer extends Player {
    public BlackPlayer(Board board, Collection<Move> whiteInitialLegalMoves, Collection<Move> blackInitialLegalMoves) {

        super(board, blackInitialLegalMoves, whiteInitialLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }
}
