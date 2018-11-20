import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class Pawn extends Piece {

    // constant holding vector values for pawn movement
    private static final int[] DIRECTIONAL_VECTORS  = {8, 16, 7, 9};



    Pawn(final int pieceLocation, final Side color , final boolean isFirstMove) {
        super(pieceLocation, color, PieceType.PAWN, isFirstMove);
    }

    @Override
    public Collection<Move> getLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int directionalVectorOffset : DIRECTIONAL_VECTORS) {
            // Get current potential move based on movement vector and movement direction determined by Side of piece
            // Black will be positive vector White will be negative
            final int currentPotentialMove = this.locationOnBoard + (this.getColor().getMovementDirection() * directionalVectorOffset);

            if (!GameUtilities.isValidBoardLocation(currentPotentialMove)) {
                continue;
            }


            if (directionalVectorOffset == 8 && !board.getBoardSquare(currentPotentialMove).isOccupied()) {
                // if you're moving forward and the tile is not occupied add legal move
                if ((this.color == Side.WHITE && this.locationOnBoard >= 8 && this.locationOnBoard <= 16) ||
                        (this.color == Side.BLACK && this.locationOnBoard >= 48 && this.locationOnBoard <= 55)) {
                    legalMoves.add(new PassivePromotionMove(board, this,
                                   currentPotentialMove, PieceType.QUEEN));
                    legalMoves.add(new PassivePromotionMove(board, this,
                            currentPotentialMove, PieceType.ROOK));
                    legalMoves.add(new PassivePromotionMove(board, this,
                            currentPotentialMove, PieceType.BISHOP));
                    legalMoves.add(new PassivePromotionMove(board, this,
                            currentPotentialMove, PieceType.KNIGHT));
                } else {
                    legalMoves.add(new PawnMove(board, this, currentPotentialMove));
                }
            } else if (directionalVectorOffset == 16 && this.isFirstMove()) {
                // if pawn has not moved yet, is on the appropriate rank for its side, and
                // square directly in front is unoccupied, double move is possible

                // get square directly in front of pawn
                final int forwardSquare = this.locationOnBoard + (this.color.getMovementDirection() * 8);
                if (!board.getBoardSquare(forwardSquare).isOccupied() && !board.getBoardSquare(currentPotentialMove).isOccupied()) {
                    // if the square directly in front of the pawn and two spaces ahead are unoccupied, double move is
                    // possible
                    legalMoves.add(new PawnJump(board, this, currentPotentialMove));
                }
            } else if (directionalVectorOffset == 7 &&
                      !((GameUtilities.COLUMN_H[this.locationOnBoard] && this.color.isWhite()) ||
                      (GameUtilities.COLUMN_A[this.locationOnBoard] && this.color.isBlack()))) {
                // if this is a diagonal vector of 7, pawn can only capture if square is occupied by piece of opposite color
                // vector does not work for white pawns on H file and Black pawns on A file
                final BoardSquare potentialMoveSquare = board.getBoardSquare(currentPotentialMove);
                if(potentialMoveSquare.isOccupied()) {
                    // if the square is occupied, get the piece and its Side
                    final Piece pieceOnSquare = potentialMoveSquare.getPiece();
                    final Side pieceColor = pieceOnSquare.getColor();
                    if (this.color != pieceColor) {
                        // if it is an opponent's piece, it can be captured
                        if ((this.color == Side.WHITE && this.locationOnBoard >= 8 && this.locationOnBoard <= 15) ||
                            (this.color == Side.BLACK && this.locationOnBoard >= 48 && this.locationOnBoard <= 55)) {
                            // This move will be a promotion move
                            legalMoves.add(new PromotionCaptureMove(board, this,
                                    currentPotentialMove, pieceOnSquare, PieceType.QUEEN));
                            legalMoves.add(new PromotionCaptureMove(board, this,
                                    currentPotentialMove, pieceOnSquare, PieceType.ROOK));
                            legalMoves.add(new PromotionCaptureMove(board, this,
                                    currentPotentialMove, pieceOnSquare, PieceType.KNIGHT));
                            legalMoves.add(new PromotionCaptureMove(board, this,
                                    currentPotentialMove, pieceOnSquare, PieceType.BISHOP));
                        } else {
                            legalMoves.add(new PawnCaptureMove(board, this, currentPotentialMove, pieceOnSquare));
                        }
                    }
                } else if (board.getEnPassantPawn() != null) {
                    // if there is an enPassantPawn related to the board, en Passant may be possible

                    if (board.getBoardSquare(this.locationOnBoard - 1).getPiece() == board.getEnPassantPawn() &&
                    this.getColor() == Side.BLACK) {
                        // if the square to the left or right of the pawn holds the en passant pawn
                        // en passant is possible
                        legalMoves.add(new EnPassantCapture(board,
                            this, currentPotentialMove, board.getEnPassantPawn()));
                    } else if (board.getBoardSquare(this.locationOnBoard + 1).getPiece() == board.getEnPassantPawn() &&
                    this.getColor() == Side.WHITE) {
                        legalMoves.add(new EnPassantCapture(board,
                                this, currentPotentialMove, board.getEnPassantPawn()));
                    }
                }
            } else if (directionalVectorOffset == 9 &&
                     !((GameUtilities.COLUMN_H[this.locationOnBoard] && this.color.isBlack()) ||
                      (GameUtilities.COLUMN_A[this.locationOnBoard] && this.color.isWhite()))) {
                // if this is a diagonal vector of 9, pawn can only capture if square is occupied by piece of opposite color
                // vector does not work for Black pawns on H file and White pawns on A file
                final BoardSquare potentialMoveSquare = board.getBoardSquare(currentPotentialMove);
                if(potentialMoveSquare.isOccupied()) {
                    // if the square is occupied, get the piece and its Side
                    final Piece pieceOnSquare = potentialMoveSquare.getPiece();
                    final Side pieceColor = pieceOnSquare.getColor();
                    if (this.color != pieceColor) {
                        // if it is an opponent's piece, it can be captured
                        if ((this.color == Side.WHITE && this.locationOnBoard >= 8 && this.locationOnBoard <= 15) ||
                                (this.color == Side.BLACK && this.locationOnBoard >= 48 && this.locationOnBoard <= 55)) {
                            // This move will be a promotion move
                            legalMoves.add(new PromotionCaptureMove(board, this,
                                    currentPotentialMove, pieceOnSquare, PieceType.QUEEN));
                            legalMoves.add(new PromotionCaptureMove(board, this,
                                    currentPotentialMove, pieceOnSquare, PieceType.ROOK));
                            legalMoves.add(new PromotionCaptureMove(board, this,
                                    currentPotentialMove, pieceOnSquare, PieceType.KNIGHT));
                            legalMoves.add(new PromotionCaptureMove(board, this,
                                    currentPotentialMove, pieceOnSquare, PieceType.BISHOP));
                        } else {
                            legalMoves.add(new PawnCaptureMove(board, this, currentPotentialMove, pieceOnSquare));
                        }
                    }
                } else if (board.getEnPassantPawn() != null) {
                    // if there is an enPassantPawn related to the board, en Passant may be possible

                    if (board.getBoardSquare(this.locationOnBoard - 1).getPiece() == board.getEnPassantPawn() &&
                        this.getColor() == Side.WHITE ) {
                        // if the square to the left or right of the pawn holds the en passant pawn
                        // en passant is possible
                        legalMoves.add(new EnPassantCapture(board,
                                this, currentPotentialMove, board.getEnPassantPawn()));
                    } else if (board.getBoardSquare(this.locationOnBoard + 1).getPiece() == board.getEnPassantPawn() &&
                                this.getColor() == Side.BLACK) {
                        legalMoves.add(new EnPassantCapture(board,
                                this, currentPotentialMove, board.getEnPassantPawn()));
                    }
                }
            }
        }

        // return unmodifiable list of pawn's legal moves 
        return Collections.unmodifiableList(legalMoves);
    }
    
	@Override
	public Pawn movePiece(Move move) {
		return new Pawn(move.getMovingTo(), move.getMovingPiece().getColor(), false);
	}

	public Piece promotePawn(Move move) {
        if (!(move instanceof PassivePromotionMove || move instanceof PromotionCaptureMove)) {
            throw new RuntimeException("Invalid move type!");
        }
        if ( move instanceof PassivePromotionMove) {
            PassivePromotionMove passiveMove = (PassivePromotionMove) move;
            switch (passiveMove.getPromotionChoice()) {
                case QUEEN:
                    return new Queen(move.getMovingTo(), move.getMovingPiece().getColor(), false);
                case ROOK:
                    return new Rook(move.getMovingTo(), move.getMovingPiece().getColor(), false);
                case BISHOP:
                    return new Bishop(move.getMovingTo(), move.getMovingPiece().getColor(), false);
                case KNIGHT:
                    return new Knight(move.getMovingTo(), move.getMovingPiece().getColor(), false);
                default:
                    throw new RuntimeException("Invalid promotion type!");
            }
        } else {
            PromotionCaptureMove captureMove = (PromotionCaptureMove) move;
            switch (captureMove.getPromotionChoice()) {
                case QUEEN:
                    return new Queen(move.getMovingTo(), move.getMovingPiece().getColor(), false);
                case ROOK:
                    return new Rook(move.getMovingTo(), move.getMovingPiece().getColor(), false);
                case BISHOP:
                    return new Bishop(move.getMovingTo(), move.getMovingPiece().getColor(), false);
                case KNIGHT:
                    return new Knight(move.getMovingTo(), move.getMovingPiece().getColor(), false);
                default:
                    throw new RuntimeException("Invalid promotion type!");
            }
        }

    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}

