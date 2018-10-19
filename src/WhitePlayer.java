import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WhitePlayer extends Player {
	// Constructor for White player object
    public WhitePlayer(Board board, Collection<Move> whiteInitialLegalMoves, Collection<Move> blackInitialLegalMoves) {
        super(board, whiteInitialLegalMoves, blackInitialLegalMoves);
    }
	
	// Method to return the active pieces for the player playing white
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }
	
	// Method to return the proper Side Enum for a player
    @Override
    public Side getSide() {
        return Side.WHITE;
    }
	
	// Method to return the opponent for the white player
    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegalMoves, Collection<Move> opponentLegalMoves) {

        final List<Move> kingCastles = new ArrayList<>();
        // White KingSide Castle
        if(!this.playersKing.isFirstMove() && !this.isInCheck()) {
            if (!this.board.getBoardSquare(61).isOccupied() &&
                !this.board.getBoardSquare(62).isOccupied() ) {
                final BoardSquare rookSquare = this.board.getBoardSquare(63);

                if(rookSquare.isOccupied() && rookSquare.getPiece().isFirstMove()) {
                    if(Player.findAttacksOnTile(61, opponentLegalMoves).isEmpty() &&
                       Player.findAttacksOnTile(62, opponentLegalMoves).isEmpty()) {

                        // TODO: add in castle move
                        kingCastles.add(null);
                    }
                }
            }

            if(!this.board.getBoardSquare(59).isOccupied() &&
               !this.board.getBoardSquare(58).isOccupied() &&
               !this.board.getBoardSquare(57).isOccupied()) {
                final BoardSquare rookSquare = this.board.getBoardSquare(56);

                if (rookSquare.isOccupied() && rookSquare.getPiece().isFirstMove()) {
                    if (Player.findAttacksOnTile(59, opponentLegalMoves).isEmpty() &&
                        Player.findAttacksOnTile(58, opponentLegalMoves).isEmpty() &&
                        Player.findAttacksOnTile(57, opponentLegalMoves).isEmpty()) {
                        // TODO: add in castle move
                        kingCastles.add(null);
                    }
                }
            }
        }

        return Collections.unmodifiableList(kingCastles);
    }
}
