import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import java.io.InputStream;
import java.util.Collection;

/*
*
*
*
*
*
*                                                                              */
public class UnoccupiedBoardSquare extends BoardSquare {

    // Constructor. Location should be immutable
    protected UnoccupiedBoardSquare( final int loc, final SquareColor squareColor) {
        // use constructor defined in BoardSquare
        super(loc, squareColor);
        this.getStyleClass().addAll(squareColor.getSquareCSS(), this.emptySquareCss());
         this.setOnMouseClicked(e -> handleMouseClicked());
    }

    private void handleMouseClicked() {
        // 1) Check if the game has a currently selected active square
        // 2) if it does compare its piece's legal moves to the clicked unnoccupied square
        // 3) execute move if possible
        if (GameDriver.selectedPieceSquare != null) {
            // get the piece that is currently selected
            Piece selectedPiece = GameDriver.selectedPieceSquare.getPiece();
            // get the selected piece's legal moves
            Collection<Move> selectedPieceMoves = selectedPiece.getLegalMoves(GameDriver.activeGameBoard);
            // If the piece is a King, check if there are any possible Castling Moves to add to legal moves list
            if (selectedPiece.pieceType.isKing()) {
                Collection<Move> playerLegalMoves = GameDriver.activeGameBoard.getCurrentPlayer().getLegalMoves();
                Collection<Move> opponentLegalMoves = GameDriver.activeGameBoard.getCurrentPlayer().getOpponent().getLegalMoves();
                Player currentPlayer = GameDriver.activeGameBoard.getCurrentPlayer();
                selectedPieceMoves.addAll(currentPlayer.calculateKingCastles(playerLegalMoves, opponentLegalMoves));
            }

            // If the piece is a promoting pawn, get the players promotion choice
            if (selectedPiece.pieceType == Piece.PieceType.PAWN) {
                if (((selectedPiece.color == Side.WHITE && selectedPiece.locationOnBoard >= 8 && selectedPiece.locationOnBoard <= 15) ||
                    (selectedPiece.color == Side.BLACK && selectedPiece.locationOnBoard >= 48 && selectedPiece.locationOnBoard <= 55))) {
                    GameDriver.throwGetPromotionChoice();
                }
            }
            // Loop through each of the selected piece's legal moves to the location the user just clicked
            for (final Move move: selectedPieceMoves) {
                // Location of a legal move's destination
                int destination = move.getMovingTo();
                // The BoardSquare object of teh destination
                BoardSquare destinationSquare = GameDriver.activeGameBoard.getBoardSquare(destination);
                // Remove unneeded CSS classes
                destinationSquare.getStyleClass().remove("empty-legal-move-board-square");
                destinationSquare.getStyleClass().remove("attacked-board-square");
                // If the clicked square matches the destination of a move
                if (this.location == move.movingTo) {
                    if ( move instanceof PassivePromotionMove) {
                        if (((PassivePromotionMove) move).getPromotionChoice() ==
                                GameDriver.activeGameBoard.getCurrentPlayer().getPromotionChoice()) {
                            MoveExecution me = GameDriver.activeGameBoard.getCurrentPlayer().makeMove(move);
                            if (me.getMoveStatus() == MoveStatus.DONE) {
                                GameUtilities.updateBoard(move);
                            } else if (me.getMoveStatus() == MoveStatus.PLAYER_IN_CHECK) {
                                GameDriver.throwPlayerInCheckMessage();
                            }
                        }
                    } else {
                        // Attempt to execute the move
                        MoveExecution me = GameDriver.activeGameBoard.getCurrentPlayer().makeMove(move);
                        if (me.getMoveStatus() == MoveStatus.DONE) {
                            GameUtilities.updateBoard(move);
                        } else if (me.getMoveStatus() == MoveStatus.PLAYER_IN_CHECK) {
                            GameDriver.throwPlayerInCheckMessage();
                        }
                    }
                }
            }
        }
    }


    // Override isOccupied() method from BoardSquare
    @Override
    public boolean isOccupied() {
        // because this is UnnocupiedBoardSquare, it will always return false
        return false;
    }

    // override getPiece() method from BoardSquare
    @Override
    public Piece getPiece() {
        // will return null as it is UnnocupiedBoardSquare
        return null;
    }

    @Override
    public String toString() {
        return "-";
    }

    public String emptySquareCss() {
        return "empty-square";
    }

}
