/* Capture Move subclass of Move to handle capturing moves
 *  will need to keep track of piece being captured
 * 
 * 
 */

public class CaptureMove extends Move {
	// Holds the piece being captured on the given move 	
	final Piece threatenedPiece;
	
	CaptureMove(final Board board, final Piece movingPiece, final int movingTo,
	            final Piece threatenedPiece) {
		super(board, movingPiece, movingTo);
		this.threatenedPiece = threatenedPiece;
	}
	
	@Override
	public Board execute() {
		return null;
	}

	@Override
	public boolean isAttack() {
		return true;
	}

	@Override
	public Piece getAttackedPiece() {
		return this.threatenedPiece;
	}

	@Override
	public int hashCode() {
		//TODO: Implament hashCode
		return 0;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}

		if (!(other instanceof CaptureMove)) {
			return false;
		}

		Move otherMove = (CaptureMove) other;
		return (super.equals(otherMove) && getAttackedPiece().equals(otherMove.getAttackedPiece()));
	}
}