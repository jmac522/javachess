import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WhitePlayer extends Player {
	// Constructor for White player object
    public WhitePlayer(Board board, Collection<Move> whiteInitialLegalMoves, Collection<Move> blackInitialLegalMoves) {
        super(board, whiteInitialLegalMoves, blackInitialLegalMoves);
    }
	
	// Method to return the active pieces for the player playing white
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }
	
	// Method to return the proper Side Enum for a player
    @Override
    public Side getSide() {
        return Side.WHITE;
    }
	
	// Method to return the opponent for the white player
    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegalMoves, Collection<Move> opponentLegalMoves) {

        final List<Move> kingCastles = new ArrayList<>();

        if(!this.playersKing.isFirstMove() && !this.isInCheck()) {

        }

        return Collections.unmodifiableList(kingCastles);
    }
}
