public abstract class Move {
	// Board the move is related to
	final Board board;
	// The piece being moved
	final Piece movingPiece;
	// Location on the board (0-63) where piece is moving to
	final int movingTo;
	
	protected Move(Board board, Piece movingPiece, int movingTo) {
		this.board = board;
		this.movingPiece = movingPiece;
		this.movingTo = movingTo;
	}
	
	public int getMovingTo {
		return this.movingTo;
	}
	
	public Piece getMovingPiece() {
		return this.movingPiece;
	}
	
	public abstract Board execute();
}
