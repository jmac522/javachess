/* Passive Move subclass of Move to handle non capturing moves
 * 
 * 
 * 
 */
public final class PassiveMove extends Move {
	
	PassiveMove(final Board board, final Piece movingPiece, final int movingTo) {
		super(board, movingPiece, movingTo);
	}
}