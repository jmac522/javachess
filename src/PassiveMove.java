/* Passive Move subclass of Move to handle non capturing moves
 * 
 * 
 * 
 */

// TODO: Find A BETTER NAME IDIOT!
public final class PassiveMove extends Move {
	
	PassiveMove(final Board board, final Piece movingPiece, final int movingTo) {
		super(board, movingPiece, movingTo);
	}
	
	// Method to execute a passive move (non capturing) by a Knight, Rook, or Bishop
	
	@Overrides
	public Board execute() {
		
		final Board.Builder = new Board.Builder();
		for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
			// Loop through all of the pieces of the player making the move, if a piece
			// is not the one being moved, it can be added to the board in its current
			// position 			
			if(!this.movingPiece.equals(piece)) {
				builder.setPiece(piece);
			}		
		}
		
		for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
			builder.setPiece(piece);
		}
		// Move the piece
		builder.setPiece(this.movingPiece.movePiece(this));
		builder.setSideToMove(this.board.getCurrentPlayer.getOpponent().getColor())
				
		return builder.build();
	}
}