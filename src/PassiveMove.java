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

}