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

		final Board.Builder builder = new Board.Builder();
		for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
			// Loop through all of the pieces of the player making the move, if a piece
			// is not the one being moved, it can be added to the board in its current
			// position
			if(!this.movingPiece.equals(piece)) {
				builder.setPiece(piece);
			}
		}

		for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
		    if (piece != threatenedPiece) {
                builder.setPiece(piece);
            }
		}
		// Move the piece
		builder.setPiece(this.movingPiece.movePiece(this));
		builder.setSideToMove(this.board.getCurrentPlayer().getOpponent().getSide());

		return builder.build();
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
    public String toString() {
        return movingPiece.pieceType.moveNotation() + GameUtilities.moveNameLookup.get(movingPiece.locationOnBoard) + " x " +
                threatenedPiece.pieceType.moveNotation() + GameUtilities.moveNameLookup.get(movingTo);
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