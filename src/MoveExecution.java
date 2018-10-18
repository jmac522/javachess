public class MoveExecution {

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
}
