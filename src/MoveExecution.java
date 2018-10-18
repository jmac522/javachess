// Class to handle executing a selected move and generating the new gameboard
// based on that move
public class MoveExecution {
	// Member fields for the original boardstate prior to executing the move
	// the move to be executed
	// and a MoveStatus enum for keeping track of when the move has been executed
    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveExecution(final Board transitionBoard,
                         final Move move,
                         final MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }
    
    public MoveStatus getMoveStatus() {
    	return this.moveStatus;
    }
}
