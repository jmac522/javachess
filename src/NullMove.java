public class NullMove extends Move {
    protected NullMove() {
        super(null, null, -1);
    }

    @Override
    public Board execute() {
        throw new RuntimeException("Invalid Move!");
    }
}
