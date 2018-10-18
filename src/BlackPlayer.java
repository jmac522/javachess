import java.util.Collection;

public class BlackPlayer extends Player {
	
	// Constructor for the Black Player
    public BlackPlayer(Board board, Collection<Move> whiteInitialLegalMoves, Collection<Move> blackInitialLegalMoves) {

        super(board, blackInitialLegalMoves, whiteInitialLegalMoves);
    }
	
	// Method to return all active black pieces
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }
    
    
	// Method to return the Side Enum for the player with the black pieces
    @Override
    public Side getSide() {
        return Side.BLACK;
    }
	
	// Method to return the opponent of the Player with the black pieces
    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }
}
