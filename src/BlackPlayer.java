import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BlackPlayer extends Player {
	
	// Constructor for the Black Player
    public BlackPlayer(Board board, Collection<Move> whiteInitialLegalMoves, Collection<Move> blackInitialLegalMoves) {

        super(board, blackInitialLegalMoves, whiteInitialLegalMoves);
    }
	
	// Method to return all active black pieces
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }
    
    
	// Method to return the Side Enum for the player with the black pieces
    @Override
    public Side getSide() {
        return Side.BLACK;
    }
	
	// Method to return the opponent of the Player with the black pieces
    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegalMoves, Collection<Move> opponentLegalMoves) {

        final List<Move> kingCastles = new ArrayList<>();
        // White KingSide Castle
        if(!this.playersKing.isFirstMove() && !this.isInCheck()) {
            if (!this.board.getBoardSquare(6).isOccupied() &&
                    !this.board.getBoardSquare(5).isOccupied() ) {
                final BoardSquare rookSquare = this.board.getBoardSquare(7);

                if(rookSquare.isOccupied() && rookSquare.getPiece().isFirstMove()) {
                    if(Player.findAttacksOnTile(6, opponentLegalMoves).isEmpty() &&
                            Player.findAttacksOnTile(5, opponentLegalMoves).isEmpty()) {
                        if (rookSquare.getPiece().pieceType.isRook()) {
                            kingCastles.add(new CastleMove.KingSideCastleMove(this.board,
                                                                              this.getPlayerKing(),
                                                                     6,
                                                                              (Rook) rookSquare.getPiece(),
                                                                               rookSquare.getPiece().getLocationOnBoard(),
                                                                   5 ));
                        }
                    }
                }
            }

            if(!this.board.getBoardSquare(1).isOccupied() &&
                    !this.board.getBoardSquare(2).isOccupied() &&
                    !this.board.getBoardSquare(3).isOccupied()) {
                final BoardSquare rookSquare = this.board.getBoardSquare(0);

                if (rookSquare.isOccupied() && rookSquare.getPiece().isFirstMove()) {
                    if (Player.findAttacksOnTile(1, opponentLegalMoves).isEmpty() &&
                            Player.findAttacksOnTile(2, opponentLegalMoves).isEmpty() &&
                            Player.findAttacksOnTile(3, opponentLegalMoves).isEmpty()) {
                        if (rookSquare.getPiece().pieceType.isRook()) {
                            kingCastles.add(new CastleMove.QueenSideCastleMove(this.board,
                                    this.getPlayerKing(),
                                    2,
                                    (Rook) rookSquare.getPiece(),
                                    rookSquare.getPiece().getLocationOnBoard(),
                                    3 ));
                        }
                    }
                }
            }
        }

        return kingCastles;
    }
}
