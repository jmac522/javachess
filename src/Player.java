import java.util.Collection;

public abstract class Player {
	// member fields for the current gameboard, the players king, and all the legal moves
	// for the player based on the current game board
    protected final Board board;
    protected final King playersKing;
    protected final Collection<Move> legalMoves;
	
	
	// Super Constrcutor for Player objects
    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves) {
        this.board = board;
        this.playersKing = initKing();
        this.legalMoves = legalMoves;
    }
	
	// Get the king for this player
    private King initKing() {
    	// go through each of the players active pieces
        for(final Piece piece : getActivePieces()) {
        	// if the piece is of type king, cast to King and return
            if(piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        // if we reach this point, there was no king on the board and this is
        // invalid (Should never get here)
        throw new RuntimeException("No King on board, invalid game.");
    }
	
	// Method for checking if a given move is contained within the possible legal moves
    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }
	
	// Method for checking if the player is in check
    public boolean isInCheck() {
        return false;
    }
	
	// Method for checking if a player has been mated
    public boolean isMated() {
        return false;
    }
	
	// Method for checking if the game is at a stalemate
    public boolean isStaleMated() {
        return false;
    }
	
	// Method to determine if a player has castled yet
	// could be useful for AI 
    public boolean hasCastled() {
        return false;
    }
	
	// method for executing a given move and generating the new gameboard
	// based on the move
    public MoveExecution makeMove(final Move move) {
        return null;
    }
	
	// Abstract methods to be overridden in player subclasses
    public abstract Collection<Piece> getActivePieces();
    public abstract Side getSide();
    public abstract Player getOpponent();
}
