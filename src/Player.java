import java.util.Collection;

public abstract class Player {

    protected final Board board;
    protected final King playersKing;
    protected final Collection<Move> legalMoves;

    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves) {
        this.board = board;
        this.playersKing = initKing();
        this.legalMoves = legalMoves;
    }

    private King initKing() {
        for(final Piece piece : getActivePieces()) {
            if(piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("No King on board, invalid game.");
    }

    public abstract Collection<Piece> getActivePieces();
}
