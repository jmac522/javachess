// Class to keep track needed data during the processes of deciding whether to executing a move
// Enum MoveStatus keeps track of if proposed move was successful (DONE),
// illegal move (ILLEGAL_MOVE) or leaves a player in check (PLAYER_IN_CHECK)
// so the move is executed (or not) accordingly 

public class MoveExecution {
	// Member fields for the original boardstate prior to executing the move
	// the move to be executed
	// and a MoveStatus enum for keeping track of when the move has been executed
    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;
	
	// Constructor 
    public MoveExecution(final Board transitionBoard,
                         final Move move,
                         final MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }
    
    // Getter to access a Move's status enum
    public MoveStatus getMoveStatus() {
    	return this.moveStatus;
    }

    public Board getTransitionBoard() {
        return this.transitionBoard;
    }
}
