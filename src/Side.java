/*
*  Side in an enum to hold WHITE and BLACK as constants to represent a piece's affiliation
*
*
*
* */

public enum Side  {
    WHITE {
        @Override
        public int getMovementDirection() {
            return -1;
        }
    },
    BLACK {
        @Override
        public int getMovementDirection() {
            return 1;
        }
    };

    // Method for getting movement direction based on side (Black is moving in Positive direction, White in Negative)
    public abstract int getMovementDirection();
}
