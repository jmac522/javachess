import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.InputStream;
import java.util.*;

public abstract class Player {
	// member fields for the current gameboard, the players king, and all the legal moves
	// for the player based on the current game board
    protected final Board board;
    protected final King playersKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;
    protected final PlayerType playerType;

	
	
	// Super Constrcutor for Player objects
    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves,
           final PlayerType playerType) {
        this.board = board;
        this.playersKing = initKing();
        this.playerType = playerType;
        if (this.playersKing != null) {
            // Adds any legal castling moves to the legal move list
            legalMoves.addAll(calculateKingCastles(legalMoves, opponentMoves));
            // sets member field to unmodifiable collection for immutability
            this.legalMoves = Collections.unmodifiableCollection(legalMoves);
            // Determines if player is in checking using find Attacks on Tile
            this.isInCheck = !Player.findAttacksOnTile(this.playersKing.getLocationOnBoard(), opponentMoves).isEmpty();
        } else {
            this.legalMoves = null;
            this.isInCheck = false;
        }
    }

    //Method that takes a piece's location and checks opponents legal moves to see
    // which moves attack that location
    protected static Collection<Move> findAttacksOnTile(final int pieceLocation, final Collection<Move> moves) {
    	final List<Move> attackingMoves = new ArrayList<>();
    	if (moves != null) {
            for (Move move : moves) {
                if (pieceLocation == move.getMovingTo()) {
                    attackingMoves.add(move);
                }
            }
        }
    	return Collections.unmodifiableList(attackingMoves);
    }
	
	// Get the king for this player
    private King initKing() {
    	// go through each of the players active pieces
        for(final Piece piece : getActivePieces()) {
        	// if the piece is of type king, cast to King and return
            if(piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        // if we reach this point, there was no king on the board and this is
        // invalid (Should never get here)
        // throw new RuntimeException("No King on board, invalid game.");
        return null;
    }
	
	// Method for checking if a given move is contained within the possible legal moves
    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }
	
	// Method for checking if the player is in check
    public boolean isInCheck() {
        return this.isInCheck;
    }
	
	// Method for checking if a player has been mated
    public boolean isMated() {
    	// if the king is in check and has no legal escape moves, it is checkmate
        return this.isInCheck() && !hasEscapeMoves();
    }
    
    protected boolean hasEscapeMoves() {
    	for(final Move move : this.legalMoves) {
    		final MoveExecution execution = makeMove(move);
    		if (execution.getMoveStatus().isDone()) {
    			return true;
    		}
    	}
    	return false;
    }
	
	// Method for checking if the game is at a stalemate
    public boolean isStaleMated() {
        return !this.isInCheck() && !hasEscapeMoves();
    }
	
	// Method to determine if a player has castled yet
	// could be useful for AI 
    public boolean hasCastled() {
        return false;
    }
	
	// method for executing a given move and generating the new gameboard
	// based on the move
    public MoveExecution makeMove(final Move move) {
    	// If the move is not legal, return existing board unchanged, set status to ILLEGAL
    	if(!isMoveLegal(move)) {
    		return new MoveExecution(this.board, move, MoveStatus.ILLEGAL_MOVE);
    	}
    	
    	// If it is a potential legal move, create a new board representing 
    	// the new state if the move is executed
    	final Board updatedBoard = move.execute();
    	Collection<Move> kingAttacks;
    	// Create a collection of opponents legal moves that could attack the current player's
    	// King on the updatedBoard
        if (updatedBoard.getCurrentPlayer()
                .getOpponent()
                .getPlayerKing() != null) {
            kingAttacks = Player.findAttacksOnTile(updatedBoard.getCurrentPlayer()
                            .getOpponent()
                            .getPlayerKing()
                            .getLocationOnBoard(),
                    updatedBoard.getCurrentPlayer()
                            .getLegalMoves());
        } else {
            kingAttacks = new ArrayList<>();
        }


    	if(!kingAttacks.isEmpty()) {
    		// If the opponent is attacking the moving players king after executing the move
    		// this is an illegal move as it would put the player in check so
    		// the current board should remain unchanged and move status marked
    		// appropriately 
    		return new MoveExecution(this.board, move, MoveStatus.PLAYER_IN_CHECK);
    	}
    	
    	// If the move is a legal movement for the piece, and does not leave the player in check
    	// we can pass the updated board to represent the move being executed
        return new MoveExecution(updatedBoard, move, MoveStatus.DONE);
    }
    
    public King getPlayerKing() {
    	return this.playersKing;
    }
    
    public Collection<Move> getLegalMoves(){
    	return this.legalMoves;
    }
    public PlayerType getPlayerType() { return this.playerType; }


    // Method to return a random move for a computer player
    public void computerRandomMove(Player computerPlayer) {
        // Get the computers legal moves and lengths
        Collection<Move> computerLegalMoves = computerPlayer.getLegalMoves();
        int movesSize = computerLegalMoves.size();
        Random rand = new Random();
        MoveStatus currentStatus = MoveStatus.ILLEGAL_MOVE;
        MoveExecution me = null;
        Move selectedMoved = null;
        // Until an executable move is found, keep selecting a random move from the potential moves list
        while (currentStatus != MoveStatus.DONE) {
            int randomIndex = rand.nextInt(movesSize);
            selectedMoved = (Move) computerLegalMoves.toArray()[randomIndex];
            me = computerPlayer.makeMove(selectedMoved);
            currentStatus = me.getMoveStatus();
        }
        if (me.getMoveStatus() == MoveStatus.DONE) {
            GameUtilities.updateBoard(selectedMoved);
        }
    }

    public void miniMaxRootMove(int depth, Board board, boolean isMaximisingPlayer) {
        // Set up benchmark tracker
        GameDriver.benchmarkTracker.updateBenchmark(0);

        // Collect possible legal moves, set counter to keep track of their values
        Collection<Move> possibleMoves = this.getLegalMoves();
        double bestMove = -10000;
        Move bestMoveFound = null;

        // Loop through each of the moves
        for(Move move : possibleMoves) {
            Board tempBoard = move.execute();
            double currentCandidateMoveValue = alphaBetaMiniMax(depth - 1, tempBoard, -10000,
                                                      10000, !isMaximisingPlayer);
            if(currentCandidateMoveValue >= bestMove) {
                bestMove = currentCandidateMoveValue;
                if (this.makeMove(move).getMoveStatus() == MoveStatus.DONE) {
                    bestMoveFound = move;
                }
            }
        }
        if (bestMoveFound != null) {
            GameUtilities.updateBoard(bestMoveFound);
        } else {
            computerRandomMove(this);
        }
    }

    private double alphaBetaMiniMax(int depth, Board board, double alpha, double beta, boolean isMaximisingPlayer) {
        // Update benchmark tracker
        BenchmarkTracker tracker = GameDriver.benchmarkTracker;
        tracker.updateBenchmark(tracker.getPositionsCalculated() + 1);

        // If desired search depth is reached, return the pieces heuristics.  Inverting value, as computer is currently
        // always played by black
        if (depth == 0) {
            double testVar = -(board.getCurrentPlayer().getPlayersHeuristic(board));
            return testVar;
        }

        // Get all the possible legal moves for the current player
        Collection<Move> candidateMoves = board.getCurrentPlayer().getLegalMoves();


        if (isMaximisingPlayer) {
            // if maxing player, start best move at arbitrary low value
            double bestMove = -10000;

            // Loop through each of the possible moves
            for (Move move : candidateMoves) {
                // Attempt a move execution to determine if it is valid
                MoveExecution moveAttempt = board.getCurrentPlayer().makeMove(move);
                // If the move can be executed ( i.e. doesn't leave player in check, etc.)
                if (moveAttempt.getMoveStatus() == MoveStatus.DONE) {
                    // Get a representation of the board if the current move were executed
                    Board tempBoard = moveAttempt.getTransitionBoard();
                    // Compare current best found move to minimax value from next depth, select the larger of the two
                    bestMove = Math.max(bestMove, alphaBetaMiniMax(depth  - 1, tempBoard, alpha,
                            beta, false));
                    // Adjust alpha based on best move
                    alpha = Math.max(alpha, bestMove);
                    // if beta < alpha, this node does not need to be searched further
                    if (beta <= alpha) {
                        return bestMove;
                    }
                }
            }
            // return the result
            return bestMove;
        } else {
            // if maxing player, start best move at arbitrary large value
            double bestMove = 10000;

            // Loop through each of the possible moves
            for (Move move : candidateMoves) {
                // Attempt a move execution to determine if it is valid
                MoveExecution moveAttempt = board.getCurrentPlayer().makeMove(move);
                // If the move can be executed ( i.e. doesn't leave player in check, etc.)
                if (moveAttempt.getMoveStatus() == MoveStatus.DONE &&
                !(move instanceof CaptureMove && (move.getAttackedPiece().pieceType == Piece.PieceType.KING))) {
                    // Get a representation of the board if the current move were executed
                    Board tempBoard = moveAttempt.getTransitionBoard();
                    // Compare current best found move to minimax value from next depth, select the smaller of the two
                    bestMove = Math.min(bestMove, alphaBetaMiniMax(depth -1, tempBoard, alpha,
                            beta, true));
                    // Adjust beta based on best move
                    beta = Math.min(beta, bestMove);
                    // if beta < alpha, this node does not need to be searched further
                    if (beta <= alpha) {
                        return bestMove;
                    }
                }
            }
            // return the result
            return bestMove;
        }
    }

	
	// Abstract methods to be overridden in player subclasses
    public abstract double getPlayersHeuristic(Board board);
    public abstract Collection<Piece> getActivePieces();
    public abstract Side getSide();
    public abstract Player getOpponent();
    public abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegalMoves,
                                                             Collection<Move> opponentLegalMoves);
    public enum PlayerType {
        HUMAN {
            @Override
            public boolean isComputer() {
                return false;
            }
        },
        COMPUTER {
            @Override
            public boolean isComputer() {
                return true;
            }
        };

        public abstract boolean isComputer();
    }
}
