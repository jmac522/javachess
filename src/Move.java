public abstract class Move {
	// Board the move is related to
	final Board board;
	// The piece being moved
	final Piece movingPiece;
	// Location on the board (0-63) where piece is moving to
	final int movingTo;

	public static final Move NULL_MOVE = new NullMove();
	
	protected Move(Board board, Piece movingPiece, int movingTo) {
		this.board = board;
		this.movingPiece = movingPiece;
		this.movingTo = movingTo;
	}



	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}

		if (!(other instanceof Move)) {
			return false;
		}

		Move otherMove = (Move) other;
		return (this.getMovingTo() == otherMove.getMovingTo() &&
				this.movingPiece.equals(otherMove.getMovingPiece()));
	}


	public int getMovingTo() {
		return this.movingTo;
	}

	public int getCurrentLocation() {
		return this.getMovingPiece().getLocationOnBoard();
	}
	
	public Piece getMovingPiece() {
		return this.movingPiece;
	}

	public boolean isAttack() {
		return false;
	}

	public boolean isCastlingMove() {
		return false;
	}

	public Piece getAttackedPiece() {
		return null;
	}

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
            builder.setPiece(piece);
        }
        // Move the piece
        builder.setPiece(this.movingPiece.movePiece(this));
        builder.setSideToMove(this.board.getCurrentPlayer().getOpponent().getSide());

        return builder.build();
    }

}
