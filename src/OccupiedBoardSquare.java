import java.util.Collection;


public class OccupiedBoardSquare extends BoardSquare {
    // An occupied square also has a piece object in addition to a location
    // The piece on a square should be immutable. Can only be referenced
    // through getPiece() method
    private final Piece currentPiece;
    private boolean isSelected = false;
    // Constructor. location and piece should be immutable
    protected OccupiedBoardSquare(final int loc, final Piece p, final SquareColor squareColor) {
        // use constructor defined in BoardSquare to assign location
        super(loc,  squareColor);

        // use passed piece object to assign currentPiece
        this.currentPiece = p;
        String pieceCSS = p.color.toString() + p.getPieceType().toString();
        this.getStyleClass().addAll(squareColor.getSquareCSS(), pieceCSS);
        this.setOnMouseClicked(e -> handleMouseClicked());
    }


    // Override isOccupied() method from BoardSquare
    @Override
    public boolean isOccupied() {
        // because this is Occupied, it will always return true
        return true;
    }

    // override getPiece() method from BoardSquare
    @Override
    public Piece getPiece() {
        // will return the piece contained on the square
        return this.currentPiece;
    }

    @Override
    public String toString() {
        return this.currentPiece.getColor().isBlack() ? this.getPiece().toString().toLowerCase() :
                this.currentPiece.toString();
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    // Method to handle selecting a square with a piece on it
    private void handleMouseClicked() {
        // if the square is not yet selected respond appropriately
        if (!this.isSelected) {
            if (GameDriver.sideToMove == this.getPiece().color) {
                // if the piece belongs to the current side to move it can be selected
                if (GameDriver.selectedPieceSquare != null) {
                    // remove selection styling from previously selected piece
                    GameDriver.selectedPieceSquare.getStyleClass().remove("selected-board-square");
                    OccupiedBoardSquare oldSelection = (OccupiedBoardSquare) GameDriver.selectedPieceSquare;
                    oldSelection.setIsSelected(false);
                    Collection<Move> oldLegalMoves = oldSelection.getPiece().getLegalMoves(GameDriver.activeGameBoard);
                    for (Move move : oldLegalMoves) {
                        int destination = move.getMovingTo();
                        BoardSquare destinationSquare = GameDriver.activeGameBoard.getBoardSquare(destination);
                        destinationSquare.getStyleClass().remove("empty-legal-move-board-square");
                        destinationSquare.getStyleClass().remove("attacked-board-square");
                    }
                }

                // Set this square as the current selected square and add appropriate styling
                this.isSelected = true;
                this.getStyleClass().add("selected-board-square");
                GameDriver.selectedPieceSquare = this;

                // Add an indication of possible legal moves for the selected piece
                Collection<Move> piecesLegalMoves = this.getPiece().getLegalMoves(GameDriver.activeGameBoard);
                if (this.getPiece().pieceType == Piece.PieceType.KING) {
                    Collection<Move> playerLegalMoves = GameDriver.activeGameBoard.getCurrentPlayer().getLegalMoves();
                    Collection<Move> opponentLegalMoves = GameDriver.activeGameBoard.getCurrentPlayer().getOpponent().getLegalMoves();
                    Player currentPlayer = GameDriver.activeGameBoard.getCurrentPlayer();
                    piecesLegalMoves.addAll(currentPlayer.calculateKingCastles(playerLegalMoves, opponentLegalMoves));
                }

                // If the piece is a promoting pawn, get the players promotion choice
                for (final Move move : piecesLegalMoves) {
                    int destination = move.getMovingTo();
                    BoardSquare destinationSquare = GameDriver.activeGameBoard.getBoardSquare(destination);
                    if (destinationSquare.isOccupied()) {
                        destinationSquare.getStyleClass().add("attacked-board-square");
                    }
                    destinationSquare.getStyleClass().add("empty-legal-move-board-square");
                }
            } else {
                if (GameDriver.selectedPieceSquare != null) {
                    // handle checking if this is a move selection to capture an opposing piece
                    Piece selectedPiece = GameDriver.selectedPieceSquare.getPiece();
                    Collection<Move> selectedPieceMoves = selectedPiece.getLegalMoves(GameDriver.activeGameBoard);
                    // If the piece is a promoting pawn, get the players promotion choice
                    if (selectedPiece.pieceType == Piece.PieceType.PAWN) {
                        if (((selectedPiece.color == Side.WHITE && selectedPiece.locationOnBoard >= 8 && selectedPiece.locationOnBoard <= 15) ||
                                (selectedPiece.color == Side.BLACK && selectedPiece.locationOnBoard >= 48 && selectedPiece.locationOnBoard <= 55))) {
                            GameDriver.throwGetPromotionChoice();
                        }
                    }
                    for (final Move move: selectedPieceMoves) {
                        int destination = move.getMovingTo();
                        BoardSquare destinationSquare = GameDriver.activeGameBoard.getBoardSquare(destination);
                        destinationSquare.getStyleClass().remove("empty-legal-move-board-square");
                        destinationSquare.getStyleClass().remove("attacked-board-square");
                        if (this.location == move.movingTo) {
                            if ( move instanceof PromotionCaptureMove) {
                                if (((PromotionCaptureMove) move).getPromotionChoice() ==
                                    GameDriver.activeGameBoard.getCurrentPlayer().getPromotionChoice()) {
                                    MoveExecution me = GameDriver.activeGameBoard.getCurrentPlayer().makeMove(move);
                                    if (me.getMoveStatus() == MoveStatus.DONE) {
                                        GameUtilities.updateBoard(move);
                                    } else if (me.getMoveStatus() == MoveStatus.PLAYER_IN_CHECK) {
                                        GameDriver.throwPlayerInCheckMessage();
                                    }
                                }
                            } else {
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
        } else {
            // if the square is already selected, deselect it
            GameDriver.selectedPieceSquare = null;
            this.isSelected = false;
            this.getStyleClass().remove("selected-board-square");
            Collection<Move> piecesLegalMoves = this.getPiece().getLegalMoves(GameDriver.activeGameBoard);
            for (final Move move : piecesLegalMoves) {
                int destination = move.getMovingTo();
                BoardSquare destinationSquare = GameDriver.activeGameBoard.getBoardSquare(destination);
                destinationSquare.getStyleClass().remove("empty-legal-move-board-square");
                destinationSquare.getStyleClass().remove("attacked-board-square");
            }
        }
    }

}
