/* Capture Move subclass of Move to handle capturing moves
 *  will need to keep track of piece being captured
 * 
 * 
 */

public final class CaptureMove extends Move {
	// Holds the piece being captured on the given move 	
	final Piece threatenedPiece;
	
	CaptureMove(final Board board, final Piece movingPiece, final int movingTo,
	            final Piece threatenedPiece) {
		super(board, movingPiece, movingTo);
		this.threatenedPiece = threatenedPiece;
	}
	
	@Overrides
	public Board exectue() {
		return null;
	}
}