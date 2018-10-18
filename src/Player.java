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

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return false;
    }

    public boolean isMated() {
        return false;
    }

    public boolean isStaleMated() {
        return false;
    }

    public boolean hasCastled() {
        return false;
    }

    public MoveExecution makeMove(final Move move) {
        return null;
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Side getSide();
    public abstract Player getOpponent();
}
