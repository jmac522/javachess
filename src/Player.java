import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class Player {
	// member fields for the current gameboard, the players king, and all the legal moves
	// for the player based on the current game board
    protected final Board board;
    protected final King playersKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;
	
	
	// Super Constrcutor for Player objects
    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves) {
        this.board = board;
        this.playersKing = initKing();
        this.legalMoves = legalMoves;
        this.legalMoves.addAll(calculateKingCastles(legalMoves, opponentMoves));
        // Determines if player is in checking using find Attacks on Tile
        this.isInCheck = !Player.findAttacksOnTile(this.playersKing.getLocationOnBoard(), opponentMoves).isEmpty();
    }

    //Method that takes a piece's location and checks opponents legal moves to see
    // which moves attack that location
    protected static Collection<Move> findAttacksOnTile(final int pieceLocation, final Collection<Move> moves) {
    	final List<Move> attackingMoves = new ArrayList<>();
    	for (Move move : moves) {
    		if (pieceLocation == move.getMovingTo()) {
    			attackingMoves.add(move);
    		}
    	}
    	return Collections.unmodifiableList(attackingMoves);
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
        return this.isInCheck();
    }
	
	// Method for checking if a player has been mated
    public boolean isMated() {
    	// if the king is in check and has no legal escape moves, it is checkmate
        return this.isInCheck() && !hasEscapeMoves();
    }
    
    protected boolean hasEscapeMoves() {
    	for(final Move move : this.legalMoves) {
    		final MoveExecution execution = makeMove(move);
    		if (execution.getMoveStatus().isDone()) {
    			return true;
    		}
    	}
    	return false;
    }
	
	// Method for checking if the game is at a stalemate
    public boolean isStaleMated() {
        return !this.isInCheck() && !hasEscapeMoves();
    }
	
	// Method to determine if a player has castled yet
	// could be useful for AI 
    public boolean hasCastled() {
        return false;
    }
	
	// method for executing a given move and generating the new gameboard
	// based on the move
    public MoveExecution makeMove(final Move move) {
    	// If the move is not legal, return existing board unchanged, set status to ILLEGAL
    	if(!isMoveLegal(move)) {
    		return new MoveExecution(this.board, move, MoveStatus.ILLEGAL_MOVE);
    	}
    	
    	// If it is a potential legal move, create a new board representing 
    	// the new state if the move is executed
    	final Board updatedBoard = move.execute();
    	
    	// Create a collection of opponents legal moves that could attack the current player's
    	// King on the updatedBoard
    	final Collection<Move> kingAttacks = Player.findAttacksOnTile(updatedBoard.getCurrentPlayer()
                                                                                  .getOpponent()
                                                                                  .getPlayerKing()
                                                                                  .getLocationOnBoard(),
    	                                                              updatedBoard.getCurrentPlayer()
                                                                                  .getLegalMoves());
    	
    	if(!kingAttacks.isEmpty()) {
    		// If the opponent is attacking the moving players king after executing the move
    		// this is an illegal move as it would put the player in check so
    		// the current board should remain unchanged and move status marked
    		// appropriately 
    		return new MoveExecution(this.board, move, MoveStatus.PLAYER_IN_CHECK);
    	}
    	
    	// If the move is a legal movement for the piece, and does not leave the player in check
    	// we can pass the updated board to represent the move being executed
        return new MoveExecution(updatedBoard, move, MoveStatus.DONE);
    }
    
    public King getPlayerKing() {
    	return this.playersKing;
    }
    
    public Collection<Move> getLegalMoves(){
    	return this.legalMoves;
    }
	
	// Abstract methods to be overridden in player subclasses
    public abstract Collection<Piece> getActivePieces();
    public abstract Side getSide();
    public abstract Player getOpponent();
    protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegalMoves,
                                                             Collection<Move> opponentLegalMoves);
}
